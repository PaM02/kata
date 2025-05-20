package  com.test.kata_backend.controller;
import  com.test.kata_backend.dto.ProductRequest;
import  com.test.kata_backend.entity.ProductEntity;
import  com.test.kata_backend.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "2-Products", description = "Endpoints d'auth")
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
    public ResponseEntity<ProductEntity> retrieveProductById(@RequestParam("id") Integer id){
        return ResponseEntity.ok(productService.retrieveProductById(id));
    }


}
