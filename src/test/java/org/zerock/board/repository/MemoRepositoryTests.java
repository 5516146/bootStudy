package org.zerock.board.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.board.entity.Memo;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest // 테스트 코드임 명시
public class MemoRepositoryTests {

    @Autowired // 생성자 자동 주입
    MemoRepository memoRepository;

    @Test
    public void testClass() {
        // 객체 주입 테스트
        System.out.println(memoRepository.getClass().getName());
//        memoRepository에 생성된 클래스명과 이름을 알아옴
//        결과 : jdk.proxy3.$Proxy115 (동적 프록시 : 인터페이스 실행 구현 클래스)
    }

    @Test
    public void testInsertUmmies() {
        // 더미 데이터 추가
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder()
                    .memoText("sample 메모들 ........" + i)
                    .build(); // Memo 클래스에 memoText(1 ~ 100) 생성

            memoRepository.save(memo);
            // save(JPA 상속으로 사용), 없으면 insert, 있으면 update
        });
    }

    @Test
    public void testSelect() {
        // 데이터 가져오기 (mno 사용)
        Long mno = 100l;

        Optional<Memo> result = memoRepository.findById(mno);
//        findById : 쿼리 선 실행 후 결과 출력
//        import java.util.Optional;
        if (result.isPresent()) {
            System.out.println("=====================================");
            Memo memo = result.get();
            System.out.println(memo);
            System.out.println("=====================================");
        }
    }

    @Test
    @Transactional
    public void testSelect2() {
        Long mno = 100l;
        Memo memo = memoRepository.getOne(mno); 
//        getOne : 보안 때문에 현재 차단, 호출 시 쿼리문 실행, @Transactional 필수

        System.out.println("=====================================");
        System.out.println(memo);
        System.out.println("=====================================");

//        no session error : @Transactional (jakarta)
    }

    @Test
    public void testUpdate(){
        Memo memo = Memo.builder()
                .mno(300l)
                .memoText("수정된 텍스트 ............ ")
                .build();

        System.out.println(memoRepository.save(memo));
    }

    @Test
    public void testDelete(){
        Long mno = 300l;
        memoRepository.deleteById(mno);
    }

    @Test
    public void testPageDefault(){
        Pageable pageable = PageRequest.of(0, 10); // 내장된 페이징 처리

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);
    }

    @Test
    public void testPageDefaults() {
        // jpa에 내장된 페이징, 정렬 기법 활용
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2); // sort1을 실행 후 sort2 실행

        Pageable pageable = PageRequest.of(0, 10, sortAll);

        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);


        //Hibernate:
        //    select
        //        m1_0.mno,
        //        m1_0.memo_text
        //    from
        //        tbl_memo m1_0
        //    limit
        //        ?, ?
        //Hibernate:
        //    select
        //        count(m1_0.mno)
        //    from
        //        tbl_memo m1_0
        //Page 1 of 10 containing org.zerock.boardboot.entity.Memo instances

        System.out.println("---------------------------------------");

        System.out.println("Total Pages: "+result.getTotalPages()); // 총 몇 페이지

        System.out.println("Total Count: "+result.getTotalElements()); // 전체 개수

        System.out.println("Page Number: "+result.getNumber()); // 현재 페이지 번호

        System.out.println("Page Size: "+result.getSize()); // 페이지당 데이터 개수

        System.out.println("has next page?: "+result.hasNext());    // 다음 페이지 존재 여부

        System.out.println("first page?: "+result.isFirst());  //  시작페이지 여부

        //Page 1 of 10 containing org.zerock.boardboot.entity.Memo instances
        //---------------------------------------
        //Total Pages: 10
        //Total Count: 99
        //Page Number: 0
        //Page Size: 10
        //has next page?: true
        //first page?: true

        System.out.println("-----------------------------------");

        for(Memo memo : result.getContent()) {
            System.out.println(memo);
            //-----------------------------------
            //Memo(mno=1, memoText=Sample....1)
            //Memo(mno=2, memoText=Sample....2)
            //Memo(mno=3, memoText=Sample....3)
            //Memo(mno=4, memoText=Sample....4)
            //Memo(mno=5, memoText=Sample....5)
            //Memo(mno=6, memoText=Sample....6)
            //Memo(mno=7, memoText=Sample....7)
            //Memo(mno=8, memoText=Sample....8)
            //Memo(mno=9, memoText=Sample....9)
            //Memo(mno=10, memoText=Sample....10)
        }
    }

}
