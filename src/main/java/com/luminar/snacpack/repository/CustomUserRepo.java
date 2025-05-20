package com.luminar.snacpack.repository;

import com.luminar.snacpack.model.CustomUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomUserRepo extends JpaRepository<CustomUserDetails, Long> {

    @Query(value = "SELECT * FROM custom_user_details WHERE username ILIKE :username", nativeQuery = true)
    Optional<CustomUserDetails> findByUsername(@Param("username") String username);

}
