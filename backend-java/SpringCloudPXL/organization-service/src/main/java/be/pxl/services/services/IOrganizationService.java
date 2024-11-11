package be.pxl.services.services;

import be.pxl.services.domain.dto.OrganizationResponse;
import be.pxl.services.domain.dto.OrganizationWithDepartmentsAndEmployeesResponse;
import be.pxl.services.domain.dto.OrganizationWithDepartmentsResponse;
import be.pxl.services.domain.dto.OrganizationWithEmployeesResponse;

public interface IOrganizationService {
    OrganizationResponse getOrganizationById(Long id);

    OrganizationWithDepartmentsResponse getOrganizationByIdWithDepartments(Long id);

    OrganizationWithDepartmentsAndEmployeesResponse getOrganizationByIdWithDepartmentsAndEmployees(Long id);

    OrganizationWithEmployeesResponse getOrganizationByIdWithEmployees(Long id);
}
