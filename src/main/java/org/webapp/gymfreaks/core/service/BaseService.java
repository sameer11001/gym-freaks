package org.webapp.gymfreaks.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.webapp.gymfreaks.core.model.BaseEntity;
import org.webapp.gymfreaks.core.repository.BaseRepository;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseService<T extends BaseEntity, ID> {

    @Autowired
    private BaseRepository<T, ID> baseRepostitory;

    public T findById(ID id) {
        return baseRepostitory.findById(id).orElseThrow();
    }

    public List<T> findAll() {
        return baseRepostitory.findAll();
    }

    public Boolean existsById(ID id) {
        return baseRepostitory.existsById(id);
    }

    public T insert(T entity) {
        return baseRepostitory.save(entity);
    }

    public List<T> insertAll(List<T> entities) {
        return baseRepostitory.saveAll(entities);
    }

    public T update(T entity) {
        return baseRepostitory.save(entity);
    }

    public T updateById(ID id, T entity) {
        if (!baseRepostitory.existsById(id)) {
            throw new RuntimeException();
        }
        baseRepostitory.findById(id);
        return baseRepostitory.save(entity);
    }

    public void deleteById(ID id) {
        baseRepostitory.deleteById(id);
    }

}
