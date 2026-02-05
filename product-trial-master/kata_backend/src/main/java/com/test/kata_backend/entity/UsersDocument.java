package com.test.kata_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UsersDocument {
    @Id
    private String id;
    private Integer idSql;
    private String username;
    private String firstname;
    private String email;
    private String password;
}
