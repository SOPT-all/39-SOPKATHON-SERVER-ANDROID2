package org.sopt.clothes.repository;

import java.util.List;

import org.sopt.clothes.entity.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClothesRepository extends JpaRepository<Clothes, Long> {
	List<Clothes> findAllByOrderByIdDesc();
}
