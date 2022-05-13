package com.lec.carrental.repository;

import com.lec.carrental.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {




}
