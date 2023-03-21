package kr.co.javacafe.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import kr.co.javacafe.domain.Customer;
import kr.co.javacafe.domain.Recipe;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;
	
	
//	고객 정보 추가 테스트
	//@Test
	public void InsertTest() {
		IntStream.rangeClosed(101, 200).forEach(i -> {
			Customer customer = Customer.builder()
					.cname("강감찬")
					.cphone("017-576-3122")
					.cpoint((long) 0)
					.build();
			Customer result = customerRepository.save(customer);
			log.info("cno : " + result.getCno());	
		});
	}
//	고객 정보 조회 테스트
	//@Test
	public void SelectTest() {
		Long cno = 10L;
		Optional<Customer> result = customerRepository.findById(cno);
		Customer customer = result.orElseThrow();
		log.info("고객 정보 선택 조회 :" + customer);
		
	}
//	고객 정보 수정 테스트
	//@Test
	public void UpdateTest() {
		Long cno = 10L;
		Optional<Customer> result = customerRepository.findById(cno);
		Customer customer = result.orElseThrow();
		customer.change("서궁성","010-6666-6666",(long) 250000);
		customerRepository.save(customer);
		log.info("수정된 고객 정보 : " + customer);
	}
//	고객 정보 삭제 테스트
	//@Test
	public void DeleteTest() {
		Long cno = 10L;
		customerRepository.deleteById(cno);
		log.info("삭제된 고객 번호 :" + cno);
		
	}
//	페이징 테스트
	//@Test
	public void PagingTest() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("cno").descending());
		Page<Customer> result = customerRepository.findAll(pageable);
		log.info("total count : "+result.getTotalElements());
		log.info("total pages : "+result.getTotalPages());
		log.info("page number : "+result.getNumber());
		log.info("page size : "+result.getSize());
		List<Customer> datalist = result.getContent();
		datalist.forEach(data -> log.info(data));
		
	}
//	단일 검색 테스트
	//@Test
	public void SearchTest() {
		Pageable pagealbe = PageRequest.of(1, 10,Sort.by("cno").descending());
		customerRepository.search(pagealbe);
	}
//	다중 검색 테스트
	//@Test
	public void SearchAllTest() {
		String[] types = {"cname", "cphone"};
		String keyword = "1";
		Pageable pageable = PageRequest.of(0, 10, Sort.by("cno").descending());
		Page<Customer> result = customerRepository.searchAll(types, keyword, pageable);
	
		
		log.info(result.getTotalPages());
		log.info(result.getSize());
		log.info(result.getNumber());
		log.info(result.hasPrevious()+" : "+result.hasNext());
		result.getContent().forEach(data -> log.info(data));
	}
	
	@Test
	public void findByCphoneTest() {
		
		//List<Customer> entitylist = customerRepository.findByCphone("010-1111-1111");
		//entitylist.forEach(entity -> log.info(entity));
		log.info(customerRepository.findFirstByCphone("010-1111-1111"));
		
	}
	
	
	
		
}
