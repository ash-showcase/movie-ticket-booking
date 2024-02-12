package com.example.moviescheduleservice.service;

import com.example.moviescheduleservice.dto.CityWiseTheaterResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface MovieScheduleService {

    List<CityWiseTheaterResponseDTO> getCityWiseMovieSchedule(Long cityId, Long movieId, LocalDateTime date);
}
