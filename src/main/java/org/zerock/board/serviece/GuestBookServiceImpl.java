package org.zerock.board.serviece;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.board.dto.GuestBookDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.GuestBook;
import org.zerock.board.entity.QGuestBook;
import org.zerock.board.repository.GuestBookRepository;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor // repository를 주입하기 위해서 사용
public class GuestBookServiceImpl implements GuestBookService {

    private final GuestBookRepository repository; // 반드시 final로 선언

    @Override
    public Long register(GuestBookDTO dto){
        log.info("DTO ------------------------------------ ");
        log.info(dto);

        GuestBook entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO); // 검색 조건 처리 부분

        Page<GuestBook> result = repository.findAll(booleanBuilder, pageable); // Querydsl 사용

        Function<GuestBook, GuestBookDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public GuestBookDTO read(Long gno) {
        Optional<GuestBook> result = repository.findById(gno);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestBookDTO dto) {
        Optional<GuestBook> result = repository.findById(dto.getGno());

        if(result.isPresent()){
            GuestBook entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO){
        String type = requestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGuestBook qGuestBook = QGuestBook.guestBook;
        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qGuestBook.gno.gt(0l);

        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0){ // 검색 조건이 없는 경우
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")){ // 제목 검색
            conditionBuilder.or(qGuestBook.title.contains(keyword));
        }
        if (type.contains("c")){ // 내용 검색
            conditionBuilder.or(qGuestBook.content.contains(keyword));
        }
        if (type.contains("w")) { // 작성자 검색
            conditionBuilder.or(qGuestBook.writer.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder); // 모든 조건 통합

        return booleanBuilder;
    }
}
