package kr.co.javacafe.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import kr.co.javacafe.domain.QRecipe;
import kr.co.javacafe.domain.QShop;
import kr.co.javacafe.domain.Recipe;
import kr.co.javacafe.domain.Shop;
import kr.co.javacafe.dto.ShopJoinDTO;

public class ShopSearchImpl extends QuerydslRepositorySupport implements ShopSearch {

	public ShopSearchImpl() {
		super(Shop.class);
	}

	@Override
	public List<ShopJoinDTO> findBySidJoin(String sid) {
		
		QShop shop = QShop.shop;
		QRecipe recipe = QRecipe.recipe;
		
		JPQLQuery<Shop> query = from(shop);
		query.where(shop.sid.eq(sid));
		query.leftJoin(recipe).on(shop.rno.eq(recipe.rno));
		JPQLQuery<ShopJoinDTO> dtoquery = query.select(Projections.bean(ShopJoinDTO.class,
				shop.sno,
				shop.sid,
				shop.scount,
				shop.sprice,
				shop.rno,
				shop.rname,
				shop.rprice,
				recipe.rstate));
		List<ShopJoinDTO> dtolist = dtoquery.fetch();
		long count = dtoquery.fetchCount()+1L;
		return dtolist;
	}

	@Override
	public List<ShopJoinDTO> findBest() {

		QShop shop = QShop.shop;
		QRecipe recipe = QRecipe.recipe;		
		
		JPQLQuery<Shop> query = from(shop);
		query.leftJoin(recipe).on(shop.rno.eq(recipe.rno));
		query.groupBy(shop.rno);

		JPQLQuery<Tuple> tupleJPQLQuery = null;
		List<Tuple> tupleList = query.select(shop.rno, recipe.rname, shop.count(), shop.scount.sum()).groupBy(shop.rno).orderBy(shop.scount.sum().desc()).fetch();
		List<ShopJoinDTO> dtoList = tupleList.stream().map(tuple -> {
			ShopJoinDTO dto = ShopJoinDTO.builder()
					.rno(tuple.get(0, null))
					.rname(tuple.get(1, null)) 
					.cnt(tuple.get(2, null))
					.totalCount(tuple.get(3, null))
					.build();
			return dto;
		}).collect(Collectors.toList());
		
		System.out.println(tupleList.get(0));
		System.out.println(tupleList.get(1));

        long totalCount = query.fetchCount(); 
		return dtoList;
	}
}
