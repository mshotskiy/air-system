package com.shotskiy.airsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class FlightDateOnly {
    private Date started;

    public FlightDateOnly(Date started) {
        this.started = started;
    }
}
