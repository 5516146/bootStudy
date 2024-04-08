package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.board.entity.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    // JPA 레포지토리는 인터페이스 자체이고 JpaRepository 인터페이스를 상속하는 것 만드로 모든 작없이 끝남.
    // extends JpaRepository<엔티티, pk타입>

    // insert 작업 : save(엔티티 객체)
    // select 작업 : findById(키 타입), getOne(키 타입)
    // update 작업 : save(엔티티 객체)
    // delete 작업 : deleteById(키 타입),  delete(엔티티 객체)

    // 쿼리 메서드 (메서드 명이 쿼리를 대체함)
    // https://docs.spring.io/spring-data/jpa/docs/current-SNAPSHOT/reference/html/#jpa.query-methods
    // https://docs.spring.io/spring-data/jpa/docs/current-SNAPSHOT/reference/html/#jpa.query-methods.query-creation


}
