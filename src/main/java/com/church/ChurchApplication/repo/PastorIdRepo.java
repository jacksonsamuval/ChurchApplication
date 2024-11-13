package com.church.ChurchApplication.repo;

import com.church.ChurchApplication.entity.PastorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PastorIdRepo extends JpaRepository<PastorId, Integer> {
    PastorId findByEmail(String email);
}
