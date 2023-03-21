package kr.co.javacafe.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import kr.co.javacafe.domain.Board;


public interface BoardSearch {

	Page<Board> search1(Pageable pageable);
	
	
	Page<Board> searchAll(String [] types, String keyword, Pageable pageable);
	
}
