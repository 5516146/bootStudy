package org.zerock.board.service;

import org.zerock.board.dto.ReviewDTO;
import org.zerock.board.entity.MMember;
import org.zerock.board.entity.Movie;
import org.zerock.board.entity.Review;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> getListOfMovie(Long mno);

    Long register(ReviewDTO movieReviewDTO);

    void modify(ReviewDTO movieReviewDTO);

    void remove(Long reviewnum);

    default Review dtoToEntity(ReviewDTO movieReviewDTO){
        Review movieReview = Review.builder()
                .reviewNum(movieReviewDTO.getReviewnum())
                .movie(Movie.builder().mno(movieReviewDTO.getMno()).build())
                .mmember(MMember.builder().mid(movieReviewDTO.getMid()).build())
                .grade(movieReviewDTO.getGrade())
                .text(movieReviewDTO.getText())
                .build();

        return movieReview;
    }

    default ReviewDTO entityToDto(Review movieReview){
        ReviewDTO movieReviewDTO = ReviewDTO.builder()
                .reviewnum(movieReview.getReviewNum())
                .mno(movieReview.getMovie().getMno())
                .mid(movieReview.getMmember().getMid())
                .nickname(movieReview.getMmember().getNickname())
                .email(movieReview.getMmember().getEmail())
                .grade(movieReview.getGrade())
                .text(movieReview.getText())
                .regDate(movieReview.getRegDate())
                .modDate(movieReview.getModDate())
                .build();

        return movieReviewDTO;
    }
}
