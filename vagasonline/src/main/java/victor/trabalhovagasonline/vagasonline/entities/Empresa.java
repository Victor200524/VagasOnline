package victor.trabalhovagasonline.vagasonline.entities;

public class Empresa {
    private String nome_fantasia;
    private String razao_social;
    private String tipo;

    public Empresa(String nome_fantasia, String razao_social, String tipo) {
        this.nome_fantasia = nome_fantasia;
        this.razao_social = razao_social;
        this.tipo = tipo;
    }

    public Empresa() {
        this("","","");
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }
    public void setNome_fantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public String getRazao_social() {
        return razao_social;
    }
    public void setRazao_social(String razao_social) {
        this.razao_social = razao_social;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
