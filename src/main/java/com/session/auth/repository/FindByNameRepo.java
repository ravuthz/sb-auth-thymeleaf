package com.session.auth.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NoRepositoryBean
public interface FindByNameRepo<T, ID> extends PagingAndSortingRepository<T, ID> {
    // Optional<T> findByName(String name);
    T findByName(String name);

    List<T> findAllByNameIsLike(String name);

    List<T> findAllByNameContains(String name);

    @Transactional
    default T findOrCreate(String name, T newEntry) {
        T entity = findByName(name);
        if (entity == null) {
            entity = newEntry;
        }
        save(entity);
        return entity;
    }

}
