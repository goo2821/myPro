package com.bs.demo.Repository;

import com.bs.demo.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

  Iterable<Product> findByProductNameContainingAndProductAddressContaining(String name, String address);
  

}
