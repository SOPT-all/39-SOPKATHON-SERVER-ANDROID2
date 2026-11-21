package org.sopt.post.repository;

import java.util.Collection;
import java.util.List;

import org.sopt.post.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {

	//long countByPost_Id(Long postId);

	boolean existsByUser_IdAndPost_Id(Long userId, Long postId);
	long countByPostId(Long postId);

	@Query("select l.post.id as postId, count(l.id) as totalCount " +
		"from Like l where l.post.id in :postIds group by l.post.id")
	List<PostCountProjection> countLikesByPostIds(@Param("postIds") Collection<Long> postIds);

	@Query("select l.post.id from Like l " +
		"where l.user.id = :userId and l.post.id in :postIds")
	List<Long> findLikedPostIds(@Param("userId") Long userId, @Param("postIds") Collection<Long> postIds);
}
