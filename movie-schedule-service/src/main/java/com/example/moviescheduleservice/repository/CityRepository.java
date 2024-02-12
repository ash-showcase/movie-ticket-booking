package com.example.moviescheduleservice.repository;

import com.example.moviescheduleservice.repository.entity.movie.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
