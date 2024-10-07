package com.obss.bookWeb.service;

import com.obss.bookWeb.exception.ReadListException;
import com.obss.bookWeb.model.ReadList;

public interface ReadListService {

    public ReadList addProductToReadlist(Integer userId, Integer productId) throws ReadListException;

    public void removeProductFromReadlist(Integer readlistId,Integer productId) throws ReadListException;

    public void removeAllProductFromReadlist(Integer readlistId) throws ReadListException;

    public ReadList getAllReadlistProduct(Integer readlistId)throws ReadListException;


}
