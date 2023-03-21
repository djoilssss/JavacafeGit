package kr.co.javacafe.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.co.javacafe.domain.Inventory;
import kr.co.javacafe.domain.Sales;
import kr.co.javacafe.repository.search.InvenSearch;
import kr.co.javacafe.repository.search.SalesSearch;

public interface SalesRepository extends JpaRepository<Sales, Long>, SalesSearch {

	
	
	
	
}

