package victor.trabalhovagasonline.vagasonline.entities;

public class Cargo {
    private String nome;

    public Cargo(String nome) {
        this.nome = nome;
    }

    public Cargo() {
        this("");
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}