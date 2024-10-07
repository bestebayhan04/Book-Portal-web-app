package com.obss.bookWeb.service;

import java.util.List;
import com.obss.bookWeb.modelDto.UserUpdateDto;
import org.springframework.stereotype.Service;
import com.obss.bookWeb.exception.UserException;
import com.obss.bookWeb.model.User;
import com.obss.bookWeb.modelDto.AdminDto;
import com.obss.bookWeb.modelDto.UserDto;

@Service
public interface UserService {
    public User getUserByEmailId(String emailId)throws UserException;
    public User addUser(UserDto customer)  throws UserException;
    public User addUserAdmin(AdminDto admin	)  throws UserException;
    public User getUserDetails(Integer userId)throws UserException;
    public List<User> getAllUserDetails() throws UserException;
    public String deleteUser(Integer userId) throws UserException;
    User updateUserDetails(Integer userId, UserUpdateDto customer) throws UserException;
}
