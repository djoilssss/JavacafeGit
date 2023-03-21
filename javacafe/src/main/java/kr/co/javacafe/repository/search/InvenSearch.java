package kr.co.javacafe.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.javacafe.domain.Admin;
import kr.co.javacafe.domain.Inventory;
import kr.co.javacafe.domain.Sales;

public interface InvenSearch {

    Page<Inventory> isearch(Pageable pageable);

    Page<Inventory> isearchAll(String[] types, String keyword, Pageable pageable);

 
}