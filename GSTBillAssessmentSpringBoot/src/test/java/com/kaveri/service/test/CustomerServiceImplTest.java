package com.kaveri.service.test;

import com.kaveri.model.GSTBill;
import com.kaveri.repo.GSTBillRepo;
import com.kaveri.service.CustomerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerServiceImplTest {

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @MockBean
    private GSTBillRepo gstBillRepo;

    @Test
    public void saveTest() {
        GSTBill gstBill = new GSTBill("CS-101", "Kaveri", 505050, "Rahuri", "kaveri@gmail.com", "Mobile", "VGBH6666HH", new Date(), 7000, 600, 600, 8200);
        customerServiceImpl.saveGSTBill(gstBill);

        Mockito.verify(gstBillRepo, Mockito.times(1)).save(gstBill);
    }

}
