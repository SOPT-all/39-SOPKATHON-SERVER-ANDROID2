package org.sopt.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.post.dto.response.PostLikeResponse;
import org.sopt.post.entity.Like;
import org.sopt.post.entity.Post;
import org.sopt.post.exception.AlreadyLikedException;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.post.exception.UserNotFoundException;
import org.sopt.post.repository.LikeRepository;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.entity.User;
import org.sopt.user.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final LikeRepository likeRepository;

	@Transactional
	public PostLikeResponse likePost(Long userId, Long postId) {
		User user = userRepository.findById(userId)
			.orElseThrow(UserNotFoundException::new);
		Post post = postRepository.findById(postId)
			.orElseThrow(PostNotFoundException::new);

		if (likeRepository.existsByUserIdAndPostId(userId, postId)) {
			throw new AlreadyLikedException();
		}

		try {
			likeRepository.saveAndFlush(new Like(user, post));
		} catch (DataIntegrityViolationException exception) {
			throw new AlreadyLikedException();
		}

		return new PostLikeResponse(postId, true, likeRepository.countByPostId(postId));
	}
}
