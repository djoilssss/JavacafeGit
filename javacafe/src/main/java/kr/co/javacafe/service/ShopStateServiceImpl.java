package kr.co.javacafe.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.javacafe.domain.Board;
import kr.co.javacafe.domain.Shop;
import kr.co.javacafe.domain.ShopState;
import kr.co.javacafe.dto.BoardDTO;
import kr.co.javacafe.dto.ShopStateDTO;
import kr.co.javacafe.dto.PageRequestDTO;
import kr.co.javacafe.dto.PageResponseDTO;
import kr.co.javacafe.repository.ShopStateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ShopStateServiceImpl implements ShopStateService {

	private final ShopStateRepository shopStateRepository;
	private final ModelMapper modelMapper;

	@Override
	public Long register(ShopStateDTO kioskStateDTO) {
		ShopState kioskState = modelMapper.map(kioskStateDTO, ShopState.class);
		Long ksno = shopStateRepository.save(kioskState).getSsno();
		return ksno;
	}

	@Override
	public PageResponseDTO<ShopStateDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("ssno");

        Page<ShopState> result = shopStateRepository.searchAll(types, keyword, pageable);

        List<ShopStateDTO> dtoList = result.getContent().stream()
                .map(entity -> modelMapper.map(entity,ShopStateDTO.class)).collect(Collectors.toList());

        return PageResponseDTO.<ShopStateDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
	}

	@Override
	public ShopStateDTO readOne(Long ssno) {
		Optional<ShopState> result = shopStateRepository.findById(ssno);
		ShopState shopState = result.orElseThrow();
		ShopStateDTO dto = modelMapper.map(shopState, ShopStateDTO.class);
		return dto;
	}

	@Override
	public Long countTodayOrder(String today) {
		return shopStateRepository.countTodayOrder(today);
	}

	@Override
	public void modify(ShopStateDTO shopStateDTO) {
		Optional<ShopState> result = shopStateRepository.findById(shopStateDTO.getSsno());
		ShopState shopState = result.orElseThrow();
		shopState.cphoneUpdate(shopStateDTO.getCphone());
		shopStateRepository.save(shopState);
	}
	
}
