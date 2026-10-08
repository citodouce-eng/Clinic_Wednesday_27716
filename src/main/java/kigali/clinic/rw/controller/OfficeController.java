package kigali.clinic.rw.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;
import java.util.List;
import kigali.clinic.rw.domain.Office;
import kigali.clinic.rw.service.OfficeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController 
@RequestMapping (value = "/api/offices")
public class OfficeController {

    private final OfficeService officeService;

    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping
    public ResponseEntity<List<Office>> getAllOffices(){
      return new ResponseEntity<>(officeService.getAllOffices(), HttpStatus.OK);
    }

    @GetMapping("/busiest")
    public ResponseEntity<?> getBusiestOffice() {
        List<Object[]> rows = officeService.getBusiestOffice();
        if (rows.isEmpty()) {
            return new ResponseEntity<>("No appointments yet", HttpStatus.OK);
        }
        return new ResponseEntity<>(rows.get(0), HttpStatus.OK);
    }

    @PostMapping(value="/save", consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE
     )
    public ResponseEntity<?> saveOffice(@RequestBody Office office){
        
      String returnedMessage =  officeService.saveOffice(office);

      if (returnedMessage.equalsIgnoreCase("saved successfully")){
        return new ResponseEntity<>(returnedMessage,HttpStatus.CREATED );
      }
      
      
        return new ResponseEntity<>(returnedMessage,HttpStatus.CONFLICT );
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOffice(@PathVariable UUID id) {
        String returnedMessage = officeService.deleteOffice(id);

        if (returnedMessage.equalsIgnoreCase("deleted successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        }

        return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
    }

    
}
