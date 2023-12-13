package poov.trabalhopoov.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import poov.trabalhopoov.models.Vacina;
import poov.trabalhopoov.models.Pessoa;

public class VacinaDAO {
    private final Connection conexao;

    public VacinaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public boolean editar(Vacina vacina) throws SQLException {
        String sqlUpdate = "UPDATE vacina SET nome = ?, descricao = ? WHERE codigo = ?;";

        try (PreparedStatement pstmtUpd = conexao.prepareStatement(sqlUpdate)) {
            pstmtUpd.setString(1, vacina.getNome());
            pstmtUpd.setString(2, vacina.getDescricao());
            pstmtUpd.setLong(3, vacina.getCodigo());

            int resultado = pstmtUpd.executeUpdate();

            if (resultado == 1) {
                System.out.println("Alteracao da vacina executada com sucesso");
                return true;
            } else {
                System.out.println("Erro alterando a vacina com codigo: " + vacina.getCodigo());
            }
        }
        return false;
    }

    public boolean remover(Vacina vacina) throws SQLException {

        boolean retorno = false;

        String sqlUpdate = "DELETE FROM vacina WHERE codigo = ? AND nome = ? AND descricao = ?;";
        PreparedStatement pstmtUpd = conexao.prepareStatement(sqlUpdate);

        pstmtUpd.setLong(1, vacina.getCodigo());
        pstmtUpd.setString(2, vacina.getNome());
        pstmtUpd.setString(3, vacina.getDescricao());

        int resultado = pstmtUpd.executeUpdate();

        if (resultado == 1) {
            System.out.println("Removida.");
            retorno = true;
        } else {
            System.out.println("Erro ao remover: " + vacina.getCodigo());
        }

        pstmtUpd.close();

        return retorno;
    }

    public void criar(Vacina vacina) throws SQLException {
        String sql = "INSERT INTO vacina(codigo, nome, descricao) VALUES (?, ?, ?);";
        PreparedStatement pstmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        pstmt.setLong(1, vacina.getCodigo());
        pstmt.setString(2, vacina.getNome());
        pstmt.setString(3, vacina.getDescricao());

        if (pstmt.executeUpdate() == 1) {
            System.out.println("Insercao da vacina feita com sucesso");
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                vacina.setCodigo(rs.getLong(1));
            } else {
                System.out.println("Erro ao obter o codigo gerado pelo BD para a vacina");
            }
            rs.close();
        } else {
            System.out.println("Erro ao inserir a vacina");
        }
        pstmt.close();
    }

    public List<Vacina> pesquisarVacina(String codigo, String nome, String descricao) throws SQLException {
        List<Vacina> vacinas = new ArrayList<>();

        String sql = "SELECT * FROM vacina WHERE codigo::text = ? OR nome LIKE ? OR descricao LIKE ?;";
        PreparedStatement pstmt = conexao.prepareStatement(sql);
        pstmt.setString(1, codigo);
        pstmt.setString(2, nome);
        pstmt.setString(3, descricao);

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Vacina v = new Vacina(rs.getLong(1), rs.getString(2), rs.getString(3));
            vacinas.add(v);
        }

        if (vacinas.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Nenhuma vacina encontrada");
            alert.showAndWait();
        }

        rs.close();
        pstmt.close();

        return vacinas;
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

    public List<Vacina> buscarTodasVacinas() throws SQLException {
        List<Vacina> vacinas = new ArrayList<>();

        String sql = "SELECT * FROM vacina";

        Statement stmt = conexao.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Vacina v = new Vacina(rs.getLong("codigo"), rs.getString("nome"), rs.getString("descricao"));
            vacinas.add(v);
        }

        return vacinas;
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
