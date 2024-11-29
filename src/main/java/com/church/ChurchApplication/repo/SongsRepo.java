package com.church.ChurchApplication.repo;

import com.church.ChurchApplication.entity.Songs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongsRepo extends JpaRepository<Songs,Integer> {
}
