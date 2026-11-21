package org.sopt.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Position {
	GENERAL_MANAGER("부장"),
	DEPUTY_GENERAL_MANAGER("차장"),
	MANAGER("과장"),
	ASSISTANT_MANAGER("대리"),
	STAFF("사원");

	private final String label;
}
