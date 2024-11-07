package be.pxl.services.services;

import be.pxl.services.client.NotificationClient;
import be.pxl.services.domain.Employee;
import be.pxl.services.domain.NotificationRequest;
import be.pxl.services.domain.dto.EmployeeRequest;
import be.pxl.services.domain.dto.EmployeeResponse;
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
        return employees.stream().map(employee -> mapToEmployeeResponse(employee)).toList();
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        log.info("Map {} to EmployeeRespone", employee.getName());
        return EmployeeResponse.builder()
                .age(employee.getAge())
                .name(employee.getName())
                .position(employee.getPosition())
                .departmentId(employee.getDepartmentId())
                .organizationId(employee.getOrganizationId())
                .build();
    }

    @Override
    public void addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.builder()
                .age(employeeRequest.getAge())
                .name(employeeRequest.getName())
                .position(employeeRequest.getPosition())
                .departmentId(employeeRequest.getDepartmentId())
                .organizationId(employeeRequest.getOrganizationId())
                .build();

        employeeRepository.save(employee);

        NotificationRequest notificationRequest =
                NotificationRequest.builder()
                        .message("Employee Created")
                        .sender("AJ")
                        .build();

        notificationClient.sendNotification(notificationRequest);
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.getById(id);
        return mapToEmployeeResponse(employee);
    }

    @Override
    public List<EmployeeResponse> getEmployeeByDepartment(Long departmentId) {
        List<Employee> employees = employeeRepository.findByDepartmentId(departmentId);
        return employees.stream().map(employee -> mapToEmployeeResponse(employee)).toList();
    }

    @Override
    public List<EmployeeResponse> getEmployeeByOrganization(Long organizationId) {
        List<Employee> employees = employeeRepository.findByOrganizationId(organizationId);
        return employees.stream().map(employee -> mapToEmployeeResponse(employee)).toList();
    }
}
