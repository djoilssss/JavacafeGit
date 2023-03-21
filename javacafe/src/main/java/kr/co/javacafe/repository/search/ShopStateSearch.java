package kr.co.javacafe.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.javacafe.domain.ShopState;

public interface ShopStateSearch {

	Page<ShopState> searchAll(String[] types, String keyword, Pageable pageable);
	
	Long countTodayOrder(String today);
}
