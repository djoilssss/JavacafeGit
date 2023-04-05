package kr.co.javacafe.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopJoinDTO {
	private Long sno;
	private String sid;
	private Long rno;
	private Long scount;
	private Long sprice;
	private String cphone;
	private LocalDateTime regDate;
	private LocalDateTime modDate;	

	private String rcate;
	private String rname;
	private String reng;
	private String rdesc;
	private String rtext;
	private int rcost;
	private Long rprice;
	private int rkcal;
	private String rimg;
	private int rstate;
	
	private Long cnt;
	private Long totalCount;
}
