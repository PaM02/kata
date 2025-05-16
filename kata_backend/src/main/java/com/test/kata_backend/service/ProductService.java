package  com.test.kata_backend.service;

import  com.test.kata_backend.dto.ProductRequest;
import  com.test.kata_backend.entity.ProductEntity;
import  com.test.kata_backend.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

   public List<ProductEntity> retrieveAllProducts(){
        return productRepository.findAll();
    }


   public ResponseEntity<String> createProduct(@RequestBody ProductRequest productRequest){

       ProductEntity productEntity = new ProductEntity();
       productEntity.setName(productRequest.getName());
       productEntity.setDescription(productRequest.getDescription());
       productEntity.setImage(productRequest.getImage());
       productEntity.setCategory(productRequest.getCategory());
       productEntity.setPrice(productRequest.getPrice());
       productEntity.setQuantity(productRequest.getQuantity());
       productEntity.setInternalReference(productRequest.getInternalReference());
       productEntity.setShellId(productRequest.getShellId());
       productEntity.setInventoryStatus(productRequest.getInventoryStatus());
       productEntity.setRating(productRequest.getRating());
       productEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
       productEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
       productRepository.save(productEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully");
   }

   public ResponseEntity<String> updateProduct(@RequestBody ProductRequest productRequest){
       Optional<ProductEntity> productEntity = productRepository.findById(productRequest.getId());

       if(productEntity.isPresent()){
           productEntity.get().setName(productRequest.getName());
           productEntity.get().setDescription(productRequest.getDescription());
           productEntity.get().setImage(productRequest.getImage());
           productEntity.get().setCategory(productRequest.getCategory());
           productEntity.get().setPrice(productRequest.getPrice());
           productEntity.get().setQuantity(productRequest.getQuantity());
           productEntity.get().setInternalReference(productRequest.getInternalReference());
           productEntity.get().setShellId(productRequest.getShellId());
           productEntity.get().setInventoryStatus(productRequest.getInventoryStatus());
           productEntity.get().setRating(productRequest.getRating());
           productEntity.get().setCreatedAt(new Timestamp(System.currentTimeMillis()));
           productEntity.get().setUpdatedAt(new Timestamp(System.currentTimeMillis()));
           productRepository.save(productEntity.get());
           return ResponseEntity.status(HttpStatus.CREATED).body("Product saved successfully");
       }else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
       }


   }

    public ProductEntity retrieveProductById(Integer id){
       return productRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));
   }

   public ResponseEntity<String> removeProduct(Integer id){
       Optional<ProductEntity> productEntity =productRepository.findById(id);;
       if(productEntity.isPresent()){
           productRepository.delete(productEntity.get());
           return ResponseEntity.ok("Product deleted successfully");
       }else{
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
       }
   }

}
