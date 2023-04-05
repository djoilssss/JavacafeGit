package kr.co.javacafe.repository.search;

import java.util.List;

import kr.co.javacafe.dto.ShopJoinDTO;

public interface ShopSearch {

	List<ShopJoinDTO> findBySidJoin(String sid);
	
	List<ShopJoinDTO> findBest();
}
