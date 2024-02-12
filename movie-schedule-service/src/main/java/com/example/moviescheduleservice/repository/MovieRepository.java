package com.example.moviescheduleservice.repository;

import com.example.moviescheduleservice.repository.entity.movie.City;
import com.example.moviescheduleservice.repository.entity.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
