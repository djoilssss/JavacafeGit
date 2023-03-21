package kr.co.javacafe.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

import kr.co.javacafe.domain.QSales;
import kr.co.javacafe.domain.Sales;

public class SalesSearchImpl extends QuerydslRepositorySupport implements SalesSearch {

	public SalesSearchImpl() {
		super(Sales.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Page<Sales> Ssearch(Pageable pageable) {
		QSales sales = QSales.sales1;

        JPQLQuery<Sales> query = from(sales);

        BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

        booleanBuilder.or(sales.year.contains("11")); // iname like ...

        booleanBuilder.or(sales.month.contains("11")); // iclass like ....

        query.where(booleanBuilder);
        query.where(sales.sno.gt(0L));


        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Sales> list = query.fetch();

        long count = query.fetchCount();


        return null;
	}


	@Override
	public Page<Sales> SsearchAll(String[] types, String keyword, Pageable pageable) {
		 QSales sales = QSales.sales1;
	        JPQLQuery<Sales> query = from(sales);

	        if( (types != null && types.length > 0) && keyword != null ){ //검색 조건과 키워드가 있다면

	            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

	            for(String type: types){

	                switch (type){
	                    case "y":
	                        booleanBuilder.or(sales.year.contains(keyword));
	                        break;
	                    case "m":
	                        booleanBuilder.or(sales.month.contains(keyword));
	                        break;                    
	                }
	            }//end for
	            query.where(booleanBuilder);
	        }//end if

	        //bno > 0
	        query.where(sales.sno.gt(0L));

	        //paging
	        this.getQuerydsl().applyPagination(pageable, query);

	        List<Sales> list = query.fetch();

	        long count = query.fetchCount();

	        return new PageImpl<>(list, pageable, count);

	}



}
