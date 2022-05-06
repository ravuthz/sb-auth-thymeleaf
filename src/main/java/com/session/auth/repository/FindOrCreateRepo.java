package com.session.auth.repository;


import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface FindOrCreateRepo<T, ID> {
    T findOrCreate(String name);
//    default T findOrCreate(String name) {
//        Class<T> clazz = null;
//        T entity = findByName(name);
//        if (entity == null) {
//            try {
//                entity = new T();
//            } catch (InstantiationException | IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        save(entity);
//        return entity;
//    }
}
