package kr.co.javacafe.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.javacafe.domain.Recipe;

public interface RecipeSearch {

		Page<Recipe> search(Pageable pageable); // 테스트
		Page<Recipe> searchAll(String[] types, String keyword, String category, String[] states, Pageable pageable);
		
}
