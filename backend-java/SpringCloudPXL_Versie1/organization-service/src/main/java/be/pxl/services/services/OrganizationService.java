package be.pxl.services.services;

import be.pxl.services.domain.Organization;
import be.pxl.services.domain.dto.OrganizationResponse;
import be.pxl.services.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService implements IOrganizationService{
    private final OrganizationRepository organizationRepository;
    @Override
    public OrganizationResponse getOrganizationById(Long id) {
        Organization organization = null; //organizationRepository.getById(id);
        return mapToOrganizationResponse(organization);
    }

    @Override
    public OrganizationResponse getOrganizationByIdWithDepartments(Long id) {
        Organization organization = null; //organizationRepository.findByIdWithDepartments(id);
        return mapToOrganizationResponse(organization);
    }

    @Override
    public OrganizationResponse getOrganizationByIdWithDepartmentsAndEmployees(Long id) {
        Organization organization = null; //organizationRepository.findByIdWithDepartmentsAndEmployees(id);
        return mapToOrganizationResponse(organization);
    }

    @Override
    public OrganizationResponse getOrganizationByIdWithEmployees(Long id) {
        Organization organization = null; //organizationRepository.findByIdWithEmployees(id);
        return mapToOrganizationResponse(organization);
    }

    private OrganizationResponse mapToOrganizationResponse(Organization organization){
        return OrganizationResponse.builder()
                .name(organization.getName())
                .address(organization.getAddress())
                .employees(organization.getEmployees())
                .departments(organization.getDepartments())
                .build();
    }
}
