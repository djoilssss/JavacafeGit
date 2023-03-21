package kr.co.javacafe.domain;

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
public class ShopState extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ssno;
	
	private String sid;
	
	private String cphone;
	
	private Long ssstate;
	
	private Long sstotal;
	
	private Long sstoday;
	
	public void cphoneUpdate(String cphone) {
		this.cphone = cphone;
	}
	
}
