package com.obss.bookWeb.service;

import com.obss.bookWeb.exception.FavListException;
import com.obss.bookWeb.model.FavList;

public interface FavListService {

    public FavList addProductToFavlist(Integer userId, Integer productId) throws FavListException;

    public void removeProductFromFavlist(Integer favlistId,Integer productId) throws FavListException;

    public void removeAllProductFromFavlist(Integer favlistId) throws FavListException;

    public FavList getAllFavlistProduct(Integer favlistId)throws FavListException;



}

