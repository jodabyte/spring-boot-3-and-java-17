package com.example.springboot3andjava17.common.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface Repository<T> extends ListCrudRepository<T,String>, ListPagingAndSortingRepository<T,String> {
    
}
