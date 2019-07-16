package com.zenika.uglysystem.persistence;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "entity", path = "entities")
public interface MyEntityRepository extends PagingAndSortingRepository<MyEntity, Long> {
    List<MyEntity> findByName(@Param("name") String name);
}
