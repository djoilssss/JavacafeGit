package kr.co.javacafe.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopDTO {

	private Long sno;
	private String sid;
	private Long scount;
	private Long sprice;
	private String cphone;
	private LocalDateTime regDate;
	private LocalDateTime modDate;

	private Long rno;
	private String rname;
	private Long rprice;	
}
