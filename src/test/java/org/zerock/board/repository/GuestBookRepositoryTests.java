package org.zerock.board.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.board.entity.GuestBook;
import org.zerock.board.entity.QGuestBook;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestBookRepositoryTests {

    @Autowired // 인터페이스 자동 주입
    private GuestBookRepository guestBookRepository;

    @Test
    public void insertDummies(){ // 테이블에 더미데이터 300개 추가
        IntStream.rangeClosed(1, 300).forEach(i -> {
            GuestBook guestBook = GuestBook.builder()
                    .title("제목 .. " + i).content("내용 .. " + i).writer("user" + (i % 10))
                    .build();

            // jpa save 메소드를 실행하며 출력
            System.out.println(guestBookRepository.save(guestBook));
        });
    }

    @Test
    public void updateTest(){ // 게시물을 수정
        Optional<GuestBook> result = guestBookRepository.findById(300l); // 300번 게시물 찾기

        if (result.isPresent()){ // 객체가 있으면
            GuestBook guestBook = result.get();

            System.out.println(guestBook.getTitle());
            guestBook.changeTitle("수정된 제목 ... ");

            System.out.println(guestBook.getContent());
            guestBook.changeContent("수정된 내용 ... ");

            guestBookRepository.save(guestBook);
        }
    }

    @Test
    public void testQuery1(){ // 단일 조건으로 쿼리 생성 및 페이징 및 정렬
        // 페이지 타입으로 요청 처리 (0번 페이지에 10개씩 객체 저장, gno를 기준으로 내림차순 정렬)
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestBook qGuestBook = QGuestBook.guestBook; // Querydsl용 객체 생성 (동적 처리용)

        String keyword = "9"; // 프론트 페이지에서 찾을 변수

        BooleanBuilder builder = new BooleanBuilder(); // 다중 조건 처리용 객체

        BooleanExpression expTitle = qGuestBook.title.contains(keyword); // title이 keyword인 표현식 생성
        BooleanExpression expContent = qGuestBook.content.contains(keyword); // content이 keyword인 표현식 생성
        BooleanExpression expAll = expTitle.or(expContent); // 2개의 표현식 합체

        builder.and(expAll); // 다중 조건 처리용 객체에 표현식 저장
        builder.and(qGuestBook.gno.gt(0l)); // where문 추가 (gno > 0)

        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable); // 페이지 타입의 객체 생성

        result.stream().forEach(guestBook -> {
            System.out.println("검색 결과 : " + guestBook);
        });
    }

}
