package kr.co.javacafe.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.javacafe.dto.ShopDTO;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ShopServiceTest {

	@Autowired
	private ShopService shopService;
	
	@Test
	public void getBySidTest() {
		String sid = "20230315-101615";
		List<ShopDTO> dtolist = shopService.getByKid(sid);
		dtolist.forEach(dto -> log.info(dto));
	}
}
