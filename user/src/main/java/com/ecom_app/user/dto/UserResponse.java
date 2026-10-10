package com.ecom_app.user.dto;

import com.ecom_app.user.model.UserRole;
import lombok.Data;

@Data
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role ;
    private AddressDTO address;


}
