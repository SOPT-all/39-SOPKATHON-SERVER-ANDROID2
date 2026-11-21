package org.sopt.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Department {
	SALES_1("영업1팀"),
	SALES_2("영업2팀"),
	MARKETING("마케팅팀"),
	HR("인사팀"),
	DEVELOPMENT("개발팀"),
	DESIGN("디자인팀");

	private final String label;
}
