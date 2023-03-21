package kr.co.javacafe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.javacafe.dto.RecipeStockDTO;
import kr.co.javacafe.dto.RecipeStockListDTO;
import kr.co.javacafe.service.RecipeStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/recipestock")
@Log4j2
@RequiredArgsConstructor
public class RecipeStockController {

	private final RecipeStockService recipeStockService;
	
	@ApiOperation(value = "RecipeStock POST", notes = "POST 방식으로 재료 등록")
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Long> register(@Valid @RequestBody RecipeStockDTO recipeStockDTO, BindingResult bindingResult) throws BindException{
		log.info(recipeStockDTO);
		if(bindingResult.hasErrors()) {
			throw new BindException(bindingResult);
		}
		Map<String, Long> resultMap = new HashMap<>();
		Long rsno = recipeStockService.register(recipeStockDTO);
		resultMap.put("rsno", rsno);
		return resultMap;
	}
	@ApiOperation(value = "RecipeStock POST", notes = "POST 방식으로 중복 확인")
	@PostMapping(value = "/check", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Long> check(@RequestBody RecipeStockDTO recipeStockDTO){
		Map<String, Long> resultMap = new HashMap<>();
		RecipeStockDTO checkDTO = recipeStockService.getDuplicateCheck(recipeStockDTO);
		if(checkDTO!=null) {
			resultMap.put("rsno", checkDTO.getRsno());
		}else {
			resultMap.put("rsno", 0L);
		}
		return resultMap;
	}
	
	@ApiOperation(value = "RecipeStock List", notes = "GET 방식으로 특정 재료 목록 조회")
	@GetMapping(value = "list/{rno}")
	public List<RecipeStockListDTO> getList(@PathVariable("rno") Long rno){
		List<RecipeStockListDTO> dtolist = recipeStockService.getJoinList(rno);
		return dtolist;
	}
	
	@ApiOperation(value = "Read RecipeStock", notes = "GET 방식으로 특정 재료 조회")
	@GetMapping("/{rsno}")
	public RecipeStockDTO getRecipeStockDTO(@PathVariable("rsno") Long rsno) {
		RecipeStockDTO recipeStockDTO = recipeStockService.read(rsno);
		return recipeStockDTO;
	}
	
	@ApiOperation(value = "Delete RecipeStock", notes = "DELETE 방식으로 특정 재료 삭제")
	@DeleteMapping("/{rsno}")
	public Map<String, Long> remove(@PathVariable("rsno") Long rsno){
		recipeStockService.remove(rsno);
		Map<String, Long> resultMap = new HashMap<>();
		resultMap.put("rsno", rsno);
		return resultMap;
	}
	
	@ApiOperation(value = "Update RecipeStock", notes = "PUT 방식으로 특정 재료 수정")
	@PutMapping(value = "/{rsno}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Long> update(@PathVariable("rsno") Long rsno, @RequestBody RecipeStockDTO recipeStockDTO){
		recipeStockDTO.setRsno(rsno);
		recipeStockService.modify(recipeStockDTO);
		Map<String, Long> resultMap = new HashMap<>();
		resultMap.put("rsno", rsno);
		return resultMap;		
	}
}
