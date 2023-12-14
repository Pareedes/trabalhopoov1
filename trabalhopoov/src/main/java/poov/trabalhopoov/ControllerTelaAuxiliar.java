package poov.trabalhopoov;

import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import poov.trabalhopoov.models.Vacina;
import javafx.event.ActionEvent;

import poov.trabalhopoov.models.dao.DAOFactory;
import poov.trabalhopoov.models.dao.VacinaDAO;

public class ControllerTelaAuxiliar {

    private Vacina vacina;
    private VacinaDAO vacinaDAO;

    @FXML
    private Button botaoCancelar;

    @FXML
    private Button botaoConfirmar;

    @FXML
    private TextField vacinaCodigo;

    @FXML
    private TextField vacinaDescricao;

    @FXML
    private TextField vacinaNome;

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public void configurarFormulario(Vacina vacina) {
        this.vacina = vacina;
        vacinaCodigo.setText(String.valueOf(vacina.getCodigo()));
        vacinaNome.setText(vacina.getNome());
        vacinaDescricao.setText(vacina.getDescricao());
    }

    private boolean validarCampos() {
        String codigo = vacinaCodigo.getText();
        String nome = vacinaNome.getText();
        String descricao = vacinaDescricao.getText();

        if (nome.isEmpty() || descricao.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Nome e descrição são obrigatórios");
            alert.showAndWait();
            return false;
        }

        if (!codigo.isEmpty() && !codigo.matches("\\d+")) {
            // Se o código não for vazio e não for um número
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Código deve ser um número ou estar vazio");
            alert.showAndWait();
            return false;
        }

        return true;
    }

    @FXML
    void botaoConfirmarClicado(ActionEvent event) {
        String codigo = vacinaCodigo.getText();
        String nome = vacinaNome.getText();
        String descricao = vacinaDescricao.getText();

        // Verificar se a string 'codigo' contém apenas números, permitindo que seja
        // vazia
        if (!codigo.isEmpty()) {
            try {
                Long.parseLong(codigo);
            } catch (NumberFormatException e) {
                // Se não for um número, exibir uma mensagem de erro e retornar
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Digite apenas números no campo Código");
                alert.showAndWait();
                return;
            }
        }

        if (validarCampos()) {
            // Se o campo de código estiver vazio, gerar automaticamente o próximo número
            if (codigo.isEmpty()) {
                DAOFactory daoFactory = new DAOFactory();
                try {
                    daoFactory.abrirConexao();
                    vacinaDAO = daoFactory.criarVacinaDAO(); // Certifique-se de inicializar o vacinaDAO

                    // Obter o próximo número baseado no último código
                    long proximoCodigo = vacinaDAO.proximoCodigo();
                    vacina = new Vacina(proximoCodigo, nome, descricao);
                } catch (SQLException e) {
                    DAOFactory.mostrarSQLException(e);
                    return;
                } finally {
                    daoFactory.fecharConexao();
                }
            } else {
                vacina = new Vacina(Long.parseLong(codigo), nome, descricao);
            }

            DAOFactory daoFactory = new DAOFactory();
            try {
                daoFactory.abrirConexao();
                vacinaDAO = daoFactory.criarVacinaDAO(); // Certifique-se de inicializar o vacinaDAO

                // salvando a vacina
                vacinaDAO.criar(vacina);
                ((Button) event.getSource()).getScene().getWindow().hide();

            } catch (SQLException e) {
                DAOFactory.mostrarSQLException(e);
            } finally {
                daoFactory.fecharConexao();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Um dos campos está vazio");
            alert.showAndWait();
        }
    }

    @FXML
    void botaoCancelarClicado(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

}
