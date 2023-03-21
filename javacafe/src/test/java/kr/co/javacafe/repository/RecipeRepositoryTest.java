package kr.co.javacafe.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import kr.co.javacafe.domain.Recipe;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class RecipeRepositoryTest {

		@Autowired
		private RecipeRepository recipeRepository;
		
		// @Test
		public void testInsert() {
			IntStream.rangeClosed(1, 30).forEach(i -> {
				Recipe recipe = Recipe.builder()
						.rcate("0")
						.rname("카페 메뉴 "+i)
						.reng("Cafe Menu "+i)
						.rdesc("테스트 소개글")
						.rtext("테스트 레시피")
						.rcost(1000)
						.rprice(3000)
						.rkcal(100)
						.rimg("/img")
						.rstate(1)
						.build();
				Recipe result = recipeRepository.save(recipe);
				log.info("rno : "+result.getRno());
			});
		}
		
		// @Test
		public void testSelect() {
			Long rno = 10L;
			Optional<Recipe> result = recipeRepository.findById(rno);
			Recipe recipe = result.orElseThrow();
			log.info(recipe);
		}
		
		// @Test
		public void testUpdate() {
			Long rno = 10L;
			Optional<Recipe> result = recipeRepository.findById(rno);
			Recipe recipe = result.orElseThrow();
			recipe.change("Update rname", "Update reng", "Update rdesc", "Update rtext", null, 0, 0, 0, 0, null);
			recipeRepository.save(recipe);
		}
		
		// @Test
		public void testDelete() {
			Long rno = 1L;
			recipeRepository.deleteById(rno);
		}
		
		// @Test 
		public void testPaging() {
			Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());
			Page<Recipe> result = recipeRepository.findAll(pageable);
			log.info("total count : "+result.getTotalElements());
			log.info("total pages : "+result.getTotalPages());
			log.info("page number : "+result.getNumber());
			log.info("page size : "+result.getSize());
			List<Recipe> datalist = result.getContent();
			datalist.forEach(data -> log.info(data));
		}
		
		// @Test
		public void testSearch() {
			Pageable pageable = PageRequest.of(1, 10, Sort.by("rno").descending());
			recipeRepository.search(pageable);
		}
		
		//@Test
		public void testSearchAll() {
			String[] types = {"rname", "reng", "rdesc"};
			String[] states = {"rname", "reng", "rdesc"};
			String keyword = "1";
			String category = "0";
			Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());
			Page<Recipe> result = recipeRepository.searchAll(types, keyword, category, states, pageable);
			
			log.info(result.getTotalPages());
			log.info(result.getSize());
			log.info(result.getNumber());
			log.info(result.hasPrevious()+" : "+result.hasNext());
			result.getContent().forEach(data -> log.info(data));
		}
		
		@Test
		public void findByRcateTest() {
			String rcate = "0";
			Pageable pageable = PageRequest.of(1, 10, Sort.by("rno").descending());
			Page<Recipe> dtolist = recipeRepository.findByRcate(rcate, pageable);
			List<Recipe> datalist = dtolist.getContent();
			datalist.forEach(dto -> log.info(dto));
		}
}
