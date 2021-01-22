package com.bs.demo.Controller;

import com.bs.demo.Entity.Product;
import com.bs.demo.Repository.ProductRepository;
import com.bs.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/investments")
public class ProductController {

  private final ProductRepository productRepo;

  @Autowired
  ProductController(ProductRepository productRepo) {
    this.productRepo = productRepo;
  }

  // /investments?page=1
  @GetMapping
  public Iterable<Product> findProductAll(
      @PageableDefault(sort = "id", direction = Sort.Direction.DESC, value = 2) Pageable pageable) {

    return productRepo.findAll(pageable);

  }

  @GetMapping(value="/test")
  public Object test(){
    return productRepo.findByProductNameContainingAndProductAddressContaining("오", "중원");
  }

  @GetMapping(value = "/{type}")
  public Object findProduct(@PathVariable String type,
      @PageableDefault(sort = "id", direction = Sort.Direction.DESC, value = 2) Pageable pageable,
      @RequestParam(value = "yield", required = false, defaultValue = "0") int yield,
      @RequestParam(value = "address", required = false, defaultValue = "") String address,
      @RequestParam(value = "title", required = false, defaultValue = "") String title) {

    //return productRepo.findByProductNameContainingAndProductAddressContainingAndYieldGreaterThanEqualAndTitleContaining(type, address, yield, title);
    return productRepo.findByProductNameContainingAndProductAddressContaining("오", "중원");

  }

  @GetMapping(value = "/detail/{idx}")
  public Object findProduct(@PathVariable int idx) {

    if (!productRepo.existsById(idx))
      return Result.PRODUCT_NOT_FOUND.toResponse(HttpStatus.BAD_REQUEST);

    return productRepo.findById(idx);

  }

  @PostMapping()
  public Object addProduct(@RequestBody Product product) {

    productRepo.save(product);

    return Result.SUCCESS.toResponse(HttpStatus.OK);

  }

  @PutMapping(value = "")
  public Object updateProduct(@RequestBody Product product) {

    if (!productRepo.existsById(product.getId()))
      return Result.PRODUCT_NOT_FOUND.toResponse(HttpStatus.BAD_REQUEST);

    productRepo.save(product);

    return Result.SUCCESS.toResponse(HttpStatus.OK);

  }

  @DeleteMapping(value = "{idx}")
  public Object removeProduct(@PathVariable int idx) {

    if (!productRepo.existsById(idx))
      return Result.PRODUCT_NOT_FOUND.toResponse(HttpStatus.BAD_REQUEST);

    productRepo.deleteById(idx);

    return Result.SUCCESS.toResponse(HttpStatus.OK);

  }

}
