package be.pxl.services.services;

import be.pxl.services.domain.Department;
import be.pxl.services.domain.dto.DepartmentRequest;
import be.pxl.services.domain.dto.DepartmentResponse;
import be.pxl.services.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService{
    private final DepartmentRepository departmentRepository;
    private static final Logger log = LoggerFactory.getLogger(DepartmentService.class);
    @Override
    public List<DepartmentResponse> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(department -> mapToDepartmentResponse(department)).toList();
    }

    @Override
    public void addDepartment(DepartmentRequest departmentRequest) {
        Department department = Department.builder()
                .organizationId(departmentRequest.getOrganizationId())
                .name(departmentRequest.getName())
                .employees(departmentRequest.getEmployees())
                .position(departmentRequest.getPosition())
                .build();

        departmentRepository.save(department);
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {
        Department department = departmentRepository.getById(id);
        return mapToDepartmentResponse(department);
    }

    @Override
    public List<DepartmentResponse> getDepartmentByOrganization(Long organizationId) {
        List<Department> departments = departmentRepository.findByOrganizationId(organizationId);
        return departments.stream().map(department -> mapToDepartmentResponse(department)).toList();
    }

    @Override
    public List<DepartmentResponse> getDepartmentByOrganizationWithEmployees(Long organizationId) {
        List<Department> departments = Collections.emptyList(); //departmentRepository.findByOrganizationIdWithEmployees(organizationId);
        return departments.stream().map(department -> mapToDepartmentResponse(department)).toList();
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
}
