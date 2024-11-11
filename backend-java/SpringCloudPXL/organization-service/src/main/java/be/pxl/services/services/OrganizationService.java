package be.pxl.services.services;

import be.pxl.services.client.DepartmentClient;
import be.pxl.services.client.EmployeeClient;
import be.pxl.services.domain.Department;
import be.pxl.services.domain.Employee;
import be.pxl.services.domain.Organization;
import be.pxl.services.domain.dto.*;
import be.pxl.services.exception.OrganizationNotFoundException;
import be.pxl.services.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationService implements IOrganizationService{
    private final OrganizationRepository organizationRepository;
    private final EmployeeClient employeeClient;
    private final DepartmentClient departmentClient;
    private static final Logger log = LoggerFactory.getLogger(OrganizationService.class);
    @Override
    public OrganizationResponse getOrganizationById(Long id) {
        return organizationRepository.findById(id).map(this::mapToOrganizationResponse).orElseThrow(() -> new OrganizationNotFoundException("Organization with " + id + " not found"));
    }

    @Override
    public OrganizationWithEmployeesResponse getOrganizationByIdWithEmployees(Long id) {
        OrganizationWithEmployeesResponse organization = organizationRepository.findById(id).map(this::mapToOrganizationWithEmployeesResponse).orElseThrow(() -> new OrganizationNotFoundException("Organization with " + id + " not found"));
        organization.setEmployees(employeeClient.findByOrganization(organization.getId()));

        return organization;
    }

    @Override
    public OrganizationWithDepartmentsResponse getOrganizationByIdWithDepartments(Long id) {
        OrganizationWithDepartmentsResponse organization = organizationRepository.findById(id).map(this::mapToOrganizationWithDepartmentResponse).orElseThrow(() -> new OrganizationNotFoundException("Organization with " + id + " not found"));
        organization.setDepartments(departmentClient.findByOrganization(organization.getId()));

        return organization;
    }

    @Override
    public OrganizationWithDepartmentsAndEmployeesResponse getOrganizationByIdWithDepartmentsAndEmployees(Long id) {
        OrganizationWithDepartmentsAndEmployeesResponse organization = organizationRepository.findById(id).map(this::mapToOrganizationWithEmployeesAndDepartmentsResponse).orElseThrow(() -> new OrganizationNotFoundException("Organization with " + id + " not found"));
        organization.setEmployees(employeeClient.findByOrganization(organization.getId()));
        organization.setDepartments(departmentClient.findByOrganization(organization.getId()));

        return organization;
    }

    private OrganizationResponse mapToOrganizationResponse(Organization organization){
        log.info("Map {} To OrganizationResponse", organization);
        return OrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .address(organization.getAddress())
                .build();
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee){
        log.info("Map {} To EmployeeResponse", employee);
        return EmployeeResponse.builder()
                .id(employee.getOrganizationId())
                .organizationId(employee.getOrganizationId())
                .departmentId(employee.getDepartmentId())
                .name(employee.getName())
                .age(employee.getAge())
                .position(employee.getPosition())
                .build();
    }

    private DepartmentResponse mapToDepartmentResponse(Department department){
        log.info("Map {} To DepartmentResponse", department);
        return DepartmentResponse.builder()
                .id(department.getId())
                .organizationId(department.getOrganizationId())
                .name(department.getName())
                .position(department.getPosition())
                .build();
    }

    private OrganizationWithEmployeesResponse mapToOrganizationWithEmployeesResponse(Organization organization){
        return OrganizationWithEmployeesResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .address(organization.getAddress())
                .employees(Optional.ofNullable(organization.getEmployees())
                        .orElse(Collections.emptyList()).stream().map(this::mapToEmployeeResponse).toList())
                .build();
    }

    private OrganizationWithDepartmentsResponse mapToOrganizationWithDepartmentResponse(Organization organization){
        return OrganizationWithDepartmentsResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .address(organization.getAddress())
                .departments(Optional.ofNullable(organization.getDepartments())
                        .orElse(Collections.emptyList()).stream().map(this::mapToDepartmentResponse).toList())
                .build();
    }

    private OrganizationWithDepartmentsAndEmployeesResponse mapToOrganizationWithEmployeesAndDepartmentsResponse(Organization organization){
        return OrganizationWithDepartmentsAndEmployeesResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .address(organization.getAddress())
                .employees(Optional.ofNullable(organization.getEmployees())
                        .orElse(Collections.emptyList()).stream().map(this::mapToEmployeeResponse).toList())
                .departments(Optional.ofNullable(organization.getDepartments())
                        .orElse(Collections.emptyList()).stream().map(this::mapToDepartmentResponse).toList())
                .build();
    }
}
