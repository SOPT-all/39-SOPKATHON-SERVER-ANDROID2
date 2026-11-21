package org.sopt.post.repository;

import org.sopt.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, FeedPostRepository {
}
