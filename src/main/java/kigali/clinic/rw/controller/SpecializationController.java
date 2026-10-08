package kigali.clinic.rw.controller;

import java.util.List;
import java.util.UUID;

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

import kigali.clinic.rw.domain.Specialization;
import kigali.clinic.rw.service.SpecializationService;

@RestController
@RequestMapping("/api/specializations")
public class SpecializationController {

    private final SpecializationService specializationService;

    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @PostMapping("/save")
    public ResponseEntity<Specialization> saveSpecialization(@RequestBody Specialization specialization) {
        return new ResponseEntity<>(specializationService.saveSpecialization(specialization), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Specialization>> getAllSpecializations() {
        return new ResponseEntity<>(specializationService.getAllSpecializations(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Specialization> getSpecializationById(@PathVariable UUID id) {
        return new ResponseEntity<>(specializationService.getSpecializationById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Specialization> updateSpecialization(@PathVariable UUID id,
                                                             @RequestBody Specialization specialization) {
        return new ResponseEntity<>(specializationService.updateSpecialization(id, specialization), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSpecialization(@PathVariable UUID id) {
        specializationService.deleteSpecialization(id);
        return new ResponseEntity<>("Specialization deleted successfully", HttpStatus.OK);
    }
    @GetMapping("/unused")
public ResponseEntity<List<Specialization>> getUnusedSpecializations() {
    return new ResponseEntity<>(specializationService.getUnusedSpecializations(), HttpStatus.OK);
}
}
