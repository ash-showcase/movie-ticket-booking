package com.example.moviebookingservice.repository;

import com.example.moviebookingservice.repository.entities.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
