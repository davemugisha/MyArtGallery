package com.example.MyArtGallery.Repository;

import com.example.MyArtGallery.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInterface extends JpaRepository<User,Integer> {
    Optional<User> findByUserName(String userName);

    Optional<User> findByEmail(String email);

    Optional<User> findByUserNameOrEmail(String userName, String email);
}
