package com.kaveri.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GSTBill {

    @Id
    private String invoiceNo;

    private String customerName;

    private long customerContact;

    private String customerAddress;

    private String customerEmail;

    private String billDescription;

    private String gstNo;

    //@JsonFormat(pattern = "dd-MM-yyyy", timezone = "Asia/Kolkata")
    private Date gstBillDate;

    private int amount;

    private int cgstAmount;

    private int sgstAmount;

    private int totalAmount;


}
