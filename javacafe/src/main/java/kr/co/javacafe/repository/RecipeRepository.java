package kr.co.javacafe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.co.javacafe.domain.Recipe;
import kr.co.javacafe.repository.search.RecipeSearch;

public interface RecipeRepository extends JpaRepository<Recipe, Long>, RecipeSearch {

	@Query(value = "select now()", nativeQuery = true)
	String getTime();
	
	// findBy필드명으로 따로 쿼리 없이 메서드 생성 가능
	Page<Recipe> findByRcate(@Param("rcate") String rcate, @Param("pageable") Pageable pageable);
}
