package org.sopt.post.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.sopt.clothes.entity.Clothes;
import org.sopt.clothes.repository.ClothesRepository;
import org.sopt.common.code.ErrorCode;
import org.sopt.common.exception.BaseException;
import org.sopt.post.dto.request.PostCreateRequest;
import org.sopt.post.dto.response.PostCreateResponse;
import org.sopt.post.entity.Post;
import org.sopt.post.entity.PostClothes;
import org.sopt.post.entity.PostVisibility;
import org.sopt.post.repository.PostClothesRepository;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.entity.User;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PostService {
	private final PostRepository postRepository;
	private final PostClothesRepository postClothesRepository;
	private final UserRepository userRepository;
	private final ClothesRepository clothesRepository;

	@Transactional
	public PostCreateResponse createPost(Long authorId, PostCreateRequest request) {
		// 사용자·대상자·옷 검증부터 게시글과 옷 연결 저장까지 하나의 트랜잭션으로 처리
		User author = userRepository.findById(authorId)
			.orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

		User targetUser = userRepository.findById(request.targetUserId())
			.orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));
		List<Clothes> clothes = findClothes(request.clothesIds());

		Post post = postRepository.save(Post.builder()
			.title(request.title())
			.imageKey(request.imageKey())
			.description(request.description())
			.visibility(request.visibility())
			.author(author)
			.targetUser(targetUser)
			.build());

		// 코디에 포함된 옷과 게시글을 연결 테이블(post_clothes)에 저장
		List<PostClothes> postClothes = clothes.stream()
			.map(item -> new PostClothes(post, item))
			.toList();
		postClothesRepository.saveAll(postClothes);

		return new PostCreateResponse(post.getId());
	}

	private List<Clothes> findClothes(List<Long> clothesIds) {
		// 중복 ID는 제거하고 조회하며, 옷 ID가 하나라도 존재하지 않으면 전체 요청을 실패시킴
		Set<Long> uniqueIds = new LinkedHashSet<>(clothesIds);
		List<Clothes> clothes = clothesRepository.findAllById(uniqueIds);
		if (clothes.size() != uniqueIds.size()) {
			throw new BaseException(ErrorCode.CLOTHES_NOT_FOUND);
		}
		return clothes;
	}
}
