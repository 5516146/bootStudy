package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.entity.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying
    @Query("DELETE FROM Reply r WHERE r.board.bno = :bno")
    void deleteByBno(Long bno);

    List<Reply> getRepliesByBoardOrderByRno(Board board);
}
