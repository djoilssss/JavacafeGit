package kr.co.javacafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import kr.co.javacafe.domain.Event;
import kr.co.javacafe.repository.search.EventSearch;

public interface EventRepository extends JpaRepository<Event, Long>, EventSearch {

	@Query(value = "select now()", nativeQuery = true)

	String gettime();
}
