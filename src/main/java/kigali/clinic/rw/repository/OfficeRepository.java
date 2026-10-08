package kigali.clinic.rw.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kigali.clinic.rw.domain.Office;

public interface OfficeRepository extends JpaRepository<Office, UUID> {

    List<Office> findByName(String name);

    Optional<Office> findByNameAndOfficeNumber(String name, Integer officeNumber);

    Optional<Office> findByOfficeNumber(int officeNumber);

   
    @Query("""
        SELECT o.name, o.officeNumber, COUNT(a)
        FROM Appointment a
        JOIN a.doctor d
        JOIN d.office o
        GROUP BY o
        ORDER BY COUNT(a) DESC
        """)
    List<Object[]> findBusiestOffice();
}