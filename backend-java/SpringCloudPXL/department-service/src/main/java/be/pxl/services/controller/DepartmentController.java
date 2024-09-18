package be.pxl.services.controller;

import be.pxl.services.domain.dto.DepartmentRequest;
import be.pxl.services.services.IDepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final IDepartmentService departmentService;

    @GetMapping
    public ResponseEntity findAll(){
        return new ResponseEntity(departmentService.getAllDepartments(), HttpStatus.OK);
    }

    @PostMapping
    public void add(@RequestBody DepartmentRequest departmentRequest){
        departmentService.addDepartment(departmentRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        return new ResponseEntity(departmentService.getDepartmentById(id), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity findByOrganization(@PathVariable Long organizationId){
        return new ResponseEntity(departmentService.getDepartmentByOrganization(organizationId), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}/with-employees")
    public ResponseEntity findByOrganizationWithEmployees(@PathVariable Long organizationId){
        return new ResponseEntity(departmentService.getDepartmentByOrganizationWithEmployees(organizationId), HttpStatus.OK);
    }
}
