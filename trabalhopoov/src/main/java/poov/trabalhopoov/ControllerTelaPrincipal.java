package poov.trabalhopoov;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.time.LocalDate;

import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import poov.trabalhopoov.models.Pessoa;
import poov.trabalhopoov.models.Vacina;
import poov.trabalhopoov.models.dao.DAOFactory;
import poov.trabalhopoov.models.dao.VacinaDAO;

public class ControllerTelaPrincipal {

    private Stage stageTelaAuxiliar;
    private Stage stageTelaAuxiliarEditar;
    List<Vacina> vacinas = null;
    List<Pessoa> pessoas = null;
    private ControllerTelaAuxiliar telaAuxiliar = null;
    private ControllerTelaAuxiliarEditar telaAuxiliarEditar = null;
    private Vacina vacina;
    private Pessoa pessoa;
    private VacinaDAO vacinaDAO;

    @FXML
    private Button botaoCriarAplicação;

    @FXML
    private Button botaoPesquisarPessoa;

    @FXML
    private Button botaoPesquisarVacina;

    @FXML
    private Button botaoVacinaEditar;

    @FXML
    private Button botaoVacinaNova;

    @FXML
    private Button botaoVacinaRemover;

    @FXML
    private TableColumn<Pessoa, Long> colunaCodigoPessoa;

    @FXML
    private TableColumn<Vacina, Long> colunaCodigoVacina;

    @FXML
    private TableColumn<Pessoa, String> colunaCpfPessoa;

    @FXML
    private TableColumn<Vacina, String> colunaDescricaoVacina;

    @FXML
    private TableColumn<Pessoa, LocalDate> colunaNascimentoPessoa;

    @FXML
    private TableColumn<Pessoa, String> colunaNomePessoa;

    @FXML
    private TableColumn<Vacina, String> colunaNomeVacina;

    @FXML
    private DatePicker dataFinal;

    @FXML
    private DatePicker dataInicial;

    @FXML
    private TextField pessoaCodigo;

    @FXML
    private TextField pessoaCpf;

    @FXML
    private TextField pessoaNome;

    @FXML
    private TableView<Pessoa> tabelaPessoa;

    @FXML
    private TableView<Vacina> tabelaVacina;

    @FXML
    private TextField vacinaCodigo;

    @FXML
    private TextArea vacinaDescricao;

    @FXML
    private TextField vacinaNome;

    @FXML
    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/telaauxiliar.fxml"));// tela criar vacina
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/telaauxiliareditar.fxml"));// tela editar
                                                                                                     // vacina
        Parent root = loader.load();
        Parent root2 = loader2.load();
        stageTelaAuxiliar = new Stage();
        stageTelaAuxiliarEditar = new Stage();
        Scene cena = new Scene(root);
        Scene cena2 = new Scene(root2);
        stageTelaAuxiliar.setScene(cena);
        stageTelaAuxiliarEditar.setScene(cena2);
        stageTelaAuxiliar.setTitle("Tela Auxiliar");
        stageTelaAuxiliarEditar.setTitle("Tela Auxiliar: Editar");
        stageTelaAuxiliar.show();
        stageTelaAuxiliarEditar.show();
        stageTelaAuxiliar.hide();
        stageTelaAuxiliarEditar.hide();

        telaAuxiliarEditar = loader2.getController();
        telaAuxiliar = loader.getController();

