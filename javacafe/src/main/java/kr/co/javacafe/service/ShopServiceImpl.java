package kr.co.javacafe.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import kr.co.javacafe.domain.Recipe;
import kr.co.javacafe.domain.Shop;
import kr.co.javacafe.dto.RecipeDTO;
import kr.co.javacafe.dto.ShopDTO;
import kr.co.javacafe.dto.ShopJoinDTO;
import kr.co.javacafe.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ShopServiceImpl implements ShopService {

	private final ShopRepository shopRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public Long register(ShopDTO shopDTO) {
		Shop shop = modelMapper.map(shopDTO, Shop.class);
		Long kno = shopRepository.save(shop).getSno();
		return kno;
	}

	@Override
	public List<ShopDTO> getByKid(String sid) {
		Pageable pageable = PageRequest.of(0, 100, Sort.by("sno").descending());
		Page<Shop> entitiylist = shopRepository.findBySid(sid, pageable);
		List<ShopDTO> dtolist = entitiylist.getContent().stream().map(shop -> modelMapper.map(shop, ShopDTO.class)).collect(Collectors.toList());
		return dtolist;
	}

	@Override
	public List<ShopJoinDTO> getByKidJoin(String sid) {
		List<ShopJoinDTO> dtolist = shopRepository.findBySidJoin(sid);
		return dtolist;
	}
	
}
