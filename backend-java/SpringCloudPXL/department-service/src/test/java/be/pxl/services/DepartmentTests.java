package be.pxl.services;

import be.pxl.services.domain.Department;
import be.pxl.services.domain.dto.DepartmentWithEmployeesResponse;
import be.pxl.services.repository.DepartmentRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class DepartmentTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DepartmentRepository departmentRepository;

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
        departmentRepository.deleteAll();
    }

    @Test
    public void testFindAllDepartments() throws Exception {
        Long organizationId = 1L;

        Department department1 = Department.builder()
                .name("ICT")
                .organizationId(organizationId)
                .position("position")
                .build();

        Department department2 = Department.builder()
                .name("Healthcare")
                .organizationId(organizationId)
                .position("position")
                .build();

        departmentRepository.save(department1);
        departmentRepository.save(department2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("ICT"));
    }

    @Test
    public void testAddDepartment() throws Exception {
        Long organizationId = 1L;

        Department department = Department.builder()
                .name("ICT")
                .organizationId(organizationId)
                .position("position")
                .build();

        String departmentString = objectMapper.writeValueAsString(department);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentString))
                .andExpect(status().isCreated());

        assertEquals(1, departmentRepository.findAll().size());
    }

    @Test
    public void testGetById() throws Exception {
        Department department = Department.builder()
                .name("ICT")
                .position("position")
                .build();

        Department savedDepartment = departmentRepository.save(department);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/" + savedDepartment.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ICT"))
                .andExpect(jsonPath("$.position").value("position"));
    }

    @Test
    public void testFindByOrganization() throws Exception {
        Long organizationId = 1L;

        Department department1 = Department.builder()
                .name("ICT")
                .organizationId(organizationId)
                .position("position")
                .build();

        Department department2 = Department.builder()
                .name("Media")
                .organizationId(organizationId)
                .position("position")
                .build();

        departmentRepository.save(department1);
        departmentRepository.save(department2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/organization/" + organizationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("ICT"))
                .andExpect(jsonPath("$[0].position").value("position"))
                .andExpect(jsonPath("$[1].name").value("Media"))
                .andExpect(jsonPath("$[1].position").value("position"));
    }

    @Test
    public void testFindByOrganizationWithEmployees() throws Exception {
        Long organizationId = 1L;

        Department department1 = Department.builder()
                .name("ICT")
                .organizationId(organizationId)
                .position("Developer")
                .build();

        Department department2 = Department.builder()
                .name("Healthcare")
                .organizationId(organizationId)
                .position("Nurse")
                .build();

        departmentRepository.save(department1);
        departmentRepository.save(department2);

        /*Employee employee1 = Employee.builder()
                .name("John Doe")
                .department(department1)
                .build();

        Employee employee2 = Employee.builder()
                .name("Jane Smith")
                .department(department2)
                .build();*/

        //employeeRepository.save(employee1);
        //employeeRepository.save(employee2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/organization/" + organizationId + "/with-employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
