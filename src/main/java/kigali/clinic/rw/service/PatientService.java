package kigali.clinic.rw.service;

import kigali.clinic.rw.domain.Patient;
import kigali.clinic.rw.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepo;

    public PatientService(PatientRepository patientRepo) {
        this.patientRepo = patientRepo;
    }

    
    public Patient savePatient(Patient patient) {
        Optional<Patient> existingPatient = patientRepo.findByFirstNameAndLastNameAndDateOfBirth(
                patient.getFirstName(), patient.getLastName(), patient.getDateOfBirth());
        if (existingPatient.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Patient with this name and date of birth already exists");
        }
        return patientRepo.save(patient);
    }

   
    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    
    public Patient getPatientById(UUID id) {
        return patientRepo.findById(id).orElse(null);
    }

    
    public Patient updatePatient(UUID id, Patient patientDetails) {
        Optional<Patient> existingPatient = patientRepo.findById(id);
        
        if (existingPatient.isPresent()) {
            Patient patientToUpdate = existingPatient.get();
            patientToUpdate.setFirstName(patientDetails.getFirstName());
            patientToUpdate.setLastName(patientDetails.getLastName());
            patientToUpdate.setDateOfBirth(patientDetails.getDateOfBirth());
           
            
            return patientRepo.save(patientToUpdate);
        }
        return null; 
    }

    
    public void deletePatient(UUID id) {
        patientRepo.deleteById(id);
    }

    public List<Patient> findByLastName(String lastName) {
    return patientRepo.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
}
   
public List<Patient> getPatientsOfDoctor(UUID doctorId) {
    return patientRepo.findPatientsByDoctorId(doctorId);
}

public List<Patient> getFrequentPatients(long min) {
    return patientRepo.findFrequentPatients(min);

}
}