package com.jade.escort.service;

import com.jade.escort.model.Product;
import com.jade.escort.repo.ProductRepo;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepo repo;
    public ProductService(ProductRepo repo){
        this.repo=repo;
    }

    public List<Product> getProducts(){
        return repo.findAll();
    }

    public Product addProduct(Product prod, MultipartFile image) {
        prod.setImageName(image.getOriginalFilename());
        prod.setImageType(image.getContentType());
        try {
            prod.setImageData(image.getBytes());
        }
        catch (Exception e){
            return null;
        }
        return repo.save(prod);
    }

    public Product getProduct(int id) {
        return repo.findById(id).orElse(new Product());
    }


    @SneakyThrows
    public Product updateProduct(int id, Product product, MultipartFile imageFile) {
        product.setImageData(imageFile.getBytes());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        return repo.save(product);

    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {
        return repo.searchProduct(keyword);
    }
}
