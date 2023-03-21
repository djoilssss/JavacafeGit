package kr.co.javacafe.service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import kr.co.javacafe.domain.Recipe;
import kr.co.javacafe.dto.PageRequestDTO;
import kr.co.javacafe.dto.PageResponseDTO;
import kr.co.javacafe.dto.RecipeDTO;
import kr.co.javacafe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class RecipeServiceImpl implements RecipeService {

	private final ModelMapper modelMapper;
	private final RecipeRepository recipeRepository;
	
	@Override
	public Long register(RecipeDTO recipeDTO, HttpServletRequest request) {
		recipeDTO.setRimg(fileUpload(recipeDTO.getFile(), request));
		Recipe recipe = modelMapper.map(recipeDTO, Recipe.class);
		Long rno = recipeRepository.save(recipe).getRno();
		return rno;
	}

	@Override
	public RecipeDTO readOne(Long rno) {
		Optional<Recipe> result = recipeRepository.findById(rno);
		Recipe recipe = result.orElseThrow();
		RecipeDTO recipeDTO = modelMapper.map(recipe, RecipeDTO.class);
		return recipeDTO;
	}

	@Override
	public void modify(RecipeDTO recipeDTO, HttpServletRequest request) {
		Optional<Recipe> result = recipeRepository.findById(recipeDTO.getRno());
		Recipe recipe = result.orElseThrow();
		// 첨부파일 확인
		if(!recipeDTO.getFile().getOriginalFilename().equals("")) {
			log.info("Exists New File");
			recipeDTO.setRimg(fileUpload(recipeDTO.getFile(), request));
		}else {
			log.info("Not Exist New File");
			recipeDTO.setRimg(recipe.getRimg());
		}
		recipe.change(recipeDTO.getRcate(), recipeDTO.getRname(), recipeDTO.getReng(), recipeDTO.getRdesc(), recipeDTO.getRtext(), recipeDTO.getRcost(), recipeDTO.getRprice(), recipeDTO.getRkcal(), recipeDTO.getRstate(), recipeDTO.getRimg());
		recipeRepository.save(recipe);
	}

	@Override
	public void remove(Long rno) {
		recipeRepository.deleteById(rno);
		
	}

	@Override
	public PageResponseDTO<RecipeDTO> list(PageRequestDTO pageRequestDTO) {
		// String[] types = pageRequestDTO.getTypes();
		// 검색 유형
		String[] types = null;
		if(pageRequestDTO.getType()!=null) {
			types = pageRequestDTO.getType().split(",");
		}
		String[] states = null;
		if(pageRequestDTO.getState()!=null) {
			states = pageRequestDTO.getState().split(",");
		}		
		String keyword = pageRequestDTO.getKeyword();
		String category = pageRequestDTO.getCategory();
		Pageable pageable = pageRequestDTO.getPageable("rno");
		Page<Recipe> result = recipeRepository.searchAll(types, keyword, category, states, pageable);
		List<RecipeDTO> dtoList = result.getContent().stream().map(recipe -> modelMapper.map(recipe, RecipeDTO.class)).collect(Collectors.toList());
		return PageResponseDTO.<RecipeDTO>withAll()
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
	public List<Recipe> recipeList() {
		
		return recipeRepository.findAll();
	}

	@Override
	public List<RecipeDTO> getByRcate(String rcate) {
		Pageable pageable = PageRequest.of(0, 100, Sort.by("rno").descending());
		Page<Recipe> entitiylist = recipeRepository.findByRcate(rcate, pageable);
		List<RecipeDTO> dtolist = entitiylist.getContent().stream().map(recipe -> modelMapper.map(recipe, RecipeDTO.class)).collect(Collectors.toList());
		return dtolist;
	}



}
