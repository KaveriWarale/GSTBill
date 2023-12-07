package com.kaveri.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class Customer {

    @NotNull
   // @Size(min = 2,message = "Customer Name At least 2 Character long")
    @Pattern(regexp = "(^$|[A-Za-z]{2,50})", message = "Name Must be Only Character")
    private String customerName;

    @Range(min = 1000000000,max = 9999999999L,message = "Contact Number Must be 10 Character")
    //@Digits(message = "Enter 10 Digits Number",fraction = 0,integer = 10)
    //@Pattern(regexp = "(^$|[0-9]{10})", message = "Contact Number Must be Only Number")
    private  long customerContact;

    @NotEmpty(message = "Address not be Empty")
    private  String customerAddress;

    @NotNull
    @Email(message = "Email Id Must be Valid")
    private String customerEmail;

    @NotNull(message = "Description not be Null")
    private  String billDescription;

    @NotNull
    private  String gstNo;

    @NotNull
    private int totalAmount;

    //  @CreditCardNumber(message = "Enter Valid Card Number")
    //private int cardNumber;

}
