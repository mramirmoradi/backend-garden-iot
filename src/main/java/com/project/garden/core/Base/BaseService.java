package com.project.garden.core.Base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BaseService<T extends BaseEntity> {

    T create(T t);

    T update(long id, T t);

    void delete(long id);

    Page<T> find(BaseSpecification<T> specification, Pageable pageable);

    T find(long id);

    List<T> find(List<Long> ids);

    Page<T> find(Pageable pageable);

    List<T> find(BaseSpecification<T> specification);

    List<T> find();

    void preWrite(T t);

    void preCreate(T t);

    void preUpdate(long id, T t);

    void postWrite(T t);

    void postRead(T t);

    void preDelete(long id);

    void sequence(Map<Long, Integer> map);

}
