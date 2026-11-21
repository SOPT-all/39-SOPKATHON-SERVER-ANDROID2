package org.sopt.post.repository;

import java.util.Collection;
import java.util.List;

import org.sopt.post.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // 상세 조회에서 현재 사용자가 좋아요를 눌렀는지 확인
    boolean existsByUser_IdAndPost_Id(Long userId, Long postId);

    // 좋아요 API에서 사용하는 동일한 확인 메서드
    boolean existsByUserIdAndPostId(Long userId, Long postId);

	@Query("select l.post.id from Like l " +
		"where l.user.id = :userId and l.post.id in :postIds")
	List<Long> findLikedPostIds(@Param("userId") Long userId, @Param("postIds") Collection<Long> postIds);

	@Modifying
	@Query("delete from Like l where l.user.id = :userId and l.post.id = :postId")
	int deleteByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);
}
    long countByPostId(Long postId);

    @Query("select l.post.id as postId, count(l.id) as totalCount " +
        "from Like l where l.post.id in :postIds group by l.post.id")
    List<PostCountProjection> countLikesByPostIds(@Param("postIds") Collection<Long> postIds);

    @Query("select l.post.id from Like l " +
        "where l.user.id = :userId and l.post.id in :postIds")
    List<Long> findLikedPostIds(
        @Param("userId") Long userId,
        @Param("postIds") Collection<Long> postIds
    );
}
