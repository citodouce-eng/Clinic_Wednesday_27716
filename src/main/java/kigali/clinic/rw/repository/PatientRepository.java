package kigali.clinic.rw.repository;

import kigali.clinic.rw.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;




public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Optional<Patient> findByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, java.sql.Date dateOfBirth);

    List<Patient> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

  @Query("""
        SELECT DISTINCT p FROM Patient p 
       JOIN Appointment a ON a.patient = p 
       WHERE a.doctor.id = :doctorId """)
List<Patient> findPatientsByDoctorId(@Param("doctorId") UUID doctorId);

@Query(""" 
SELECT a.patient
FROM Appointment a
GROUP BY a.patient
HAVING COUNT(a) >= :min
ORDER BY COUNT(a) DESC
""")
List<Patient> findFrequentPatients(@Param("min") long min);


}