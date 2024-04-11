package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.zerock.board.entity.GuestBook;

public interface GuestBookRepository extends JpaRepository<GuestBook, Long>, QuerydslPredicateExecutor<GuestBook> {
//                                                         엔티티명,   pk 타입                            엔티티명
    // insert : save
    // select : findById
    // update : save
    // delete : deleteById

    // Querydsl : Q도메인을 이용해 자동으로 검색 조건을 완성시킴 (다중검색용) -> QuerydslPredicateExecutor 추가 상속
    // http://querydsl.com/ 참고해 API 의존성을 주입 받음
}
