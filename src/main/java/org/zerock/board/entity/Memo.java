package org.zerock.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_memo") // DB Table name 지정
@ToString // 객체가 아닌 문자로 전환
@Getter
@Builder // 메소드.필드(값).필드(값).builder; (빌더 패턴)
@AllArgsConstructor // new 클래스(모든 필드값 파라미터로 만듬 );, 빌더와 같이 사용
@NoArgsConstructor // new 클래스();, 빌더와 같이 사용
public class Memo  {
    // 엔티티는 DB에 테이블과 필드를 생성시켜 관리하는 객체
    // 엔티티를 이용해 JPA를 활성화하려면 application.properties에 필수로 항목 추가
    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;
    @Column(length = 200, nullable = false)
    private String memoText;


}
