package kr.co.javacafe.service;

import kr.co.javacafe.domain.Admin;
import kr.co.javacafe.dto.AdminDTO;
import kr.co.javacafe.dto.AdminJoinDTO;
import kr.co.javacafe.dto.SalesDTO;
 

public interface AdminService {
	static class MidExistException extends Exception{
	}
	void join(AdminJoinDTO joinDTO)throws MidExistException;
	
    //수정
	void modify(AdminJoinDTO adminJoinDTO);
    //삭제
    void remove(String id);        
    //조회
    AdminJoinDTO readOne(String id); 
}
