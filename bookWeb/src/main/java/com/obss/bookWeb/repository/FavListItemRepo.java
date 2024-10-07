package com.obss.bookWeb.repository;

import com.obss.bookWeb.model.FavListItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavListItemRepo extends JpaRepository<FavListItem,Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM FavListItem ci WHERE ci.favList.favlistId = :favlistId AND ci.product.productId = :productId")
    public void removeProductFromFavlist(@Param("favlistId") Integer favlistId, @Param("productId") Integer productId);

    @Transactional
    @Modifying
    @Query("DELETE FROM FavListItem ci WHERE ci.favList.favlistId = :favlistId ")
    public void removeAllProductFromFavlist(@Param("favlistId") Integer favlistId);
}
