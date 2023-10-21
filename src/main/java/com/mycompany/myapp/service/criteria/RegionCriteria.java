package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Region} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.RegionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /regions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RegionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter regionName;

    private LongFilter countryId;

    private Boolean distinct;

    public RegionCriteria() {}

    public RegionCriteria(RegionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.regionName = other.regionName == null ? null : other.regionName.copy();
        this.countryId = other.countryId == null ? null : other.countryId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RegionCriteria copy() {
        return new RegionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRegionName() {
        return regionName;
    }

    public StringFilter regionName() {
        if (regionName == null) {
            regionName = new StringFilter();
        }
        return regionName;
    }

    public void setRegionName(StringFilter regionName) {
        this.regionName = regionName;
    }

    public LongFilter getCountryId() {
        return countryId;
    }

    public LongFilter countryId() {
        if (countryId == null) {
            countryId = new LongFilter();
        }
        return countryId;
    }

    public void setCountryId(LongFilter countryId) {
        this.countryId = countryId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RegionCriteria that = (RegionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(regionName, that.regionName) &&
            Objects.equals(countryId, that.countryId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, regionName, countryId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (regionName != null ? "regionName=" + regionName + ", " : "") +
            (countryId != null ? "countryId=" + countryId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
