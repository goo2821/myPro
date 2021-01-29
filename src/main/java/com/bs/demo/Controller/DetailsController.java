package com.bs.demo.Controller;

import com.bs.demo.Repository.DetailRepository;
import com.bs.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/details")
public class DetailsController {

  private DetailRepository detailRepo;

  @Autowired
  DetailsController(DetailRepository detailRepo) {
    this.detailRepo = detailRepo;
  }

  @GetMapping(value = "/{product_id}")
  public Object findDetail(@PathVariable int product_id) {

    return detailRepo.findById(product_id);
    
  }

  @GetMapping(value = "/docs/{product_id}")
  public Object findDoc(@PathVariable int product_id) {

    if (!detailRepo.existsById(product_id))
      return Result.DETAIL_NOT_FOUND.toResponse(HttpStatus.BAD_REQUEST);

    return detailRepo.findById(product_id);
  }

  @GetMapping(value = "/pictures/{product_id}")
  public Object findPicture(@PathVariable int product_id) {

    if (!detailRepo.existsById(product_id))
      return Result.DETAIL_NOT_FOUND.toResponse(HttpStatus.BAD_REQUEST);

    return detailRepo.findById(product_id);

  }

}
