package kr.co.javacafe.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.javacafe.domain.RecipeStock;
import kr.co.javacafe.dto.RecipeStockDTO;
import kr.co.javacafe.dto.RecipeStockListDTO;
import kr.co.javacafe.repository.RecipeStockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class RecipeStockServiceImpl implements RecipeStockService {

	private final RecipeStockRepository recipeStockRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public Long register(RecipeStockDTO recipeStockDTO) {
		log.info(recipeStockDTO);
		log.info("엔티티로 변환");
		RecipeStock recipeStock = modelMapper.map(recipeStockDTO, RecipeStock.class);
		log.info(recipeStock);
		Long rsno = recipeStockRepository.save(recipeStock).getRsno();
		return rsno;
	}

	@Override
	public RecipeStockDTO read(Long rsno) {
		Optional<RecipeStock> recipeStockOptional = recipeStockRepository.findById(rsno);
		RecipeStock recipeStock = recipeStockOptional.orElseThrow();
		return modelMapper.map(recipeStock, RecipeStockDTO.class);
	}

	@Override
	public void modify(RecipeStockDTO recipeStockDTO) {
		Optional<RecipeStock> recipeStockOptional = recipeStockRepository.findById(recipeStockDTO.getRsno());
		RecipeStock recipeStock = recipeStockOptional.orElseThrow();
		recipeStock.changeNumber(recipeStockDTO.getRsnumber());
		recipeStockRepository.save(recipeStock);
	}

	@Override
	public void remove(Long rsno) {
		recipeStockRepository.deleteById(rsno);
	}

	@Override
	public List<RecipeStockDTO> getAll(Long rno) {
		List<RecipeStockDTO> dtolist = recipeStockRepository.listOfBoard(rno).stream()
				.map(entity -> modelMapper.map(entity, RecipeStockDTO.class))
				.collect(Collectors.toList());
		return dtolist;
	}

	@Override
	public List<RecipeStockListDTO> getJoinList(Long rno) {
		List<RecipeStockListDTO> dtolist = recipeStockRepository.selectJoinList(rno);
		return dtolist;
	}

	@Override
	public RecipeStockDTO getDuplicateCheck(RecipeStockDTO recipeStockDTO) {
		RecipeStock recipeStock = null;
		RecipeStockDTO dto = null;
		recipeStock = recipeStockRepository.selectDuplicateCheck(recipeStockDTO.getRno(), recipeStockDTO.getIno());
		if(recipeStock != null) {
			dto = modelMapper.map(recipeStock, RecipeStockDTO.class);
		}
		return dto;
	}
	
}
