package com.lec.carrental.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lec.carrental.domain.Car;
import com.lec.carrental.domain.Reservation;
import com.lec.carrental.domain.User;
import com.lec.carrental.domain.enumeration.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ReservationDTO {

    private Long id;
    private CarDTO carId;
    private Long userId;
    private LocalDateTime pickUpTime;
    private LocalDateTime dropOffTime;
    private String pickUpLocation;
    private String dropOffLocation;
    private ReservationStatus status;
    private Double totalPrice;

    public ReservationDTO(Reservation reservation) {
        this.id = reservation.getId();
        this.carId = new CarDTO(reservation.getCarId());
        this.userId = reservation.getUserId().getId();
        this.pickUpTime = reservation.getPickUpTime();
        this.dropOffTime = reservation.getDropOffTime();
        this.pickUpLocation = reservation.getPickUpLocation();
        this.dropOffLocation = reservation.getDropOffLocation();
        this.status = reservation.getStatus();
        this.totalPrice = reservation.getTotalPrice();
    }
}
