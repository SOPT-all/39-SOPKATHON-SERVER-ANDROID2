package org.sopt.post.repository;

import java.util.List;

import org.sopt.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, FeedPostRepository {
	long countByAuthorId(Long authorId);

	long countByTargetUserId(Long targetUserId);

	List<Post> findByTargetUserIdOrderByCreatedAtDesc(Long targetUserId);
}
