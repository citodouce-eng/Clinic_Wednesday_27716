package kigali.clinic.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import kigali.clinic.rw.domain.Specialization;
import kigali.clinic.rw.repository.SpecializationRepository;

@Service
public class SpecializationService {

    private final SpecializationRepository specializationRepository;

    public SpecializationService(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    public Specialization saveSpecialization(Specialization specialization) {
        String normalizedName = specialization.getName() == null ? null : specialization.getName().trim();
        specialization.setName(normalizedName);

        Optional<Specialization> existing = specializationRepository.findByNameIgnoreCase(normalizedName);
        if (existing.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Specialization already exists: " + normalizedName);
        }
        return specializationRepository.save(specialization);
    }

    public List<Specialization> getAllSpecializations() {
        return specializationRepository.findAll();
    }

    public Specialization getSpecializationById(UUID id) {
        return specializationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Specialization not found with id: " + id));
    }

    public Specialization updateSpecialization(UUID id, Specialization updatedSpecialization) {
        Specialization existing = getSpecializationById(id);
        existing.setName(updatedSpecialization.getName());
        return specializationRepository.save(existing);
    }

    public void deleteSpecialization(UUID id) {
        specializationRepository.delete(getSpecializationById(id));
    }

    public List<Specialization> getUnusedSpecializations() {
    return specializationRepository.findUnusedSpecializations();
}
}
