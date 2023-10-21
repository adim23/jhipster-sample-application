package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "hire_date")
    private Instant hireDate;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "commission_pct")
    private Float commissionPct;

    @Column(name = "long_f")
    private Long longF;

    @Column(name = "int_f")
    private Integer intF;

    @Column(name = "bool_f")
    private Boolean boolF;

    @Column(name = "big_f", precision = 21, scale = 2)
    private BigDecimal bigF;

    @Column(name = "dur_f")
    private Duration durF;

    @Column(name = "local_df")
    private LocalDate localDF;

    @Column(name = "zone_df")
    private ZonedDateTime zoneDF;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "uu_f", length = 36)
    private UUID uuF;

    @Lob
    @Column(name = "pict")
    private byte[] pict;

    @Column(name = "pict_content_type")
    private String pictContentType;

    @Lob
    @Column(name = "comments")
    private String comments;

    @Lob
    @Column(name = "cv")
    private byte[] cv;

    @Column(name = "cv_content_type")
    private String cvContentType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tasks", "employee", "jobHistory" }, allowSetters = true)
    private Set<Job> jobs = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "jobs", "manager", "department", "jobHistory" }, allowSetters = true)
    private Employee manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "employees", "location", "location", "jobHistory" }, allowSetters = true)
    private Department department;

    @JsonIgnoreProperties(value = { "job", "department", "employee" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "employee")
    private JobHistory jobHistory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Employee firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Employee lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public Employee email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Employee phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getHireDate() {
        return this.hireDate;
    }

    public Employee hireDate(Instant hireDate) {
        this.setHireDate(hireDate);
        return this;
    }

    public void setHireDate(Instant hireDate) {
        this.hireDate = hireDate;
    }

    public Double getSalary() {
        return this.salary;
    }

    public Employee salary(Double salary) {
        this.setSalary(salary);
        return this;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Float getCommissionPct() {
        return this.commissionPct;
    }

    public Employee commissionPct(Float commissionPct) {
        this.setCommissionPct(commissionPct);
        return this;
    }

    public void setCommissionPct(Float commissionPct) {
        this.commissionPct = commissionPct;
    }

    public Long getLongF() {
        return this.longF;
    }

    public Employee longF(Long longF) {
        this.setLongF(longF);
        return this;
    }

    public void setLongF(Long longF) {
        this.longF = longF;
    }

    public Integer getIntF() {
        return this.intF;
    }

    public Employee intF(Integer intF) {
        this.setIntF(intF);
        return this;
    }

    public void setIntF(Integer intF) {
        this.intF = intF;
    }

    public Boolean getBoolF() {
        return this.boolF;
    }

    public Employee boolF(Boolean boolF) {
        this.setBoolF(boolF);
        return this;
    }

    public void setBoolF(Boolean boolF) {
        this.boolF = boolF;
    }

    public BigDecimal getBigF() {
        return this.bigF;
    }

    public Employee bigF(BigDecimal bigF) {
        this.setBigF(bigF);
        return this;
    }

    public void setBigF(BigDecimal bigF) {
        this.bigF = bigF;
    }

    public Duration getDurF() {
        return this.durF;
    }

    public Employee durF(Duration durF) {
        this.setDurF(durF);
        return this;
    }

    public void setDurF(Duration durF) {
        this.durF = durF;
    }

    public LocalDate getLocalDF() {
        return this.localDF;
    }

    public Employee localDF(LocalDate localDF) {
        this.setLocalDF(localDF);
        return this;
    }

    public void setLocalDF(LocalDate localDF) {
        this.localDF = localDF;
    }

    public ZonedDateTime getZoneDF() {
        return this.zoneDF;
    }

    public Employee zoneDF(ZonedDateTime zoneDF) {
        this.setZoneDF(zoneDF);
        return this;
    }

    public void setZoneDF(ZonedDateTime zoneDF) {
        this.zoneDF = zoneDF;
    }

    public UUID getUuF() {
        return this.uuF;
    }

    public Employee uuF(UUID uuF) {
        this.setUuF(uuF);
        return this;
    }

    public void setUuF(UUID uuF) {
        this.uuF = uuF;
    }

    public byte[] getPict() {
        return this.pict;
    }

    public Employee pict(byte[] pict) {
        this.setPict(pict);
        return this;
    }

    public void setPict(byte[] pict) {
        this.pict = pict;
    }

    public String getPictContentType() {
        return this.pictContentType;
    }

    public Employee pictContentType(String pictContentType) {
        this.pictContentType = pictContentType;
        return this;
    }

    public void setPictContentType(String pictContentType) {
        this.pictContentType = pictContentType;
    }

    public String getComments() {
        return this.comments;
    }

    public Employee comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public byte[] getCv() {
        return this.cv;
    }

    public Employee cv(byte[] cv) {
        this.setCv(cv);
        return this;
    }

    public void setCv(byte[] cv) {
        this.cv = cv;
    }

    public String getCvContentType() {
        return this.cvContentType;
    }

    public Employee cvContentType(String cvContentType) {
        this.cvContentType = cvContentType;
        return this;
    }

    public void setCvContentType(String cvContentType) {
        this.cvContentType = cvContentType;
    }

    public Set<Job> getJobs() {
        return this.jobs;
    }

    public void setJobs(Set<Job> jobs) {
        if (this.jobs != null) {
            this.jobs.forEach(i -> i.setEmployee(null));
        }
        if (jobs != null) {
            jobs.forEach(i -> i.setEmployee(this));
        }
        this.jobs = jobs;
    }

    public Employee jobs(Set<Job> jobs) {
        this.setJobs(jobs);
        return this;
    }

    public Employee addJob(Job job) {
        this.jobs.add(job);
        job.setEmployee(this);
        return this;
    }

    public Employee removeJob(Job job) {
        this.jobs.remove(job);
        job.setEmployee(null);
        return this;
    }

    public Employee getManager() {
        return this.manager;
    }

    public void setManager(Employee employee) {
        this.manager = employee;
    }

    public Employee manager(Employee employee) {
        this.setManager(employee);
        return this;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee department(Department department) {
        this.setDepartment(department);
        return this;
    }

    public JobHistory getJobHistory() {
        return this.jobHistory;
    }

    public void setJobHistory(JobHistory jobHistory) {
        if (this.jobHistory != null) {
            this.jobHistory.setEmployee(null);
        }
        if (jobHistory != null) {
            jobHistory.setEmployee(this);
        }
        this.jobHistory = jobHistory;
    }

    public Employee jobHistory(JobHistory jobHistory) {
        this.setJobHistory(jobHistory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return getId() != null && getId().equals(((Employee) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
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
            ", pictContentType='" + getPictContentType() + "'" +
            ", comments='" + getComments() + "'" +
            ", cv='" + getCv() + "'" +
            ", cvContentType='" + getCvContentType() + "'" +
            "}";
    }
}
