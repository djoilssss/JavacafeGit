package kr.co.javacafe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.javacafe.domain.Customer;
import kr.co.javacafe.repository.search.CustomerSearch;

public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerSearch{
	
	Customer findFirstByCphone(String cphone);
	
}
