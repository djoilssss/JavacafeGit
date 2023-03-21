package kr.co.javacafe.dto;

import java.sql.Timestamp;
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
public class RecipeDTO {

	private Long rno;
	private String rcate;
	
	@NotEmpty
	@Size(min = 3, max = 200)
	private String rname;

	@NotEmpty
	@Size(min = 3, max = 200)
	private String reng;
	
	@NotEmpty
	private String rdesc;
	private String rtext;
	private int rcost;
	private int rprice;
	private int rkcal;
	private String rimg;
	private int rstate;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
	
	// 파일
	private MultipartFile file;
	
}
