package be.pxl.services.controller;

import be.pxl.services.client.NotificationClient;
import be.pxl.services.domain.dto.EmployeeRequest;
import be.pxl.services.services.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final IEmployeeService employeeService;

    @Autowired
    NotificationClient notificationClient;

    @GetMapping
    public ResponseEntity findAll(){
        return new ResponseEntity(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody EmployeeRequest employeeRequest){
        employeeService.addEmployee(employeeRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        return new ResponseEntity(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity findByDepartment(@PathVariable Long departmentId){
        return new ResponseEntity(employeeService.getEmployeeByDepartment(departmentId), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity findByOrganization(@PathVariable Long organizationId){
        return new ResponseEntity(employeeService.getEmployeeByOrganization(organizationId), HttpStatus.OK);
    }
}
