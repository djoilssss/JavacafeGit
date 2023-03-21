package kr.co.javacafe.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.javacafe.dto.RecipeStockDTO;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class RecipeStockServiceTest {

	@Autowired
	private RecipeStockService recipeStockService;
	
	// @Test
	public void registerTest() {
		log.info(recipeStockService);
		RecipeStockDTO recipeStockDTO = RecipeStockDTO.builder()
				.rsnumber("200개")
				.rno(20L)
				.ino(10L)
				.build();
		recipeStockService.register(recipeStockDTO);
	}
	
	@Test
	public void duplicateCheckTest() {
		RecipeStockDTO recipeStockDTO = RecipeStockDTO.builder()
				.rno(20L)
				.ino(10L)
				.build();
		RecipeStockDTO resultDTO = recipeStockService.getDuplicateCheck(recipeStockDTO);
		log.info(resultDTO);
	}
	
}
