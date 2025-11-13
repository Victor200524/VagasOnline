package victor.trabalhovagasonline.vagasonline.entities;

public class Cargo {
    private String nome;

    public Cargo(String nome) {
        this.nome = nome;
    }

    public Cargo() {
        this("");
    }

    // --- INÍCIO DA CORREÇÃO ---

    // Adicione este método GETTER público
    public String getNome() {
        return nome;
    }

    // Adicione este método SETTER público
    public void setNome(String nome) {
        this.nome = nome;
    }

    // --- FIM DA CORREÇÃO ---


    @Override
    public String toString() {
        return nome;
    }
}