package org.sopt.post.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.sopt.post.entity.FeedSort;
import org.sopt.post.entity.Post;
import org.sopt.user.entity.Department;

public interface FeedPostRepository{
	List<Post> findVisiblePosts(
		Long viewerId,
		Department viewerDepartment,
		FeedSort sort,
		LocalDateTime popularSince,
		FeedCursor cursor,
		int maxResults
	);
}
