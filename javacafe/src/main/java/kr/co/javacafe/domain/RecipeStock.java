package kr.co.javacafe.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "RecipeStock", indexes = {@Index(name = "idx_recipe_stock_recipe_rno", columnList = "recipe_rno")})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"recipe", "inventory"})
public class RecipeStock extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rsno;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_rno")
	private Recipe recipe;
	/*
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inventory_ino")
	private Inventory inventory;	
	*/
	private Long ino;
	
	private String rsnumber;
	
	public void changeNumber(String rsnumber) {
		this.rsnumber = rsnumber;
	}
}
