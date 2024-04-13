package org.zerock.board.service;

import org.zerock.board.dto.GuestBookDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.GuestBook;

public interface GuestBookService {
    Long register (GuestBookDTO dto);

    PageResultDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO requestDTO);

     GuestBookDTO read(Long gno);

     void remove(Long gno);

     void modify(GuestBookDTO dto);

    default GuestBook dtoToEntity(GuestBookDTO dto){
        GuestBook entity = GuestBook.builder()
                .gno(dto.getGno()).title(dto.getTitle()).content(dto.getContent()).writer(dto.getWriter())
                .build();

        return entity;
    }

    default GuestBookDTO entityToDto(GuestBook entity){
        GuestBookDTO dto = GuestBookDTO.builder()
                .gno(entity.getGno()).title(entity.getTitle()).content(entity.getContent()).writer(entity.getWriter())
                .modDate(entity.getModDate()).regDate(entity.getRegDate())
                .build();

        return dto;
    }
}
