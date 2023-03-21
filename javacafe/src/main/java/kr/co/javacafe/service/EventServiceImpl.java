package kr.co.javacafe.service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.javacafe.domain.Event;
import kr.co.javacafe.dto.PageRequestDTO;
import kr.co.javacafe.dto.PageResponseDTO;
import kr.co.javacafe.dto.EventDTO;
import kr.co.javacafe.dto.HomePageRequestDTO;
import kr.co.javacafe.dto.HomePageResponseDTO;
import kr.co.javacafe.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {

	private final ModelMapper modelMapper;
	private final EventRepository eventRepository;
		
	// 작성
	@Override
	public Long register(EventDTO eventDTO, HttpServletRequest request) {
		eventDTO.setEimg(fileUpload(eventDTO.getFile(), request));
		Event event = modelMapper.map(eventDTO, Event.class);
		Long eno = eventRepository.save(event).getEno();
		return eno;
	}
	
	// 조회
	@Override
	public EventDTO readOne(Long eno) {
		
		Optional<Event> result = eventRepository.findById(eno);
		
		Event event = result.orElseThrow();
		
		EventDTO eventDTO = modelMapper.map(event, EventDTO.class);
		
		return eventDTO;
	}
		
	// 수정
	@Override
	public void modify(EventDTO eventDTO, HttpServletRequest request) {
		
		Optional<Event> result = eventRepository.findById(eventDTO.getEno());
		Event event = result.orElseThrow();
		// 첨부파일
		if(!eventDTO.getFile().getOriginalFilename().equals("")) {
			log.info("Exists New file");
			eventDTO.setEimg(fileUpload(eventDTO.getFile(), request));
		}else {
			log.info("Not Exists New file");
			eventDTO.setEimg(event.getEimg());
		}
		// 제목 & 내용만 수정 가능
		event.change(eventDTO.getEtitle(), eventDTO.getEcontent(), eventDTO.getEimg());
		eventRepository.save(event);
	}
	
	
	// 이미지 파일 첨부
	private String fileUpload(MultipartFile file, HttpServletRequest request) {
		 Long unixtime = System.currentTimeMillis();
		 String fileName = unixtime + "_" + file.getOriginalFilename();
		// 이클립스 
		// String rootPath = System.getProperty("user.dir");
		// String path = rootPath + "/src/main/resources/static/files/";
		 String path = "C:\\upload\\";
		 try {
			 // String path = WebUtils.getRealPath(request.getSession().getServletContext(), "/resources/data/recipe/");
			 File folder = new File(path);
			 if(!folder.exists()) folder.mkdirs();
			 File destination = new File(path + File.separator + fileName);
			 file.transferTo(destination);
			 System.out.println(destination);
			 System.out.println("업로드 성공");
		 }catch (Exception e) {
			 System.out.println("업로드 실패");
		 }
		return fileName;
	}

	// 삭제
	@Override
	public void remove(Long eno) {
		
		eventRepository.deleteById(eno);
		
	}

	// 목록 & 검색 기능
	@Override
	public PageResponseDTO<EventDTO> list(PageRequestDTO pageRequestDTO) {
		
		String[] types = pageRequestDTO.getTypes();
		String keyword = pageRequestDTO.getKeyword();
		Pageable pageable = pageRequestDTO.getPageable("eno");
		
		Page<Event> result = eventRepository.searchAll(types, keyword, pageable);
		
		List<EventDTO> dtoList = result.getContent().stream()
				.map(event -> modelMapper.map(event, EventDTO.class))
				.collect(Collectors.toList());
		
		return PageResponseDTO.<EventDTO>withAll()
				.pageRequestDTO(pageRequestDTO)
				.dtoList(dtoList)
				.total((int)result.getTotalElements())
				.build();
	}

	@Override
	public HomePageResponseDTO<EventDTO> list2(HomePageRequestDTO pageRequestDTO2) {
		String[] types = pageRequestDTO2.getTypes();
		String keyword = pageRequestDTO2.getKeyword();
		Pageable pageable = pageRequestDTO2.getPageable("eno");
		
		Page<Event> result = eventRepository.searchAll(types, keyword, pageable);
		
		List<EventDTO> dtoList = result.getContent().stream()
				.map(event -> modelMapper.map(event, EventDTO.class))
				.collect(Collectors.toList());
		
		return HomePageResponseDTO.<EventDTO>withAll()
				.pageRequestDTO2(pageRequestDTO2)
				.dtoList(dtoList)
				.total((int)result.getTotalElements())
				.build();
	
	}
	
//	// 이미지 업로드
//	public String fileUpload(MultipartFile file, HttpServletRequest request) {
//		Long unixtime = System.currentTimeMillis();
//		String fileName = unixtime + "_" + file.getOriginalFilename();
//		String path = "C:\\upload\\"; // 파일 업로드되는 경로
//		try {
//			File folder = new File(path);
//			if (!folder.exists()) folder.mkdirs();
//			File destination = new File(path + File.separator + fileName);
//			file.transferTo(destination);
//			System.out.println(destination);
//			System.out.println("업로드 성공");
//		}catch (Exception e) {
//			System.out.println("업로드 실패");
//		}
//		return fileName;
//	}
//	

}
