package com.mycompany.myapp.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Employee} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeDTO implements Serializable {

    private Long id;

    /**
     * The firstname attribute.
     */
    @Schema(description = "The firstname attribute.")
    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Instant hireDate;

    private Double salary;

    private Float commissionPct;

    private Long longF;

    private Integer intF;

    private Boolean boolF;

    private BigDecimal bigF;

    private Duration durF;

    private LocalDate localDF;

    private ZonedDateTime zoneDF;

    private UUID uuF;

    @Lob
    private byte[] pict;

    private String pictContentType;

    @Lob
    private String comments;

    @Lob
    private byte[] cv;

    private String cvContentType;
    private EmployeeDTO manager;

    /**
     * Another side of the same relationship
     */
    @Schema(description = "Another side of the same relationship")
    private DepartmentDTO department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getHireDate() {
        return hireDate;
    }

    public void setHireDate(Instant hireDate) {
        this.hireDate = hireDate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Float getCommissionPct() {
        return commissionPct;
    }

    public void setCommissionPct(Float commissionPct) {
        this.commissionPct = commissionPct;
    }

    public Long getLongF() {
        return longF;
    }

    public void setLongF(Long longF) {
        this.longF = longF;
    }

    public Integer getIntF() {
        return intF;
    }

    public void setIntF(Integer intF) {
        this.intF = intF;
    }

    public Boolean getBoolF() {
        return boolF;
    }

    public void setBoolF(Boolean boolF) {
        this.boolF = boolF;
    }

    public BigDecimal getBigF() {
        return bigF;
    }

    public void setBigF(BigDecimal bigF) {
        this.bigF = bigF;
    }

    public Duration getDurF() {
        return durF;
    }

    public void setDurF(Duration durF) {
        this.durF = durF;
    }

    public LocalDate getLocalDF() {
        return localDF;
    }

    public void setLocalDF(LocalDate localDF) {
        this.localDF = localDF;
    }

    public ZonedDateTime getZoneDF() {
        return zoneDF;
    }

    public void setZoneDF(ZonedDateTime zoneDF) {
        this.zoneDF = zoneDF;
    }

    public UUID getUuF() {
        return uuF;
    }

    public void setUuF(UUID uuF) {
        this.uuF = uuF;
    }

    public byte[] getPict() {
        return pict;
    }

    public void setPict(byte[] pict) {
        this.pict = pict;
    }

    public String getPictContentType() {
        return pictContentType;
    }

    public void setPictContentType(String pictContentType) {
        this.pictContentType = pictContentType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public byte[] getCv() {
        return cv;
    }

    public void setCv(byte[] cv) {
        this.cv = cv;
    }

    public String getCvContentType() {
        return cvContentType;
    }

    public void setCvContentType(String cvContentType) {
        this.cvContentType = cvContentType;
    }

    public EmployeeDTO getManager() {
        return manager;
    }

    public void setManager(EmployeeDTO manager) {
        this.manager = manager;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeDTO)) {
            return false;
        }

        EmployeeDTO employeeDTO = (EmployeeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, employeeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", hireDate='" + getHireDate() + "'" +
            ", salary=" + getSalary() +
            ", commissionPct=" + getCommissionPct() +
            ", longF=" + getLongF() +
            ", intF=" + getIntF() +
            ", boolF='" + getBoolF() + "'" +
            ", bigF=" + getBigF() +
            ", durF='" + getDurF() + "'" +
            ", localDF='" + getLocalDF() + "'" +
            ", zoneDF='" + getZoneDF() + "'" +
            ", uuF='" + getUuF() + "'" +
            ", pict='" + getPict() + "'" +
            ", comments='" + getComments() + "'" +
            ", cv='" + getCv() + "'" +
            ", manager=" + getManager() +
            ", department=" + getDepartment() +
            "}";
    }
}
