package com.church.ChurchApplication.repo;

import com.church.ChurchApplication.entity.Banners;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannersRepo extends JpaRepository<Banners,Integer> {
    List<Banners> findTop4ByOrderByCreatedAtDesc();
}
