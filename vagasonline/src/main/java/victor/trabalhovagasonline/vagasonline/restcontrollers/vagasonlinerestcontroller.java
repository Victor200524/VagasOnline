package victor.trabalhovagasonline.vagasonline.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.trabalhovagasonline.vagasonline.services.VagasService;

@RestController
@RequestMapping(value = "apis")
public class vagasonlinerestcontroller {
    @Autowired
    VagasService vagasService;

    @GetMapping("vagas/get-all")
    public ResponseEntity<Object> getAllVagas(){
        return ResponseEntity.ok(vagasService.getAllVagas());
    }
}
