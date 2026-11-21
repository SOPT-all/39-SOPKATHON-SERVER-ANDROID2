package org.sopt.user.service;

import org.sopt.user.dto.request.UserCreateRequest;
import org.sopt.user.dto.response.UserCreateResponse;
import org.sopt.user.entity.User;
import org.sopt.common.code.ErrorCode;
import org.sopt.common.exception.BaseException;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public UserCreateResponse createUser(UserCreateRequest request) {
		if (userRepository.existsByNameAndDepartmentAndPositionAndDescriptionAndProfileImageKey(
			request.name(),
			request.department(),
			request.position(),
			request.description(),
			request.profileImageKey()
		)) {
			throw new BaseException(ErrorCode.USER_ALREADY_EXISTS);
		}

		User user = User.builder()
			.name(request.name())
			.department(request.department())
			.position(request.position())
			.description(request.description())
			.profileImageKey(request.profileImageKey())
			.build();

		User savedUser = userRepository.save(user);
		return new UserCreateResponse(savedUser.getId());
	}
}
