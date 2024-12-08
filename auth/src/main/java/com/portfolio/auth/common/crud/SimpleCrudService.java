package com.portfolio.auth.common.crud;

import com.portfolio.auth.common.Identifiable;
import com.portfolio.auth.common.exception.ValidationException;
import com.portfolio.auth.common.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.*;

/**
 * Abstract CRUD service providing basic operations such as Create, Read, Update, and Delete (CRUD)
 * with support for pagination. This service uses a generic repository and ModelMapper to facilitate
 * data transformation between entities and DTO objects.
 *
 * @param <Repository>  The repository type extending JpaRepository
 * @param <Entity>      The entity type implementing Identifiable
 * @param <CreateRequestDto>  The Data Transfer Object (DTO) type for creating entities
 * @param <UpdateRequestDto>  The Data Transfer Object (DTO) type for updating entities
 * @param <ResponseDto> The Response DTO type for returning results
 * @param <PrimaryKey>  The primary key type of the entity
 */
@Transactional
@Slf4j
@Component
public abstract class SimpleCrudService<
        Repository extends JpaRepository<Entity, PrimaryKey>,
        Entity extends Identifiable<PrimaryKey>,
        CreateRequestDto,
        UpdateRequestDto,
        ResponseDto,
        PrimaryKey> {

    protected final Repository repository;
    protected final Class<Entity> entityClass;
    protected final Class<ResponseDto> responseDtoClass;

    @Autowired
    private Validator validator;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Constructor to initialize the CRUD service with the repository and entity/DTO classes.
     *
     * @param repository       The repository instance
     * @param entityClass      The class type of the entity
     * @param responseDtoClass The class type of the response DTO
     */
    public SimpleCrudService(Repository repository, Class<Entity> entityClass, Class<ResponseDto> responseDtoClass) {
        this.repository = repository;
        this.entityClass = entityClass;
        this.responseDtoClass = responseDtoClass;
    }

    /**
     * Validates an entity using the Validator API.
     *
     * @param entity The entity to validate
     * @throws ValidationException if validation fails
     */
    protected void validate(Entity entity) throws ValidationException {
        log.debug("Validating entity: {}", entity);
        Set<ConstraintViolation<Object>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            Map<String, List<String>> errors = new HashMap<>();

            violations.forEach(violation -> {
                String field = violation.getPropertyPath().toString(); // Convert property path to String
                String message = violation.getMessage(); // Extract the error message

                // Add the message to the list of errors for the field
                errors.computeIfAbsent(field, k -> new ArrayList<>()).add(message);
            });
            log.error("Validation errors: {}", errors);
            throw new ValidationException("Validation error", errors);
        }
    }

    Entity findOneById(PrimaryKey id) throws ResourceNotFoundException {
        log.debug("Finding entity by id: {}", id);
        return repository.findById(id).orElseThrow(() ->{
            log.error("Could not find entity with id: {}", id);
            return new ResourceNotFoundException("Could not find entity with id: " + id);
        });
    }

    /**
     * Finds all entities with pagination support.
     *
     * @param pageable A DTO containing page number and size and sorting
     * @return A paginated result of entities mapped to ResponseDto
     */
    @Transactional(readOnly = true)
    public Page<ResponseDto> findAll(Pageable pageable) {
        return findAll(pageable, responseDtoClass);
    }

    /**
     * Finds all entities with pagination support.
     *
     * @param pageable A DTO containing page number and size and sorting
     * @param responseType The class type of the response DTO
     * @return A paginated result of entities mapped to ResponseDto
     */
    @Transactional(readOnly = true)
    public <T> Page<T> findAll(Pageable pageable, Class<T> responseType) {
        log.debug("Finding all entities with paginated data {}", pageable);
        return repository.findAll(pageable).map(entity -> modelMapper.map(entity, responseType));
    }

    /**
     * Finds entities using a specification with pagination.
     * Note: The repository must extend JpaSpecificationExecutor for this to work.
     *
     * @param spec          Specification for filtering entities
     * @param pageable DTO containing pageable parameters
     * @return A paginated result of entities mapped to ResponseDto
     * @throws UnsupportedOperationException if the repository does not support specifications
     */
    @Transactional(readOnly = true)
    public Page<ResponseDto> findAll(Specification<Entity> spec, Pageable pageable) {
        return findAll(spec, pageable, responseDtoClass);
    }

    /**
     * Finds entities using a specification with pagination.
     * Note: The repository must extend JpaSpecificationExecutor for this to work.
     *
     * @param spec          Specification for filtering entities
     * @param pageable DTO containing pageable parameters
     * @param responseType The class type of the response DTO
     * @return A paginated result of entities mapped to ResponseDto
     * @throws UnsupportedOperationException if the repository does not support specifications
     */
    @Transactional(readOnly = true)
    public <T> Page<T> findAll(Specification<Entity> spec, Pageable pageable, Class<T> responseType) {
        log.debug("Finding all entities with paginated data {} and specification {}", pageable, spec);
        if (!(repository instanceof JpaSpecificationExecutor)) {
            log.error("Repository does not support specifications");
            throw new UnsupportedOperationException(
                    "Repository does not support specifications. Please extend JpaSpecificationExecutor.");
        }

        @SuppressWarnings("unchecked")
        JpaSpecificationExecutor<Entity> specificationExecutor = (JpaSpecificationExecutor<Entity>) repository;
        return specificationExecutor.findAll(spec, pageable)
                .map(entity -> modelMapper.map(entity, responseType));
    }

    /**
     * Finds all entities without pagination.
     *
     * @return A list of all entities mapped to ResponseDto
     */
    @Transactional(readOnly = true)
    public List<ResponseDto> findAll() {
        return findAll(responseDtoClass);
    }

    /**
     * Finds all entities without pagination.
     * @param responseType The class type of the response DTO
     *
     * @return A list of all entities mapped to ResponseDto
     */
    @Transactional(readOnly = true)
    public <T> List<T> findAll(Class<T> responseType) {
        log.debug("Finding all entities without pagination");
        List<Entity> entities = repository.findAll();
        return entities.stream().map(entity -> modelMapper.map(entity, responseType)).toList();
    }

    /**
     * Finds an entity by its primary key.
     *
     * @param id The primary key of the entity
     * @return The entity mapped to ResponseDto
     * @throws ResourceNotFoundException if the entity is not found
     */
    @Transactional(readOnly = true)
    public ResponseDto findById(PrimaryKey id) throws ResourceNotFoundException {
        return findById(id, responseDtoClass);
    }

    /**
     * Finds an entity by its primary key.
     *
     * @param id The primary key of the entity
     * @param responseType The class type of the response DTO
     * @return The entity mapped to ResponseDto
     * @throws ResourceNotFoundException if the entity is not found
     */
    @Transactional(readOnly = true)
    public <T> T findById(PrimaryKey id, Class<T> responseType) throws ResourceNotFoundException {
        return mapToDto(findOneById(id), responseType);
    }

    /**
     * Saves a new entity to the repository.
     *
     * @param createDto The DTO containing data for the new entity
     * @return The saved entity mapped to ResponseDto
     */
    @Transactional
    public ResponseDto save(CreateRequestDto createDto) throws ValidationException {
        log.debug("Saving entity {}", createDto);
        Entity entity = modelMapper.map(createDto, entityClass);
        validate(entity);
        entity = repository.save(entity);
        return mapToDto(entity, responseDtoClass);
    }

    /**
     * Updates an existing entity by ID.
     *
     * @param id             The primary key of the entity
     * @param updateDto      DTO with updated data
     * @param isPatchRequest Flag indicating partial or full update
     * @return The updated entity mapped to ResponseDto
     */
    @Transactional
    public ResponseDto update(PrimaryKey id, UpdateRequestDto updateDto, Boolean isPatchRequest) throws ResourceNotFoundException, ValidationException {
        log.debug("Updating entity {} with {}", id, updateDto);
        Entity entity = findOneById(id);
        if (isPatchRequest) {
            modelMapper.map(updateDto, entity);
        } else {
            entity = modelMapper.map(updateDto, entityClass);
            entity.setId(id);
        }
        validate(entity);
        entity = repository.save(entity);
        return mapToDto(entity, responseDtoClass);
    }

    /**
     * Deletes an entity by ID (soft delete if the entity supports it).
     *
     * @param id The primary key of the entity
     * @return The deleted entity mapped to ResponseDto
     */
    @Transactional
    public ResponseDto delete(PrimaryKey id) throws ResourceNotFoundException {
        log.debug("Deleting entity with id {}", id);
        Entity entity = findOneById(id);
        repository.delete(entity);
        return mapToDto(entity, responseDtoClass);
    }

    /**
     * Maps an entity to a ResponseDto using ModelMapper.
     *
     * @param source      The entity object
     * @param targetClass The class of the target DTO
     * @param <T>         The type of the DTO
     * @return The mapped DTO object
     */
    private <T> T mapToDto(Object source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }
}
