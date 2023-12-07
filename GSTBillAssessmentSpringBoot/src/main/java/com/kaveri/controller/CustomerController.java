package com.kaveri.controller;

import com.kaveri.exception.RecordNotFoundException;
import com.kaveri.model.Customer;
import com.kaveri.model.GSTBill;
import com.kaveri.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/gst")
@CrossOrigin
public class CustomerController {

    @Autowired
    CustomerServiceImpl customerServiceImpl;

    @PostMapping("/save")

    public ResponseEntity<Customer> saveGSTBill(@Valid @RequestBody Customer customer) throws MessagingException {
        return new ResponseEntity<>(customerServiceImpl.generateGSTBill(customer), HttpStatus.CREATED);
    }

    @GetMapping("/findall")
    public ResponseEntity<List<GSTBill>> findAllData() {
        return ResponseEntity.ok(customerServiceImpl.findAll());
    }

    @GetMapping("/findbyid/{invoiceNo}")
    public ResponseEntity<GSTBill> findById(@PathVariable String invoiceNo) {
        return ResponseEntity.ok(customerServiceImpl.findById(invoiceNo).orElseThrow(() -> new RecordNotFoundException("Record Not Found")));
    }

    @GetMapping("/findbyname/{customerName}")
    public ResponseEntity<List<GSTBill>> findByName(@PathVariable String customerName) {
        return ResponseEntity.ok(customerServiceImpl.findAll().stream().filter(c -> c.getCustomerName().toLowerCase().equals(customerName.toLowerCase())).toList());
    }

    @GetMapping("/findbybilldate/{billDate}")
    public ResponseEntity<List<GSTBill>> findByBillDate(@PathVariable String billDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return ResponseEntity.ok(customerServiceImpl.findAll().stream().filter(c -> simpleDateFormat.format(c.getGstBillDate()).equals(billDate)).toList());
    }

    @GetMapping("/findbyany/{anyInput}")
    public ResponseEntity<List<GSTBill>> findByAnyInput(@PathVariable String anyInput) {
        return ResponseEntity.ok(customerServiceImpl.findAll().stream().filter(gst -> gst.getCustomerName().contains(anyInput)
                || gst.getInvoiceNo().equals(anyInput) || gst.getCustomerEmail().equals(anyInput) || gst.getCustomerAddress().contains(anyInput)
                || gst.getGstBillDate().equals(anyInput) || gst.getGstNo().equals(anyInput) || String.valueOf(gst.getCustomerContact()).equals(anyInput)
        ).toList());
    }

    @DeleteMapping("/deletebyinvoiceno/{invoiceNo}")
    public ResponseEntity<String> deleteByInvoiceNo(@PathVariable String invoiceNo) {

        customerServiceImpl.deleteByInvoiceNo(invoiceNo);
        return ResponseEntity.ok("Delete Success..");
    }

}