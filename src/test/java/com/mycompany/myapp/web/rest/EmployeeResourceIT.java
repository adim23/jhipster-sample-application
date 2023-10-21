package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Department;
import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.domain.Job;
import com.mycompany.myapp.domain.JobHistory;
import com.mycompany.myapp.repository.EmployeeRepository;
import com.mycompany.myapp.service.dto.EmployeeDTO;
import com.mycompany.myapp.service.mapper.EmployeeMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link EmployeeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_HIRE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HIRE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_SALARY = 1D;
    private static final Double UPDATED_SALARY = 2D;
    private static final Double SMALLER_SALARY = 1D - 1D;

    private static final Float DEFAULT_COMMISSION_PCT = 1F;
    private static final Float UPDATED_COMMISSION_PCT = 2F;
    private static final Float SMALLER_COMMISSION_PCT = 1F - 1F;

    private static final Long DEFAULT_LONG_F = 1L;
    private static final Long UPDATED_LONG_F = 2L;
    private static final Long SMALLER_LONG_F = 1L - 1L;

    private static final Integer DEFAULT_INT_F = 1;
    private static final Integer UPDATED_INT_F = 2;
    private static final Integer SMALLER_INT_F = 1 - 1;

    private static final Boolean DEFAULT_BOOL_F = false;
    private static final Boolean UPDATED_BOOL_F = true;

    private static final BigDecimal DEFAULT_BIG_F = new BigDecimal(1);
    private static final BigDecimal UPDATED_BIG_F = new BigDecimal(2);
    private static final BigDecimal SMALLER_BIG_F = new BigDecimal(1 - 1);

    private static final Duration DEFAULT_DUR_F = Duration.ofHours(6);
    private static final Duration UPDATED_DUR_F = Duration.ofHours(12);
    private static final Duration SMALLER_DUR_F = Duration.ofHours(5);

    private static final LocalDate DEFAULT_LOCAL_DF = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LOCAL_DF = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_LOCAL_DF = LocalDate.ofEpochDay(-1L);

    private static final ZonedDateTime DEFAULT_ZONE_DF = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ZONE_DF = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ZONE_DF = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final UUID DEFAULT_UU_F = UUID.randomUUID();
    private static final UUID UPDATED_UU_F = UUID.randomUUID();

    private static final byte[] DEFAULT_PICT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PICT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PICT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PICT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CV = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CV = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CV_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CV_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/employees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createEntity(EntityManager em) {
        Employee employee = new Employee()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .hireDate(DEFAULT_HIRE_DATE)
            .salary(DEFAULT_SALARY)
            .commissionPct(DEFAULT_COMMISSION_PCT)
            .longF(DEFAULT_LONG_F)
            .intF(DEFAULT_INT_F)
            .boolF(DEFAULT_BOOL_F)
            .bigF(DEFAULT_BIG_F)
            .durF(DEFAULT_DUR_F)
            .localDF(DEFAULT_LOCAL_DF)
            .zoneDF(DEFAULT_ZONE_DF)
            .uuF(DEFAULT_UU_F)
            .pict(DEFAULT_PICT)
            .pictContentType(DEFAULT_PICT_CONTENT_TYPE)
            .comments(DEFAULT_COMMENTS)
            .cv(DEFAULT_CV)
            .cvContentType(DEFAULT_CV_CONTENT_TYPE);
        return employee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity(EntityManager em) {
        Employee employee = new Employee()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .hireDate(UPDATED_HIRE_DATE)
            .salary(UPDATED_SALARY)
            .commissionPct(UPDATED_COMMISSION_PCT)
            .longF(UPDATED_LONG_F)
            .intF(UPDATED_INT_F)
            .boolF(UPDATED_BOOL_F)
            .bigF(UPDATED_BIG_F)
            .durF(UPDATED_DUR_F)
            .localDF(UPDATED_LOCAL_DF)
            .zoneDF(UPDATED_ZONE_DF)
            .uuF(UPDATED_UU_F)
            .pict(UPDATED_PICT)
            .pictContentType(UPDATED_PICT_CONTENT_TYPE)
            .comments(UPDATED_COMMENTS)
            .cv(UPDATED_CV)
            .cvContentType(UPDATED_CV_CONTENT_TYPE);
        return employee;
    }

    @BeforeEach
    public void initTest() {
        employee = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();
        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);
        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testEmployee.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployee.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testEmployee.getHireDate()).isEqualTo(DEFAULT_HIRE_DATE);
        assertThat(testEmployee.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testEmployee.getCommissionPct()).isEqualTo(DEFAULT_COMMISSION_PCT);
        assertThat(testEmployee.getLongF()).isEqualTo(DEFAULT_LONG_F);
        assertThat(testEmployee.getIntF()).isEqualTo(DEFAULT_INT_F);
        assertThat(testEmployee.getBoolF()).isEqualTo(DEFAULT_BOOL_F);
        assertThat(testEmployee.getBigF()).isEqualByComparingTo(DEFAULT_BIG_F);
        assertThat(testEmployee.getDurF()).isEqualTo(DEFAULT_DUR_F);
        assertThat(testEmployee.getLocalDF()).isEqualTo(DEFAULT_LOCAL_DF);
        assertThat(testEmployee.getZoneDF()).isEqualTo(DEFAULT_ZONE_DF);
        assertThat(testEmployee.getUuF()).isEqualTo(DEFAULT_UU_F);
        assertThat(testEmployee.getPict()).isEqualTo(DEFAULT_PICT);
        assertThat(testEmployee.getPictContentType()).isEqualTo(DEFAULT_PICT_CONTENT_TYPE);
        assertThat(testEmployee.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testEmployee.getCv()).isEqualTo(DEFAULT_CV);
        assertThat(testEmployee.getCvContentType()).isEqualTo(DEFAULT_CV_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createEmployeeWithExistingId() throws Exception {
        // Create the Employee with an existing ID
        employee.setId(1L);
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].hireDate").value(hasItem(DEFAULT_HIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].commissionPct").value(hasItem(DEFAULT_COMMISSION_PCT.doubleValue())))
            .andExpect(jsonPath("$.[*].longF").value(hasItem(DEFAULT_LONG_F.intValue())))
            .andExpect(jsonPath("$.[*].intF").value(hasItem(DEFAULT_INT_F)))
            .andExpect(jsonPath("$.[*].boolF").value(hasItem(DEFAULT_BOOL_F.booleanValue())))
            .andExpect(jsonPath("$.[*].bigF").value(hasItem(sameNumber(DEFAULT_BIG_F))))
            .andExpect(jsonPath("$.[*].durF").value(hasItem(DEFAULT_DUR_F.toString())))
            .andExpect(jsonPath("$.[*].localDF").value(hasItem(DEFAULT_LOCAL_DF.toString())))
            .andExpect(jsonPath("$.[*].zoneDF").value(hasItem(sameInstant(DEFAULT_ZONE_DF))))
            .andExpect(jsonPath("$.[*].uuF").value(hasItem(DEFAULT_UU_F.toString())))
            .andExpect(jsonPath("$.[*].pictContentType").value(hasItem(DEFAULT_PICT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pict").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICT))))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].cvContentType").value(hasItem(DEFAULT_CV_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].cv").value(hasItem(Base64Utils.encodeToString(DEFAULT_CV))));
    }

    @Test
    @Transactional
    void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL_ID, employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.hireDate").value(DEFAULT_HIRE_DATE.toString()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.doubleValue()))
            .andExpect(jsonPath("$.commissionPct").value(DEFAULT_COMMISSION_PCT.doubleValue()))
            .andExpect(jsonPath("$.longF").value(DEFAULT_LONG_F.intValue()))
            .andExpect(jsonPath("$.intF").value(DEFAULT_INT_F))
            .andExpect(jsonPath("$.boolF").value(DEFAULT_BOOL_F.booleanValue()))
            .andExpect(jsonPath("$.bigF").value(sameNumber(DEFAULT_BIG_F)))
            .andExpect(jsonPath("$.durF").value(DEFAULT_DUR_F.toString()))
            .andExpect(jsonPath("$.localDF").value(DEFAULT_LOCAL_DF.toString()))
            .andExpect(jsonPath("$.zoneDF").value(sameInstant(DEFAULT_ZONE_DF)))
            .andExpect(jsonPath("$.uuF").value(DEFAULT_UU_F.toString()))
            .andExpect(jsonPath("$.pictContentType").value(DEFAULT_PICT_CONTENT_TYPE))
            .andExpect(jsonPath("$.pict").value(Base64Utils.encodeToString(DEFAULT_PICT)))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.cvContentType").value(DEFAULT_CV_CONTENT_TYPE))
            .andExpect(jsonPath("$.cv").value(Base64Utils.encodeToString(DEFAULT_CV)));
    }

    @Test
    @Transactional
    void getEmployeesByIdFiltering() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        Long id = employee.getId();

        defaultEmployeeShouldBeFound("id.equals=" + id);
        defaultEmployeeShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeesByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName equals to DEFAULT_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the employeeList where firstName equals to UPDATED_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the employeeList where firstName equals to UPDATED_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName is not null
        defaultEmployeeShouldBeFound("firstName.specified=true");

        // Get all the employeeList where firstName is null
        defaultEmployeeShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName contains DEFAULT_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the employeeList where firstName contains UPDATED_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName does not contain DEFAULT_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the employeeList where firstName does not contain UPDATED_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName equals to DEFAULT_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the employeeList where lastName equals to UPDATED_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the employeeList where lastName equals to UPDATED_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName is not null
        defaultEmployeeShouldBeFound("lastName.specified=true");

        // Get all the employeeList where lastName is null
        defaultEmployeeShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByLastNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName contains DEFAULT_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the employeeList where lastName contains UPDATED_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName does not contain DEFAULT_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the employeeList where lastName does not contain UPDATED_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email equals to DEFAULT_EMAIL
        defaultEmployeeShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the employeeList where email equals to UPDATED_EMAIL
        defaultEmployeeShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultEmployeeShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the employeeList where email equals to UPDATED_EMAIL
        defaultEmployeeShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email is not null
        defaultEmployeeShouldBeFound("email.specified=true");

        // Get all the employeeList where email is null
        defaultEmployeeShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email contains DEFAULT_EMAIL
        defaultEmployeeShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the employeeList where email contains UPDATED_EMAIL
        defaultEmployeeShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email does not contain DEFAULT_EMAIL
        defaultEmployeeShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the employeeList where email does not contain UPDATED_EMAIL
        defaultEmployeeShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultEmployeeShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the employeeList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultEmployeeShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultEmployeeShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the employeeList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultEmployeeShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phoneNumber is not null
        defaultEmployeeShouldBeFound("phoneNumber.specified=true");

        // Get all the employeeList where phoneNumber is null
        defaultEmployeeShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultEmployeeShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the employeeList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultEmployeeShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultEmployeeShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the employeeList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultEmployeeShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllEmployeesByHireDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where hireDate equals to DEFAULT_HIRE_DATE
        defaultEmployeeShouldBeFound("hireDate.equals=" + DEFAULT_HIRE_DATE);

        // Get all the employeeList where hireDate equals to UPDATED_HIRE_DATE
        defaultEmployeeShouldNotBeFound("hireDate.equals=" + UPDATED_HIRE_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByHireDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where hireDate in DEFAULT_HIRE_DATE or UPDATED_HIRE_DATE
        defaultEmployeeShouldBeFound("hireDate.in=" + DEFAULT_HIRE_DATE + "," + UPDATED_HIRE_DATE);

        // Get all the employeeList where hireDate equals to UPDATED_HIRE_DATE
        defaultEmployeeShouldNotBeFound("hireDate.in=" + UPDATED_HIRE_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByHireDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where hireDate is not null
        defaultEmployeeShouldBeFound("hireDate.specified=true");

        // Get all the employeeList where hireDate is null
        defaultEmployeeShouldNotBeFound("hireDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesBySalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary equals to DEFAULT_SALARY
        defaultEmployeeShouldBeFound("salary.equals=" + DEFAULT_SALARY);

        // Get all the employeeList where salary equals to UPDATED_SALARY
        defaultEmployeeShouldNotBeFound("salary.equals=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    void getAllEmployeesBySalaryIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary in DEFAULT_SALARY or UPDATED_SALARY
        defaultEmployeeShouldBeFound("salary.in=" + DEFAULT_SALARY + "," + UPDATED_SALARY);

        // Get all the employeeList where salary equals to UPDATED_SALARY
        defaultEmployeeShouldNotBeFound("salary.in=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    void getAllEmployeesBySalaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary is not null
        defaultEmployeeShouldBeFound("salary.specified=true");

        // Get all the employeeList where salary is null
        defaultEmployeeShouldNotBeFound("salary.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesBySalaryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary is greater than or equal to DEFAULT_SALARY
        defaultEmployeeShouldBeFound("salary.greaterThanOrEqual=" + DEFAULT_SALARY);

        // Get all the employeeList where salary is greater than or equal to UPDATED_SALARY
        defaultEmployeeShouldNotBeFound("salary.greaterThanOrEqual=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    void getAllEmployeesBySalaryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary is less than or equal to DEFAULT_SALARY
        defaultEmployeeShouldBeFound("salary.lessThanOrEqual=" + DEFAULT_SALARY);

        // Get all the employeeList where salary is less than or equal to SMALLER_SALARY
        defaultEmployeeShouldNotBeFound("salary.lessThanOrEqual=" + SMALLER_SALARY);
    }

    @Test
    @Transactional
    void getAllEmployeesBySalaryIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary is less than DEFAULT_SALARY
        defaultEmployeeShouldNotBeFound("salary.lessThan=" + DEFAULT_SALARY);

        // Get all the employeeList where salary is less than UPDATED_SALARY
        defaultEmployeeShouldBeFound("salary.lessThan=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    void getAllEmployeesBySalaryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where salary is greater than DEFAULT_SALARY
        defaultEmployeeShouldNotBeFound("salary.greaterThan=" + DEFAULT_SALARY);

        // Get all the employeeList where salary is greater than SMALLER_SALARY
        defaultEmployeeShouldBeFound("salary.greaterThan=" + SMALLER_SALARY);
    }

    @Test
    @Transactional
    void getAllEmployeesByCommissionPctIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where commissionPct equals to DEFAULT_COMMISSION_PCT
        defaultEmployeeShouldBeFound("commissionPct.equals=" + DEFAULT_COMMISSION_PCT);

        // Get all the employeeList where commissionPct equals to UPDATED_COMMISSION_PCT
        defaultEmployeeShouldNotBeFound("commissionPct.equals=" + UPDATED_COMMISSION_PCT);
    }

    @Test
    @Transactional
    void getAllEmployeesByCommissionPctIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where commissionPct in DEFAULT_COMMISSION_PCT or UPDATED_COMMISSION_PCT
        defaultEmployeeShouldBeFound("commissionPct.in=" + DEFAULT_COMMISSION_PCT + "," + UPDATED_COMMISSION_PCT);

        // Get all the employeeList where commissionPct equals to UPDATED_COMMISSION_PCT
        defaultEmployeeShouldNotBeFound("commissionPct.in=" + UPDATED_COMMISSION_PCT);
    }

    @Test
    @Transactional
    void getAllEmployeesByCommissionPctIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where commissionPct is not null
        defaultEmployeeShouldBeFound("commissionPct.specified=true");

        // Get all the employeeList where commissionPct is null
        defaultEmployeeShouldNotBeFound("commissionPct.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByCommissionPctIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where commissionPct is greater than or equal to DEFAULT_COMMISSION_PCT
        defaultEmployeeShouldBeFound("commissionPct.greaterThanOrEqual=" + DEFAULT_COMMISSION_PCT);

        // Get all the employeeList where commissionPct is greater than or equal to UPDATED_COMMISSION_PCT
        defaultEmployeeShouldNotBeFound("commissionPct.greaterThanOrEqual=" + UPDATED_COMMISSION_PCT);
    }

    @Test
    @Transactional
    void getAllEmployeesByCommissionPctIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where commissionPct is less than or equal to DEFAULT_COMMISSION_PCT
        defaultEmployeeShouldBeFound("commissionPct.lessThanOrEqual=" + DEFAULT_COMMISSION_PCT);

        // Get all the employeeList where commissionPct is less than or equal to SMALLER_COMMISSION_PCT
        defaultEmployeeShouldNotBeFound("commissionPct.lessThanOrEqual=" + SMALLER_COMMISSION_PCT);
    }

    @Test
    @Transactional
    void getAllEmployeesByCommissionPctIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where commissionPct is less than DEFAULT_COMMISSION_PCT
        defaultEmployeeShouldNotBeFound("commissionPct.lessThan=" + DEFAULT_COMMISSION_PCT);

        // Get all the employeeList where commissionPct is less than UPDATED_COMMISSION_PCT
        defaultEmployeeShouldBeFound("commissionPct.lessThan=" + UPDATED_COMMISSION_PCT);
    }

    @Test
    @Transactional
    void getAllEmployeesByCommissionPctIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where commissionPct is greater than DEFAULT_COMMISSION_PCT
        defaultEmployeeShouldNotBeFound("commissionPct.greaterThan=" + DEFAULT_COMMISSION_PCT);

        // Get all the employeeList where commissionPct is greater than SMALLER_COMMISSION_PCT
        defaultEmployeeShouldBeFound("commissionPct.greaterThan=" + SMALLER_COMMISSION_PCT);
    }

    @Test
    @Transactional
    void getAllEmployeesByLongFIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where longF equals to DEFAULT_LONG_F
        defaultEmployeeShouldBeFound("longF.equals=" + DEFAULT_LONG_F);

        // Get all the employeeList where longF equals to UPDATED_LONG_F
        defaultEmployeeShouldNotBeFound("longF.equals=" + UPDATED_LONG_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByLongFIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where longF in DEFAULT_LONG_F or UPDATED_LONG_F
        defaultEmployeeShouldBeFound("longF.in=" + DEFAULT_LONG_F + "," + UPDATED_LONG_F);

        // Get all the employeeList where longF equals to UPDATED_LONG_F
        defaultEmployeeShouldNotBeFound("longF.in=" + UPDATED_LONG_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByLongFIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where longF is not null
        defaultEmployeeShouldBeFound("longF.specified=true");

        // Get all the employeeList where longF is null
        defaultEmployeeShouldNotBeFound("longF.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByLongFIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where longF is greater than or equal to DEFAULT_LONG_F
        defaultEmployeeShouldBeFound("longF.greaterThanOrEqual=" + DEFAULT_LONG_F);

        // Get all the employeeList where longF is greater than or equal to UPDATED_LONG_F
        defaultEmployeeShouldNotBeFound("longF.greaterThanOrEqual=" + UPDATED_LONG_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByLongFIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where longF is less than or equal to DEFAULT_LONG_F
        defaultEmployeeShouldBeFound("longF.lessThanOrEqual=" + DEFAULT_LONG_F);

        // Get all the employeeList where longF is less than or equal to SMALLER_LONG_F
        defaultEmployeeShouldNotBeFound("longF.lessThanOrEqual=" + SMALLER_LONG_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByLongFIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where longF is less than DEFAULT_LONG_F
        defaultEmployeeShouldNotBeFound("longF.lessThan=" + DEFAULT_LONG_F);

        // Get all the employeeList where longF is less than UPDATED_LONG_F
        defaultEmployeeShouldBeFound("longF.lessThan=" + UPDATED_LONG_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByLongFIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where longF is greater than DEFAULT_LONG_F
        defaultEmployeeShouldNotBeFound("longF.greaterThan=" + DEFAULT_LONG_F);

        // Get all the employeeList where longF is greater than SMALLER_LONG_F
        defaultEmployeeShouldBeFound("longF.greaterThan=" + SMALLER_LONG_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByIntFIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where intF equals to DEFAULT_INT_F
        defaultEmployeeShouldBeFound("intF.equals=" + DEFAULT_INT_F);

        // Get all the employeeList where intF equals to UPDATED_INT_F
        defaultEmployeeShouldNotBeFound("intF.equals=" + UPDATED_INT_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByIntFIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where intF in DEFAULT_INT_F or UPDATED_INT_F
        defaultEmployeeShouldBeFound("intF.in=" + DEFAULT_INT_F + "," + UPDATED_INT_F);

        // Get all the employeeList where intF equals to UPDATED_INT_F
        defaultEmployeeShouldNotBeFound("intF.in=" + UPDATED_INT_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByIntFIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where intF is not null
        defaultEmployeeShouldBeFound("intF.specified=true");

        // Get all the employeeList where intF is null
        defaultEmployeeShouldNotBeFound("intF.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByIntFIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where intF is greater than or equal to DEFAULT_INT_F
        defaultEmployeeShouldBeFound("intF.greaterThanOrEqual=" + DEFAULT_INT_F);

        // Get all the employeeList where intF is greater than or equal to UPDATED_INT_F
        defaultEmployeeShouldNotBeFound("intF.greaterThanOrEqual=" + UPDATED_INT_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByIntFIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where intF is less than or equal to DEFAULT_INT_F
        defaultEmployeeShouldBeFound("intF.lessThanOrEqual=" + DEFAULT_INT_F);

        // Get all the employeeList where intF is less than or equal to SMALLER_INT_F
        defaultEmployeeShouldNotBeFound("intF.lessThanOrEqual=" + SMALLER_INT_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByIntFIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where intF is less than DEFAULT_INT_F
        defaultEmployeeShouldNotBeFound("intF.lessThan=" + DEFAULT_INT_F);

        // Get all the employeeList where intF is less than UPDATED_INT_F
        defaultEmployeeShouldBeFound("intF.lessThan=" + UPDATED_INT_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByIntFIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where intF is greater than DEFAULT_INT_F
        defaultEmployeeShouldNotBeFound("intF.greaterThan=" + DEFAULT_INT_F);

        // Get all the employeeList where intF is greater than SMALLER_INT_F
        defaultEmployeeShouldBeFound("intF.greaterThan=" + SMALLER_INT_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByBoolFIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where boolF equals to DEFAULT_BOOL_F
        defaultEmployeeShouldBeFound("boolF.equals=" + DEFAULT_BOOL_F);

        // Get all the employeeList where boolF equals to UPDATED_BOOL_F
        defaultEmployeeShouldNotBeFound("boolF.equals=" + UPDATED_BOOL_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByBoolFIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where boolF in DEFAULT_BOOL_F or UPDATED_BOOL_F
        defaultEmployeeShouldBeFound("boolF.in=" + DEFAULT_BOOL_F + "," + UPDATED_BOOL_F);

        // Get all the employeeList where boolF equals to UPDATED_BOOL_F
        defaultEmployeeShouldNotBeFound("boolF.in=" + UPDATED_BOOL_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByBoolFIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where boolF is not null
        defaultEmployeeShouldBeFound("boolF.specified=true");

        // Get all the employeeList where boolF is null
        defaultEmployeeShouldNotBeFound("boolF.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByBigFIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bigF equals to DEFAULT_BIG_F
        defaultEmployeeShouldBeFound("bigF.equals=" + DEFAULT_BIG_F);

        // Get all the employeeList where bigF equals to UPDATED_BIG_F
        defaultEmployeeShouldNotBeFound("bigF.equals=" + UPDATED_BIG_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByBigFIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bigF in DEFAULT_BIG_F or UPDATED_BIG_F
        defaultEmployeeShouldBeFound("bigF.in=" + DEFAULT_BIG_F + "," + UPDATED_BIG_F);

        // Get all the employeeList where bigF equals to UPDATED_BIG_F
        defaultEmployeeShouldNotBeFound("bigF.in=" + UPDATED_BIG_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByBigFIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bigF is not null
        defaultEmployeeShouldBeFound("bigF.specified=true");

        // Get all the employeeList where bigF is null
        defaultEmployeeShouldNotBeFound("bigF.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByBigFIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bigF is greater than or equal to DEFAULT_BIG_F
        defaultEmployeeShouldBeFound("bigF.greaterThanOrEqual=" + DEFAULT_BIG_F);

        // Get all the employeeList where bigF is greater than or equal to UPDATED_BIG_F
        defaultEmployeeShouldNotBeFound("bigF.greaterThanOrEqual=" + UPDATED_BIG_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByBigFIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bigF is less than or equal to DEFAULT_BIG_F
        defaultEmployeeShouldBeFound("bigF.lessThanOrEqual=" + DEFAULT_BIG_F);

        // Get all the employeeList where bigF is less than or equal to SMALLER_BIG_F
        defaultEmployeeShouldNotBeFound("bigF.lessThanOrEqual=" + SMALLER_BIG_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByBigFIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bigF is less than DEFAULT_BIG_F
        defaultEmployeeShouldNotBeFound("bigF.lessThan=" + DEFAULT_BIG_F);

        // Get all the employeeList where bigF is less than UPDATED_BIG_F
        defaultEmployeeShouldBeFound("bigF.lessThan=" + UPDATED_BIG_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByBigFIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where bigF is greater than DEFAULT_BIG_F
        defaultEmployeeShouldNotBeFound("bigF.greaterThan=" + DEFAULT_BIG_F);

        // Get all the employeeList where bigF is greater than SMALLER_BIG_F
        defaultEmployeeShouldBeFound("bigF.greaterThan=" + SMALLER_BIG_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByDurFIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where durF equals to DEFAULT_DUR_F
        defaultEmployeeShouldBeFound("durF.equals=" + DEFAULT_DUR_F);

        // Get all the employeeList where durF equals to UPDATED_DUR_F
        defaultEmployeeShouldNotBeFound("durF.equals=" + UPDATED_DUR_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByDurFIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where durF in DEFAULT_DUR_F or UPDATED_DUR_F
        defaultEmployeeShouldBeFound("durF.in=" + DEFAULT_DUR_F + "," + UPDATED_DUR_F);

        // Get all the employeeList where durF equals to UPDATED_DUR_F
        defaultEmployeeShouldNotBeFound("durF.in=" + UPDATED_DUR_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByDurFIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where durF is not null
        defaultEmployeeShouldBeFound("durF.specified=true");

        // Get all the employeeList where durF is null
        defaultEmployeeShouldNotBeFound("durF.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByDurFIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where durF is greater than or equal to DEFAULT_DUR_F
        defaultEmployeeShouldBeFound("durF.greaterThanOrEqual=" + DEFAULT_DUR_F);

        // Get all the employeeList where durF is greater than or equal to UPDATED_DUR_F
        defaultEmployeeShouldNotBeFound("durF.greaterThanOrEqual=" + UPDATED_DUR_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByDurFIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where durF is less than or equal to DEFAULT_DUR_F
        defaultEmployeeShouldBeFound("durF.lessThanOrEqual=" + DEFAULT_DUR_F);

        // Get all the employeeList where durF is less than or equal to SMALLER_DUR_F
        defaultEmployeeShouldNotBeFound("durF.lessThanOrEqual=" + SMALLER_DUR_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByDurFIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where durF is less than DEFAULT_DUR_F
        defaultEmployeeShouldNotBeFound("durF.lessThan=" + DEFAULT_DUR_F);

        // Get all the employeeList where durF is less than UPDATED_DUR_F
        defaultEmployeeShouldBeFound("durF.lessThan=" + UPDATED_DUR_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByDurFIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where durF is greater than DEFAULT_DUR_F
        defaultEmployeeShouldNotBeFound("durF.greaterThan=" + DEFAULT_DUR_F);

        // Get all the employeeList where durF is greater than SMALLER_DUR_F
        defaultEmployeeShouldBeFound("durF.greaterThan=" + SMALLER_DUR_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByLocalDFIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where localDF equals to DEFAULT_LOCAL_DF
        defaultEmployeeShouldBeFound("localDF.equals=" + DEFAULT_LOCAL_DF);

        // Get all the employeeList where localDF equals to UPDATED_LOCAL_DF
        defaultEmployeeShouldNotBeFound("localDF.equals=" + UPDATED_LOCAL_DF);
    }

    @Test
    @Transactional
    void getAllEmployeesByLocalDFIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where localDF in DEFAULT_LOCAL_DF or UPDATED_LOCAL_DF
        defaultEmployeeShouldBeFound("localDF.in=" + DEFAULT_LOCAL_DF + "," + UPDATED_LOCAL_DF);

        // Get all the employeeList where localDF equals to UPDATED_LOCAL_DF
        defaultEmployeeShouldNotBeFound("localDF.in=" + UPDATED_LOCAL_DF);
    }

    @Test
    @Transactional
    void getAllEmployeesByLocalDFIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where localDF is not null
        defaultEmployeeShouldBeFound("localDF.specified=true");

        // Get all the employeeList where localDF is null
        defaultEmployeeShouldNotBeFound("localDF.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByLocalDFIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where localDF is greater than or equal to DEFAULT_LOCAL_DF
        defaultEmployeeShouldBeFound("localDF.greaterThanOrEqual=" + DEFAULT_LOCAL_DF);

        // Get all the employeeList where localDF is greater than or equal to UPDATED_LOCAL_DF
        defaultEmployeeShouldNotBeFound("localDF.greaterThanOrEqual=" + UPDATED_LOCAL_DF);
    }

    @Test
    @Transactional
    void getAllEmployeesByLocalDFIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where localDF is less than or equal to DEFAULT_LOCAL_DF
        defaultEmployeeShouldBeFound("localDF.lessThanOrEqual=" + DEFAULT_LOCAL_DF);

        // Get all the employeeList where localDF is less than or equal to SMALLER_LOCAL_DF
        defaultEmployeeShouldNotBeFound("localDF.lessThanOrEqual=" + SMALLER_LOCAL_DF);
    }

    @Test
    @Transactional
    void getAllEmployeesByLocalDFIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where localDF is less than DEFAULT_LOCAL_DF
        defaultEmployeeShouldNotBeFound("localDF.lessThan=" + DEFAULT_LOCAL_DF);

        // Get all the employeeList where localDF is less than UPDATED_LOCAL_DF
        defaultEmployeeShouldBeFound("localDF.lessThan=" + UPDATED_LOCAL_DF);
    }

    @Test
    @Transactional
    void getAllEmployeesByLocalDFIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where localDF is greater than DEFAULT_LOCAL_DF
        defaultEmployeeShouldNotBeFound("localDF.greaterThan=" + DEFAULT_LOCAL_DF);

        // Get all the employeeList where localDF is greater than SMALLER_LOCAL_DF
        defaultEmployeeShouldBeFound("localDF.greaterThan=" + SMALLER_LOCAL_DF);
    }

    @Test
    @Transactional
    void getAllEmployeesByZoneDFIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where zoneDF equals to DEFAULT_ZONE_DF
        defaultEmployeeShouldBeFound("zoneDF.equals=" + DEFAULT_ZONE_DF);

        // Get all the employeeList where zoneDF equals to UPDATED_ZONE_DF
        defaultEmployeeShouldNotBeFound("zoneDF.equals=" + UPDATED_ZONE_DF);
    }

    @Test
    @Transactional
    void getAllEmployeesByZoneDFIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where zoneDF in DEFAULT_ZONE_DF or UPDATED_ZONE_DF
        defaultEmployeeShouldBeFound("zoneDF.in=" + DEFAULT_ZONE_DF + "," + UPDATED_ZONE_DF);

        // Get all the employeeList where zoneDF equals to UPDATED_ZONE_DF
        defaultEmployeeShouldNotBeFound("zoneDF.in=" + UPDATED_ZONE_DF);
    }

    @Test
    @Transactional
    void getAllEmployeesByZoneDFIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where zoneDF is not null
        defaultEmployeeShouldBeFound("zoneDF.specified=true");

        // Get all the employeeList where zoneDF is null
        defaultEmployeeShouldNotBeFound("zoneDF.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByZoneDFIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where zoneDF is greater than or equal to DEFAULT_ZONE_DF
        defaultEmployeeShouldBeFound("zoneDF.greaterThanOrEqual=" + DEFAULT_ZONE_DF);

        // Get all the employeeList where zoneDF is greater than or equal to UPDATED_ZONE_DF
        defaultEmployeeShouldNotBeFound("zoneDF.greaterThanOrEqual=" + UPDATED_ZONE_DF);
    }

    @Test
    @Transactional
    void getAllEmployeesByZoneDFIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where zoneDF is less than or equal to DEFAULT_ZONE_DF
        defaultEmployeeShouldBeFound("zoneDF.lessThanOrEqual=" + DEFAULT_ZONE_DF);

        // Get all the employeeList where zoneDF is less than or equal to SMALLER_ZONE_DF
        defaultEmployeeShouldNotBeFound("zoneDF.lessThanOrEqual=" + SMALLER_ZONE_DF);
    }

    @Test
    @Transactional
    void getAllEmployeesByZoneDFIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where zoneDF is less than DEFAULT_ZONE_DF
        defaultEmployeeShouldNotBeFound("zoneDF.lessThan=" + DEFAULT_ZONE_DF);

        // Get all the employeeList where zoneDF is less than UPDATED_ZONE_DF
        defaultEmployeeShouldBeFound("zoneDF.lessThan=" + UPDATED_ZONE_DF);
    }

    @Test
    @Transactional
    void getAllEmployeesByZoneDFIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where zoneDF is greater than DEFAULT_ZONE_DF
        defaultEmployeeShouldNotBeFound("zoneDF.greaterThan=" + DEFAULT_ZONE_DF);

        // Get all the employeeList where zoneDF is greater than SMALLER_ZONE_DF
        defaultEmployeeShouldBeFound("zoneDF.greaterThan=" + SMALLER_ZONE_DF);
    }

    @Test
    @Transactional
    void getAllEmployeesByUuFIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where uuF equals to DEFAULT_UU_F
        defaultEmployeeShouldBeFound("uuF.equals=" + DEFAULT_UU_F);

        // Get all the employeeList where uuF equals to UPDATED_UU_F
        defaultEmployeeShouldNotBeFound("uuF.equals=" + UPDATED_UU_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByUuFIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where uuF in DEFAULT_UU_F or UPDATED_UU_F
        defaultEmployeeShouldBeFound("uuF.in=" + DEFAULT_UU_F + "," + UPDATED_UU_F);

        // Get all the employeeList where uuF equals to UPDATED_UU_F
        defaultEmployeeShouldNotBeFound("uuF.in=" + UPDATED_UU_F);
    }

    @Test
    @Transactional
    void getAllEmployeesByUuFIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where uuF is not null
        defaultEmployeeShouldBeFound("uuF.specified=true");

        // Get all the employeeList where uuF is null
        defaultEmployeeShouldNotBeFound("uuF.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByJobIsEqualToSomething() throws Exception {
        Job job;
        if (TestUtil.findAll(em, Job.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            job = JobResourceIT.createEntity(em);
        } else {
            job = TestUtil.findAll(em, Job.class).get(0);
        }
        em.persist(job);
        em.flush();
        employee.addJob(job);
        employeeRepository.saveAndFlush(employee);
        Long jobId = job.getId();
        // Get all the employeeList where job equals to jobId
        defaultEmployeeShouldBeFound("jobId.equals=" + jobId);

        // Get all the employeeList where job equals to (jobId + 1)
        defaultEmployeeShouldNotBeFound("jobId.equals=" + (jobId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByManagerIsEqualToSomething() throws Exception {
        Employee manager;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            manager = EmployeeResourceIT.createEntity(em);
        } else {
            manager = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(manager);
        em.flush();
        employee.setManager(manager);
        employeeRepository.saveAndFlush(employee);
        Long managerId = manager.getId();
        // Get all the employeeList where manager equals to managerId
        defaultEmployeeShouldBeFound("managerId.equals=" + managerId);

        // Get all the employeeList where manager equals to (managerId + 1)
        defaultEmployeeShouldNotBeFound("managerId.equals=" + (managerId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByDepartmentIsEqualToSomething() throws Exception {
        Department department;
        if (TestUtil.findAll(em, Department.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            department = DepartmentResourceIT.createEntity(em);
        } else {
            department = TestUtil.findAll(em, Department.class).get(0);
        }
        em.persist(department);
        em.flush();
        employee.setDepartment(department);
        employeeRepository.saveAndFlush(employee);
        Long departmentId = department.getId();
        // Get all the employeeList where department equals to departmentId
        defaultEmployeeShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the employeeList where department equals to (departmentId + 1)
        defaultEmployeeShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByJobHistoryIsEqualToSomething() throws Exception {
        JobHistory jobHistory;
        if (TestUtil.findAll(em, JobHistory.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            jobHistory = JobHistoryResourceIT.createEntity(em);
        } else {
            jobHistory = TestUtil.findAll(em, JobHistory.class).get(0);
        }
        em.persist(jobHistory);
        em.flush();
        employee.setJobHistory(jobHistory);
        jobHistory.setEmployee(employee);
        employeeRepository.saveAndFlush(employee);
        Long jobHistoryId = jobHistory.getId();
        // Get all the employeeList where jobHistory equals to jobHistoryId
        defaultEmployeeShouldBeFound("jobHistoryId.equals=" + jobHistoryId);

        // Get all the employeeList where jobHistory equals to (jobHistoryId + 1)
        defaultEmployeeShouldNotBeFound("jobHistoryId.equals=" + (jobHistoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeShouldBeFound(String filter) throws Exception {
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].hireDate").value(hasItem(DEFAULT_HIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].commissionPct").value(hasItem(DEFAULT_COMMISSION_PCT.doubleValue())))
            .andExpect(jsonPath("$.[*].longF").value(hasItem(DEFAULT_LONG_F.intValue())))
            .andExpect(jsonPath("$.[*].intF").value(hasItem(DEFAULT_INT_F)))
            .andExpect(jsonPath("$.[*].boolF").value(hasItem(DEFAULT_BOOL_F.booleanValue())))
            .andExpect(jsonPath("$.[*].bigF").value(hasItem(sameNumber(DEFAULT_BIG_F))))
            .andExpect(jsonPath("$.[*].durF").value(hasItem(DEFAULT_DUR_F.toString())))
            .andExpect(jsonPath("$.[*].localDF").value(hasItem(DEFAULT_LOCAL_DF.toString())))
            .andExpect(jsonPath("$.[*].zoneDF").value(hasItem(sameInstant(DEFAULT_ZONE_DF))))
            .andExpect(jsonPath("$.[*].uuF").value(hasItem(DEFAULT_UU_F.toString())))
            .andExpect(jsonPath("$.[*].pictContentType").value(hasItem(DEFAULT_PICT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pict").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICT))))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].cvContentType").value(hasItem(DEFAULT_CV_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].cv").value(hasItem(Base64Utils.encodeToString(DEFAULT_CV))));

        // Check, that the count call also returns 1
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeShouldNotBeFound(String filter) throws Exception {
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEmployee are not directly saved in db
        em.detach(updatedEmployee);
        updatedEmployee
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .hireDate(UPDATED_HIRE_DATE)
            .salary(UPDATED_SALARY)
            .commissionPct(UPDATED_COMMISSION_PCT)
            .longF(UPDATED_LONG_F)
            .intF(UPDATED_INT_F)
            .boolF(UPDATED_BOOL_F)
            .bigF(UPDATED_BIG_F)
            .durF(UPDATED_DUR_F)
            .localDF(UPDATED_LOCAL_DF)
            .zoneDF(UPDATED_ZONE_DF)
            .uuF(UPDATED_UU_F)
            .pict(UPDATED_PICT)
            .pictContentType(UPDATED_PICT_CONTENT_TYPE)
            .comments(UPDATED_COMMENTS)
            .cv(UPDATED_CV)
            .cvContentType(UPDATED_CV_CONTENT_TYPE);
        EmployeeDTO employeeDTO = employeeMapper.toDto(updatedEmployee);

        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEmployee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployee.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testEmployee.getHireDate()).isEqualTo(UPDATED_HIRE_DATE);
        assertThat(testEmployee.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testEmployee.getCommissionPct()).isEqualTo(UPDATED_COMMISSION_PCT);
        assertThat(testEmployee.getLongF()).isEqualTo(UPDATED_LONG_F);
        assertThat(testEmployee.getIntF()).isEqualTo(UPDATED_INT_F);
        assertThat(testEmployee.getBoolF()).isEqualTo(UPDATED_BOOL_F);
        assertThat(testEmployee.getBigF()).isEqualByComparingTo(UPDATED_BIG_F);
        assertThat(testEmployee.getDurF()).isEqualTo(UPDATED_DUR_F);
        assertThat(testEmployee.getLocalDF()).isEqualTo(UPDATED_LOCAL_DF);
        assertThat(testEmployee.getZoneDF()).isEqualTo(UPDATED_ZONE_DF);
        assertThat(testEmployee.getUuF()).isEqualTo(UPDATED_UU_F);
        assertThat(testEmployee.getPict()).isEqualTo(UPDATED_PICT);
        assertThat(testEmployee.getPictContentType()).isEqualTo(UPDATED_PICT_CONTENT_TYPE);
        assertThat(testEmployee.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testEmployee.getCv()).isEqualTo(UPDATED_CV);
        assertThat(testEmployee.getCvContentType()).isEqualTo(UPDATED_CV_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeWithPatch() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee using partial update
        Employee partialUpdatedEmployee = new Employee();
        partialUpdatedEmployee.setId(employee.getId());

        partialUpdatedEmployee
            .lastName(UPDATED_LAST_NAME)
            .salary(UPDATED_SALARY)
            .longF(UPDATED_LONG_F)
            .intF(UPDATED_INT_F)
            .boolF(UPDATED_BOOL_F)
            .bigF(UPDATED_BIG_F)
            .localDF(UPDATED_LOCAL_DF)
            .zoneDF(UPDATED_ZONE_DF)
            .comments(UPDATED_COMMENTS);

        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testEmployee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployee.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testEmployee.getHireDate()).isEqualTo(DEFAULT_HIRE_DATE);
        assertThat(testEmployee.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testEmployee.getCommissionPct()).isEqualTo(DEFAULT_COMMISSION_PCT);
        assertThat(testEmployee.getLongF()).isEqualTo(UPDATED_LONG_F);
        assertThat(testEmployee.getIntF()).isEqualTo(UPDATED_INT_F);
        assertThat(testEmployee.getBoolF()).isEqualTo(UPDATED_BOOL_F);
        assertThat(testEmployee.getBigF()).isEqualByComparingTo(UPDATED_BIG_F);
        assertThat(testEmployee.getDurF()).isEqualTo(DEFAULT_DUR_F);
        assertThat(testEmployee.getLocalDF()).isEqualTo(UPDATED_LOCAL_DF);
        assertThat(testEmployee.getZoneDF()).isEqualTo(UPDATED_ZONE_DF);
        assertThat(testEmployee.getUuF()).isEqualTo(DEFAULT_UU_F);
        assertThat(testEmployee.getPict()).isEqualTo(DEFAULT_PICT);
        assertThat(testEmployee.getPictContentType()).isEqualTo(DEFAULT_PICT_CONTENT_TYPE);
        assertThat(testEmployee.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testEmployee.getCv()).isEqualTo(DEFAULT_CV);
        assertThat(testEmployee.getCvContentType()).isEqualTo(DEFAULT_CV_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeWithPatch() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee using partial update
        Employee partialUpdatedEmployee = new Employee();
        partialUpdatedEmployee.setId(employee.getId());

        partialUpdatedEmployee
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .hireDate(UPDATED_HIRE_DATE)
            .salary(UPDATED_SALARY)
            .commissionPct(UPDATED_COMMISSION_PCT)
            .longF(UPDATED_LONG_F)
            .intF(UPDATED_INT_F)
            .boolF(UPDATED_BOOL_F)
            .bigF(UPDATED_BIG_F)
            .durF(UPDATED_DUR_F)
            .localDF(UPDATED_LOCAL_DF)
            .zoneDF(UPDATED_ZONE_DF)
            .uuF(UPDATED_UU_F)
            .pict(UPDATED_PICT)
            .pictContentType(UPDATED_PICT_CONTENT_TYPE)
            .comments(UPDATED_COMMENTS)
            .cv(UPDATED_CV)
            .cvContentType(UPDATED_CV_CONTENT_TYPE);

        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEmployee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployee.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testEmployee.getHireDate()).isEqualTo(UPDATED_HIRE_DATE);
        assertThat(testEmployee.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testEmployee.getCommissionPct()).isEqualTo(UPDATED_COMMISSION_PCT);
        assertThat(testEmployee.getLongF()).isEqualTo(UPDATED_LONG_F);
        assertThat(testEmployee.getIntF()).isEqualTo(UPDATED_INT_F);
        assertThat(testEmployee.getBoolF()).isEqualTo(UPDATED_BOOL_F);
        assertThat(testEmployee.getBigF()).isEqualByComparingTo(UPDATED_BIG_F);
        assertThat(testEmployee.getDurF()).isEqualTo(UPDATED_DUR_F);
        assertThat(testEmployee.getLocalDF()).isEqualTo(UPDATED_LOCAL_DF);
        assertThat(testEmployee.getZoneDF()).isEqualTo(UPDATED_ZONE_DF);
        assertThat(testEmployee.getUuF()).isEqualTo(UPDATED_UU_F);
        assertThat(testEmployee.getPict()).isEqualTo(UPDATED_PICT);
        assertThat(testEmployee.getPictContentType()).isEqualTo(UPDATED_PICT_CONTENT_TYPE);
        assertThat(testEmployee.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testEmployee.getCv()).isEqualTo(UPDATED_CV);
        assertThat(testEmployee.getCvContentType()).isEqualTo(UPDATED_CV_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Delete the employee
        restEmployeeMockMvc
            .perform(delete(ENTITY_API_URL_ID, employee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
