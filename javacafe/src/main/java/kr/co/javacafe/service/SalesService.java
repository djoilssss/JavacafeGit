package kr.co.javacafe.service;


import kr.co.javacafe.dto.PageRequestDTO;

import java.util.List;

import org.springframework.data.domain.Pageable;

import kr.co.javacafe.domain.Sales;
import kr.co.javacafe.dto.HomePageRequestDTO;
import kr.co.javacafe.dto.PageResponseDTO;
import kr.co.javacafe.dto.HomePageResponseDTO;
import kr.co.javacafe.dto.SalesDTO;


public interface SalesService {
	//등록
    Long register(SalesDTO salesDTO);    
    //조회
    SalesDTO readOne(Long sno);    
    //수정
    void modify(SalesDTO salesDTO);    
    //삭제
    void remove(Long sno);    
    
    //검색 / 리스트처리
    PageResponseDTO<SalesDTO> list(PageRequestDTO pageRequestDTO);
    
    List<Sales> list();
    
}
