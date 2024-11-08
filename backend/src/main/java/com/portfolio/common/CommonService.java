package com.portfolio.common;

import com.portfolio.common.dto.PaginationDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * CommonService is a generic interface for basic CRUD (Create, Read, Update, Delete) operations.
 * It provides a standard set of methods for managing entities in a service layer.
 *
 * @param <CreateDto>  the type of the DTO used for creating entities
 * @param <UpdateDto>  the type of the DTO used for updating entities
 * @param <ResponseDto>  the type of the DTO returned from CRUD operations
 * @param <PrimaryKey>  the type of the primary key used to identify entities
 */
public interface CommonService<CreateDto, UpdateDto, ResponseDto, PrimaryKey> {
    /**
     * Retrieves a paginated list of entities.
     *
     * @param paginationDto  the pagination parameters
     * @param <R>  the type of the entities in the paginated list
     * @return a page of entities
     * @throws UnsupportedOperationException if the method is not implemented
     */
    default <R> Page<R> findAll(PaginationDto paginationDto) {
        throw new UnsupportedOperationException("findAll(PaginationDto) not implemented");
    }

    /**
     * Retrieves a list of all entities.
     *
     * @param <R>  the type of the entities in the list
     * @return a list of all entities
     * @throws UnsupportedOperationException if the method is not implemented
     */
    default <R> List<R> findAll() {
        throw new UnsupportedOperationException("findAll() not implemented");
    }

    /**
     * Finds an entity by its primary key.
     *
     * @param id  the primary key of the entity to find
     * @return the found entity
     * @throws Exception if an error occurs during the search or if the entity is not found
     */
    ResponseDto findById(PrimaryKey id) throws Exception;

    /**
     * Saves a new entity.
     *
     * @param createDto  the DTO containing the details of the entity to create
     * @return the saved entity
     * @throws Exception if an error occurs during the save operation
     */
    ResponseDto save(CreateDto createDto) throws Exception;

    /**
     * Updates an existing entity.
     *
     * @param id  the primary key of the entity to update
     * @param updateDto  the DTO containing the updated details of the entity
     * @return the updated entity
     * @throws Exception if an error occurs during the update operation
     */
    ResponseDto update(PrimaryKey id, UpdateDto updateDto) throws Exception;

    /**
     * Deletes an entity by its primary key.
     *
     * @param id  the primary key of the entity to delete
     * @throws Exception if an error occurs during the delete operation
     */
    void delete(PrimaryKey id) throws Exception;
}

