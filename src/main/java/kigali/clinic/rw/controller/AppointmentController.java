package kigali.clinic.rw.controller;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import kigali.clinic.rw.domain.Appointment;
import kigali.clinic.rw.domain.AppointmentStatus;   
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import kigali.clinic.rw.dto.AppointmentDto;
import kigali.clinic.rw.service.AppointmentService;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/save")
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody AppointmentDto request) {
        return new ResponseEntity<>(appointmentService.createAppointment(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDto> updateAppointment(@PathVariable UUID id,
                                                            @RequestBody AppointmentDto request) {
        return ResponseEntity.ok(appointmentService.updateAppointment(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable UUID id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-status")
    public List<Appointment> findByStatus(@RequestParam AppointmentStatus status) {
        return appointmentService.findByStatus(status);
    }

    @GetMapping("/between")
    public List<Appointment> findBetween(@RequestParam String start,
                                          @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return appointmentService.findBetween(startDate, endDate);
    }

    @GetMapping("/status/by-status")
    public ResponseEntity<List<Object[]>> getAppointmentCountByStatus() {
    return new ResponseEntity<>(appointmentService.countAppointmentsByStatus(), HttpStatus.OK);
    }
}