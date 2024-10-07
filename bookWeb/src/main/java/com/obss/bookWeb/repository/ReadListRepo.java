package com.obss.bookWeb.repository;

import com.obss.bookWeb.model.FavList;
import com.obss.bookWeb.model.ReadList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadListRepo extends JpaRepository<ReadList,Integer> {

    ReadList findByUser_UserId(Integer userId);
}
