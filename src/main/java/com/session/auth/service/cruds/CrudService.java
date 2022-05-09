package com.session.auth.service.cruds;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.List;

public interface CrudService<T> extends Serializable {
    void setRepository(PagingAndSortingRepository<T, Long> repository);

    PagingAndSortingRepository<T, Long> getRepository();

    List<T> listAll();

    Page<T> listAll(Pageable pageable);

    T findOrFail(Long id) throws EntityNotFoundException;

    void delete(T entity) throws EntityNotFoundException;

    void delete(long id) throws EntityNotFoundException;

    T save(T entity);

}
