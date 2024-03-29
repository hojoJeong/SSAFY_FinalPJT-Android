package com.ssafy.cafe.controller.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cafe.model.dto.Product;
import com.ssafy.cafe.model.service.ProductService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/rest/product")
@CrossOrigin("*")
public class ProductRestController {
    @Autowired
    ProductService pService;
    
    @GetMapping()
    @ApiOperation(value="전체 상품의 목록을 반환한다.", response = List.class)
    public ResponseEntity<List<Product>> getProductList(){
        return new ResponseEntity<List<Product>>(pService.getProductList(), HttpStatus.OK);
    }
    
    @GetMapping("/{productId}")
    @ApiOperation(value="{productId}에 해당하는 상품의 정보를 comment와 함께 반환한다."
            + "이 기능은 상품의 comment를 조회할 때 사용된다.", response = List.class)
    public List<Map<String, Object>> getProductWithComments(@PathVariable Integer productId){
        return pService.selectWithComment(productId);
    }
    
    @GetMapping("/recommended/{userId}")
    @ApiOperation(value="{userId}에 해당하는 유저가 가장 많이 주문한 5개 메뉴를 반환한다.")
    public ResponseEntity<List<Product>> getRecommenedProduct(@PathVariable String userId){
      
        return new ResponseEntity<List<Product>>(pService.selectRecommendedProduct(userId), HttpStatus.OK);
    }
}
