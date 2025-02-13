package com.church.ChurchApplication.repo;

import com.church.ChurchApplication.entity.VideoStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<VideoStorage, Integer> {

    List<VideoStorage> findAllByOrderByCreatedAtDesc();
    List<VideoStorage> findTop5ByUrlIsNotNullOrderByCreatedAtDesc();
}
