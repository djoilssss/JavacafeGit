package kr.co.javacafe.service;


import javax.servlet.http.HttpServletRequest;

import kr.co.javacafe.dto.EventDTO;
import kr.co.javacafe.dto.HomePageRequestDTO;
import kr.co.javacafe.dto.HomePageResponseDTO;
import kr.co.javacafe.dto.PageRequestDTO;
import kr.co.javacafe.dto.PageResponseDTO;


public interface EventService {
	// 작성
	Long register(EventDTO eventDTO, HttpServletRequest request);
	// 조회
	EventDTO readOne(Long eno);
	// 수정
	void modify(EventDTO eventDTO, HttpServletRequest request);
	// 삭제
	void remove(Long eno);
	// 목록 & 검색 기능
	PageResponseDTO<EventDTO> list(PageRequestDTO pageRequestDTO);
	
	//홈페이지용
    HomePageResponseDTO<EventDTO> list2(HomePageRequestDTO pageRequestDTO2);

}
