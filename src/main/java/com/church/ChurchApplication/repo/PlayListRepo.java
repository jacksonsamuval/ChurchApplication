package com.church.ChurchApplication.repo;

import com.church.ChurchApplication.entity.PlayList;
import com.church.ChurchApplication.entity.Ulogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayListRepo extends JpaRepository<PlayList,Integer> {

    List<PlayList> findByUlogin(Ulogin ulogin);
}
