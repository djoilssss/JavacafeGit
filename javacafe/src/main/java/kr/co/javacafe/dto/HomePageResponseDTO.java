package kr.co.javacafe.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class HomePageResponseDTO<E> {
	private int page;
	private int size;
	private int total;
	
	// 시작, 끝 페이지 번호
	private int start;
	private int end;
	// 이전, 다음 페이지의 존재 여부
	private boolean prev;
	private boolean next;
	
	private List<E> dtoList;
	
	
	
	
	@Builder(builderMethodName = "withAll")
	public HomePageResponseDTO(HomePageRequestDTO pageRequestDTO2, List<E> dtoList, int total) {
		if(total <= 0) {
			return;
		}
		
		this.page = pageRequestDTO2.getPage();
		this.size = pageRequestDTO2.getSize();
		this.total = total;
		this.dtoList = dtoList;
		this.end = (int)(Math.ceil(this.page/4.0))*4;
		this.start = this.end - 4;
		int last = (int)(Math.ceil((total/(double)size)));	
		this.end = end > last ? last : end;
		this.prev = this.start > 1;
		this.next = total > this.end * this.size;
	}
	
}
