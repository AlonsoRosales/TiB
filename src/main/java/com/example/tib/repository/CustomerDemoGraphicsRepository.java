package com.example.tib.repository;

import com.example.tib.entity.CustomerDemoGraphics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDemoGraphicsRepository extends JpaRepository<CustomerDemoGraphics,String> {
}
