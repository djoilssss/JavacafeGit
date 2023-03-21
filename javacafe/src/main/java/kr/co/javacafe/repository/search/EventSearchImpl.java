package kr.co.javacafe.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

import kr.co.javacafe.domain.QEvent;
import kr.co.javacafe.domain.Event;

public class EventSearchImpl extends QuerydslRepositorySupport implements EventSearch {

	public EventSearchImpl() {
		super(Event.class);
	}

	@Override
	public Page<Event> search1(Pageable pageable) { 
		
		QEvent event = QEvent.event; // Q도메인 객체

		JPQLQuery<Event> query = from(event); // select.. from event

		BooleanBuilder booleanBuilder = new BooleanBuilder();
		
		booleanBuilder.or(event.etitle.contains("11")); // title like...
		
		booleanBuilder.or(event.econtent.contains("11")); // content like...
		
		query.where(booleanBuilder);
		
		query.where(event.eno.gt(0L));
		
		query.where(event.etitle.contains("1")); // where title like ...

		//paging 시작
		this.getQuerydsl().applyPagination(pageable, query);
		
		//JPQLQuery fetch 시작 부분
		
		List<Event> List = query.fetch();
		
		long count = query.fetchCount();
		
		return null;
	}

	@Override
	public Page<Event> searchAll(String[] types, String keyword, Pageable pageable) {
		
		QEvent event = QEvent.event;
		JPQLQuery<Event> query = from(event);
		
		// 검색 조건과 키워드가 있다면
		if((types != null && types.length > 0) && keyword != null) {
			BooleanBuilder booleanBuilder = new BooleanBuilder(); // (
			
			for(String type: types) {
				switch(type) {
				
				case "t":
					booleanBuilder.or(event.etitle.contains(keyword));
					break;
				case "c":
					booleanBuilder.or(event.econtent.contains(keyword));
					break;
				case "w":
					booleanBuilder.or(event.ewriter.contains(keyword));
				}
			} // end for
			query.where(booleanBuilder);
		} // end if
		
		// eno > 0
		query.where(event.eno.gt(0L));
		
		// paging
		this.getQuerydsl().applyPagination(pageable, query);
		
		List<Event> list = query.fetch();
		
		long count = query.fetchCount();
		
		// 페이징 처리 최종 결과는 Page<T> 타입을 반환하는것
		// Querydsl에서는 이를 직접 처리해야 하는 불편함이 있다. 
		// Spring Data JPA에서는 이를 처리를 위해서 PAgeImpl이라는 클래스를 제공해서
		// 3개의 파마리터로 Page<t> 생성 가능
		// List<T> = 실제 목록 데이터, Pageable = 페이지 관련 정보를 가진 객체, long = 전체 개수
		return new PageImpl<>(list, pageable, count);
	}

}
