package org.sopt.user.repository;

import org.sopt.user.entity.User;
import org.sopt.user.entity.Department;
import org.sopt.user.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	boolean existsByNameAndDepartmentAndPositionAndDescriptionAndProfileImageKey(
		String name,
		Department department,
		Position position,
		String description,
		String profileImageKey
	);
}