        configurarTabela();
    }

    private void configurarTabela() {
        pessoas = buscarPessoas();
        vacinas = buscarVacinas();

        colunaCodigoPessoa.setCellValueFactory(new PropertyValueFactory<Pessoa, Long>("codigo"));
        colunaNomePessoa.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("nome"));
        colunaCpfPessoa.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("cpf"));
        colunaNascimentoPessoa.setCellValueFactory(new PropertyValueFactory<Pessoa, LocalDate>("Nascimento"));

        colunaCodigoVacina.setCellValueFactory(new PropertyValueFactory<Vacina, Long>("codigo"));
        colunaNomeVacina.setCellValueFactory(new PropertyValueFactory<Vacina, String>("nome"));
        colunaDescricaoVacina.setCellValueFactory(new PropertyValueFactory<Vacina, String>("descricao"));

        tabelaPessoa.getItems().addAll(pessoas);
        tabelaVacina.getItems().addAll(vacinas);
        tabelaPessoa.setOnMouseClicked(this::tratarCliqueTabela);
        tabelaVacina.setOnMouseClicked(this::tratarCliqueTabela);
    }

    private void tratarCliqueTabela(MouseEvent event) {
        if (event.getClickCount() == 1 && (!tabelaVacina.getSelectionModel().isEmpty())) {
            Vacina selectedVacina = tabelaVacina.getSelectionModel().getSelectedItem();
            if (telaAuxiliar != null) {
                telaAuxiliar.setVacina(selectedVacina);
                vacina = selectedVacina;
            } else {
                System.err.println("Erro a iniciar a tela.");
            }
        }
    }

    private List<Pessoa> buscarPessoas() {
        DAOFactory daoFactory = new DAOFactory();
        try {
            daoFactory.abrirConexao();
            VacinaDAO vacinaDAO = daoFactory.criarVacinaDAO();
            List<Pessoa> pessoas = vacinaDAO.buscarTodasPessoas();

            for (Pessoa pessoa : pessoas) {
                System.out.println(pessoa);
            }
            return pessoas;
        } catch (SQLException e) {
            DAOFactory.mostrarSQLException(e);
        } finally {
            daoFactory.fecharConexao();
        }
        return null;
    }

    private List<Vacina> buscarVacinas() {
        DAOFactory daoFactory = new DAOFactory();
        try {
            daoFactory.abrirConexao();
            VacinaDAO vacinaDAO = daoFactory.criarVacinaDAO();
            List<Vacina> vacinas = vacinaDAO.buscarTodasVacinas();

            for (Vacina vacina : vacinas) {
                System.out.println(vacina);
            }
            return vacinas;
        } catch (SQLException e) {
            DAOFactory.mostrarSQLException(e);
        } finally {
            daoFactory.fecharConexao();
        }
        return null;

    }
    
    @FXML
    void pesquisarPessoaClicado(ActionEvent event) {
        String codigo = pessoaCodigo.getText();
        String nome = pessoaNome.getText();
        String cpf = pessoaCpf.getText();
        LocalDate dataInicio = dataInicial.getValue();
        LocalDate dataFim = dataFinal.getValue();
        
        if (codigo.isEmpty() && nome.isEmpty() && cpf.isEmpty() && dataInicio == null && dataFim == null) {
            // Todas vacinas caso esteja vazio
            configurarTabela();
        } else {
            // Pesquisa com o campo
            DAOFactory daoFactory = new DAOFactory();
            try {
                daoFactory.abrirConexao();
                VacinaDAO vacinaDAO = daoFactory.criarVacinaDAO();
                if (dataInicio == null || dataFim == null) {
                    // Use dataInicio e dataFim para realizar a pesquisa
                    System.out.println("Pesquisando no intervalo de datas: " + dataInicio + " a " + dataFim);
                    pessoas = vacinaDAO.pesquisarPessoa(codigo, nome, cpf);
                } else {
                    // As datas não foram completamente selecionadas
                    System.out.println("Selecione ambas as datas para pesquisar por intervalo.");
                }
            } catch (SQLException e) {
                DAOFactory.mostrarSQLException(e);
            } finally {
                daoFactory.fecharConexao();
            }
        }
        // Apaga e atualiza a tabela
        tabelaPessoa.getItems().clear();
        tabelaPessoa.getItems().addAll(pessoas);
        tabelaVacina.getItems().clear();
        tabelaVacina.getItems().addAll(vacinas);
    }

    @FXML
    void pesquisarVacinaClicado(ActionEvent event) {
        String codigo = vacinaCodigo.getText();
        String nome = vacinaNome.getText();
        String descricao = vacinaDescricao.getText();

        if (codigo.isEmpty() && nome.isEmpty() && descricao.isEmpty()) {
            // Todas vacinas caso esteja vazio
            configurarTabela();
        } else {
            // Pesquisa com o campo
            DAOFactory daoFactory = new DAOFactory();
            try {
                daoFactory.abrirConexao();
                VacinaDAO vacinaDAO = daoFactory.criarVacinaDAO();
                vacinas = vacinaDAO.pesquisarVacina(codigo, nome, descricao);
            } catch (SQLException e) {
                DAOFactory.mostrarSQLException(e);
            } finally {
                daoFactory.fecharConexao();
            }
        }
        // Apaga e atualiza a tabela
        tabelaPessoa.getItems().clear();
        tabelaPessoa.getItems().addAll(pessoas);
        tabelaVacina.getItems().clear();
        tabelaVacina.getItems().addAll(vacinas);
    }

    @FXML
    void vacinaEditarClicado(ActionEvent event) {
        stageTelaAuxiliarEditar.show();
    }

    @FXML
    void vacinaNovaClicado(ActionEvent event) {
        stageTelaAuxiliar.show();
    }

    @FXML
    void criarAplicacaoClicado(ActionEvent event) {

    }

    @FXML
    void vacinaRemoverClicado(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remover Vacina");
        alert.setHeaderText("Remover Vacina");
        alert.setContentText("Tem certeza que deseja remover a vacina?");
        alert.showAndWait();
        if (alert.getResult().getText().equals("OK")) {
            DAOFactory daoFactory = new DAOFactory();
            try {
                daoFactory.abrirConexao();
                VacinaDAO vacinaDAO = daoFactory.criarVacinaDAO();
                vacinaDAO.remover(vacina);
                tabelaVacina.getItems().remove(vacina);
            } catch (SQLException e) {
                DAOFactory.mostrarSQLException(e);
            } finally {
                daoFactory.fecharConexao();
            }
        }
    }

}
