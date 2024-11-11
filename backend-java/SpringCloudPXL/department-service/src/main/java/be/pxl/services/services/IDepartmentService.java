package be.pxl.services.services;

import be.pxl.services.domain.dto.DepartmentRequest;
import be.pxl.services.domain.dto.DepartmentResponse;
import be.pxl.services.domain.dto.DepartmentWithEmployeesResponse;

import java.util.List;

public interface IDepartmentService {
    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse addDepartment(DepartmentRequest departmentRequest);

    DepartmentResponse getDepartmentById(Long id);

    List<DepartmentResponse> getDepartmentsByOrganizationId(Long organizationId);

    List<DepartmentWithEmployeesResponse> getDepartmentsByOrganizationIdWithEmployees(Long organizationId);
}
