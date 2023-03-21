package kr.co.javacafe.repository.search;

import java.util.List;

import kr.co.javacafe.domain.RecipeStock;
import kr.co.javacafe.dto.RecipeStockListDTO;

public interface RecipeStockSearch {
	
	List<RecipeStockListDTO> selectJoinList(Long rno);
}
