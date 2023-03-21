package kr.co.javacafe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import kr.co.javacafe.domain.Shop;
import kr.co.javacafe.repository.search.ShopSearch;

public interface ShopRepository extends JpaRepository<Shop, Long>, ShopSearch {

	Page<Shop> findBySid(@Param("sid") String sid, @Param("pageable") Pageable pageable);
	
}
