package com.example.moviescheduleservice.controller;

import com.example.moviescheduleservice.dto.CityWiseTheaterResponseDTO;
import com.example.moviescheduleservice.service.MovieScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/screenings")
public class MovieScheduleController {

    @Autowired
    private MovieScheduleService movieScheduleService;

    @GetMapping(path = "/city/{city}/movie/{movie}/date/{date}")
    @ResponseBody
    @Cacheable(value = "movieScheduleCache", key = "#city + '_' + #movie + '_' + #date")
    public List<CityWiseTheaterResponseDTO> getScreenings(@PathVariable String city, @PathVariable String movie, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        return movieScheduleService.getCityWiseMovieSchedule(Long.parseLong(city), Long.parseLong(movie), date);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleRedirectException(final Exception e, final HttpServletRequest request) throws Exception {
        log.error("Error handling request", e);
        //Probably not send the internal error message in the response.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error handling request");
    }

}
