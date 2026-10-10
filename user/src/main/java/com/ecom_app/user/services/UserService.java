package com.ecom_app.user.services;


import com.ecom_app.user.model.Address;
import com.ecom_app.user.model.User;
import com.ecom_app.user.dto.UserRequest;
import com.ecom_app.user.dto.UserResponse;
import com.ecom_app.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<UserResponse> fetchAllUsers() {
        return  userRepository.findAll().stream().map(u -> modelMapper.map(u, UserResponse.class)).toList();
    }

    public UserResponse fetchUsers(String id) {

        User user = userRepository.findById(String.valueOf(id)).orElse(null);

        return modelMapper.map(user, UserResponse.class);}

    public String addUser(UserRequest userRequest) {

        User user = modelMapper.map(userRequest, User.class);
        userRepository.save(user);
        return "User added successfully";
    }
    public boolean updateUser(String id, UserRequest updatedUser) {
        return userRepository.findById(String.valueOf(id)).map(existinguser ->{
            UpdateUserFromRequest(existinguser,updatedUser);
            userRepository.save(existinguser);
            return true;
        }).orElse(false);
    }

    public void UpdateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

            if(userRequest.getAddress() != null) {
                Address address = new Address();
                address.setStreet(userRequest.getAddress().getStreet());
                address.setState(userRequest.getAddress().getState());
                address.setCity(userRequest.getAddress().getCity());
                address.setCountry(userRequest.getAddress().getCountry());
                address.setZipcode(userRequest.getAddress().getZipcode());

            }

    }
}
