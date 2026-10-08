package kigali.clinic.rw.repository;


import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentStatus;
import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.domain.Patient;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
	boolean existsByPatientAndDoctorAndAppointmentDate(Patient patient, Doctor doctor,
													   LocalDate appointmentDate);

	boolean existsByPatientAndDoctorAndAppointmentDateAndIdNot(Patient patient, Doctor doctor,
															   LocalDate appointmentDate, UUID id);

    List<Appointment> findByDoctorIdAndStatusOrderByAppointmentDateAsc(UUID doctorId, AppointmentStatus status);

    @Query("""
	    SELECT a FROM Appointment a
	    WHERE a.status = :status AND a.appointmentDate = :day
	    """)
    List<Appointment> findByStatusOnDay(@Param("status") AppointmentStatus status,
					@Param("day") LocalDate day);

    @Query("""
	    SELECT a FROM Appointment a
	    WHERE LOWER(a.reason) LIKE LOWER(CONCAT('%', :keyword, '%'))
	    ORDER BY a.appointmentDate
	    """)
    List<Appointment> searchByReason(@Param("keyword") String keyword);

    @Query("""
        SELECT a.patient.firstName, a.doctor.firstName, a.appointmentDate
        FROM Appointment a
        WHERE a.status = :status AND a.appointmentDate < :today
        """)
    List<Object[]> findPendingPastAppointments(@Param("status") AppointmentStatus status,
            @Param("today") LocalDate today);

    @Query("""
        SELECT d, COUNT(a)
        FROM Appointment a JOIN a.doctor d
        GROUP BY d
        ORDER BY COUNT(a) DESC
        """)
    List<Object[]> countAppointmentsPerDoctor();

    @Query("""
        SELECT a
        FROM Appointment a
        JOIN a.doctor d
        JOIN d.specializations s
        WHERE s.name = :specializationName
        """)
    List<Appointment> findAppointmentsBySpecializationName(@Param("specializationName") String specializationName);

    Page<Appointment> findByDoctorId(UUID doctorId, Pageable pageable);

   List<Appointment>findByStatusOrderByAppointmentDateAsc(
        AppointmentStatus status);

        List<Appointment> findByAppointmentDateBetweenOrderByAppointmentDateAsc(
        LocalDate start, LocalDate end);

    boolean existsByDoctor_IdAndAppointmentDateAndStatusNot(
        UUID doctorId, LocalDate appointmentDate, AppointmentStatus status);



@Query("""
        SELECT a.status, COUNT(a)
        FROM Appointment a
        GROUP BY a.status
        """)
    List<Object[]> countAppointmentsByStatus();
}