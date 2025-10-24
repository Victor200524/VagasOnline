package victor.trabalhovagasonline.vagasonline.entities;

public class Vaga {
    private String registro;
    private Empresa empresa;
    private String cargo;
    private String cidade;
    private String estado;
    private String pre_requisitos;
    private String formacao;
    private String conhecimentos_requeridos;
    private String regime;
    private String jornada_trabalho;
    private  String remuneracao;

    public Vaga(String registro, Empresa empresa, String cargo, String cidade, String estado, String pre_requisitos, String formacao, String conhecimentos_requeridos, String regime, String jornada_trabalho, String remuneracao) {
        this.registro = registro;
        this.empresa = empresa;
        this.cargo = cargo;
        this.cidade = cidade;
        this.estado = estado;
        this.pre_requisitos = pre_requisitos;
        this.formacao = formacao;
        this.conhecimentos_requeridos = conhecimentos_requeridos;
        this.regime = regime;
        this.jornada_trabalho = jornada_trabalho;
        this.remuneracao = remuneracao;
    }

    public Vaga() {
        this("",null,"","","","","","","","","");
    }

    public String getRegistro() {
        return registro;
    }
    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public Empresa getEmpresa() {
        return empresa;
    }
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getCargo() {
        return cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPre_requisitos() {
        return pre_requisitos;
    }
    public void setPre_requisitos(String pre_requisitos) {
        this.pre_requisitos = pre_requisitos;
    }

    public String getFormacao() {
        return formacao;
    }
    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public String getConhecimentos_requeridos() {
        return conhecimentos_requeridos;
    }
    public void setConhecimentos_requeridos(String conhecimentos_requeridos) {
        this.conhecimentos_requeridos = conhecimentos_requeridos;
    }

    public String getRegime() {
        return regime;
    }
    public void setRegime(String regime) {
        this.regime = regime;
    }

    public String getJornada_trabalho() {
        return jornada_trabalho;
    }
    public void setJornada_trabalho(String jornada_trabalho) {
        this.jornada_trabalho = jornada_trabalho;
    }

    public String getRemuneracao() {
        return remuneracao;
    }
    public void setRemuneracao(String remuneracao) {
        this.remuneracao = remuneracao;
    }
}
