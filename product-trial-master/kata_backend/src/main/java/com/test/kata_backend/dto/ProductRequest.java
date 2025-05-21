package  com.test.kata_backend.dto;

import  com.test.kata_backend.model.InventortStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductRequest {
    private Integer id;
    private Integer code;
    private String name;
    private String description;
    private String image;
    private String category;
    private Integer price;
    private Integer quantity;
    private String internalReference;
    private Integer shellId;
    private InventortStatusEnum inventoryStatus;
    private Integer rating;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
