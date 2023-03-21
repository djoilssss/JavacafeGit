package kr.co.javacafe.service;

import java.util.List;

import kr.co.javacafe.dto.ShopDTO;
import kr.co.javacafe.dto.ShopJoinDTO;

public interface ShopService {

	Long register(ShopDTO kioskDTO);
	
	// 주문 번호로 주문 레코드 불러오기
	List<ShopDTO> getByKid(String sid);
	
	// 주문 번호로 주문 레코드 불러오기 + 메뉴 레프트 조인
	List<ShopJoinDTO> getByKidJoin(String sid);
}
