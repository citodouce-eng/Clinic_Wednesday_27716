package kigali.clinic.rw.controller;

import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.service.DoctorService;

@RestController 
@RequestMapping ("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping ("/save")
    public ResponseEntity<Doctor> saveDoctor(@RequestBody Doctor doctor) {
        Doctor saved = doctorService.saveDoctor(doctor);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping 
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return new ResponseEntity<>(doctorService.getAllDoctors(), HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Doctor> getDoctorByID(@PathVariable UUID id) {
        return new ResponseEntity<>(doctorService.getDoctorById(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable UUID id, @RequestBody Doctor doctor) {
        Doctor updated = doctorService.updateDoctor(id, doctor);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable UUID id) {
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>("Doctor deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/by-specialization")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialization(@RequestParam String name) {
        return new ResponseEntity<>(doctorService.findBySpecialization(name), HttpStatus.OK);
    }

   @GetMapping("/without-office")
public ResponseEntity<List<Doctor>> getDoctorsWithoutOffice() {
    return new ResponseEntity<>(doctorService.getDoctorsWithoutOffice(), HttpStatus.OK);
}

}