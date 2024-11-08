package com.portfolio.common;

import com.portfolio.common.dto.PaginationDto;
import com.portfolio.common.exception.DuplicateResourceException;
import com.portfolio.common.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Abstract CRUD service to provide basic operations like create, read, update, and delete (CRUD)
 * with pagination support for entities. It utilizes a generic repository and ModelMapper to
 * handle the data transformation between entity and DTO objects.
 *
 * @param <Repository> The repository type which extends JpaRepository
 * @param <Entity>     The entity type
 * @param <RequestDto>        The Data Transfer Object (DTO) type for creating and updating entities
 * @param <ResponseDto>The Response Data Transfer Object (DTO) type for returning results
 * @param <PrimaryKey> The type of the primary key for the entity
 */
public abstract class CrudService<Repository extends JpaRepository<Entity, PrimaryKey>,
        Entity,
        RequestDto,
        ResponseDto,
        PrimaryKey> {

    protected final Repository repository;
    protected final ModelMapper modelMapper;
    private final Class<Entity> entityClass;
    private final Class<ResponseDto> responseDtoClass;

    public CrudService(Repository repository, ModelMapper modelMapper, Class<Entity> entityClass, Class<ResponseDto> responseDtoClass) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.entityClass = entityClass;
        this.responseDtoClass = responseDtoClass;
    }

    /**
     * Finds all entities with pagination support.
     *
     * @param paginationDto A PaginationDto object containing page number and size for pagination
     * @return A paginated result of entities mapped to ResponseDto
     */
    public Page<ResponseDto> findAll(PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(Math.max(paginationDto.getPage() - 1, 0), paginationDto.getSize());
        Page<Entity> entityPage = repository.findAll(pageable);
        return entityPage.map(entity -> modelMapper.map(entity, responseDtoClass));
    }

    /**
     * Finds all entities without pagination.
     *
     * @return A list of all entities mapped to ResponseDto
     */
    public List<ResponseDto> findAll() {
        List<Entity> entities = repository.findAll();
        return entities.stream().map(entity -> modelMapper.map(entity, responseDtoClass)).toList();
    }

    /**
     * Finds an entity by its primary key.
     *
     * @param id The primary key of the entity to be found
     * @return The entity mapped to ResponseDto if found
     * @throws RuntimeException if the entity is not found
     */
    public ResponseDto findById(PrimaryKey id) throws ResourceNotFoundException {
        Entity entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Record not found"));
        return modelMapper.map(entity, responseDtoClass);
    }

    /**
     * Saves a new entity to the repository.
     *
     * @param createDto The DTO containing data for creating a new entity
     * @return The saved entity mapped to ResponseDto
     */
    public ResponseDto save(RequestDto createDto) throws DuplicateResourceException {
        Entity entity = modelMapper.map(createDto, entityClass);
        entity = repository.save(entity);
        return modelMapper.map(entity, responseDtoClass);
    }

    /**
     * Updates an existing entity in the repository.
     *
     * @param id        The primary key of the entity to be updated
     * @param updateDto The DTO containing updated data for the entity
     * @return The updated entity mapped to ResponseDto
     * @throws RuntimeException if the entity with the specified id does not exist
     */
    public ResponseDto update(PrimaryKey id, RequestDto updateDto, Boolean isPatchRequest) throws ResourceNotFoundException {
        Entity entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Record not found"));
        if(isPatchRequest){
            modelMapper.map(updateDto, entity);
        }else{
            entity = modelMapper.map(updateDto, entityClass);
            try {
                Method setIdMethod = entityClass.getMethod("setId", id.getClass());
                setIdMethod.invoke(entity, id);
            } catch (Exception e) {
                throw new RuntimeException("Failed to set ID on entity", e);
            }
        }
        entity = repository.save(entity);
        return modelMapper.map(entity, responseDtoClass);
    }

    /**
     * Deletes an entity by its primary key.
     *
     * @param id The primary key of the entity to be deleted
     * @return The deleted entity mapped to ResponseDto
     * @throws RuntimeException if the entity with the specified id does not exist
     */
    public ResponseDto delete(PrimaryKey id) throws ResourceNotFoundException {
        Entity entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Record not found"));
        repository.delete(entity);
        return modelMapper.map(entity, responseDtoClass);
    }
}
