package kigali.clinic.rw.controller;

import kigali.clinic.rw.domain.Patient;
import kigali.clinic.rw.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import kigali.clinic.rw.service.DoctorService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;
    private final DoctorService doctorService;
    public PatientController(PatientService patientService, DoctorService doctorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
    }


    @PostMapping("/save")
    public ResponseEntity<Patient> savePatient(@RequestBody Patient patient) {
        Patient saved = patientService.savePatient(patient);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable UUID id) {
        Patient patient = patientService.getPatientById(id);
        if (patient != null) {
            return new ResponseEntity<>(patient, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable UUID id, @RequestBody Patient patient) {
        Patient updated = patientService.updatePatient(id, patient);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>("Patient deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/by-last-name")
    public List<Patient> findByLastName(@RequestParam String lastName) {
    return patientService.findByLastName(lastName);

}

      @GetMapping("/of-doctor/{doctorId}")
      public ResponseEntity<?> getPatientsOfDoctor(@PathVariable UUID doctorId) {


    if (!doctorService.doctorExists(doctorId)) {
        return new ResponseEntity<>("The doctor with that id does not exist", HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(patientService.getPatientsOfDoctor(doctorId), HttpStatus.OK);
}

   @GetMapping("/frequent")
   public ResponseEntity<List<Patient>> getFrequentPatients(@RequestParam long min) {
    return new ResponseEntity<>(patientService.getFrequentPatients(min), HttpStatus.OK);
}
}