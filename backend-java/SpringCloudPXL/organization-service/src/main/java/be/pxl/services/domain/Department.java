package be.pxl.services.domain;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    private Long id;
    private Long organizationId;
    private String name;
    @Transient
    private List<Employee> employees;
    private String position;
}
