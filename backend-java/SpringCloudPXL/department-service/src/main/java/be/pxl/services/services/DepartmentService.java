package be.pxl.services.services;

import be.pxl.services.Exception.DepartmentNotFoundException;
import be.pxl.services.client.EmployeeClient;
import be.pxl.services.domain.Department;
import be.pxl.services.domain.Employee;
import be.pxl.services.domain.dto.DepartmentRequest;
import be.pxl.services.domain.dto.DepartmentResponse;
import be.pxl.services.domain.dto.DepartmentWithEmployeesResponse;
import be.pxl.services.domain.dto.EmployeeResponse;
import be.pxl.services.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService{
    private final DepartmentRepository departmentRepository;
    private final EmployeeClient employeeClient;
    private static final Logger log = LoggerFactory.getLogger(DepartmentService.class);
    @Override
    public List<DepartmentResponse> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(this::mapToDepartmentResponse).toList();
    }

    @Override
    public DepartmentResponse addDepartment(DepartmentRequest departmentRequest) {
        Department department = Department.builder()
                .organizationId(departmentRequest.getOrganizationId())
                .name(departmentRequest.getName())
                .employees(departmentRequest.getEmployees())
                .position(departmentRequest.getPosition())
                .build();

        department = departmentRepository.save(department);

        log.info("Department id {} created", department.getId());

        return mapToDepartmentResponse(department);
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {
        return departmentRepository.findById(id).map(this::mapToDepartmentResponse).orElseThrow(() -> new DepartmentNotFoundException("Department with " + id + " not found"));
    }

    @Override
    public List<DepartmentResponse> getDepartmentsByOrganizationId(Long organizationId) {
        List<Department> departments = departmentRepository.findByOrganizationId(organizationId);
        return departments.stream().map(this::mapToDepartmentResponse).toList();
    }

    @Override
    public List<DepartmentWithEmployeesResponse> getDepartmentsByOrganizationIdWithEmployees(Long organizationId) {
        List<DepartmentWithEmployeesResponse> departments = departmentRepository.findByOrganizationId(organizationId).stream().map(this::mapToDepartmentWithEmployeesResponse).toList();
        departments.forEach(d -> d.setEmployees(employeeClient.findByDepartment(d.getId())));

        return departments;
    }

    private DepartmentResponse mapToDepartmentResponse(Department department){
        log.info("Map {} to DepartmentResponse", department.getName());
        return DepartmentResponse.builder()
                .organizationId(department.getOrganizationId())
                .name(department.getName())
                .employees(department.getEmployees())
                .position(department.getPosition())
                .build();
    }

    private DepartmentWithEmployeesResponse mapToDepartmentWithEmployeesResponse(Department department){
        return DepartmentWithEmployeesResponse.builder()
                .id(department.getId())
                .organizationId(department.getOrganizationId())
                .name(department.getName())
                .position(department.getPosition())
                .employees(Optional.ofNullable(department.getEmployees())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(this::mapToEmployeeResponse)
                        .toList())
                .build();
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        log.info("Map {} to EmployeeRespone", employee.getName());
        return EmployeeResponse.builder()
                .id(employee.getId())
                .age(employee.getAge())
                .name(employee.getName())
                .position(employee.getPosition())
                .departmentId(employee.getDepartmentId())
                .organizationId(employee.getOrganizationId())
                .build();
    }
}
