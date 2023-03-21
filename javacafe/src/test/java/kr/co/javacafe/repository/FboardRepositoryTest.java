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

import kr.co.javacafe.domain.Event;
import kr.co.javacafe.domain.FBoard;
import kr.co.javacafe.domain.Recipe;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class FboardRepositoryTest {
	
	@Autowired
	private FBoardRepository fboardRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Test
	public void testeinsert() {
		IntStream.rangeClosed(1, 35).forEach(i->{
			Event event = Event.builder()
					.etitle("게시글"+i)
					.econtent("게시글내용"+i)
					.ewriter("관리자")
					.build();
			Event result = eventRepository.save(event);
			
				
		});
	}
	
	@Test
	   public void testinsert() {
	      IntStream.rangeClosed(1, 35).forEach(i->{ //for문 생각  i값을 board에다가 1~100까지 넣을것 
	         FBoard fboard = FBoard.builder()
	               .ftitle("title..."+i) // title에 i값 넣기
	               .fcontent("content..."+i) //content에 i값 넣기
	               .fwriter("user") // writer에는 i%10한 user번호로 나타내기
	               .build(); //생성하기
	         FBoard result = fboardRepository.save(fboard); //생성한 db를 save(board) board에다가 세이브하고 result에 넣기
	         log.info("fNO : "+result.getFno()); //result에 담겨있는 bno갖고와서 출력
        });

	   }
	   
       @Test
	   public void testUpdate() {
		   
		   Long fno = 115L;
		   
		   Optional<FBoard> result = fboardRepository.findById(fno);
		   
		   FBoard fboard = result.orElseThrow();
		   
		   fboard.change("update..", "update content 100");		   
		   fboardRepository.save(fboard);
	   }
	
	   @Test
	   public void testDelete() {
		   Long fno = 1L;
		   
		   fboardRepository.deleteById(fno);
	   }
	
	@Test
	public void testPaging() {
	//1 page order by bno desc
	Pageable pageable = PageRequest.of(0, 10, Sort.by("fno").descending());
	Page<FBoard> result = fboardRepository.findAll(pageable);
	
	log.info("total count: " + result.getTotalElements());
	log.info("total pages: " + result.getTotalPages());
	log.info("page number: " + result.getTotalPages());
	log.info("page size: " + result.getSize());
	
	List<FBoard> todoList = result.getContent();
	
  todoList.forEach(board -> log.info(board));
}
	@Test
	public void testSearchAll() {		
		String[] types = {"t","c","w"};
		
		String keyword="1";
		
		Pageable pageable = PageRequest.of(0, 10, Sort.by("fno").descending());	
		Page<FBoard> result = fboardRepository.searchAll(types, keyword, pageable);
		
	}
	@Test
	public void testSearchAll2() {		
		String[] types = {"t","c","w"};
		
		String keyword="1";
		
		Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());	
		Page<FBoard> result = fboardRepository.searchAll(types, keyword, pageable);
		
		log.info(result.getTotalPages());
		log.info(result.getSize());
		log.info(result.getNumber());
		log.info(result.hasPrevious() +": " + result.hasNext());
		
		result.getContent().forEach(board -> log.info(board));
	}
	
	

}
