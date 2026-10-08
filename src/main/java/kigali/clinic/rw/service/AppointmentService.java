package kigali.clinic.rw.service;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentStatus;
import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.domain.Patient;
import kigali.clinic.rw.dto.AppointmentDto;
import kigali.clinic.rw.repository.AppointmentRepository;
import kigali.clinic.rw.repository.DoctorRepository;
import kigali.clinic.rw.repository.PatientRepository;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              DoctorRepository doctorRepository,
                              PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public AppointmentDto createAppointment(AppointmentDto request) {
        Appointment appointment = new Appointment();
        applyRequest(appointment, request);
        return toDto(appointmentRepository.save(appointment));
    }

    public List<AppointmentDto> getAllAppointments() {
        return appointmentRepository.findAll().stream().map(this::toDto).toList();
    }

    public AppointmentDto getAppointmentById(UUID id) {
        return toDto(findAppointment(id));
    }

    public AppointmentDto updateAppointment(UUID id, AppointmentDto request) {
        Appointment appointment = findAppointment(id);
        applyRequest(appointment, request);
        return toDto(appointmentRepository.save(appointment));
    }

    public void deleteAppointment(UUID id) {
        appointmentRepository.delete(findAppointment(id));
    }

    private Appointment findAppointment(UUID id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Appointment not found: " + id));
    }

    private void applyRequest(Appointment appointment, AppointmentDto request) {
        if (request.getDoctorId() == null || request.getPatientId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Both doctorId and patientId are required");
        }

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Doctor not found: " + request.getDoctorId()));
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Patient not found: " + request.getPatientId()));

        boolean duplicateExists = appointment.getId() == null
            ? appointmentRepository.existsByPatientAndDoctorAndAppointmentDate(
                patient, doctor, request.getAppointmentDate())
            : appointmentRepository.existsByPatientAndDoctorAndAppointmentDateAndIdNot(
                patient, doctor, request.getAppointmentDate(), appointment.getId());
        if (duplicateExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                "An appointment already exists for this patient, doctor, and date");
        }

        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setReason(request.getReason());
        appointment.setStatus(request.getStatus());
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
    }

    private AppointmentDto toDto(Appointment appointment) {
        AppointmentDto response = new AppointmentDto();
        response.setId(appointment.getId());
        response.setAppointmentDate(appointment.getAppointmentDate());
        response.setReason(appointment.getReason());
        response.setStatus(appointment.getStatus());
        response.setDoctorId(appointment.getDoctor().getId());
        response.setPatientId(appointment.getPatient().getId());
        return response;
    }

    public List<Appointment> findByStatus(AppointmentStatus status) {
    return appointmentRepository.findByStatusOrderByAppointmentDateAsc(status);
}

  public List<Appointment> findBetween(LocalDate start, LocalDate end) {
    return appointmentRepository
            .findByAppointmentDateBetweenOrderByAppointmentDateAsc(start, end);
}

public String saveAppointment(Appointment appointment) {

    boolean booked = appointmentRepository.existsByDoctor_IdAndAppointmentDateAndStatusNot(
                    appointment.getDoctor().getId(), appointment.getAppointmentDate(),AppointmentStatus.CANCELLED);

    if (booked) {
        return "Doctor is already booked on that date";
    }

    appointmentRepository.save(appointment);

    return "Appointment saved successfully";
}

public List<Object[]> countAppointmentsByStatus(){
    return appointmentRepository.countAppointmentsByStatus();
    }

}