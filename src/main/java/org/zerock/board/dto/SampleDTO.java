package org.zerock.board.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true) // 빌더 패턴
public class SampleDTO {
    // DTO는 프론트에서 자바까지의 객체를 담당
    // Entity는 DB에서 자바까지의 영속성을 담당
    // 나중에는 dtotoentity, edtitytodto라는 메소드가 전이 역할 진행

    private Long sno;
    private String first;
    private String last;
    private LocalDateTime regTime;
}
