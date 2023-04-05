package kr.co.javacafe.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import groovy.util.logging.Log4j2;
import kr.co.javacafe.dto.ShopJoinDTO;

@SpringBootTest
@Log4j2
public class ShopRepositoryTest {

	@Autowired
	private ShopRepository shopRepository;
	
	@Test
	public void test() {
		List<ShopJoinDTO> dtolist = shopRepository.findBest();
		dtolist.forEach(dto -> System.out.println(dto));
	}
	
}
