package com.test.kata_backend.repository;

import com.test.kata_backend.entity.UsersDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSearchRepository extends ElasticsearchRepository<UsersDocument, String> {
    List<UsersDocument> findByUsername(String userName);
}