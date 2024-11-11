package be.pxl.services.services;

import be.pxl.services.client.NotificationClient;
import be.pxl.services.domain.Employee;
import be.pxl.services.domain.NotificationRequest;
import be.pxl.services.domain.dto.EmployeeRequest;
import be.pxl.services.domain.dto.EmployeeResponse;
import be.pxl.services.exception.EmployeeNotFoundException;
import be.pxl.services.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService{
    private final EmployeeRepository employeeRepository;
    private final NotificationClient notificationClient;
    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    @Override
    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(this::mapToEmployeeResponse).toList();
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        log.info("Map {} to EmployeeRespone", employee.getName());
        return EmployeeResponse.builder()
                .id(employee.getId())
                .age(employee.getAge())
                .name(employee.getName())
                .position(employee.getPosition())
                .departmentId(employee.getDepartmentId())
                .organizationId(employee.getOrganizationId())
                .build();
    }

    @Override
    public EmployeeResponse addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.builder()
                .age(employeeRequest.getAge())
                .name(employeeRequest.getName())
                .position(employeeRequest.getPosition())
                .departmentId(employeeRequest.getDepartmentId())
                .organizationId(employeeRequest.getOrganizationId())
                .build();

        employee = employeeRepository.save(employee);

        log.info("Employee with id {} created", employee.getId());

        NotificationRequest notificationRequest =
                NotificationRequest.builder()
                        .message("Employee Created")
                        .sender("AJ")
                        .build();

        notificationClient.sendNotification(notificationRequest);

        return mapToEmployeeResponse(employee);
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        /*Employee employee = employeeRepository.getById(id);
        return mapToEmployeeResponse(employee);*/
        return employeeRepository.findById(id).map(this::mapToEmployeeResponse).orElseThrow(() -> new EmployeeNotFoundException("Employee with " + id + " not found"));
    }

    @Override
    public List<EmployeeResponse> getEmployeeByDepartment(Long departmentId) {
        List<Employee> employees = employeeRepository.findByDepartmentId(departmentId);
        return employees.stream().map(this::mapToEmployeeResponse).toList();
    }

    @Override
    public List<EmployeeResponse> getEmployeeByOrganization(Long organizationId) {
        List<Employee> employees = employeeRepository.findByOrganizationId(organizationId);
        return employees.stream().map(this::mapToEmployeeResponse).toList();
    }
}
