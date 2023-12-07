package com.kaveri.repo;

import com.kaveri.model.GSTBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GSTBillRepo extends JpaRepository<GSTBill,String> {


}
