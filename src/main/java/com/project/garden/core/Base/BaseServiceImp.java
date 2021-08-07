package com.project.garden.core.Base;

import com.project.garden.core.devlog.DevLog;
import com.project.garden.core.exception.Error;
import com.project.garden.core.exception.ErrorStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class BaseServiceImp<T extends BaseEntity> implements BaseService<T> {
    private final BaseRepository<T> repository;

    public BaseServiceImp(BaseRepository<T> repository) {
        this.repository = repository;
    }

    @DevLog
    @Override
    @Transactional
    public T create(T t) {
        preCreate(t);
        return save(t);
    }

    @DevLog
    @Override
    @Transactional
    public T update(long id, T t) {
        preUpdate(id, t);
        if (repository.findById(id).isEmpty())
            throw new Error(ErrorStatus.NOTFOUND, t.getClass().getSimpleName(), "NOT FOUND | PRE UPDATE", null);
        save(t);
        return find(id);
    }

    @DevLog
    @Override
    @Transactional
    public void delete(long id) {
        preDelete(id);
        Optional<T> o = repository.findById(id);
        if (o.isEmpty())
            throw new Error(ErrorStatus.NOTFOUND, null, "NOT FOUND | DELETE", null);
        T t = o.get();
        repository.delete(t);
    }

    @Override
    public Page<T> find(BaseSpecification<T> specification, Pageable pageable) {
        Page<T> page = repository.findAll(specification, pageable);
        for (T t : page)
            postRead(t);
        return page;
    }

    @DevLog
    @Override
    @Transactional(readOnly = true)
    public T find(long id) {
        Optional<T> optional = repository.findById(id);
        if (optional.isEmpty())
            throw new Error(ErrorStatus.NOTFOUND, null, "NOT FOUND | FIND BY ID", null);
        T t = optional.get();
        postRead(t);
        return t;
    }

    @Override
    @Transactional
    public void sequence(Map<Long, Integer> map) {
        List<T> list = new ArrayList<>();
        repository.findAllById(map.keySet()).forEach(t -> {
            t.setSequence(map.get(t.getId()));
            list.add(t);
        });
        repository.saveAll(list);
    }

    private T save(T t) {
        preWrite(t);
        repository.save(t);
        postWrite(t);
        return t;
    }

    @Override
    public List<T> find(BaseSpecification<T> specification) {
        List<T> list = repository.findAll(specification);
        for (T t : list) {
            postRead(t);
        }
        return list;
    }

    @DevLog
    @Override
    @Transactional(readOnly = true)
    public List<T> find() {
        List<T> ts = repository.findAll();
        for (T t : ts)
            postRead(t);
        return ts;
    }


    @Override
    public List<T> find(List<Long> ids) {
        List<T> ts = repository.findAllById(ids);
        for (T t : ts)
            postRead(t);
        return ts;
    }

    @DevLog
    @Override
    @Transactional(readOnly = true)
    public Page<T> find(Pageable pageable) {
        Page<T> page = repository.findAll(pageable);
        for (T t : page)
            postRead(t);
        return page;
    }

    @DevLog
    @Override
    public void preWrite(T t) {
    }

    @DevLog
    @Override
    public void preCreate(T t) {
    }

    @DevLog
    @Override
    public void preUpdate(long id, T t) {

    }

    @DevLog
    @Override
    public void postWrite(T t) {

    }

    @DevLog
    @Override
    public void postRead(T t) {

    }

    @DevLog
    @Override
    public void preDelete(long id) {

    }

}
