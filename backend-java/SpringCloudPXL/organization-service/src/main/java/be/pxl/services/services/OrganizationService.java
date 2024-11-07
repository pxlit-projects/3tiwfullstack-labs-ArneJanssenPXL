package be.pxl.services.services;

import be.pxl.services.domain.Organization;
import be.pxl.services.domain.dto.OrganizationResponse;
import be.pxl.services.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService implements IOrganizationService{
    private final OrganizationRepository organizationRepository;
    private static final Logger log = LoggerFactory.getLogger(OrganizationService.class);
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
        log.info("Map {} To OrganizationResponse", organization);
        return OrganizationResponse.builder()
                .name(organization.getName())
                .address(organization.getAddress())
                .employees(organization.getEmployees())
                .departments(organization.getDepartments())
                .build();
    }
}
