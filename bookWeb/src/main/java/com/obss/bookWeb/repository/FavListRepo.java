package com.obss.bookWeb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.obss.bookWeb.model.FavList;

@Repository
public interface FavListRepo extends JpaRepository<FavList, Integer> {
    FavList findByUser_UserId(Integer userId);

}
