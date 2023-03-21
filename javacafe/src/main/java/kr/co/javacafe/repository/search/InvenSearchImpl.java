package kr.co.javacafe.repository.search;

import java.util.List;

import org.hibernate.dialect.pagination.SQL2008StandardLimitHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

import ch.qos.logback.core.joran.conditional.Condition;
import kr.co.javacafe.domain.Admin;
import kr.co.javacafe.domain.Inventory;
import kr.co.javacafe.domain.QAdmin;
import kr.co.javacafe.domain.QInventory;
import kr.co.javacafe.domain.QSales;
import kr.co.javacafe.domain.Sales;


public class InvenSearchImpl extends QuerydslRepositorySupport implements InvenSearch {

    public InvenSearchImpl(){
        super(Inventory.class);
        
    }
    
    
    
    @Override
    public Page<Inventory> isearch(Pageable pageable) {

        QInventory inventory = QInventory.inventory;

        JPQLQuery<Inventory> query = from(inventory);

        BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

        booleanBuilder.or(inventory.iname.contains("11")); // iname like ...

        booleanBuilder.or(inventory.iclass.contains("11")); // iclass like ....

        query.where(booleanBuilder);
        query.where(inventory.ino.gt(0L));


        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Inventory> list = query.fetch();

        long count = query.fetchCount();


        return null;

    }

    @Override
    public Page<Inventory> isearchAll(String[] types, String keyword, Pageable pageable) {

        QInventory inventory = QInventory.inventory;
        JPQLQuery<Inventory> query = from(inventory);

        if( (types != null && types.length > 0) && keyword != null ){ //검색 조건과 키워드가 있다면

            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

            for(String type: types){

                switch (type){
                    case "n":
                        booleanBuilder.or(inventory.iname.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(inventory.iclass.contains(keyword));
                        break;                    
                }
            }//end for
            query.where(booleanBuilder);
        }//end if

        //bno > 0
        query.where(inventory.ino.gt(0L));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Inventory> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);

    }
 


}