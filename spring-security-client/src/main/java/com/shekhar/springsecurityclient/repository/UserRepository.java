package com.shekhar.springsecurityclient.repository;

import com.shekhar.springsecurityclient.entity.User;
import com.shekhar.springsecurityclient.error.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    public List<User> findByFirstNameIgnoreCase(String firstName) throws UserNotFoundException;
}
