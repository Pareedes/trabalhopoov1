package poov.trabalhopoov.models;

import java.time.LocalDate;

public class Pessoa {
    private long codigo;
    private String nome;
    private String cpf;
    private LocalDate nascimento;
    private Situacao situação;
    
    public Pessoa() {
        nome = "Sem nome";
        cpf = "Sem cpf";
    }

    public Pessoa(long codigo, String nome, String cpf, LocalDate nascimento, Situacao situação) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.nascimento = nascimento;
        this.situação = situação;
    }

    public Pessoa(long codigo, String nome, String cpf, LocalDate nascimento) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.nascimento = nascimento;
    }

    public Pessoa(long codigo, String nome, String cpf) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public Situacao getSituação() {
        return situação;
    }

    public void setSituação(Situacao situação) {
        this.situação = situação;
    }

    @Override
    public String toString() {
        return "Pessoa [codigo=" + codigo + ", nome=" + nome + ", cpf=" + cpf + ", Nascimento=" + nascimento
                + ", situação=" + situação + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (codigo ^ (codigo >>> 32));
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        result = prime * result + ((nascimento == null) ? 0 : nascimento.hashCode());
        result = prime * result + ((situação == null) ? 0 : situação.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pessoa other = (Pessoa) obj;
        if (codigo != other.codigo)
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        if (nascimento == null) {
            if (other.nascimento != null)
                return false;
        } else if (!nascimento.equals(other.nascimento))
            return false;
        if (situação != other.situação)
            return false;
        return true;
    }

    
}
