package com.lec.carrental.service;

import com.lec.carrental.domain.Car;
import com.lec.carrental.domain.FileDB;
import com.lec.carrental.exception.BadRequestException;
import com.lec.carrental.exception.ResourceNotFoundException;
import com.lec.carrental.repository.CarRepository;
import com.lec.carrental.repository.FileDBRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class CarService {

    private final CarRepository carRepository;
    private final FileDBRepository fileDBRepository;
    private final static String IMAGE_NOT_FOUND_MSG = "image with id %s not found";

    public void add(Car car, String imageId) throws BadRequestException {
        FileDB fileDB = fileDBRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(IMAGE_NOT_FOUND_MSG, imageId)));

        Set<FileDB> fileDBs = new HashSet<>();
        fileDBs.add(fileDB);

        car.setImage(fileDBs);

        carRepository.save(car);
    }
}
