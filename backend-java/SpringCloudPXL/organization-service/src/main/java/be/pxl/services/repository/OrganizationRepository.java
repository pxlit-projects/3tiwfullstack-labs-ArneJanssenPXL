package be.pxl.services.repository;

import be.pxl.services.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    /*@Query("SELECT o FROM Organization o LEFT JOIN FETCH o.departments WHERE o.id = :organizationId")
    Organization findByIdWithDepartments(@Param("organizationId") Long organizationId);

    @Query("SELECT o FROM Organization o LEFT JOIN FETCH o.departments d LEFT JOIN FETCH d.employees WHERE o.id = :organizationId")
    Organization findByIdWithDepartmentsAndEmployees(@Param("organizationId") Long organizationId);

    @Query("SELECT o FROM Organization o LEFT JOIN FETCH o.employees WHERE o.id = :organizationId")
    Organization findByIdWithEmployees(@Param("organizationId") Long organizationId);*/
}
