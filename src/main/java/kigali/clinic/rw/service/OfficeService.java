package kigali.clinic.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

import kigali.clinic.rw.domain.Office;
import kigali.clinic.rw.repository.OfficeRepository;

@Service 
public class OfficeService {

    private final OfficeRepository officeRepo;

    public OfficeService(OfficeRepository officeRepo) {
        this.officeRepo = officeRepo;
    }

   public String saveOffice(Office office){

    Optional<Office> getOneOffice = officeRepo.findByOfficeNumber(office.getOfficeNumber());
      if(getOneOffice.isPresent()){
          return "Office with this number "+office.getOfficeNumber()+ " Office already exists";
      }

      officeRepo.save(office);
      return "saved successfully";
   }

    public List<Office> getAllOffices(){
      return officeRepo.findAll();
    }
   

   public String deleteOneOffice(int officeNumber){

      Optional<Office> getOneOffice = officeRepo.findByOfficeNumber(officeNumber);

      if(getOneOffice.isPresent()){
            officeRepo.deleteById(getOneOffice.get().getId());
            return "Office with this number "+officeNumber+ " is deleted successfully";
      }else{
        return "we don't have office with that office Number "+officeNumber;
      }
   }

      public List<Object[]> getBusiestOffice() {
        return officeRepo.findBusiestOffice();

      
   }

   public String deleteOffice(UUID id) {
        Optional<Office> office = officeRepo.findById(id);
        if (office.isPresent()) {
            officeRepo.deleteById(id);
            return "deleted successfully";
        } else {
            return "office not found";
        }
    }


}
