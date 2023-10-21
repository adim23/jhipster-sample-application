package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Employee} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.EmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter email;

    private StringFilter phoneNumber;

    private InstantFilter hireDate;

    private DoubleFilter salary;

    private FloatFilter commissionPct;

    private LongFilter longF;

    private IntegerFilter intF;

    private BooleanFilter boolF;

    private BigDecimalFilter bigF;

    private DurationFilter durF;

    private LocalDateFilter localDF;

    private ZonedDateTimeFilter zoneDF;

    private UUIDFilter uuF;

    private LongFilter jobId;

    private LongFilter managerId;

    private LongFilter departmentId;

    private LongFilter jobHistoryId;

    private Boolean distinct;

    public EmployeeCriteria() {}

    public EmployeeCriteria(EmployeeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.hireDate = other.hireDate == null ? null : other.hireDate.copy();
        this.salary = other.salary == null ? null : other.salary.copy();
        this.commissionPct = other.commissionPct == null ? null : other.commissionPct.copy();
        this.longF = other.longF == null ? null : other.longF.copy();
        this.intF = other.intF == null ? null : other.intF.copy();
        this.boolF = other.boolF == null ? null : other.boolF.copy();
        this.bigF = other.bigF == null ? null : other.bigF.copy();
        this.durF = other.durF == null ? null : other.durF.copy();
        this.localDF = other.localDF == null ? null : other.localDF.copy();
        this.zoneDF = other.zoneDF == null ? null : other.zoneDF.copy();
        this.uuF = other.uuF == null ? null : other.uuF.copy();
        this.jobId = other.jobId == null ? null : other.jobId.copy();
        this.managerId = other.managerId == null ? null : other.managerId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.jobHistoryId = other.jobHistoryId == null ? null : other.jobHistoryId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeCriteria copy() {
        return new EmployeeCriteria(this);
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

    public StringFilter getFirstName() {
        return firstName;
    }

    public StringFilter firstName() {
        if (firstName == null) {
            firstName = new StringFilter();
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public StringFilter lastName() {
        if (lastName == null) {
            lastName = new StringFilter();
        }
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public StringFilter phoneNumber() {
        if (phoneNumber == null) {
            phoneNumber = new StringFilter();
        }
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public InstantFilter getHireDate() {
        return hireDate;
    }

    public InstantFilter hireDate() {
        if (hireDate == null) {
            hireDate = new InstantFilter();
        }
        return hireDate;
    }

    public void setHireDate(InstantFilter hireDate) {
        this.hireDate = hireDate;
    }

    public DoubleFilter getSalary() {
        return salary;
    }

    public DoubleFilter salary() {
        if (salary == null) {
            salary = new DoubleFilter();
        }
        return salary;
    }

    public void setSalary(DoubleFilter salary) {
        this.salary = salary;
    }

    public FloatFilter getCommissionPct() {
        return commissionPct;
    }

    public FloatFilter commissionPct() {
        if (commissionPct == null) {
            commissionPct = new FloatFilter();
        }
        return commissionPct;
    }

    public void setCommissionPct(FloatFilter commissionPct) {
        this.commissionPct = commissionPct;
    }

    public LongFilter getLongF() {
        return longF;
    }

    public LongFilter longF() {
        if (longF == null) {
            longF = new LongFilter();
        }
        return longF;
    }

    public void setLongF(LongFilter longF) {
        this.longF = longF;
    }

    public IntegerFilter getIntF() {
        return intF;
    }

    public IntegerFilter intF() {
        if (intF == null) {
            intF = new IntegerFilter();
        }
        return intF;
    }

    public void setIntF(IntegerFilter intF) {
        this.intF = intF;
    }

    public BooleanFilter getBoolF() {
        return boolF;
    }

    public BooleanFilter boolF() {
        if (boolF == null) {
            boolF = new BooleanFilter();
        }
        return boolF;
    }

    public void setBoolF(BooleanFilter boolF) {
        this.boolF = boolF;
    }

    public BigDecimalFilter getBigF() {
        return bigF;
    }

    public BigDecimalFilter bigF() {
        if (bigF == null) {
            bigF = new BigDecimalFilter();
        }
        return bigF;
    }

    public void setBigF(BigDecimalFilter bigF) {
        this.bigF = bigF;
    }

    public DurationFilter getDurF() {
        return durF;
    }

    public DurationFilter durF() {
        if (durF == null) {
            durF = new DurationFilter();
        }
        return durF;
    }

    public void setDurF(DurationFilter durF) {
        this.durF = durF;
    }

    public LocalDateFilter getLocalDF() {
        return localDF;
    }

    public LocalDateFilter localDF() {
        if (localDF == null) {
            localDF = new LocalDateFilter();
        }
        return localDF;
    }

    public void setLocalDF(LocalDateFilter localDF) {
        this.localDF = localDF;
    }

    public ZonedDateTimeFilter getZoneDF() {
        return zoneDF;
    }

    public ZonedDateTimeFilter zoneDF() {
        if (zoneDF == null) {
            zoneDF = new ZonedDateTimeFilter();
        }
        return zoneDF;
    }

    public void setZoneDF(ZonedDateTimeFilter zoneDF) {
        this.zoneDF = zoneDF;
    }

    public UUIDFilter getUuF() {
        return uuF;
    }

    public UUIDFilter uuF() {
        if (uuF == null) {
            uuF = new UUIDFilter();
        }
        return uuF;
    }

    public void setUuF(UUIDFilter uuF) {
        this.uuF = uuF;
    }

    public LongFilter getJobId() {
        return jobId;
    }

    public LongFilter jobId() {
        if (jobId == null) {
            jobId = new LongFilter();
        }
        return jobId;
    }

    public void setJobId(LongFilter jobId) {
        this.jobId = jobId;
    }

    public LongFilter getManagerId() {
        return managerId;
    }

    public LongFilter managerId() {
        if (managerId == null) {
            managerId = new LongFilter();
        }
        return managerId;
    }

    public void setManagerId(LongFilter managerId) {
        this.managerId = managerId;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public LongFilter departmentId() {
        if (departmentId == null) {
            departmentId = new LongFilter();
        }
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
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
        final EmployeeCriteria that = (EmployeeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(hireDate, that.hireDate) &&
            Objects.equals(salary, that.salary) &&
            Objects.equals(commissionPct, that.commissionPct) &&
            Objects.equals(longF, that.longF) &&
            Objects.equals(intF, that.intF) &&
            Objects.equals(boolF, that.boolF) &&
            Objects.equals(bigF, that.bigF) &&
            Objects.equals(durF, that.durF) &&
            Objects.equals(localDF, that.localDF) &&
            Objects.equals(zoneDF, that.zoneDF) &&
            Objects.equals(uuF, that.uuF) &&
            Objects.equals(jobId, that.jobId) &&
            Objects.equals(managerId, that.managerId) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(jobHistoryId, that.jobHistoryId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            firstName,
            lastName,
            email,
            phoneNumber,
            hireDate,
            salary,
            commissionPct,
            longF,
            intF,
            boolF,
            bigF,
            durF,
            localDF,
            zoneDF,
            uuF,
            jobId,
            managerId,
            departmentId,
            jobHistoryId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
            (hireDate != null ? "hireDate=" + hireDate + ", " : "") +
            (salary != null ? "salary=" + salary + ", " : "") +
            (commissionPct != null ? "commissionPct=" + commissionPct + ", " : "") +
            (longF != null ? "longF=" + longF + ", " : "") +
            (intF != null ? "intF=" + intF + ", " : "") +
            (boolF != null ? "boolF=" + boolF + ", " : "") +
            (bigF != null ? "bigF=" + bigF + ", " : "") +
            (durF != null ? "durF=" + durF + ", " : "") +
            (localDF != null ? "localDF=" + localDF + ", " : "") +
            (zoneDF != null ? "zoneDF=" + zoneDF + ", " : "") +
            (uuF != null ? "uuF=" + uuF + ", " : "") +
            (jobId != null ? "jobId=" + jobId + ", " : "") +
            (managerId != null ? "managerId=" + managerId + ", " : "") +
            (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
            (jobHistoryId != null ? "jobHistoryId=" + jobHistoryId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
