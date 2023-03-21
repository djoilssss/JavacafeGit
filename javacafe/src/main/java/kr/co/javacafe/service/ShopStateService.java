package kr.co.javacafe.service;

import kr.co.javacafe.dto.BoardDTO;
import kr.co.javacafe.dto.ShopStateDTO;
import kr.co.javacafe.dto.PageRequestDTO;
import kr.co.javacafe.dto.PageResponseDTO;

public interface ShopStateService {

	Long register(ShopStateDTO shopStateDTO);
	
	PageResponseDTO<ShopStateDTO> list(PageRequestDTO pageRequestDTO);
	
	ShopStateDTO readOne(Long ssno);
	
	Long countTodayOrder(String today);
	
	void modify(ShopStateDTO shopStateDTO);
}
