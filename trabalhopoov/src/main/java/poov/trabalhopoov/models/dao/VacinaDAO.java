package poov.trabalhopoov.models.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import javafx.scene.control.Alert;
import poov.trabalhopoov.models.Vacina;
import poov.trabalhopoov.models.Aplicacao;
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

    public boolean remover(long codigo) throws SQLException {
        String sqlUpdate = "UPDATE vacina SET situacao = false WHERE codigo = ?;";
    
        try (PreparedStatement pstmtUpd = conexao.prepareStatement(sqlUpdate)) {
            pstmtUpd.setLong(1, codigo);
    
            int resultado = pstmtUpd.executeUpdate();
    
            if (resultado == 1) {
                System.out.println("Vacina desativada com sucesso");
                return true;
            } else {
                System.out.println("Erro desativando a vacina com código: " + codigo);
            }
        }
        return false;
    }

    public long proximoCodigo() throws SQLException {
        String sql = "SELECT MAX(codigo) FROM vacina;";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    long maxCodigo = rs.getLong(1);
                    return maxCodigo + 1;
                } else {
                    return 1;
                }
            }
        }
    }

    public void criar(Vacina vacina) throws SQLException {
        String sql = "INSERT INTO vacina(codigo, nome, descricao) VALUES (?, ?, ?);";
        PreparedStatement pstmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        pstmt.setLong(1, proximoCodigo());
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

        Long codigoLong = null;
        try {
            codigoLong = (codigo.isEmpty()) ? null : Long.parseLong(codigo);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    
        String sql = "SELECT * FROM vacina WHERE situacao = true AND (codigo = ? OR nome LIKE ? OR descricao LIKE ?) ORDER BY codigo ASC;";
        PreparedStatement pstmt = conexao.prepareStatement(sql);
        pstmt.setObject(1, codigoLong);  // Use setObject para lidar com valores nulos
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

    public List<Vacina> buscarTodasVacinas() throws SQLException {
        List<Vacina> vacinas = new ArrayList<>();

        String sql = "SELECT * FROM vacina WHERE situacao = true";

        Statement stmt = conexao.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Vacina v = new Vacina(rs.getLong("codigo"), rs.getString("nome"), rs.getString("descricao"));
            vacinas.add(v);
        }

        return vacinas;
    }

    public List<Pessoa> pesquisarEntreDatas(Pessoa p, LocalDate dataInicio, LocalDate dataFinal) {
        List<Pessoa> pessoas = new ArrayList<>();
        String sql = "SELECT * FROM pessoa WHERE codigo IS NOT NULL";
    
        if (p.getCodigo() != -1) {
            try {
                Long.parseLong(String.valueOf(p.getCodigo()));
                sql += " AND codigo = ?";
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Digite apenas números no campo Código");
                alert.showAndWait();
                return pessoas;
            }
        }
    
        if (p.getNome() != null) {
            sql += " AND UPPER(nome) LIKE UPPER(?)";
        }
        if (p.getCpf() != null) {
            sql += " AND cpf LIKE ?";
        }
        if (dataInicio != null && dataFinal != null) {
            sql += " AND nascimento BETWEEN ? AND ?";
        }
    
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            int parametro = 1;
    
            // Adiciona os parâmetros à consulta SQL
            if (p.getCodigo() != -1) {
                pstmt.setLong(parametro++, p.getCodigo());
            }
            if (p.getNome() != null) {
                pstmt.setString(parametro++, p.getNome());
            }
            if (p.getCpf() != null) {
                pstmt.setString(parametro++, p.getCpf());
            }
            if (dataInicio != null && dataFinal != null) {
                pstmt.setDate(parametro++, Date.valueOf(dataInicio));
                pstmt.setDate(parametro++, Date.valueOf(dataFinal));
            }
    
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Pessoa pessoa = new Pessoa(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getDate(4).toLocalDate());
                    pessoas.add(pessoa);
                }
            }
    
            if (pessoas.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Nenhuma pessoa encontrada");
                alert.showAndWait();
            }
    
            pessoas.forEach(System.out::println);
    
            return pessoas;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    
        return pessoas;
    }

    public void criarAplicacao(Aplicacao nova) throws SQLException{
        String aplicaocao = "INSERT INTO aplicacao(data, codigo_pessoa, codigo_vacina) VALUES (?,?,?)";
        PreparedStatement pstmt = conexao.prepareStatement(aplicaocao);
        try{
            pstmt.setDate(1, Date.valueOf(nova.getData()));
            pstmt.setLong(2, nova.getPessoa().getCodigo());
            pstmt.setLong(3, nova.getVacina().getCodigo());
            pstmt.executeUpdate();
            System.out.println("Sucesso");

        }catch(SQLException exception){
            System.out.println(exception.toString());

        }finally{
            pstmt.close();
        }

    }

}
