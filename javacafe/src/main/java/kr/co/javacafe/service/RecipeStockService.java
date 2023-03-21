package kr.co.javacafe.service;

import java.util.List;

import kr.co.javacafe.dto.RecipeStockDTO;
import kr.co.javacafe.dto.RecipeStockListDTO;

public interface RecipeStockService {
	
	Long register(RecipeStockDTO recipeStockDTO);
	
	RecipeStockDTO read(Long rsno);
	
	void modify(RecipeStockDTO recipeStockDTO);
	
	void remove(Long rsno);
	
	List<RecipeStockDTO> getAll(Long rno);
	List<RecipeStockListDTO> getJoinList(Long rno);
	
	// 중복 확인
	RecipeStockDTO getDuplicateCheck(RecipeStockDTO recipeStockDTO);

}
