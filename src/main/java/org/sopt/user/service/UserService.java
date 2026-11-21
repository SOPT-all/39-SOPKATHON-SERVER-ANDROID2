package org.sopt.user.service;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.sopt.user.dto.request.UserCreateRequest;
import org.sopt.user.dto.response.UserCreateResponse;
import org.sopt.user.dto.response.UserProfileResponse;
import org.sopt.user.dto.response.UserSearchResponse;
import org.sopt.user.entity.Department;
import org.sopt.user.entity.Position;
import org.sopt.user.entity.User;
import org.sopt.common.code.ErrorCode;
import org.sopt.common.exception.BaseException;
import org.sopt.common.s3.service.ImageStorageService;
import org.sopt.post.entity.Post;
import org.sopt.post.repository.LikeRepository;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.repository.UserRepository;
import org.sopt.user.repository.UserSearchCursor;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private static final int MAX_SEARCH_LIMIT = 50;

	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final LikeRepository likeRepository;
	private final ImageStorageService imageStorageService;

	@Transactional(readOnly = true)
	public UserSearchResponse searchUsers(
		String keyword,
		String position,
		String department,
		int limit,
		String rawCursor
	) {
		if (limit < 1 || limit > MAX_SEARCH_LIMIT) {
			throw new BaseException(ErrorCode.INVALID_INPUT_VALUE);
		}

		Position positionFilter = findPosition(position);
		Department departmentFilter = findDepartment(department);
		UserSearchCursor cursor = rawCursor == null ? null : UserSearchCursor.decode(rawCursor);
		String searchTerm = keyword == null ? null : keyword.trim();
		List<Position> matchingPositions = searchTerm == null || searchTerm.isEmpty()
			? List.of()
			: Arrays.stream(Position.values())
				.filter(value -> value.getLabel().contains(searchTerm))
				.toList();

		Specification<User> specification = (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (cursor != null) {
				predicates.add(cb.greaterThan(root.get("id"), cursor.userId()));
			}
			if (positionFilter != null) {
				predicates.add(cb.equal(root.get("position"), positionFilter));
			}
			if (departmentFilter != null) {
				predicates.add(cb.equal(root.get("department"), departmentFilter));
			}
			if (searchTerm != null && !searchTerm.isEmpty()) {
				String escaped = searchTerm.replace("\\", "\\\\")
					.replace("%", "\\%")
					.replace("_", "\\_");
				var nameMatch = cb.like(root.get("name"), "%" + escaped + "%", '\\');
				predicates.add(matchingPositions.isEmpty()
					? nameMatch
					: cb.or(nameMatch, root.get("position").in(matchingPositions)));
			}
			return cb.and(predicates.toArray(Predicate[]::new));
		};

		List<User> result = userRepository.findAll(
			specification,
			PageRequest.of(0, limit + 1, Sort.by("id").ascending())
		).getContent();
		boolean hasMore = result.size() > limit;
		List<User> page = hasMore ? result.subList(0, limit) : result;
		List<UserSearchResponse.UserItem> items = page.stream()
			.map(user -> new UserSearchResponse.UserItem(
				user.getId(), user.getName(), user.getPosition().getLabel(),
				user.getDepartment().getLabel(), toImageUrl(user.getProfileImageKey())
			))
			.toList();
		String nextCursor = hasMore ? UserSearchCursor.encode(page.get(page.size() - 1).getId()) : null;
		return new UserSearchResponse(items, nextCursor, hasMore);
	}

	private Position findPosition(String label) {
		if (label == null) {
			return null;
		}
		return Arrays.stream(Position.values())
			.filter(value -> value.getLabel().equals(label))
			.findFirst()
			.orElseThrow(() -> new BaseException(ErrorCode.INVALID_INPUT_VALUE));
	}

	private Department findDepartment(String label) {
		if (label == null) {
			return null;
		}
		return Arrays.stream(Department.values())
			.filter(value -> value.getLabel().equals(label))
			.findFirst()
			.orElseThrow(() -> new BaseException(ErrorCode.INVALID_INPUT_VALUE));
	}

	@Transactional
	public UserCreateResponse createUser(UserCreateRequest request) {
		if (userRepository.existsByNameAndDepartmentAndPositionAndDescriptionAndProfileImageKey(
			request.name(),
			request.department(),
			request.position(),
			request.description(),
			request.profileImageKey()
		)) {
			throw new BaseException(ErrorCode.USER_ALREADY_EXISTS);
		}

		User user = User.builder()
			.name(request.name())
			.department(request.department())
			.position(request.position())
			.description(request.description())
			.profileImageKey(request.profileImageKey())
			.build();

		User savedUser = userRepository.save(user);
		return new UserCreateResponse(savedUser.getId());
	}

	@Transactional(readOnly = true)
	public UserProfileResponse getUserProfile(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

		List<UserProfileResponse.ReceivedOutfitResponse> receivedOutfits = postRepository
			.findByTargetUserIdOrderByCreatedAtDesc(userId)
			.stream()
			.map(this::toReceivedOutfitResponse)
			.toList();
		int receivedLikeCount = receivedOutfits.stream()
			.mapToInt(UserProfileResponse.ReceivedOutfitResponse::likeCount)
			.sum();

		return new UserProfileResponse(
			user.getId(),
			user.getName(),
			user.getPosition().getLabel(),
			user.getDepartment().getLabel(),
			toImageUrl(user.getProfileImageKey()),
			postRepository.countByAuthorId(userId),
			postRepository.countByTargetUserId(userId),
			receivedLikeCount,
			receivedOutfits
		);
	}

	private UserProfileResponse.ReceivedOutfitResponse toReceivedOutfitResponse(Post post) {
		return new UserProfileResponse.ReceivedOutfitResponse(
			post.getId(),
			post.getTitle(),
			toImageUrl(post.getImageKey()),
			(int) likeRepository.countByPostId(post.getId())
		);
	}

	private String toImageUrl(String imageKey) {
		return imageKey == null ? null : imageStorageService.getImageUrl(imageKey);
	}
}
