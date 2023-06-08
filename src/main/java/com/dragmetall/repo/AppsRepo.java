package com.dragmetall.repo;

import com.dragmetall.model.Apps;
import com.dragmetall.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppsRepo extends JpaRepository<Apps, Long> {
    List<Apps> findAllByUser(User user);
}
