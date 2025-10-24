package victor.trabalhovagasonline.vagasonline.entities;

public class Cargo {
    private String nome;

    public Cargo(String nome) {
        this.nome = nome;
    }

    public Cargo() {
        this("");
    }

    @Override
    public String toString() {
        return nome;
    }
}
