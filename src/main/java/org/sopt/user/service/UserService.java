package org.sopt.user.service;

import java.util.List;

import org.sopt.user.dto.request.UserCreateRequest;
import org.sopt.user.dto.response.UserCreateResponse;
import org.sopt.user.dto.response.UserProfileResponse;
import org.sopt.user.entity.User;
import org.sopt.common.code.ErrorCode;
import org.sopt.common.exception.BaseException;
import org.sopt.common.s3.service.ImageStorageService;
import org.sopt.post.entity.Post;
import org.sopt.post.repository.LikeRepository;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final LikeRepository likeRepository;
	private final ImageStorageService imageStorageService;

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
