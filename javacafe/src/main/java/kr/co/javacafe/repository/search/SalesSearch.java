package kr.co.javacafe.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.javacafe.domain.Sales;

public interface SalesSearch {

    Page<Sales> Ssearch(Pageable pageable);
    
    Page<Sales> SsearchAll(String[] types, String keyword, Pageable pageable);
}