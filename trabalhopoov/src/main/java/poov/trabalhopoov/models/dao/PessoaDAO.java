package poov.trabalhopoov.models.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import poov.trabalhopoov.models.Pessoa;

public class PessoaDAO {
    private final Connection conexao;

    public PessoaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public List<Pessoa> pesquisarPessoa(String codigo, String nome, String cpf) throws SQLException {
        List<Pessoa> pessoas = new ArrayList<>();

        String sql = "SELECT * FROM pessoa WHERE codigo::text = ? OR nome LIKE ? OR cpf LIKE ?;";
        PreparedStatement pstmt = conexao.prepareStatement(sql);
        pstmt.setString(1, codigo);
        pstmt.setString(2, nome);
        pstmt.setString(3, cpf);

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Pessoa v = new Pessoa(rs.getLong(1), rs.getString(2), rs.getString(3));
            pessoas.add(v);
        }

        if (pessoas.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Nenhuma pessoa encontrada");
            alert.showAndWait();
        }

        rs.close();
        pstmt.close();

        return pessoas;
    }

    public List<Pessoa> pesquisarEntreDatas(Pessoa p, LocalDate dataInicial, LocalDate dataFinal) {
        List<Pessoa> pessoas = new ArrayList<>();
        String QUERY = "SELECT * FROM pessoa WHERE codigo is not null";
        int parametro = 1;
        if (p.getCodigo() != -1) {
            QUERY += "AND codigo = ?";
        }
        if (p.getNome() != null) {
            QUERY += "AND upper(nome) like upper(?)";
        }
        if (p.getCpf() != null) {
            QUERY += "AND cpf like ?";
        }
        if (dataInicial != null && dataFinal != null) {
            QUERY += "AND dataNascimento BETWEEN ? AND ?";
        }
        try {
            PreparedStatement pstmt = conexao.prepareStatement(QUERY);
            if (p.getCodigo() != -1) {
                pstmt.setLong(parametro, p.getCodigo());
                parametro++;
            }
            if (p.getNome() != null) {
                pstmt.setString(parametro, p.getNome());
                parametro++;
            }
            if (p.getCpf() != null) {
                pstmt.setString(parametro, p.getCpf());
                parametro++;
            }
            if (dataInicial != null && dataFinal != null) {
                pstmt.setDate(parametro, Date.valueOf(dataInicial));
                parametro++;
                pstmt.setDate(parametro, Date.valueOf(dataFinal));
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Pessoa v = new Pessoa(rs.getLong(1), rs.getString(2), rs.getString(3), (rs.getDate(4).toLocalDate()));
                pessoas.add(v);
            }

            if (pessoas.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Nenhuma pessoa encontrada");
                alert.showAndWait();
            }
            for(Pessoa peopl : pessoas){
                System.out.println(peopl);
            }
            rs.close();
            pstmt.close();

            return pessoas;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return pessoas;

    }

    public List<Pessoa> buscarTodasPessoas() throws SQLException {
        List<Pessoa> pessoas = new ArrayList<>();

        String sql = "SELECT * FROM pessoa";

        Statement stmt = conexao.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            java.sql.Date sqlDate = rs.getDate("nascimento");
            java.time.LocalDate localDate = sqlDate.toLocalDate();
            Pessoa p = new Pessoa(rs.getLong("codigo"), rs.getString("nome"), rs.getString("cpf"), localDate);
            pessoas.add(p);
        }

        return pessoas;
    }

}
