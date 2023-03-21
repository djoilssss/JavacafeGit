package kr.co.javacafe.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kr.co.javacafe.domain.Inventory;
import kr.co.javacafe.domain.Sales;
import kr.co.javacafe.dto.InventoryDTO;
import kr.co.javacafe.dto.PageRequestDTO;
import kr.co.javacafe.dto.HomePageRequestDTO;
import kr.co.javacafe.dto.PageResponseDTO;
import kr.co.javacafe.dto.HomePageResponseDTO;
import kr.co.javacafe.dto.SalesDTO;

import kr.co.javacafe.repository.InventoryRepository;
import kr.co.javacafe.repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class SalesServiceImpl implements SalesService {
	
	private final ModelMapper modelMapper;
	private final SalesRepository salesRepository;
	
	//매출등록
	@Override
	public Long register(SalesDTO salesDTO) {
		Sales sales = modelMapper.map(salesDTO, Sales.class);
		Long sno = salesRepository.save(sales).getSno();
		return sno;
	}
	
	//조회 상세보기
	@Override
	public SalesDTO readOne(Long sno) {
		Optional<Sales> result = salesRepository.findById(sno);
		Sales sales = result.orElseThrow();
		SalesDTO salesDTO = modelMapper.map(sales, SalesDTO.class);
		return salesDTO;
	}

	@Override
	public void modify(SalesDTO salesDTO) {
		Optional<Sales> result = salesRepository.findById(salesDTO.getSno());
		Sales sales = result.orElseThrow();
		
		sales.change(salesDTO.getYear(),
					 salesDTO.getMonth(),
					 salesDTO.getSales());
		salesRepository.save(sales);
	}

	@Override
	public void remove(Long sno) {
		salesRepository.deleteById(sno);
		
	}

	@Override
	public PageResponseDTO<SalesDTO> list(PageRequestDTO pageRequestDTO) {
			String[] types = pageRequestDTO.getTypes();
	        String keyword = pageRequestDTO.getKeyword();
	        Pageable pageable = pageRequestDTO.getPageable("sno");

	        Page<Sales> result = salesRepository.SsearchAll(types, keyword, pageable);

	        List<SalesDTO> dtoList = result.getContent().stream()
	                .map(sales -> modelMapper.map(sales,SalesDTO.class)).collect(Collectors.toList());


	        return PageResponseDTO.<SalesDTO>withAll()
	                .pageRequestDTO(pageRequestDTO)
	                .dtoList(dtoList)
	                .total((int)result.getTotalElements())
	                .build();

	}


	@Override
	public List<Sales> list() {

		return salesRepository.findAll();
		
		 
	}

	

	

}
