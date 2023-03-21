package kr.co.javacafe.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

import kr.co.javacafe.domain.QRecipe;
import kr.co.javacafe.domain.Recipe;

public class RecipeSearchImpl extends QuerydslRepositorySupport implements RecipeSearch{

	public RecipeSearchImpl() {
		super(Recipe.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Page<Recipe> search(Pageable pageable) {
		
		QRecipe recipe = QRecipe.recipe; // Q도메인 객체
		JPQLQuery<Recipe> query = from(recipe); // select from recipe 객체
		
		// where절
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		booleanBuilder.or(recipe.rname.contains("1")); // rname like concat()
		booleanBuilder.or(recipe.reng.contains("1")); // reng like concat()
		query.where(booleanBuilder);
		query.where(recipe.rno.gt(0L)); // rno가 0보다 큼
		
		// 페이징
		this.getQuerydsl().applyPagination(pageable, query);
		
		// 쿼리 실행
		List<Recipe> list = query.fetch();
		long count = query.fetchCount();
		return null;
	}

	@Override
	public Page<Recipe> searchAll(String[] types, String keyword, String category, String[] states, Pageable pageable) {
		
		QRecipe recipe = QRecipe.recipe; // Q도메인 객체
		JPQLQuery<Recipe> query = from(recipe); // select from recipe 객체
		
		if((types != null && types.length > 0) && keyword != null) { // 검색 조건과 키워드가 있다면
			// where절
			BooleanBuilder booleanBuilder = new BooleanBuilder();
			for(String type : types) {
				System.out.println(type);
				switch(type) {
				case "rname" : 
					booleanBuilder.or(recipe.rname.contains(keyword));
					break;
				case "reng" : 
					booleanBuilder.or(recipe.reng.contains(keyword));
					break;
				case "rdesc" : 
					booleanBuilder.or(recipe.rdesc.contains(keyword));						
					break;
				} // end of switch
			} // end of for
			query.where(booleanBuilder);
		}// end of if
		
		// 카테고리 검색
		if(category != null) {
			query.where(recipe.rcate.contains(category));
		}
		
		if(states != null && states.length > 0) {
			BooleanBuilder booleanBuilder = new BooleanBuilder();
			for(String state : states) {
				System.out.println(state);
				switch(state) {
				case "0" : 
					booleanBuilder.or(recipe.rstate.eq(0));
					break;
				case "1" : 
					booleanBuilder.or(recipe.rstate.eq(1));
					break;
				} // end of switch
			} // end of for
			query.where(booleanBuilder);
		}// end of if		
		
		// rno > 0
		query.where(recipe.rno.gt(0L));
		
		// 페이징
		this.getQuerydsl().applyPagination(pageable, query);
		
		// 쿼리 실행
		List<Recipe> list = query.fetch();
		long count = query.fetchCount();		
		
		return new PageImpl<>(list, pageable, count);
	}

}
