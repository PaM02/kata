package  com.test.kata_backend.controller;
import  com.test.kata_backend.dto.ProductRequest;
import  com.test.kata_backend.entity.ProductEntity;
import  com.test.kata_backend.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public List<ProductEntity> getUsers(){
        return productService.retrieveAllProducts();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createProductEntity(@RequestBody ProductRequest productRequest){
        return productService.createProduct(productRequest);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateProductEntity(@RequestBody ProductRequest productRequest){
      return   productService.updateProduct(productRequest);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> removeProduct(
            @RequestParam("id")  Integer id){
        return productService.removeProduct(id);
    }

    @GetMapping("/item")
    public ResponseEntity<ProductEntity> retrieveProductById(Integer id){
        return ResponseEntity.ok(productService.retrieveProductById(id));
    }


}
