package poov.trabalhopoov.models;

import java.time.LocalDate;

public class Aplicacao {
    private long codigo;
    private LocalDate data;
    private Pessoa pessoa;
    private Vacina vacina;
    private Situacao situação;
    
    public Aplicacao(long codigo, LocalDate data, Pessoa pessoa, Vacina vacina, Situacao situação) {
        this.codigo = codigo;
        this.data = data;
        this.pessoa = pessoa;
        this.vacina = vacina;
        this.situação = situação;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Vacina getVacina() {
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public Situacao getSituação() {
        return situação;
    }

    public void setSituação(Situacao situação) {
        this.situação = situação;
    }

    @Override
    public String toString() {
        return "Aplicacao [codigo=" + codigo + ", data=" + data + ", pessoa=" + pessoa + ", vacina=" + vacina
                + ", situação=" + situação + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (codigo ^ (codigo >>> 32));
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((pessoa == null) ? 0 : pessoa.hashCode());
        result = prime * result + ((vacina == null) ? 0 : vacina.hashCode());
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
        Aplicacao other = (Aplicacao) obj;
        if (codigo != other.codigo)
            return false;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        if (pessoa == null) {
            if (other.pessoa != null)
                return false;
        } else if (!pessoa.equals(other.pessoa))
            return false;
        if (vacina == null) {
            if (other.vacina != null)
                return false;
        } else if (!vacina.equals(other.vacina))
            return false;
        if (situação != other.situação)
            return false;
        return true;
    }

    
}
