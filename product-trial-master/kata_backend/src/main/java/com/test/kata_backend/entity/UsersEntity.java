package  com.test.kata_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Entity
@Table(name = "users")
@Document(indexName = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "password")
    private String password;
}