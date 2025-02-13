package com.church.ChurchApplication.repo;

import com.church.ChurchApplication.entity.LiveVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveVideoRepo extends JpaRepository<LiveVideo, Integer> {
}
