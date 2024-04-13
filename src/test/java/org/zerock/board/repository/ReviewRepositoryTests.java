package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.entity.MMember;
import org.zerock.board.entity.Movie;
import org.zerock.board.entity.Review;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMovieReviews(){
        IntStream.rangeClosed(1, 200).forEach(i -> {
            Long mno = ((long)(Math.random() * 100) + 1);

            Long mid = ((long)(Math.random() * 100) + 1);

            MMember mMember = MMember.builder().mid(mid).build();
            Review movieReview = Review.builder()
                    .mmember(mMember)
                    .movie(Movie.builder().mno(mno).build())
                    .grade((int)(Math.random() * 5) + 1)
                    .text("이 형화에 대한 느낌 ... " + i)
                    .build();

            reviewRepository.save(movieReview);
        });
    }

    @Test
    public void testGetMovieReviews(){
        Movie movie = Movie.builder().mno(92l).build();

        List<Review> result = reviewRepository.findByMovie(movie);

        result.forEach(movieReview -> {
            System.out.println(movieReview.getReviewNum());
            System.out.println("\t" + movieReview.getGrade());
            System.out.println("\t" + movieReview.getText());
            System.out.println("\t" + movieReview.getMmember().getEmail());
            System.out.println("----------------------------------------");
        });
    }
}
