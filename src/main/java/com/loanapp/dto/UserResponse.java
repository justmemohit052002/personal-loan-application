package com.loanapp.dto;

import com.loanapp.enums.Role;

public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private String mobileNumber;
    private Role role;
    private String city;
    private String state;

    public UserResponse(Long id, String fullName, String email,
                        String mobileNumber, Role role,
                        String city, String state) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.role = role;
        this.city = city;
        this.state = state;
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getMobileNumber() { return mobileNumber; }
    public Role getRole() { return role; }
    public String getCity() { return city; }
    public String getState() { return state; }
}