package com.panoseko.devtrack.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT m FROM User m WHERE m.username = ?1")
    Optional<User> findMemberByUsername(String username);

}
