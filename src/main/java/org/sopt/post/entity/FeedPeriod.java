package org.sopt.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FeedPeriod {
	WEEK(7);

	private final int days;
}
