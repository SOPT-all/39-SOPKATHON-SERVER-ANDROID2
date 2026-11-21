package org.sopt.post.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sopt.post.entity.FeedSort;
import org.sopt.post.entity.Like;
import org.sopt.post.entity.Post;
import org.sopt.post.entity.PostVisibility;
import org.sopt.user.entity.Department;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FeedPostRepositoryImpl implements FeedPostRepository {
	private final EntityManager entityManager;

	@Override
	public List<Post> findVisiblePosts(
		Long viewerId,
		Department viewerDepartment,
		FeedSort sort,
		LocalDateTime popularSince,
		FeedCursor cursor,
		int maxResults
	) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Post> query = cb.createQuery(Post.class);
		Root<Post> post = query.from(Post.class);

		// 응답에 필요한 작성자·대상자를 함께 가져오기 -> N+1 막기
		post.fetch("author", JoinType.INNER);
		post.fetch("targetUser", JoinType.INNER);

		List<Predicate> predicates = new ArrayList<>();
		predicates.add(visibleTo(cb, post, viewerId, viewerDepartment));
		if (sort == FeedSort.POPULAR) {
			predicates.add(cb.greaterThanOrEqualTo(post.<LocalDateTime>get("createdAt"), popularSince));
		}

		Expression<Long> likeCount = likeCountExpression(query, cb, post);
		if (cursor != null) {
			predicates.add(cursorPredicate(cb, post, likeCount, cursor));
		}

		query.select(post).distinct(true).where(predicates.toArray(Predicate[]::new));
		if (sort == FeedSort.POPULAR) {
			query.orderBy(cb.desc(likeCount), cb.desc(post.<LocalDateTime>get("createdAt")), cb.desc(post.<Long>get("id")));
		} else {
			query.orderBy(cb.desc(post.<LocalDateTime>get("createdAt")), cb.desc(post.<Long>get("id")));
		}

		return entityManager.createQuery(query).setMaxResults(maxResults).getResultList();
	}

	private Predicate visibleTo(CriteriaBuilder cb, Root<Post> post, Long viewerId, Department viewerDepartment) {
		return cb.or(
			cb.equal(post.get("visibility"), PostVisibility.PUBLIC),
			cb.and(
				cb.equal(post.get("visibility"), PostVisibility.DEPARTMENT),
				cb.equal(post.get("author").get("department"), viewerDepartment)
			),
			cb.and(
				cb.equal(post.get("visibility"), PostVisibility.PRIVATE),
				cb.equal(post.get("targetUser").get("id"), viewerId)
			)
		);
	}

	private Expression<Long> likeCountExpression(CriteriaQuery<?> query, CriteriaBuilder cb, Root<Post> post) {
		Subquery<Long> subquery = query.subquery(Long.class);
		Root<Like> like = subquery.from(Like.class);
		subquery.select(cb.count(like.get("id")));
		subquery.where(cb.equal(like.get("post").get("id"), post.<Long>get("id")));
		return subquery;
	}

	private Predicate cursorPredicate(
		CriteriaBuilder cb,
		Root<Post> post,
		Expression<Long> likeCount,
		FeedCursor cursor
	) {
		Predicate olderPost = cb.or(
			cb.lessThan(post.<LocalDateTime>get("createdAt"), cursor.createdAt()),
			cb.and(
				cb.equal(post.<LocalDateTime>get("createdAt"), cursor.createdAt()),
				cb.lessThan(post.<Long>get("id"), cursor.postId())
			)
		);

		if (cursor.sort() == FeedSort.LATEST) {
			return olderPost;
		}

		return cb.or(
			cb.lessThan(likeCount, cursor.likeCount()),
			cb.and(cb.equal(likeCount, cursor.likeCount()), olderPost)
		);
	}
}
