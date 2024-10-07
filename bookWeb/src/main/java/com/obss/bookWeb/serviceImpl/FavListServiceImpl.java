package com.obss.bookWeb.serviceImpl;


import java.util.ArrayList;
import java.util.List;

import com.obss.bookWeb.service.FavListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.obss.bookWeb.exception.FavListException;
import com.obss.bookWeb.exception.ProductException;
import com.obss.bookWeb.exception.UserException;
import com.obss.bookWeb.model.FavList;
import com.obss.bookWeb.model.FavListItem;
import com.obss.bookWeb.model.Product;
import com.obss.bookWeb.model.User;
import com.obss.bookWeb.repository.FavListItemRepo;
import com.obss.bookWeb.repository.FavListRepo;
import com.obss.bookWeb.repository.ProductRepo;
import com.obss.bookWeb.repository.UserRepo;

@Service
@RequiredArgsConstructor
public class FavListServiceImpl implements FavListService {

    private final ProductRepo productRepository;

    private final FavListRepo favlistRepository;

    private final FavListItemRepo favlistItemRepository;

    private final UserRepo userRepository;

    public FavList addProductToFavlist(Integer userId, Integer productId) throws FavListException {

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not available in Stock..."));

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User Not Found In Database"));

        if (existingUser.getFavList() != null) {
            System.out.println("favlist is alredy alloted...");
            FavList userfavlist = existingUser.getFavList();

            List<FavListItem> favlistItems = userfavlist.getFavListItems();
            if (favlistItems != null) {
                System.out.println("favlist item inside loop...");
                for (int i = 0; i < favlistItems.size(); i++) {
                    System.out.println("inside loop");
                    if (favlistItems.get(i).getProduct().getProductId() == productId &&
                            favlistItems.get(i).getFavList().getFavlistId() == userfavlist.getFavlistId()) {
                        throw new FavListException("Product Already in the fav list.");
                    }
                }
            }
            FavListItem favlistItem = new FavListItem();
            favlistItem.setProduct(existingProduct);

            favlistItem.setFavList(userfavlist);
            userfavlist.getFavListItems().add(favlistItem);

            userfavlist.setTotalAmount(calculateCartTotal(favlistItems));
            favlistRepository.save(userfavlist);
            return userfavlist;

        } else {

            FavList newfavlist = new FavList();
            newfavlist.setUser(existingUser);
            existingUser.setFavList(newfavlist);


            FavListItem favlistItem = new FavListItem();

            favlistItem.setProduct(existingProduct);

            newfavlist.getFavListItems().add(favlistItem);
            favlistItem.setFavList(newfavlist);

            newfavlist.setTotalAmount(1.0);
            userRepository.save(existingUser);

            return existingUser.getFavList();
        }
    }

    private double calculateCartTotal(List<FavListItem> favlistItems) {

        return favlistItems.size();
    }

    @Override
    public void removeProductFromFavlist(Integer favlistId, Integer productId) throws FavListException {
        FavList existingFavList = favlistRepository.findById(favlistId).orElseThrow(() -> new FavListException("Favlist Not Found"));

        favlistItemRepository.removeProductFromFavlist(favlistId, productId);

        List<FavListItem> list = existingFavList.getFavListItems();
        if (list != null) {
            existingFavList.setTotalAmount(calculateCartTotal(list));
        } else {
            existingFavList.setTotalAmount(0.0);

        }
        favlistRepository.save(existingFavList);

    }

    @Override
    public FavList getAllFavlistProduct(Integer favlistId) throws FavListException {
        FavList existingFavList = favlistRepository.findById(favlistId).orElseThrow(() -> new FavListException("Favlist Not Found"));

        List<FavListItem> favlistItems = existingFavList.getFavListItems();
        List<Product> products = new ArrayList<>();

        for (FavListItem favlistItem : favlistItems) {
            if (favlistItem.getFavList().getFavlistId() == favlistId) {
                Product product = favlistItem.getProduct();
                products.add(product);
            }
        }
        if (products.isEmpty()) {
            throw new FavListException("Favlist is Empty...");
        }
        return existingFavList;
    }

    @Override
    public void removeAllProductFromFavlist(Integer favlistId) throws FavListException {
        FavList existingFavList = favlistRepository.findById(favlistId).orElseThrow(() -> new FavListException("Favlist Not Found"));

        favlistItemRepository.removeAllProductFromFavlist(favlistId);

        existingFavList.setTotalAmount(0.0);
        favlistRepository.save(existingFavList);
    }

}