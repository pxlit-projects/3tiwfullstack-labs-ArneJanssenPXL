package be.pxl.services.controller;

import be.pxl.services.client.NotificationClient;
import be.pxl.services.domain.dto.EmployeeRequest;
import be.pxl.services.services.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final IEmployeeService employeeService;
    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    NotificationClient notificationClient;

    @GetMapping
    public ResponseEntity findAll(){
        log.info("Finding all Employees");
        return new ResponseEntity(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody EmployeeRequest employeeRequest){
        log.info("Adding {}", employeeRequest.getName());
        employeeService.addEmployee(employeeRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        log.info("Finding employee {}", id);
        return new ResponseEntity(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity findByDepartment(@PathVariable Long departmentId){
        log.info("Finding Employee By Department Id: {}", departmentId);
        return new ResponseEntity(employeeService.getEmployeeByDepartment(departmentId), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity findByOrganization(@PathVariable Long organizationId){
        log.info("Finding Employees By Organization id: {}", organizationId);
        return new ResponseEntity(employeeService.getEmployeeByOrganization(organizationId), HttpStatus.OK);
    }
}
