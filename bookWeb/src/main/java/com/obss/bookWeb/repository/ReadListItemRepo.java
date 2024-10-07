package com.obss.bookWeb.repository;

import com.obss.bookWeb.model.ReadListItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadListItemRepo extends JpaRepository<ReadListItem,Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM ReadListItem ci WHERE ci.readList.readlistId = :readlistId AND ci.product.productId = :productId")
    public void removeProductFromReadlist(@Param("readlistId") Integer readlistId, @Param("productId") Integer productId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ReadListItem ci WHERE ci.readList.readlistId = :readlistId ")
    public void removeAllProductFromReadlist(@Param("readlistId") Integer readlistId);
}
