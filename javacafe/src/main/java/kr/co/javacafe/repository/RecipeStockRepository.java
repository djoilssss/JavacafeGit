package kr.co.javacafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.co.javacafe.domain.RecipeStock;
import kr.co.javacafe.repository.search.RecipeStockSearch;

public interface RecipeStockRepository extends JpaRepository<RecipeStock, Long>, RecipeStockSearch {

	@Query("select rs from RecipeStock rs where rs.recipe.rno = :rno")
	List<RecipeStock> listOfBoard(@Param("rno") Long rno);
	
	@Query("select rs from RecipeStock rs where rs.recipe.rno = :rno and rs.ino =:ino")
	RecipeStock selectDuplicateCheck(@Param("rno") Long rno, @Param("ino") Long ino);
}
