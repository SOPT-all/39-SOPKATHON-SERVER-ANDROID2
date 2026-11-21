package org.sopt.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Department {
	SALES_TEAM_1("영업1팀");

	private final String label;
}
