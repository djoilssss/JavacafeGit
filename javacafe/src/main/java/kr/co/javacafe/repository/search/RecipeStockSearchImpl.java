package kr.co.javacafe.repository.search;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import kr.co.javacafe.domain.QInventory;
import kr.co.javacafe.domain.QRecipe;
import kr.co.javacafe.domain.QRecipeStock;
import kr.co.javacafe.domain.Recipe;
import kr.co.javacafe.domain.RecipeStock;
import kr.co.javacafe.dto.RecipeStockListDTO;

public class RecipeStockSearchImpl extends QuerydslRepositorySupport implements RecipeStockSearch {

	public RecipeStockSearchImpl() {
		super(RecipeStockListDTO.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<RecipeStockListDTO> selectJoinList(Long rno) {
		QRecipe recipe = QRecipe.recipe;
		QInventory inventory = QInventory.inventory;
		QRecipeStock recipeStock = QRecipeStock.recipeStock;
		
		JPQLQuery<RecipeStock> query = from(recipeStock);
		query.where(recipeStock.recipe.rno.eq(rno));
		query.leftJoin(inventory).on(recipeStock.ino.eq(inventory.ino));
		JPQLQuery<RecipeStockListDTO> dtoquery = query.select(Projections.bean(RecipeStockListDTO.class,
				recipeStock.rsno,
				recipeStock.rsnumber,
				inventory.ino,
				inventory.icount,
				inventory.istate,
				inventory.iname));
		List<RecipeStockListDTO> dtolist = dtoquery.fetch();
		long count = dtoquery.fetchCount();
		return dtolist;
	}

}
