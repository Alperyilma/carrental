package com.lec.carrental.service;

import com.lec.carrental.domain.Car;
import com.lec.carrental.domain.Reservation;
import com.lec.carrental.domain.User;
import com.lec.carrental.domain.enumeration.ReservationStatus;
import com.lec.carrental.dto.ReservationDTO;
import com.lec.carrental.exception.BadRequestException;
import com.lec.carrental.exception.ResourceNotFoundException;
import com.lec.carrental.repository.CarRepository;
import com.lec.carrental.repository.ReservationRepository;
import com.lec.carrental.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final static String USER_NOT_FOUND_MSG = "user with id %d not found";
    private static final String CAR_NOT_FOUND_MSG = "car with id %d not found";
    private static final String RESERVATION_NOT_FOUND_MSG = "reservation with %d not found";



    public ReservationDTO findById(Long id) {
        return reservationRepository.findByIdOrderById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(RESERVATION_NOT_FOUND_MSG, id)));
    }

    public List<ReservationDTO> fetchAllReservations() {
        return reservationRepository.findAllBy();
    }

    public ReservationDTO findByIdAndUserId(Long id, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));

        return reservationRepository.findByIdAndUserId(id, user)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(RESERVATION_NOT_FOUND_MSG, id)));
    }

    public List<ReservationDTO> findAllByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));

        return reservationRepository.findAllByUserId(user);
    }

    public void addReservation(Reservation reservation, Long userId, Car carId) throws BadRequestException {
        boolean checkStatus = carAvailability(carId.getId(), reservation.getPickUpTime(), reservation.getDropOffTime());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));

        if (!checkStatus){
            reservation.setStatus(ReservationStatus.CREATED);
        }else{
            throw new BadRequestException("Car is already reserved! Please choose another");
        }

        reservation.setCarId(carId);
        reservation.setUserId(user);

        Double totalPrice = totalPrice(reservation.getPickUpTime(), reservation.getDropOffTime(), carId.getId());

        reservation.setTotalPrice(totalPrice);

        reservationRepository.save(reservation);
    }

    public boolean carAvailability(Long carId, LocalDateTime pickUpTime, LocalDateTime dropOffTime){
        List<Reservation> checkStatus = reservationRepository
                .checkStatus(carId, pickUpTime, dropOffTime, ReservationStatus.DONE, ReservationStatus.CANCELED);

        return  checkStatus.size() > 0;
    }

    private Double totalPrice(LocalDateTime pickUpTime, LocalDateTime dropOffTime, Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(CAR_NOT_FOUND_MSG, carId)));

        Long hours = (new Reservation()).getTotalHours(pickUpTime, dropOffTime);

        return car.getPricePerHour() * hours;
    }



}
