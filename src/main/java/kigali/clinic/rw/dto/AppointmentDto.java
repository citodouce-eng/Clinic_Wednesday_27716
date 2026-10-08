package kigali.clinic.rw.dto;

import java.time.LocalDate;
import java.util.UUID;

import kigali.clinic.rw.domain.AppointmentStatus;

public class AppointmentDto {
    private UUID id;
    private LocalDate appointmentDate;
    private String reason;
    private AppointmentStatus status;
    private UUID patientId;
    private UUID doctorId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {   // ← LocalDate ici
        this.appointmentDate = appointmentDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }
}