package com.jade.escort.controller;

import com.jade.escort.model.Product;
import com.jade.escort.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {


    private ProductService service;
    @Autowired
    public void setService(ProductService service){
        this.service=service;
    }

//    @GetMapping()
//    public String hi(){
//        return "hii";
//    }
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        return new ResponseEntity<>(service.getProducts(), HttpStatus.OK);
    }

    @GetMapping("product/{id}")
    public ResponseEntity< Product> getProduct(@PathVariable int id){
        Product p=service.getProduct(id);
        if(p!=null)
            return new ResponseEntity<>( p, HttpStatus.OK);
        else
            return new ResponseEntity<>(new Product(), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?>  addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        try {
            Product p = service.addProduct(product, imageFile);
            return new ResponseEntity <> (p,HttpStatus.CREATED );
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_IMPLEMENTED);
        }
    }



    @GetMapping("/product/{prodId}/image")
    public ResponseEntity<byte[]> getImageByProdId(@PathVariable int prodId){
        Product product =service.getProduct(prodId);
        byte[] imageFile= product.getImageData();
        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile){
        Product p=service.updateProduct(id, product, imageFile);
        if(p!=null)
            return new ResponseEntity<>("updated", HttpStatus.OK);
        else
            return new ResponseEntity<>("not updated", HttpStatus.NOT_IMPLEMENTED);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product prod=service.getProduct(id);
        if(prod!=null){
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("not updated", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        System.out.println("searching with"+keyword);
        List<Product> products=service.searchProducts(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
