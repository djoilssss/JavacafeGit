package kr.co.javacafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.javacafe.domain.ShopState;
import kr.co.javacafe.repository.search.ShopStateSearch;

public interface ShopStateRepository extends JpaRepository<ShopState, Long>, ShopStateSearch {

}
