package com.kaveri.service;

import com.kaveri.model.Customer;
import com.kaveri.model.GSTBill;
import com.kaveri.repo.GSTBillRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class CustomerServiceImpl {

    @Autowired
    private GSTBillRepo gstBillRepo;

    @Autowired
    private JavaMailSender mailSender;

    @EventListener(ApplicationReadyEvent.class)
    public void triggerMail() throws MessagingException {

    }

    public GSTBill saveGSTBill(GSTBill gstBill) {
        return gstBillRepo.save(gstBill);
    }

    public Customer generateGSTBill(Customer customer) throws MessagingException {
        GSTBill gstBill = new GSTBill();
        if (gstBillRepo.findAll().size() != 0) {
            String invoiceId = gstBillRepo.findAll().stream().map(GSTBill::getInvoiceNo).sorted(Comparator.comparing(String::valueOf).reversed()).toList().get(0);
            int newInvoiceId = Integer.parseInt(invoiceId.replaceAll("[^0-9]", ""));
            gstBill.setInvoiceNo("CS-" + (newInvoiceId + 1));
        } else {
            gstBill.setInvoiceNo("CS-100");
        }
        gstBill.setCustomerName(customer.getCustomerName());
        gstBill.setCustomerContact(customer.getCustomerContact());
        gstBill.setCustomerAddress(customer.getCustomerAddress());
        gstBill.setCustomerEmail(customer.getCustomerEmail());
        gstBill.setBillDescription(customer.getBillDescription());
        gstBill.setGstNo(customer.getGstNo());
        LocalDate currentDate = LocalDate.now();
        gstBill.setGstBillDate(java.sql.Date.valueOf(currentDate));
        gstBill.setTotalAmount(customer.getTotalAmount());
        // GST Calculation
        double gst = customer.getTotalAmount() * ((double) 18 / (100 + 18));
        gstBill.setCgstAmount((int) gst / 2);
        gstBill.setSgstAmount((int) Math.round((gst / 2)));
        gstBill.setAmount(customer.getTotalAmount() - gstBill.getCgstAmount() - gstBill.getSgstAmount());

        saveGSTBill(gstBill);
        sendEmail(gstBill);
        return customer;
    }

    public Optional<GSTBill> findById(String invoceNo) {
        return gstBillRepo.findById(invoceNo);
    }

    public void deleteByInvoiceNo(String invoiceNo) {
        gstBillRepo.deleteById(invoiceNo);
    }

    public List<GSTBill> findAll() {
        return gstBillRepo.findAll();
    }

    public void sendEmail(GSTBill gstBill) throws MessagingException {
        log.info("*************TO EMAIL*********: " + gstBill.getCustomerEmail());

        log.info("######CC MAIL######" + gstBill.getCustomerEmail());

        log.info("*************SUBJECT*********: " + gstBill.getCustomerName());

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("kaveribhaskarwarale@gmail.com");
        mimeMessageHelper.setTo(gstBill.getCustomerEmail());
        //  mimeMessageHelper.setCc(emailModel.getCcEmail());
        Calendar c1 = Calendar.getInstance();
        c1.setTime(gstBill.getGstBillDate());
        c1.add(Calendar.DATE, 5);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date());
        String text =
                "<table width='100%' border='1px solid' align='center' border-collapse='collapse'>"
                        + "<tr align='center'>"
                        + "<td><b>Invoice No <b></td>"
                        + "<td><b>Customer Name <b></td>"
                        + "<td><b>Customer Contact<b></td>"
                        + "<td><b>Customer Address<b></td>"
                        + "<td><b><b>GST Number</td>"
                        + "<td><b>Amount<b></td>"
                        + "<td><b>Bill Description<b></td>"
                        + "<td><b>CGST Amount<b></td>"
                        + "<td><b>SGST Amount<b></td>"
                        + "<td><b>Total Amount<b></td>"
                        + "<td><b>Bill Date<b></td>"
                        + "</tr>";
        text = text + "<tr align='center'>" + "<tr>"
                + "<td><b>" + gstBill.getInvoiceNo() + "<b></td>"
                + "<td><b>" + gstBill.getCustomerName() + "<b></td>"
                + "<td><b>" + gstBill.getCustomerContact() + "<b></td>"
                + "<td><b>" + gstBill.getCustomerAddress() + "<b></td>"
                + "<td><b><b>" + gstBill.getGstNo() + "</td>"
                + "<td><b>" + gstBill.getAmount() + "<b></td>"
                + "<td><b>" + gstBill.getBillDescription() + "<b></td>"
                + "<td><b>" + gstBill.getCgstAmount() + "<b></td>"
                + "<td><b>" + gstBill.getSgstAmount() + "<b></td>"
                + "<td><b>" + gstBill.getTotalAmount() + "<b></td>"
                + "<td><b>" + gstBill.getGstBillDate() + "<b></td>"
                + "</tr>";
        mimeMessageHelper.setText(text);
        mimeMessage.setContent(text, "text/html");
        mimeMessageHelper.setSubject("Hi " + gstBill.getCustomerName() + " GST Bill For Your Purchase");

        if (c2.get(Calendar.MONTH) <= c1.get(Calendar.MONTH)
                && c2.get(Calendar.DAY_OF_YEAR) <= c1.get(Calendar.DAY_OF_YEAR)
                && c2.get(Calendar.YEAR) <= c1.get(Calendar.YEAR)) {
            mailSender.send(mimeMessage);
            log.info("Mail Send Successfully...");
        }
    }
}
