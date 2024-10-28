package be.pxl.services;

import be.pxl.services.domain.Organization;
import be.pxl.services.repository.OrganizationRepository;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class OrganizationTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrganizationRepository organizationRepository;

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
    public void BeforeEach(){
        organizationRepository.deleteAll();
    }

    @Test
    public void testGetById() throws Exception {
        Organization organization = Organization.builder()
                .address("Street")
                .name("Apple")
                .build();

        organizationRepository.save(organization);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByIdWithDepartments() throws Exception {
        Organization organization = Organization.builder()
                .address("Street")
                .name("Apple")
                .build();

        organizationRepository.save(organization);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/1/with-departments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByIdWithDepartmentsAndEmployees() throws Exception {
        Organization organization = Organization.builder()
                .address("Street")
                .name("Apple")
                .build();

        organizationRepository.save(organization);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/1/with-departments-and-employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByIdWithEmployees() throws Exception {
        Organization organization = Organization.builder()
                .address("Street")
                .name("Apple")
                .build();

        organizationRepository.save(organization);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/1/with-employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
