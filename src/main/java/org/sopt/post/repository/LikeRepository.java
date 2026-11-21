package org.sopt.post.repository;

import org.sopt.post.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
	long countByPostId(Long postId);
}
