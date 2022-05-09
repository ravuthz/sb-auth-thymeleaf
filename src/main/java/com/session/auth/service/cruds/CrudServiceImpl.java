package com.session.auth.service.cruds;

import com.session.auth.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public class CrudServiceImpl<T extends BaseEntity> implements CrudService<T> {

    @Getter
    @Setter
    private PagingAndSortingRepository<T, Long> repository;

    public List<T> listAll() {
        return (List<T>) repository.findAll();
    }

    public Page<T> listAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public T findOrFail(Long id) throws EntityNotFoundException {
        T entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Entity with id: " + id));
        return entity;
    }

    public void delete(T entity) throws EntityNotFoundException {
        if (entity == null) {
            throw new EntityNotFoundException("Cannot find Entity");
        }
        repository.delete(entity);
    }

    public void delete(long id) throws EntityNotFoundException {
        delete(findOrFail(id));
    }

    public T save(T entity) {
//        if (entity.getId() == null) {
//
//        } else {
//
//        }
        repository.save(entity);
        return entity;
    }

}
