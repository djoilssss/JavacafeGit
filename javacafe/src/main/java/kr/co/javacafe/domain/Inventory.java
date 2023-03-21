package kr.co.javacafe.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Inventory extends InvenBaseEntity  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ino;					//재고번호
	
	@Column(length = 20, nullable = false)
	private String iname;				//물품명
	
	@Column(nullable = false)
	private long iprice;				//물품가격
	
	@Column(length = 300)
	private String iclass;				//물품분류
	
	@Column(length = 1000)
	private String icontent;			//물품설명
	
	@Column
	private long icount;				//재고갯수
	
	@Column(nullable = false)
	private long istate;				//입출고 상태
	
	@Column(length = 200, nullable = true)
	private String iimg;
	
	public void change(String iname, long iprice, String iclass, String icontent, long icount, long istate, String iimg) {
		this.iname = iname;
		this.iprice = iprice;
		this.iclass = iclass;
		this.icontent = icontent;
		this.icount =icount;
		this.istate = istate;
		this.iimg = iimg;
	}
	
	
	
}
