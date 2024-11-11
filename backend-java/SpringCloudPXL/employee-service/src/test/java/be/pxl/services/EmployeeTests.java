package be.pxl.services;

import be.pxl.services.domain.Employee;
import be.pxl.services.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class EmployeeTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Container
    private static MySQLContainer sqlContainer =
            new MySQLContainer("mysql:8.0.39");

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @BeforeEach
    public void beforeEach(){
        employeeRepository.deleteAll();
    }

    @Test
    public void testCreateEmployee() throws Exception {
        Employee employee = Employee.builder()
                .age(24)
                .name("Jan")
                .position("student")
                .organizationId(1L)
                .departmentId(1L)
                .build();

        String employeeString = objectMapper.writeValueAsString(employee);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeString))
                .andExpect(status().isCreated());

        assertEquals(1, employeeRepository.findAll().size());
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        Employee employee = Employee.builder()
                .age(24)
                .name("Jan")
                .position("student")
                .build();

        Employee savedEmployee = employeeRepository.save(employee);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/" + savedEmployee.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jan"))
                .andExpect(jsonPath("$.age").value(24))
                .andExpect(jsonPath("$.position").value("student"));
    }

    @Test
    public void testFindByDepartment() throws Exception {
        Long departmentId = 1L;

        Employee employee1 = Employee.builder()
                .age(24)
                .name("Jan")
                .position("student")
                .departmentId(departmentId)
                .build();

        Employee employee2 = Employee.builder()
                .age(37)
                .name("Roos")
                .position("engineer")
                .departmentId(departmentId)
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/department/" + departmentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Jan"))
                .andExpect(jsonPath("$[0].age").value("24"))
                .andExpect(jsonPath("$[0].position").value("student"))
                .andExpect(jsonPath("$[1].name").value("Roos"))
                .andExpect(jsonPath("$[1].age").value("37"))
                .andExpect(jsonPath("$[1].position").value("engineer"));
    }

    @Test
    public void testFindByOrganization() throws Exception {
        Long organizationId = 1L;

        Employee employee1 = Employee.builder()
                .age(24)
                .name("Jan")
                .position("student")
                .organizationId(organizationId)
                .build();

        Employee employee2 = Employee.builder()
                .age(37)
                .name("Roos")
                .position("engineer")
                .organizationId(organizationId)
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/organization/" + organizationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Jan"))
                .andExpect(jsonPath("$[0].age").value("24"))
                .andExpect(jsonPath("$[0].position").value("student"))
                .andExpect(jsonPath("$[1].name").value("Roos"))
                .andExpect(jsonPath("$[1].age").value("37"))
                .andExpect(jsonPath("$[1].position").value("engineer"));
    }
}
