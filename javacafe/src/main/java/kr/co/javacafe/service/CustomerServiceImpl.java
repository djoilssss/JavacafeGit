package kr.co.javacafe.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.javacafe.domain.Customer;
import kr.co.javacafe.dto.CustomerDTO;
import kr.co.javacafe.dto.PageRequestDTO;
import kr.co.javacafe.dto.PageResponseDTO;
import kr.co.javacafe.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService{

	
	private final ModelMapper modelMapper;
	private final CustomerRepository customerRepository;
	
//	고객 정보 추가
	@Override
	public Long register(CustomerDTO customerDTO) {
		Customer customer = modelMapper.map(customerDTO, Customer.class);
		Long cno =customerRepository.save(customer).getCno();
		return cno;
	}
//	고객 정보 상세보기
	@Override
	public CustomerDTO readOne(Long cno) {
		Optional<Customer> result = customerRepository.findById(cno);
		Customer customer = result.orElseThrow();
		
		CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
		
		return customerDTO;
	}
//	고객 정보 수정하기
	@Override
	public void modify(CustomerDTO customerDTO) {
		Optional<Customer> result = customerRepository.findById(customerDTO.getCno());
		
		Customer customer = result.orElseThrow();
		
		customer.change(customerDTO.getCname(), customerDTO.getCphone(),customerDTO.getCpoint());		
		customerRepository.save(customer);
		
	}

//	고객 포인트 수정 ( 적립 )
	@Override
	public void modify2(CustomerDTO customerDTO) {
		Optional<Customer> result = customerRepository.findById(customerDTO.getCno());
		
		Customer customer = result.orElseThrow();
		
		customer.change2(customerDTO.getCpoint());
		
		customerRepository.save(customer);
		
	}
	

//	고객 정보 삭제
	@Override
	public void remove(Long cno) {
		customerRepository.deleteById(cno);
		
	}
//	고객 전체 조회
	@Override
	public PageResponseDTO<CustomerDTO> list(PageRequestDTO pageRequestDTO) {
		String[] types = pageRequestDTO.getTypes();
		String keyword = pageRequestDTO.getKeyword();
		Pageable pageable = pageRequestDTO.getPageable("cno");
		
		Page<Customer> result = customerRepository.searchAll(types, keyword, pageable);
		
		List<CustomerDTO> dtoList = result.getContent().stream()
				.map(customer -> modelMapper.map(customer, CustomerDTO.class)).collect(Collectors.toList());
		
		return PageResponseDTO.<CustomerDTO>withAll()
				.pageRequestDTO(pageRequestDTO)
				.dtoList(dtoList)
				.total((int)result.getTotalElements())
				.build();
	}
//	고객 포인트 수정 ( 차감 )
	@Override
	public void modify3(CustomerDTO customerDTO) {
		Optional<Customer> result = customerRepository.findById(customerDTO.getCno());
		
		Customer customer = result.orElseThrow();
		
		customer.change3(customerDTO.getCpoint());
		
		customerRepository.save(customer);
		
	}
	@Override
	public CustomerDTO getFirstByCphone(String cphone) {
		Customer customer = customerRepository.findFirstByCphone(cphone);
		CustomerDTO customerDTO = null;
		if(customer!=null) {
			customerDTO = modelMapper.map(customer, CustomerDTO.class);
		}
		return customerDTO;
	}


}
