package com.bs.demo.Controller;

import com.bs.demo.Entity.Product;
import com.bs.demo.Repository.ProductRepository;
import com.bs.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/investments")
public class ProductController {

  @Autowired
  private ProductRepository productRepo;

  // /investments?page=1
  @GetMapping
  public Object findProductAll(@PageableDefault(sort="id", direction = Sort.Direction.DESC, value=2) Pageable pageable){

    return productRepo.findAll(pageable);

  }

  @GetMapping(value="/{type}")
  public Object findProduct(@PathVariable String type) {

    if(type.equals("dev")) return "개발형입니다.";
    else if(type.equals("real")) return "실물형입니다.";

    return "잘못된 타입입니다.";
  }

  @GetMapping(value="/detail/{idx}")
  public Object findProduct(@PathVariable int idx){

    if(!productRepo.existsById(idx)) return Result.PRODUCT_NOT_FOUND.toResponse(HttpStatus.BAD_REQUEST);

    return productRepo.findById(idx);

  }

  @PostMapping()
  public Object addProduct(@RequestBody Product product){

    productRepo.save(product);

    return Result.SUCCESS.toResponse(HttpStatus.OK);

  }

  @PutMapping(value="")
  public Object updateProduct(@RequestBody Product product){

    if(!productRepo.existsById(product.getId())) return Result.PRODUCT_NOT_FOUND.toResponse(HttpStatus.BAD_REQUEST);

    productRepo.save(product);

    return Result.SUCCESS.toResponse(HttpStatus.OK);

  }

  @DeleteMapping(value="{idx}")
  public Object removeProduct(@PathVariable int idx){

    if(!productRepo.existsById(idx)) return Result.PRODUCT_NOT_FOUND.toResponse(HttpStatus.BAD_REQUEST);

    productRepo.deleteById(idx);

    return Result.SUCCESS.toResponse(HttpStatus.OK);

  }


}
