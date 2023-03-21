package kr.co.javacafe.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.javacafe.domain.FBoard;
import kr.co.javacafe.domain.Sales;
import kr.co.javacafe.dto.EventDTO;
import kr.co.javacafe.dto.FBoardDTO;
import kr.co.javacafe.dto.HomePageRequestDTO;
import kr.co.javacafe.dto.HomePageResponseDTO;
import kr.co.javacafe.dto.PageRequestDTO;
import kr.co.javacafe.dto.PageResponseDTO;
import kr.co.javacafe.dto.SalesDTO;
import kr.co.javacafe.repository.FBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class FBoardServiceImpl implements FBoardService {
	
	private final ModelMapper modelMapper;

    private final FBoardRepository fboardRepository;
	
    @Override
    public Long register(FBoardDTO fboardDTO) {

        FBoard fboard = modelMapper.map(fboardDTO, FBoard.class);

        Long fno = fboardRepository.save(fboard).getFno();

        return fno;
    }

    @Override
    public FBoardDTO readOne(Long fno) {

        Optional<FBoard> result = fboardRepository.findById(fno);

        FBoard fboard = result.orElseThrow();

        FBoardDTO fboardDTO = modelMapper.map(fboard, FBoardDTO.class);

        return fboardDTO;
    }

    @Override
    public void modify(FBoardDTO fboardDTO) {

        Optional<FBoard> result = fboardRepository.findById(fboardDTO.getFno());

        FBoard fboard = result.orElseThrow();

        fboard.change(fboardDTO.getFtitle(), fboardDTO.getFcontent());

        fboardRepository.save(fboard);

    }

    @Override
    public void remove(Long fno) {

        fboardRepository.deleteById(fno);

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
    public PageResponseDTO<FBoardDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("fno");

        Page<FBoard> result = fboardRepository.searchAll(types, keyword, pageable);

        List<FBoardDTO> dtoList = result.getContent().stream()
                .map(fboard -> modelMapper.map(fboard,FBoardDTO.class)).collect(Collectors.toList());


        return PageResponseDTO.<FBoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();

    }

	@Override
	public HomePageResponseDTO<FBoardDTO> list2(HomePageRequestDTO pageRequestDTO2) {
		String[] types = pageRequestDTO2.getTypes();
        String keyword = pageRequestDTO2.getKeyword();
        Pageable pageable = pageRequestDTO2.getPageable("fno");

        Page<FBoard> result = fboardRepository.searchAll(types, keyword, pageable);

        List<FBoardDTO> dtoList = result.getContent().stream()
                .map(fboard -> modelMapper.map(fboard,FBoardDTO.class)).collect(Collectors.toList());


        return HomePageResponseDTO.<FBoardDTO>withAll()
                .pageRequestDTO2(pageRequestDTO2)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
	}	
}