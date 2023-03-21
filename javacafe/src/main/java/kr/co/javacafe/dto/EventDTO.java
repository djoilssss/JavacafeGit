package kr.co.javacafe.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
	// 글 번호
	private Long eno;

	@NotEmpty
	@Size(min = 3, max = 100)
	// 제목
	private String etitle;

	// 내용
	@NotEmpty
	private String econtent;

	// 작성자
	@NotEmpty
	private String ewriter;
	
	private String eimg;
	
	private MultipartFile file;

	// 작성일
	private LocalDateTime regDate;

	// 수정일
	private LocalDateTime modDate;

}
