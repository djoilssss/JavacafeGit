package kr.co.javacafe.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import kr.co.javacafe.domain.Event;

public interface EventSearch {

	Page<Event> search1(Pageable pageable);
	
	
	Page<Event> searchAll(String [] types, String keyword, Pageable pageable);
	
}
