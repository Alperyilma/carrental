package com.lec.carrental.repository;

import com.lec.carrental.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

//    @Query("SELECT u FROM User u Where u.email = ?1")
    Optional<User> findByEmail(String email);

}
