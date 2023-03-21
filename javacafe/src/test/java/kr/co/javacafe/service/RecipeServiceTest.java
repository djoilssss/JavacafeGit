package kr.co.javacafe.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.javacafe.dto.PageRequestDTO;
import kr.co.javacafe.dto.PageResponseDTO;
import kr.co.javacafe.dto.RecipeDTO;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class RecipeServiceTest {

	@Autowired
	private RecipeService recipeService;
	
	// @Test
	public void testRegister() {
		log.info(recipeService.getClass().getName());
		RecipeDTO recipeDTO = RecipeDTO.builder()
				.rcate("0")
				.rname("서비스 테스트")
				.reng("Service Test")
				.rdesc("테스트 소개글")
				.rtext("테스트 레시피")
				.rcost(1000)
				.rprice(3000)
				.rkcal(100)
				.rimg("/img")
				.rstate(1)
				.build();
		// Long rno = recipeService.register(recipeDTO);
		// log.info("rno : "+rno);
	}
	
	// @Test
	public void testModifty() {
		RecipeDTO recipeDTO = RecipeDTO.builder()
				.rno(12L)
				.rname("Modify 서비스 테스트")
				.reng("Modify Service Test")
				.rdesc("Modify Desc 서비스 테스트")
				.rtext("Modify Text 서비스 테스트")
				.build();
		// recipeService.modify(recipeDTO);
	}
	
	// @Test
	public void testList() {
		PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
				.type("n")
				.keyword("1")
				.page(1)
				.size(10)
				.build();
		PageResponseDTO<RecipeDTO> responseDTO = recipeService.list(pageRequestDTO);
		log.info(responseDTO);
	}
	
	@Test
	public void getByRcateTest() {
		String rcate = "0";
		List<RecipeDTO> dtolist = recipeService.getByRcate(rcate);
		dtolist.forEach(dto -> log.info(dto));
	}
	
	
}
