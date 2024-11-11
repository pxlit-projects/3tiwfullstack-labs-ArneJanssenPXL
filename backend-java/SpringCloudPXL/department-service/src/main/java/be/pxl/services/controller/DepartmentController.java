package be.pxl.services.controller;

import be.pxl.services.domain.dto.DepartmentRequest;
import be.pxl.services.services.IDepartmentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final IDepartmentService departmentService;
    private static final Logger log = LoggerFactory.getLogger(DepartmentController.class);

    @GetMapping
    public ResponseEntity findAll(){
        log.info("Finding all Departments");
        return new ResponseEntity(departmentService.getAllDepartments(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity add(@RequestBody DepartmentRequest departmentRequest){
        log.info("Adding Department {}", departmentRequest.getName());
        departmentService.addDepartment(departmentRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        log.info("Finding Department id: {}", id);
        return new ResponseEntity(departmentService.getDepartmentById(id), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity findByOrganization(@PathVariable Long organizationId){
        log.info("Finding Departments By Organization Id: {}", organizationId);
        return new ResponseEntity(departmentService.getDepartmentsByOrganizationId(organizationId), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}/with-employees")
    public ResponseEntity findByOrganizationWithEmployees(@PathVariable Long organizationId){
        log.info("Finding Department By Organization Id: {} With Employees", organizationId);
        return new ResponseEntity(departmentService.getDepartmentsByOrganizationIdWithEmployees(organizationId), HttpStatus.OK);
    }
}
