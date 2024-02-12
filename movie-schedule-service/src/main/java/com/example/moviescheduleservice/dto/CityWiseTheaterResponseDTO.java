package com.example.moviescheduleservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class CityWiseTheaterResponseDTO {

    private String cityName;
    private String movieName;
    private Set<TheaterDTO> theaters;

    @Data
    @AllArgsConstructor
    public static class TheaterDTO {
        private String name;
        private List<ShowDTO> shows;
    }


    @Data
    @AllArgsConstructor
    public static class ShowDTO {
        private LocalDateTime time;
        private ShowStatus Status;
    }

    public enum ShowStatus{
        AVAILABLE, ALMOST_FULL, FILLING_FAST, SOLD_OUT;
    }
}
