package kr.co.javacafe.repository.search;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import kr.co.javacafe.domain.QRecipe;
import kr.co.javacafe.domain.QShop;
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

}
