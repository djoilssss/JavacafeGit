package kr.co.javacafe.controller.rest;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import kr.co.javacafe.dto.RecipeDTO;
import kr.co.javacafe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/reciperest")
@Log4j2
@RequiredArgsConstructor
public class RecipeRestController {

	private final RecipeService recipeService;
	
	@ApiOperation(value = "Recipe Rest GET", notes = "")
	@GetMapping(value = "/cate/{rcate}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RecipeDTO> listByRcate(@PathVariable("rcate") Long rcate){
		List<RecipeDTO> dtolist = recipeService.getByRcate(String.valueOf(rcate));
		return dtolist;
	}
	
	@ApiOperation(value = "Recipe Rest Read GET", notes = "")
	@GetMapping(value = "/{rno}")
	public RecipeDTO readGET(@PathVariable("rno") Long rno) {
		return recipeService.readOne(rno);
	}
	
}
