package kr.co.javacafe.repository;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import kr.co.javacafe.domain.Inventory;
import kr.co.javacafe.domain.Recipe;
import kr.co.javacafe.domain.RecipeStock;
import kr.co.javacafe.dto.RecipeStockDTO;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class RecipeStockRepositoryTest {
 
	@Autowired
	private RecipeStockRepository recipeStockRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	// @Test
	public void inserTest() {
		
		Long rno = 20L;
		Long ino = 10L;
		
		Recipe recipe = Recipe.builder().rno(rno).build();  
		Inventory inventory = Inventory.builder().ino(ino).build();
		
		
		RecipeStock recipeStock = RecipeStock.builder()
				.recipe(recipe)
				.ino(20L)
				.rsnumber("100개")
				.build();

		recipeStockRepository.save(recipeStock);
	}
	
	@Test
	public void duplicateCheckTest() {
		Long rno = 20L;
		Long ino = 10L;
		RecipeStock recipeStock = recipeStockRepository.selectDuplicateCheck(rno, ino);
		log.info(recipeStock);
	}
}
