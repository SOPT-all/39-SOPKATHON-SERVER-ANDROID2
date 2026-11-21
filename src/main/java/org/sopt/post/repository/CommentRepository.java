package org.sopt.post.repository;

import java.util.Collection;
import java.util.List;

import org.sopt.post.entity.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	long countByPostId(Long postId);

	long countByPost_Id(Long postId);

	@EntityGraph(attributePaths = "user")
	List<Comment> findTop3ByPost_IdOrderByCreatedAtDescIdDesc(Long postId);

	@Query("select c.post.id as postId, count(c.id) as totalCount " +
		"from Comment c where c.post.id in :postIds group by c.post.id")
	List<PostCountProjection> countCommentsByPostIds(@Param("postIds") Collection<Long> postIds);
}
