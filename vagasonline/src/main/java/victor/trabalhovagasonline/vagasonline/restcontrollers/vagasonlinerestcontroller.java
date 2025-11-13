package victor.trabalhovagasonline.vagasonline.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Importe com *
import victor.trabalhovagasonline.vagasonline.entities.Interesse; // Novo
import victor.trabalhovagasonline.vagasonline.entities.Vaga; // Novo
import victor.trabalhovagasonline.vagasonline.services.VagasService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "apis")
public class vagasonlinerestcontroller {
    @Autowired
    VagasService vagasService;

    // --- Para App e Admin ---
    @GetMapping("vagas/get-all")
    public ResponseEntity<Object> getAllVagas(){
        return ResponseEntity.ok(vagasService.getAllVagas());
    }

    // --- NOVO (Para o Admin) ---
    @GetMapping("empresas/get-all")
    public ResponseEntity<Object> getAllEmpresas(){
        return ResponseEntity.ok(vagasService.getAllEmpresas());
    }

    // --- NOVO (Para o Admin) ---
    @GetMapping("cargos/get-all")
    public ResponseEntity<Object> getAllCargos(){
        return ResponseEntity.ok(vagasService.getAllCargos());
    }

    // --- NOVO (Para o Admin) ---
    @PostMapping("vagas")
    public ResponseEntity<Object> createVaga(@RequestBody Vaga vaga) {
        if (vaga == null) {
            return ResponseEntity.badRequest().body("Vaga inválida.");
        }
        Vaga novaVaga = vagasService.createVaga(vaga);
        if (novaVaga != null) {
            return ResponseEntity.ok(novaVaga);
        } else {
            return ResponseEntity.internalServerError().body("Erro ao criar vaga.");
        }
    }

    // --- NOVO (Para o Admin) ---
    @DeleteMapping("vagas/{registro}")
    public ResponseEntity<Object> deleteVaga(@PathVariable String registro) {
        if (vagasService.deleteVaga(registro)) {
            return ResponseEntity.ok().build(); // Retorna 200 OK sem corpo
        } else {
            return ResponseEntity.internalServerError().body("Erro ao deletar vaga.");
        }
    }

    // --- NOVO (Para a tela de Edição) ---
    @GetMapping("vagas/{registro}")
    public ResponseEntity<Object> getVagaByRegistro(@PathVariable String registro) {
        Vaga vaga = vagasService.getVagaByRegistro(registro);
        if (vaga != null) {
            return ResponseEntity.ok(vaga);
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 se não achar
        }
    }

    // --- NOVO (Para a tela de Edição) ---
    @PutMapping("vagas/{registro}")
    public ResponseEntity<Object> updateVaga(@PathVariable String registro, @RequestBody Vaga vaga) {
        if (vaga == null) {
            return ResponseEntity.badRequest().body("Vaga inválida.");
        }
        Vaga vagaAtualizada = vagasService.updateVaga(registro, vaga);
        if (vagaAtualizada != null) {
            return ResponseEntity.ok(vagaAtualizada);
        } else {
            return ResponseEntity.internalServerError().body("Erro ao atualizar vaga.");
        }
    }

    // --- NOVO (Para o App Android) ---
    @PostMapping("vagas/interesse")
    public ResponseEntity<Object> registerInterest(@RequestBody Interesse interesse) {
        if (interesse == null || interesse.getVaga() == null || interesse.getCandidato() == null) {
            return ResponseEntity.badRequest().body("Dados de interesse inválidos.");
        }

        Interesse novoInteresse = vagasService.registerInterest(interesse);

        if (novoInteresse != null) {
            return ResponseEntity.ok(novoInteresse);
        } else {
            return ResponseEntity.internalServerError().body("Erro ao registrar interesse.");
        }
    }
}