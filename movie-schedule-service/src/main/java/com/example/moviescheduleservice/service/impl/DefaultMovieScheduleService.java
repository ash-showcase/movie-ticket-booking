package com.example.moviescheduleservice.service.impl;

import com.example.moviescheduleservice.dto.CityWiseTheaterResponseDTO;
import com.example.moviescheduleservice.repository.CityRepository;
import com.example.moviescheduleservice.repository.MovieRepository;
import com.example.moviescheduleservice.repository.MovieScheduleRepository;
import com.example.moviescheduleservice.repository.entity.movie.City;
import com.example.moviescheduleservice.repository.entity.movie.Movie;
import com.example.moviescheduleservice.repository.entity.movie.MovieScreening;
import com.example.moviescheduleservice.repository.entity.movie.Theater;
import com.example.moviescheduleservice.service.MovieScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DefaultMovieScheduleService implements MovieScheduleService {

    @Autowired
    public DefaultMovieScheduleService(MovieScheduleRepository movieScheduleRepository, CityRepository cityRepository, MovieRepository movieRepository) {
        this.movieScheduleRepository = movieScheduleRepository;
        this.cityRepository = cityRepository;
        this.movieRepository = movieRepository;
    }

    private final MovieScheduleRepository movieScheduleRepository;
    private final CityRepository cityRepository;
    private final MovieRepository movieRepository;

    @Override
    public List<CityWiseTheaterResponseDTO> getCityWiseMovieSchedule(Long cityId, Long movieId, LocalDateTime date) {
        Optional<City> cityOptional = cityRepository.findById(cityId);
        City city = cityOptional.orElseThrow(() -> new IllegalArgumentException(String.format("City ID: %d does not exist", cityId)));

        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        Movie movie = movieOptional.orElseThrow(() -> new IllegalArgumentException(String.format("Movie ID: %d does not exist", movieId)));

        date = date.with(LocalTime.MIDNIGHT);
        LocalDateTime nextDay = date.withDayOfYear(date.getDayOfYear()+1);
        List<MovieScreening> movieScreening = movieScheduleRepository.findMovieSchedule(cityId, movieId, date, nextDay);

        Map<Theater, List<LocalDateTime>> theaterMap = movieScreening.stream().collect(Collectors.groupingBy(MovieScreening::getTheater, Collectors.mapping(MovieScreening::getStartTime, Collectors.toList())));
        Set<CityWiseTheaterResponseDTO.TheaterDTO> collect = theaterMap.entrySet().stream().map(theater -> new CityWiseTheaterResponseDTO.TheaterDTO(theater.getKey().getName(), theater.getValue().stream().map(time -> new CityWiseTheaterResponseDTO.ShowDTO( time, CityWiseTheaterResponseDTO.ShowStatus.AVAILABLE)).collect(Collectors.toList()))).collect(Collectors.toSet());
        CityWiseTheaterResponseDTO response = new CityWiseTheaterResponseDTO(city.getName(), movie.getTitle(), collect);

        return Collections.singletonList(response);
    }
}
