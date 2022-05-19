package com.lec.carrental.service;

import com.lec.carrental.domain.User;
import com.lec.carrental.helper.ExcelHelper;
import com.lec.carrental.repository.CarRepository;
import com.lec.carrental.repository.ReservationRepository;
import com.lec.carrental.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ExcelService {

    UserRepository userRepository;
    CarRepository carRepository;
    ReservationRepository reservationRepository;


    public ByteArrayInputStream loadUser() throws IOException {
        List<User> users = userRepository.findAll();

        return ExcelHelper.userExcel(users);
    }

}
