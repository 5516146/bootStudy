package org.zerock.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GuestBookDTO {
    private Long gno;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDate, modDate;
}
