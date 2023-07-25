package com.mikaelovi.mdbtask.service;

import com.mikaelovi.mdbtask.dto.BaseDto;
import com.mikaelovi.mdbtask.entity.BaseEntity;
import com.mikaelovi.mdbtask.exception.EntityNotFoundException;
import com.mikaelovi.mdbtask.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public abstract class BaseService<E extends BaseEntity, D extends BaseDto> {

    private final BaseRepository<E> repository;

    protected BaseService(BaseRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public D save(D dto) {
        return convertToDto(repository.save(convertToEntity(dto)));
    }

    public D update(D dto, Integer id) {
        E data = get(id);

        return convertToDto(repository.save(copyToEntity(dto, data)));
    }

    public Page<D> findAll(Pageable pageable) {
        final var allEntitiesPage = repository.findAll(pageable);

        return convertToPagedDto(allEntitiesPage);
    }

    E get(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    public D findOne(Integer id) {
        final var foundEntity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        return convertToDto(foundEntity);
    }

    @Transactional
    public void delete(Integer id) {
        get(id);
        repository.deleteById(id);
    }

    abstract E convertToEntity(D dto);

    abstract E copyToEntity(D dto, E data);

    abstract D convertToDto(E entity);

    Page<D> convertToPagedDto(Page<E> pagedEntity) {
        return new PageImpl<>(pagedEntity.getContent().stream().map(this::convertToDto).toList());
    }
}
