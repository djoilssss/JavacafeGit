package kr.co.javacafe.service;





import kr.co.javacafe.dto.CustomerDTO;
import kr.co.javacafe.dto.PageRequestDTO;
import kr.co.javacafe.dto.PageResponseDTO;

public interface CustomerService {
//	고객 정보 추가
	Long register(CustomerDTO customerDTO);
//	고객정보 상세조회
	CustomerDTO readOne(Long cno);
//	고객 정보 내용 수정
	void modify(CustomerDTO customerDTO);
//	고객 포인트 수정 (적립)
	void modify2(CustomerDTO customerDTO);
//	고객 포인트 수정 (차감)
	void modify3(CustomerDTO customerDTO);

//	고객정보 삭제
	void remove(Long cno);
	
//	전체조회 페이징처리
	PageResponseDTO<CustomerDTO> list(PageRequestDTO pageRequestDTO);
	
	CustomerDTO getFirstByCphone(String cphone);
	
	
}
