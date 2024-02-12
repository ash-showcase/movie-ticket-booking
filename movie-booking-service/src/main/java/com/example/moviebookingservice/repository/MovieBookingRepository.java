package com.example.moviebookingservice.repository;

import com.example.moviebookingservice.repository.entities.movie.MovieScreening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovieBookingRepository extends JpaRepository<MovieScreening, Long> {

    @Query(value = "SELECT sc FROM MovieScreening sc JOIN FETCH sc.movie m " +
            "JOIN FETCH sc.theater t JOIN FETCH t.city c WHERE m.id=:movieId and c.id=:cityId" +
            " and sc.startTime BETWEEN :fromDate and :toDate")
    List<MovieScreening> findMovieSchedule(@Param("cityId") Long cityId, @Param("movieId")Long movieId, @Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

}
