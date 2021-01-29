package com.bs.demo.Repository;

import com.bs.demo.Entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

  Page<Product> findByTypeContaining(Pageable pageable, String type);
  
}
