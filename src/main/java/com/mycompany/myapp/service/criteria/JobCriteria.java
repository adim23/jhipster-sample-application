package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Job} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.JobResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /jobs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JobCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter jobTitle;

    private LongFilter minSalary;

    private LongFilter maxSalary;

    private LongFilter taskId;

    private LongFilter employeeId;

    private LongFilter jobHistoryId;

    private Boolean distinct;

    public JobCriteria() {}

    public JobCriteria(JobCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.jobTitle = other.jobTitle == null ? null : other.jobTitle.copy();
        this.minSalary = other.minSalary == null ? null : other.minSalary.copy();
        this.maxSalary = other.maxSalary == null ? null : other.maxSalary.copy();
        this.taskId = other.taskId == null ? null : other.taskId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.jobHistoryId = other.jobHistoryId == null ? null : other.jobHistoryId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public JobCriteria copy() {
        return new JobCriteria(this);
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

    public StringFilter getJobTitle() {
        return jobTitle;
    }

    public StringFilter jobTitle() {
        if (jobTitle == null) {
            jobTitle = new StringFilter();
        }
        return jobTitle;
    }

    public void setJobTitle(StringFilter jobTitle) {
        this.jobTitle = jobTitle;
    }

    public LongFilter getMinSalary() {
        return minSalary;
    }

    public LongFilter minSalary() {
        if (minSalary == null) {
            minSalary = new LongFilter();
        }
        return minSalary;
    }

    public void setMinSalary(LongFilter minSalary) {
        this.minSalary = minSalary;
    }

    public LongFilter getMaxSalary() {
        return maxSalary;
    }

    public LongFilter maxSalary() {
        if (maxSalary == null) {
            maxSalary = new LongFilter();
        }
        return maxSalary;
    }

    public void setMaxSalary(LongFilter maxSalary) {
        this.maxSalary = maxSalary;
    }

    public LongFilter getTaskId() {
        return taskId;
    }

    public LongFilter taskId() {
        if (taskId == null) {
            taskId = new LongFilter();
        }
        return taskId;
    }

    public void setTaskId(LongFilter taskId) {
        this.taskId = taskId;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            employeeId = new LongFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getJobHistoryId() {
        return jobHistoryId;
    }

    public LongFilter jobHistoryId() {
        if (jobHistoryId == null) {
            jobHistoryId = new LongFilter();
        }
        return jobHistoryId;
    }

    public void setJobHistoryId(LongFilter jobHistoryId) {
        this.jobHistoryId = jobHistoryId;
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
        final JobCriteria that = (JobCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(jobTitle, that.jobTitle) &&
            Objects.equals(minSalary, that.minSalary) &&
            Objects.equals(maxSalary, that.maxSalary) &&
            Objects.equals(taskId, that.taskId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(jobHistoryId, that.jobHistoryId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jobTitle, minSalary, maxSalary, taskId, employeeId, jobHistoryId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (jobTitle != null ? "jobTitle=" + jobTitle + ", " : "") +
            (minSalary != null ? "minSalary=" + minSalary + ", " : "") +
            (maxSalary != null ? "maxSalary=" + maxSalary + ", " : "") +
            (taskId != null ? "taskId=" + taskId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (jobHistoryId != null ? "jobHistoryId=" + jobHistoryId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
