package org.zerock.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.board.entity.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT m, mi, avg(coalesce(r.grade, 0)), COUNT(r) FROM Movie m " +
            "LEFT OUTER JOIN MovieImage mi ON mi.movie = m " +
            "LEFT OUTER JOIN Review r ON r.movie = m GROUP BY m")
    Page<Object[]> getListPage(Pageable pageable); // 페이지 처리

    @Query("SELECT m, mi, AVG(coalesce(r.grade, 0)), COUNT(r) " +
            " FROM Movie  m LEFT OUTER JOIN MovieImage mi ON mi.movie = m " +
            " LEFT OUTER JOIN Review r ON r.movie = m " +
            " WHERE m.mno = :mno GROUP BY mi")
    List<Object[]> getMovieWithAll(Long mno); // 특정 영화 조회

}
