package com.example.moviescheduleservice;
import com.example.moviescheduleservice.repository.CityRepository;
import com.example.moviescheduleservice.repository.MovieScheduleRepository;
import com.example.moviescheduleservice.repository.entity.movie.City;
import com.example.moviescheduleservice.repository.entity.movie.Movie;
import com.example.moviescheduleservice.repository.entity.movie.MovieScreening;
import com.example.moviescheduleservice.repository.entity.movie.Theater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
public class TestDataInitializer implements ApplicationRunner {

    private final MovieScheduleRepository movieRepository;
    private final CityRepository cityRepository;


    @Autowired
    public TestDataInitializer(MovieScheduleRepository movieRepository, CityRepository cityRepository) {
        this.movieRepository = movieRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        City bengaluru = new City();
        bengaluru.setName("Bengaluru");

        City mumbai = new City();
        mumbai.setName("Mumbai");

        Theater ban1 = new Theater();
        ban1.setCity(bengaluru);
        ban1.setName("Bellandur");
        Theater ban2 = new Theater();
        ban2.setCity(bengaluru);
        ban2.setName("HSR");

        Theater mum1 = new Theater();
        mum1.setCity(mumbai);
        mum1.setName("Andheri");
        Theater mum2 = new Theater();
        mum2.setCity(mumbai);
        mum2.setName("Bandra");

        Movie movie1 = new Movie();
        movie1.setTitle("Inception");
        movie1.setDurationMinutes(120);

        Movie movie2 = new Movie();
        movie2.setTitle("Interstellar");
        movie2.setDurationMinutes(150);

        Movie movie3 = new Movie();
        movie3.setTitle("The Dark Knight");
        movie3.setDurationMinutes(110);

        Movie movie4 = new Movie();
        movie4.setTitle("Ocean 12");
        movie4.setDurationMinutes(180);

        MovieScreening screening = new MovieScreening();
        screening.setStartTime(LocalDateTime.parse("2024-02-10T11:00:00"));
        screening.setTheater(ban1);
        screening.setMovie(movie1);

        MovieScreening screening1 = new MovieScreening();
        screening1.setStartTime(LocalDateTime.parse("2024-02-10T19:00:00"));
        screening1.setTheater(ban1);
        screening1.setMovie(movie1);

        MovieScreening screening2 = new MovieScreening();
        screening2.setStartTime(LocalDateTime.parse("2024-02-10T16:00:00"));
        screening2.setTheater(ban2);
        screening2.setMovie(movie1);

        MovieScreening screening3 = new MovieScreening();
        screening3.setStartTime(LocalDateTime.parse("2024-02-11T16:30:00"));
        screening3.setTheater(ban2);
        screening3.setMovie(movie1);

        MovieScreening screening4 = new MovieScreening();
        screening4.setStartTime(LocalDateTime.parse("2024-02-10T11:00:00"));
        screening4.setTheater(mum1);
        screening4.setMovie(movie1);

        MovieScreening screening5 = new MovieScreening();
        screening5.setStartTime(LocalDateTime.parse("2024-02-10T18:00:00"));
        screening5.setTheater(mum2);
        screening5.setMovie(movie1);

        movieRepository.saveAll(List.of(screening, screening1, screening2, screening3, screening4, screening5));
    }
}