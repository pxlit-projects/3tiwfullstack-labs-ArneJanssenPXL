package be.pxl.services.controller;

import be.pxl.services.services.IOrganizationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final IOrganizationService organizationService;
    private static final Logger log = LoggerFactory.getLogger(OrganizationController.class);

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        log.info("Finding Organization {}", id);
        return new ResponseEntity(organizationService.getOrganizationById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/with-departments")
    public ResponseEntity findByIdWithDepartments(@PathVariable Long id){
        log.info("Finding Organization {} with Departments", id);
        return new ResponseEntity(organizationService.getOrganizationByIdWithDepartments(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/with-departments-and-employees")
    public ResponseEntity findByIdWithDepartmentsAndEmployees(@PathVariable Long id){
        log.info("Finding Organization {} With Departments and Employees", id);
        return new ResponseEntity(organizationService.getOrganizationByIdWithDepartmentsAndEmployees(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/with-employees")
    public ResponseEntity findByIdWithEmployees(@PathVariable Long id){
        log.info("Finding Organization {} with Employees", id);
        return new ResponseEntity(organizationService.getOrganizationByIdWithEmployees(id), HttpStatus.OK);
    }
}
