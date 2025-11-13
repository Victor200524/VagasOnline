package victor.trabalhovagasonline.vagasonline.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // IMPORTADO
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import victor.trabalhovagasonline.vagasonline.entities.Interesse;
import victor.trabalhovagasonline.vagasonline.entities.Vaga;
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

    // --- Para o Admin---
    @GetMapping("empresas/get-all")
    public ResponseEntity<Object> getAllEmpresas(){
        return ResponseEntity.ok(vagasService.getAllEmpresas());
    }

    // --- Para o Admin---
    @GetMapping("cargos/get-all")
    public ResponseEntity<Object> getAllCargos(){
        return ResponseEntity.ok(vagasService.getAllCargos());
    }

    // --- Para o Admin---
    @PostMapping("vagas")
    public ResponseEntity<Object> createVaga(@RequestBody Vaga vaga) {
        if (vaga == null) {
            return ResponseEntity.badRequest().body("Vaga inválida.");
        }
        Vaga novaVaga = vagasService.createVaga(vaga);
        if (novaVaga != null)
            return ResponseEntity.ok(novaVaga);
        else
            return ResponseEntity.internalServerError().body("Erro ao criar vaga.");
    }

    // --- Para o Admin---
    @DeleteMapping("vagas/{registro}")
    public ResponseEntity<Object> deleteVaga(@PathVariable String registro) {
        try {
            // Tenta deletar a vaga
            if (vagasService.deleteVaga(registro))
                return ResponseEntity.ok().build(); // Retorna 200 OK sem corpo
            else
                return ResponseEntity.internalServerError().body("Erro ao deletar vaga.");

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            // Captura qualquer outro erro inesperado
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }

    // --- Para o Admin---
    @GetMapping("vagas/{registro}")
    public ResponseEntity<Object> getVagaByRegistro(@PathVariable String registro) {
        Vaga vaga = vagasService.getVagaByRegistro(registro);
        if (vaga != null)
            return ResponseEntity.ok(vaga);
        else
            return ResponseEntity.notFound().build(); // Retorna 404 se não achar
    }

    // --- Para o Admin (ATUALIZADO) ---
    @PutMapping("vagas/{registro}")
    public ResponseEntity<Object> updateVaga(@PathVariable String registro, @RequestBody Vaga vaga) {
        if (vaga == null) {
            return ResponseEntity.badRequest().body("Vaga inválida.");
        }
        try {
            // Tenta atualizar a vaga
            Vaga vagaAtualizada = vagasService.updateVaga(registro, vaga);
            if (vagaAtualizada != null)
                return ResponseEntity.ok(vagaAtualizada);
            else
                return ResponseEntity.internalServerError().body("Erro ao atualizar vaga.");

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }

    // --- Para o App---
    @PostMapping("vagas/interesse")
    public ResponseEntity<Object> registerInterest(@RequestBody Interesse interesse) {
        if (interesse == null || interesse.getVaga() == null || interesse.getCandidato() == null) {
            return ResponseEntity.badRequest().body("Dados de interesse inválidos.");
        }

        Interesse novoInteresse = vagasService.registerInterest(interesse);

        if (novoInteresse != null)
            return ResponseEntity.ok(novoInteresse);
        else
            return ResponseEntity.internalServerError().body("Erro ao registrar interesse.");
    }
}