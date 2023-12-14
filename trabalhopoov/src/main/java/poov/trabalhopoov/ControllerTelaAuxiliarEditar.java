package poov.trabalhopoov;

import java.sql.SQLException;
import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import poov.trabalhopoov.models.Vacina;
import poov.trabalhopoov.models.dao.DAOFactory;
import poov.trabalhopoov.models.dao.VacinaDAO;

public class ControllerTelaAuxiliarEditar {

    private Vacina vacina;

    @FXML
    private Button botaoCancelar;

    @FXML
    private Button botaoEditar;

    @FXML
    private TextField vacinaCodigo;

    @FXML
    private TextField vacinaDescricao;

    @FXML
    private TextField vacinaNome;

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }


    private Boolean validarCampos() {
        return !vacinaCodigo.getText().isEmpty() &&
                !vacinaNome.getText().isEmpty() &&
                !vacinaDescricao.getText().isEmpty();
    }

    @FXML
    void botaoEditarClicado(ActionEvent event) {
        if (validarCampos()) {
            vacina = new Vacina(Long.parseLong(vacinaCodigo.getText()),
                    vacinaNome.getText(),
                    vacinaDescricao.getText());
            DAOFactory daoFactory = new DAOFactory();
            try {
                daoFactory.abrirConexao();
                VacinaDAO vacinaDAO = daoFactory.criarVacinaDAO();

                // salvando a vacina
                vacinaDAO.editar(vacina);
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
