package com.example.moviebookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeatDTO {
    private Integer rowNum;
    private String seatNum;

}