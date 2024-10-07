package com.obss.bookWeb.serviceImpl;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.obss.bookWeb.modelDto.UserUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.obss.bookWeb.constant.UserRole;
import com.obss.bookWeb.exception.UserException;
import com.obss.bookWeb.model.User;
import com.obss.bookWeb.modelDto.AdminDto;
import com.obss.bookWeb.modelDto.UserDto;
import com.obss.bookWeb.repository.UserRepo;
import com.obss.bookWeb.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String deleteUser(Integer userId) throws UserException {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found"));
        userRepository.delete(existingUser);
        return "User deleted successfully";
    }

    @Override
    public User getUserByEmailId(String emailId) throws UserException {
        return userRepository.findByEmail(emailId).orElseThrow(() -> new UserException("User not found"));
    }



    @Override
    public List<User> getAllUserDetails() throws UserException {
        List<User> existingAllUser = userRepository.findAll();
        if (existingAllUser.isEmpty()) {
            new UserException("User list is Empty");}
        return existingAllUser;
    }
    @Override
    public User addUser(UserDto customer) throws UserException {
        if (customer == null)
            throw new UserException("customer Can not be Null");
        Optional<User> findByEmail = userRepository.findByEmail(customer.getEmail());
        if (findByEmail.isPresent()) {
            System.out.println("inside add user method");
            throw new RuntimeException("Email alredy Register");
        }
        User newCustomer = new User();
        newCustomer.setEmail(customer.getEmail());
        newCustomer.setPassword(customer.getPassword());
        newCustomer.setFirstName(customer.getFirstName());
        newCustomer.setLastName(customer.getLastName());
        newCustomer.setRole(UserRole.ROLE_USER);
        newCustomer.setRegisterTime(LocalDateTime.now());
        return userRepository.save(newCustomer);
    }
    @Override
    public User addUserAdmin(AdminDto customer) throws UserException {
        if (customer == null)
            throw new UserException("admin Can not be Null");
        Optional<User> findByEmail = userRepository.findByEmail(customer.getEmail());
        if (findByEmail.isPresent()) {
            System.out.println("inside add user method");
            throw new RuntimeException("Email alredy Register");
        }
        User newAdmin = new User();
        newAdmin.setEmail(customer.getEmail());
        newAdmin.setPassword(customer.getPassword());
        newAdmin.setFirstName(customer.getFirstName());
        newAdmin.setLastName(customer.getLastName());
        newAdmin.setRole(UserRole.ROLE_ADMIN);
        newAdmin.setRegisterTime(LocalDateTime.now());
        return userRepository.save(newAdmin);
    }

    @Override
    public User getUserDetails(Integer userId) throws UserException {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
        return existingUser;
    }

    @Override
    public User updateUserDetails(Integer userId, UserUpdateDto customer) throws UserException {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found"));
        if (customer.getFirstName() != null && !customer.getFirstName().isEmpty()) {
            existingUser.setFirstName(customer.getFirstName());
        }
        if (customer.getLastName() != null && !customer.getLastName().isEmpty()) {
            existingUser.setLastName(customer.getLastName());
        }

        if (customer.getEmail() != null && !customer.getEmail().isEmpty()) {
            existingUser.setEmail(customer.getEmail());
        }
        if (customer.getPassword() != null && !customer.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(customer.getPassword()));
        }
        return userRepository.save(existingUser);
    }
}
