package org.zerock.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // 테이블 자동 관리
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString // 객체가 아닌 문자열로 변환
public class GuestBook extends BaseEntity { // BaseEntity를 상속받아 날짜 시간 자동 처리
    
    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MariaDB 자동 번호 생성
    private Long gno; // 방명록에서 사용할 번호
    @Column(length = 100, nullable = false) // 문자 100글자, not null
    private String title;
    @Column(length = 1500, nullable = false) // 문자 1500글자, not null
    private String content;
    @Column(length = 50, nullable = false) // 문자 50글자, not null
    private String writer;

    // Setter 역할 (수정)
    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }


}
