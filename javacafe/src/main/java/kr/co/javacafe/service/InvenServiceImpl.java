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

import kr.co.javacafe.domain.Inventory;
import kr.co.javacafe.dto.InventoryDTO;
import kr.co.javacafe.dto.PageRequestDTO;
import kr.co.javacafe.dto.PageResponseDTO;

import kr.co.javacafe.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class InvenServiceImpl implements InvenService{

    private final ModelMapper modelMapper;
    private final InventoryRepository inventoryRepository;
    

    @Override//등록
    public Long register(InventoryDTO inventoryDTO, HttpServletRequest request) {
    	inventoryDTO.setIimg(fileUpload(inventoryDTO.getFile(), request));
    	
         Inventory inventory = modelMapper.map(inventoryDTO, Inventory.class);

        Long ino = inventoryRepository.save(inventory).getIno();

        return ino;
    }

    
    
    @Override // 조회/상세보기
    public InventoryDTO readOne(Long ino) {

        Optional<Inventory> result = inventoryRepository.findById(ino);

        Inventory inventory = result.orElseThrow();

        InventoryDTO inventoryDTO = modelMapper.map(inventory, InventoryDTO.class);

        return inventoryDTO;
    }
    
    //수정
    @Override
    public void modify(InventoryDTO inventoryDTO, HttpServletRequest request) {

        Optional<Inventory> result = inventoryRepository.findById(inventoryDTO.getIno());
        
        Inventory inventory = result.orElseThrow();
        
        //이미지수정
        if(!inventoryDTO.getFile().getOriginalFilename().equals("")) {
        	inventoryDTO.setIimg(fileUpload(inventoryDTO.getFile(), request));
        } else {
        	inventoryDTO.setIimg(inventory.getIimg());
        }
        
        inventory.change(
        inventoryDTO.getIname(),inventoryDTO.getIprice(),inventoryDTO.getIclass(),
		inventoryDTO.getIcontent(),inventoryDTO.getIcount(),inventoryDTO.getIstate(),inventoryDTO.getIimg());
        
        inventoryRepository.save(inventory);

    }

    @Override
    public void remove(Long ino) {

       inventoryRepository.deleteById(ino);

    }

//    @Override
//    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {
//
//        String[] types = pageRequestDTO.getTypes();
//        String keyword = pageRequestDTO.getKeyword();
//        Pageable pageable = pageRequestDTO.getPageable("bno");
//
//        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);
//
//        return null;
//    }

    @Override
    public PageResponseDTO<InventoryDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("ino");

        Page<Inventory> result = inventoryRepository.isearchAll(types, keyword, pageable);

        List<InventoryDTO> dtoList = result.getContent().stream()
                .map(inventory -> modelMapper.map(inventory,InventoryDTO.class)).collect(Collectors.toList());


        return PageResponseDTO.<InventoryDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();

    }
    
	// 파일 업로드 메소드
	public String fileUpload(MultipartFile file, HttpServletRequest request) {
		Long unixtime =  System.currentTimeMillis();
		String fileName = unixtime + "_" + file.getOriginalFilename();
		// 이클립스 새로 고침 문제
		// String rootPath = System.getProperty("user.dir");
		// String path = rootPath + "/src/main/resources/static/files/";
		String path = "C:\\upload\\";
		try{
			// String path = WebUtils.getRealPath(request.getSession().getServletContext(), "/resources/data/recipe/");
            File folder = new File(path);
            if (!folder.exists()) folder.mkdirs();
            File destination = new File(path + File.separator + fileName);
            file.transferTo(destination);
            System.out.println(destination);
            System.out.println("성공!");
        }catch (Exception e){
        	System.out.println("실패!");
        }
		return fileName;
	}



	@Override
	public List<InventoryDTO> getAll() {
		List<InventoryDTO> dtolist = inventoryRepository.findAll().stream()
				.map(data -> modelMapper.map(data, InventoryDTO.class))
				.collect(Collectors.toList());
		return dtolist;
	}

}