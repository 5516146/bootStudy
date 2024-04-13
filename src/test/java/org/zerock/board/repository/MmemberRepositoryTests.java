package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.entity.MMember;

import java.util.stream.IntStream;

@SpringBootTest
public class MmemberRepositoryTests {

    @Autowired
    private MmemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;


    @Test
    public void insertMmembers() {

        IntStream.rangeClosed(1,100).forEach(i -> {
            MMember mmember = MMember.builder()
                    .email("r"+i +"@zerock.org")
                    .pw("1111")
                    .nickname("reviewer"+i).build();
            memberRepository.save(mmember);
        });
    }

    @Commit
    @Transactional
    @Test
    public void testDeleteMmember() {

        Long mid = 1L; //Member의 mid

        MMember mmember = MMember.builder().mid(mid).build();

        //기존
        //memberRepository.deleteById(mid);
        //reviewRepository.deleteByMember(mmember);

        //순서 주의
        reviewRepository.deleteByMember(mmember);
        memberRepository.deleteById(mid);
    }


}