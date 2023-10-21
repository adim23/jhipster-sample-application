package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Region;
import com.mycompany.myapp.repository.RegionRepository;
import com.mycompany.myapp.service.criteria.RegionCriteria;
import com.mycompany.myapp.service.dto.RegionDTO;
import com.mycompany.myapp.service.mapper.RegionMapper;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Region} entities in the database.
 * The main input is a {@link RegionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RegionDTO} or a {@link Page} of {@link RegionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RegionQueryService extends QueryService<Region> {

    private final Logger log = LoggerFactory.getLogger(RegionQueryService.class);

    private final RegionRepository regionRepository;

    private final RegionMapper regionMapper;

    public RegionQueryService(RegionRepository regionRepository, RegionMapper regionMapper) {
        this.regionRepository = regionRepository;
        this.regionMapper = regionMapper;
    }

    /**
     * Return a {@link List} of {@link RegionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RegionDTO> findByCriteria(RegionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Region> specification = createSpecification(criteria);
        return regionMapper.toDto(regionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RegionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RegionDTO> findByCriteria(RegionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Region> specification = createSpecification(criteria);
        return regionRepository.findAll(specification, page).map(regionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RegionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Region> specification = createSpecification(criteria);
        return regionRepository.count(specification);
    }

    /**
     * Function to convert {@link RegionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Region> createSpecification(RegionCriteria criteria) {
        Specification<Region> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Region_.id));
            }
            if (criteria.getRegionName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRegionName(), Region_.regionName));
            }
            if (criteria.getCountryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCountryId(), root -> root.join(Region_.country, JoinType.LEFT).get(Country_.id))
                    );
            }
        }
        return specification;
    }
}
