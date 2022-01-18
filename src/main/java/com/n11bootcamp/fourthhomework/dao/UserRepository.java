package com.n11bootcamp.fourthhomework.dao;

import com.n11bootcamp.fourthhomework.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
