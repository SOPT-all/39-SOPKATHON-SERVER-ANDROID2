package org.sopt.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Position {
	STAFF("사원"),
	ASSOCIATE("주임"),
	ASSISTANT_MANAGER("대리"),
	MANAGER("과장"),
	DEPUTY_GENERAL_MANAGER("차장"),
	GENERAL_MANAGER("부장"),
	DIRECTOR("이사");

	private final String label;
}
