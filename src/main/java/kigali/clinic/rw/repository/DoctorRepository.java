package kigali.clinic.rw.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.domain.Office;

public interface DoctorRepository extends JpaRepository<Doctor,UUID> {
	Optional<Doctor> findByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, java.time.LocalDate dateOfBirth);
	boolean existsByOffice(Office office);
	List<Doctor> findBySpecializationsName(String specializationName);

 @Query("""
 SELECT d FROM Doctor d 
 JOIN d.specializations s 
 WHERE LOWER(s.name) = LOWER(:name)
 """)
    List<Doctor> findBySpecializationName(@Param("name") String name);

@Query("""
 SELECT d FROM Doctor d 
WHERE d.office IS NULL 
ORDER BY d.lastName
""")
List<Doctor> findDoctorsWithoutOffice();

}
