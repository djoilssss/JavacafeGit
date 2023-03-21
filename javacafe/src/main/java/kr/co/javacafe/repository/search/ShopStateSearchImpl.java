package kr.co.javacafe.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

import kr.co.javacafe.domain.QShopState;
import kr.co.javacafe.domain.ShopState;

public class ShopStateSearchImpl extends QuerydslRepositorySupport implements ShopStateSearch{

	public ShopStateSearchImpl() {
		super(ShopState.class);
	}

	@Override
	public Page<ShopState> searchAll(String[] types, String keyword, Pageable pageable) {
		
		QShopState shopState = QShopState.shopState;
		JPQLQuery<ShopState> query = from(shopState);
		
		// 검색 조건과 키워드가 있다면
		if((types != null && types.length > 0) && keyword != null) {
			BooleanBuilder booleanBuilder = new BooleanBuilder(); // (
			for(String type: types) {
				switch(type) {
				case "t":
					booleanBuilder.or(shopState.sid.contains(keyword));
					break;
				}
			} // end for
			query.where(booleanBuilder);
		} // end if
		
		query.where(shopState.ssno.gt(0L));
		
		// paging
		this.getQuerydsl().applyPagination(pageable, query);
		List<ShopState> list = query.fetch();
		long count = query.fetchCount();
		return new PageImpl<>(list, pageable, count);
				
	}

	@Override
	public Long countTodayOrder(String today) {
		QShopState shopState = QShopState.shopState;
		JPQLQuery<ShopState> query = from(shopState);
		query.where(shopState.sid.contains(today));
		List<ShopState> list = query.fetch();
		long count = query.fetchCount()+1L;
		return count;
	}

	
}
