package org.sopt.post.repository;

import java.util.Collection;
import java.util.List;

import org.sopt.post.entity.PostClothes;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostClothesRepository extends JpaRepository<PostClothes, Long> {
	@EntityGraph(attributePaths = {"post", "clothes"})
	List<PostClothes> findAllByPost_IdIn(Collection<Long> postIds);
}
