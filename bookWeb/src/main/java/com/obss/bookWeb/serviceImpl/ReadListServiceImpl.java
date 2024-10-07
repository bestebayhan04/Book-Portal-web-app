package com.obss.bookWeb.serviceImpl;


import java.util.ArrayList;
import java.util.List;

import com.obss.bookWeb.model.*;
import com.obss.bookWeb.repository.*;
import com.obss.bookWeb.service.ReadListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.obss.bookWeb.exception.ReadListException;
import com.obss.bookWeb.exception.ProductException;
import com.obss.bookWeb.exception.UserException;

@Service
@RequiredArgsConstructor
public class ReadListServiceImpl implements ReadListService {

    private final ProductRepo productRepository;

    private final ReadListRepo readlistRepository;

    private final ReadListItemRepo readlistItemRepository;

    private final UserRepo userRepository;

    public ReadList addProductToReadlist(Integer userId, Integer productId) throws ReadListException {

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not available in Stock..."));

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User Not Found In Database"));

        if (existingUser.getReadList() != null) {
            System.out.println("readlist is alredy alloted...");
            ReadList userreadlist = existingUser.getReadList();

            List<ReadListItem> readlistItems = userreadlist.getReadListItems();
            if (readlistItems != null) {
                System.out.println("readlist item inside loop...");
                for (int i = 0; i < readlistItems.size(); i++) {
                    System.out.println("inside loop");
                    if (readlistItems.get(i).getProduct().getProductId() == productId &&
                            readlistItems.get(i).getReadList().getReadlistId() == userreadlist.getReadlistId()) {
                        throw new ReadListException("Product Already in the fav list.");
                    }
                }
            }
            ReadListItem readlistItem = new ReadListItem();
            readlistItem.setProduct(existingProduct);
            readlistItem.setReadList(userreadlist);
            userreadlist.getReadListItems().add(readlistItem);

            userreadlist.setTotalAmount(calculateCartTotal(readlistItems));
            readlistRepository.save(userreadlist);
            return userreadlist;

        } else {

            ReadList newreadlist = new ReadList();
            newreadlist.setUser(existingUser);
            existingUser.setReadList(newreadlist);


            ReadListItem readlistItem = new ReadListItem();

            readlistItem.setProduct(existingProduct);

            newreadlist.getReadListItems().add(readlistItem);
            readlistItem.setReadList(newreadlist);

            newreadlist.setTotalAmount(1.0);
            userRepository.save(existingUser);

            return existingUser.getReadList();
        }
    }

    private double calculateCartTotal(List<ReadListItem> readlistItems) {

        return readlistItems.size();
    }

    @Override
    public void removeProductFromReadlist(Integer readlistId, Integer productId) throws ReadListException {
        ReadList existingReadList = readlistRepository.findById(readlistId).orElseThrow(() -> new ReadListException("Readlist Not Found"));

        readlistItemRepository.removeProductFromReadlist(readlistId, productId);

        List<ReadListItem> list = existingReadList.getReadListItems();
        if (list != null) {
            existingReadList.setTotalAmount(calculateCartTotal(list));
        } else {
            existingReadList.setTotalAmount(0.0);

        }
        readlistRepository.save(existingReadList);

    }

    @Override
    public ReadList getAllReadlistProduct(Integer readlistId) throws ReadListException {
        ReadList existingReadList = readlistRepository.findById(readlistId).orElseThrow(() -> new ReadListException("Readlist Not Found"));

        List<ReadListItem> readlistItems = existingReadList.getReadListItems();
        List<Product> products = new ArrayList<>();

        for (ReadListItem readlistItem : readlistItems) {
            if (readlistItem.getReadList().getReadlistId() == readlistId) {
                Product product = readlistItem.getProduct();
                products.add(product);
            }
        }
        if (products.isEmpty()) {
            throw new ReadListException("Readlist is Empty...");
        }
        return existingReadList;
    }

    @Override
    public void removeAllProductFromReadlist(Integer readlistId) throws ReadListException {
        ReadList existingReadList = readlistRepository.findById(readlistId).orElseThrow(() -> new ReadListException("Readlist Not Found"));

        readlistItemRepository.removeAllProductFromReadlist(readlistId);

        existingReadList.setTotalAmount(0.0);
        readlistRepository.save(existingReadList);
    }
}

