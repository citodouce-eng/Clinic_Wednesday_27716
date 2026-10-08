package kigali.clinic.rw.service;

import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import kigali.clinic.rw.domain.Doctor;
import kigali.clinic.rw.repository.DoctorRepository;

@Service 
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor saveDoctor(Doctor doctor) {
        Optional<Doctor> existingDoctor = doctorRepository.findByFirstNameAndLastNameAndDateOfBirth(
                doctor.getFirstName(), doctor.getLastName(), doctor.getDateOfBirth());
        if (existingDoctor.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Doctor with this name and date of birth already exists");

        }
        if (doctor.getOffice() != null && doctorRepository.existsByOffice(doctor.getOffice())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                "This office is already assigned to another doctor");

        }
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(UUID id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isPresent()) {
            return doctor.get();
        } else {
            throw new RuntimeException("Doctor not found with id: " + id);
        }
    }

    public Doctor updateDoctor(UUID id, Doctor updatedDoctor) {
        Doctor existing = getDoctorById(id);
        existing.setFirstName(updatedDoctor.getFirstName());
        existing.setLastName(updatedDoctor.getLastName());
        existing.setDateOfBirth(updatedDoctor.getDateOfBirth());
        existing.setOffice(updatedDoctor.getOffice());
        existing.setSpecializations(updatedDoctor.getSpecializations());
        return doctorRepository.save(existing);
    }

    public void deleteDoctor(UUID id) {
        Doctor existing = getDoctorById(id);
        doctorRepository.delete(existing);
    }
    public List<Doctor> findBySpecialization(String name) {
    return doctorRepository.findBySpecializationName(name);

}

public List<Doctor> getDoctorsWithoutOffice() {
    return doctorRepository.findDoctorsWithoutOffice();
}
public boolean doctorExists(UUID id) {
    return doctorRepository.existsById(id);
}
}

