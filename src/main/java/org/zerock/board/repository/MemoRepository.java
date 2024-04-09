package org.zerock.board.repository;

import org.hibernate.mapping.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Memo;

import java.util.List;

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

//    select * from tbl_memo where mno between from and to order by mno desc
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

//    select * from tbl_memo where mno betwenn from and to - page 타입으로 리턴
    Page<Memo> findByMnoBetween (Long from, Long to, Pageable pageable);

//    10보다 작은 데이터 삭제
//    delete from tbl_memo 를 num번 실행
//    쿼리 메소드로 delete를 처리하면 9번의 쿼리문이 전달되어 비효율적
//    @Query를 사용해야 효율적
    void deleteMemoByMnoLessThan(Long num);

//    @Query : 순수한 sql 쿼리문으로 작성, 테이블명이 아닌 엔티티명으로 사용
    @Query("SELECT m FROM Memo m ORDER BY m.mno DESC")
    List<Memo> getListDesc();

//    매개값이 있는 @Query문 : 값으로 사용 (타입으로 받음)
    @Query("UPDATE Memo m SET m.memoText = :memoText WHERE m.mno = :mno")
    int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);

//    매개값이 있는 @Query문 :#{}으로 사용 (객체(빈)으로 받음)
    @Query("UPDATE Memo m SET m.memoText = :#{memoBean.memoText} WHERE m.mno = :#{memoBean.mno}")
    int updateMemoBean(@Param("memoBean") Memo memo);

//    @Query 메소드로 페이징 처리 해보기 -> 리턴타입은 Page<Memo>
    @Query(value = "SELECT m FROM Memo m WHERE m.mno > :mno", countQuery = "SELECT count(m) FROM Memo m WHERE m.mno > :mno")
    Page<Memo> getListWithQuery(Long mno, Pageable pageable);

//    DB에 존재하지 않는 값 처리 ex)날짜
    @Query(value = "SELECT m.mno, m.memoText, CURRENT_DATE FROM Memo m WHERE m.mno > :mno",
            countQuery = "SELECT count(m) FROM Memo m WHERE m.mno > :mno")
    Page<Object[]> getListWithQueryObject(Long mno, Pageable pageable);

//    Native SQL 처리 : DB용 쿼리로 사용하는 기법 - nativeQuery = true 사용
//    엔티티명 대신 테이블명을 사용
    @Query(value = "SELECT * FROM memo WHERE mno > 0", nativeQuery = true)
    List<Object[]> getNativeResult();










}
