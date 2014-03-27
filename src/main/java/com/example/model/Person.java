package com.example.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

public class Person {
    @NotNull @Email private String email;
    @NotNull private String firstName;
    @NotNull private String lastName;
        
    public Person() {
    }
    
    public Person( final String email ) {
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail( final String email ) {
        this.email = email;
    }
        
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setFirstName( final String firstName ) {
        this.firstName = firstName;
    }
    
    public void setLastName( final String lastName ) {
        this.lastName = lastName;
    }        
}
