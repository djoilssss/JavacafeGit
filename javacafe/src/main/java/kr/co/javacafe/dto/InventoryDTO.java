package kr.co.javacafe.dto;



import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTO {

		
	private long ino;					//재고번호
	
	@NotEmpty
	private String iname;				//물품명
	
	@NotNull
	private long iprice;				//물품가격
	
	
	private String iclass;				//물품분류
	private String icontent;			//물품설명
	private long icount;				//재고갯수
	private LocalDateTime iregDate;		//입고일
	private LocalDateTime imodDate;		//출고일
	private long istate;				//입출고 상태
	//이미지업로드
	private String iimg;	
	private MultipartFile file;
}
