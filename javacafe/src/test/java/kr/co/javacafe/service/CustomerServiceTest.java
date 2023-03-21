package kr.co.javacafe.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.javacafe.dto.CustomerDTO;
import kr.co.javacafe.dto.PageRequestDTO;
import kr.co.javacafe.dto.PageResponseDTO;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class CustomerServiceTest {
	
	@Autowired
	private CustomerService customerService;

	
	// @Test
	public void RegisterTest() {
		log.info(customerService.getClass().getName());
		CustomerDTO customerDTO = CustomerDTO.builder()
				.cname("이순신")
				.cphone("010-1111-1111")
				.cpoint((long) 3500)
				.build();
		Long cno = customerService.register(customerDTO);
		log.info(cno);	
	}
	// @Test
	public void ModifyTest() {
		CustomerDTO customerDTO = CustomerDTO.builder()
				.cno(11L)
				.cname("유관순")
				.cphone("010-2222-2222")
				.cpoint((long) 82500)
				.build();
		customerService.modify(customerDTO);
		log.info(customerDTO);
	
	}
	
	// @Test
	public void ListTest() {
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
				.type("n")
				.keyword("1")
				.page(1)
				.size(10)
				.build();
		PageResponseDTO<CustomerDTO> responseDTO = customerService.list(pageRequestDTO);
		log.info(responseDTO);
	}
	
	@Test
	public void getFirstByCphoneTest() {
		log.info(customerService.getFirstByCphone("010-1111-1111"));
	}
	
	
	
	
}
