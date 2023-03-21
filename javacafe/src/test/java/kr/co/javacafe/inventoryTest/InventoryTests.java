package kr.co.javacafe.inventoryTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import kr.co.javacafe.domain.Inventory;
import kr.co.javacafe.domain.Sales;
import kr.co.javacafe.dto.InventoryDTO;
import kr.co.javacafe.dto.PageRequestDTO;
import kr.co.javacafe.dto.PageResponseDTO;
import kr.co.javacafe.repository.InventoryRepository;
import kr.co.javacafe.repository.SalesRepository;
import kr.co.javacafe.service.InvenService;
import kr.co.javacafe.service.SalesService;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class InventoryTests {
	

	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private InvenService invenService;
	
	@Test //insert테스트
	public void testinsert() {
		IntStream.rangeClosed(1, 400).forEach(i->{
			Inventory inventory = Inventory.builder()
					.iname("iname..."+i)
					.iprice(300+i)
					.iclass("iclass..."+i)
					.icontent("icontent..."+i)
					.icount(i)
					.istate(1)
					.build();
			Inventory result = inventoryRepository.save(inventory);
			log.info("INO : "+result.getIno());
		});
	}
	
	
		@Test //select 테스트
		public void selectTest() {
			long ino = 100L; //select할 ino번호
			
			Optional<Inventory> result = inventoryRepository.findById(ino); //bno찾아오기
			
			Inventory inventory = result.orElseThrow(); //
			log.info(inventory); //board 출력
		}
		
		//update 테스트
		@Test
		public void testUpdate() {
			
			long ino=99L;
			
			Optional<Inventory> result = inventoryRepository.findById(ino); //ino찾아오기
			
			Inventory inventory = result.orElseThrow(); //Otional에서 원하는 객체를 바로 얻거나 오류발생시 예외를 던지게하는 메소드
			inventory.change("11",1234, "123", "234",5, 1,null);; //board에 작성된 update메소드 실행
			inventoryRepository.save(inventory); //board에 저장하기
			
		}
//		//delete test
//		@Test
//		public void testDelete() {
//			long ino = 1L;
//			inventoryRepository.deleteById(ino);
//		}
		
		
		//paging 테스트
		@Test
		public void testPaging() {
			
			//1 page oreder by ino desc
			// import org.springframework.data.domain.Pageable;
			Pageable pageable = PageRequest.of(1, 10, Sort.by("ino").descending());
							      //페이지 번호는 0 (0부터시작) //사이즈는 10 //정렬조건은 sort ~~~		
			Page<Inventory> result = inventoryRepository.findAll(pageable);
			
			log.info("total count : "+result.getTotalElements());
			log.info("total pages: "+result.getTotalPages());
			log.info("page number : " + result.getNumber());
			log.info("page size : "+result.getSize());
			
			List<Inventory> todoList = result.getContent();
			todoList.forEach(inventory -> log.info(inventory));
		}
	
		
		//paging 테스트2
		@Test
		public void testsearch1() {
			//2 page oreder by ino desc
			Pageable pageable = PageRequest.of(1, 10, Sort.by("ino").descending());
			inventoryRepository.isearch(pageable);
			
		}
		
		//검색기능 테스트
		@Test
		public void testSearchAll() {
			String[] types = {"n","c"}; //name & content
			String keyword = "2";
			Pageable pageable = PageRequest.of(0,10,Sort.by("ino").descending());
			Page<Inventory> result = inventoryRepository.isearchAll(types, keyword, pageable);
			
			//총 페이지
			log.info(result.getTotalPages());
			//페이지 사이즈
			log.info(result.getSize());
			//페이지 넘버
			log.info(result.getNumber());
			//prev next
			log.info(result.hasPrevious()+" : "+result.hasNext());
			
			result.getContent().forEach(inventory -> log.info(inventory));
		}
		
		@Test
		public void testRegister(HttpServletRequest request) {
			log.info(invenService.getClass().getName()); 
			
			InventoryDTO inventoryDTO = InventoryDTO.builder()
					.iname("생성하기")
					.iprice(300)
					.iclass("과자")
					.icontent("맛있는과자")
					.icount(100)
					.istate(1)
					.build();
			long ino = invenService.register(inventoryDTO, request);
			log.info("ino : " + ino);
		}
		
		@Test
		public void testmodify(HttpServletRequest request) {
			//변경에 필요한 데이터만
			InventoryDTO inventoryDTO = InventoryDTO.builder()
					.ino(101)
					.iname("맛있는과자로 업뎃")
					.iprice(3000)
					.iclass("과자")
					.icontent("맛있는과자30000원")
					.icount(0)
					.istate(0)
					.build();
			
			invenService.modify(inventoryDTO, request);
		}
		
		@Test
		public void testList() {
			PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
					.type("nc")
					.keyword("1")
					.page(1)
					.size(10)
					.build();
			
			PageResponseDTO<InventoryDTO> responseDTO = invenService.list(pageRequestDTO);
			log.info(responseDTO);
		}
			
		
		
}
