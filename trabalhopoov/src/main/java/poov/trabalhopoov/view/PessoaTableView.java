package poov.trabalhopoov.view;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import poov.trabalhopoov.models.Pessoa;
import java.time.LocalDate;

public class PessoaTableView extends VBox {

    private TableView<Pessoa> pessoaTableView;
    private TableColumn<Pessoa, Long> codigoTableColumn;
    private TableColumn<Pessoa, String> nomeTableColumn;
    private TableColumn<Pessoa, String> cpfTableColumn;
    private TableColumn<Pessoa, LocalDate> nascimentoTableColumn;

    public PessoaTableView() {
        buildUI();
    }

    private void buildUI() {

        pessoaTableView = new TableView<Pessoa>();
        pessoaTableView.setEditable(true);

        codigoTableColumn = new TableColumn<Pessoa, Long>("Código");
        codigoTableColumn.setCellValueFactory(new PropertyValueFactory<Pessoa, Long>("codigo"));

        nomeTableColumn = new TableColumn<Pessoa, String>("Nome");
        nomeTableColumn.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("nome"));
        nomeTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nomeTableColumn.setOnEditCommit(new EventHandler<CellEditEvent<Pessoa, String>>() {
            @Override
            public void handle(CellEditEvent<Pessoa, String> event) {
                Pessoa pessoa = event.getRowValue();
                pessoa.setNome(event.getNewValue());
            }
        });

        cpfTableColumn = new TableColumn<Pessoa, String>("cpf");
        cpfTableColumn.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("cpf"));
        cpfTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        cpfTableColumn.setOnEditCommit(new EventHandler<CellEditEvent<Pessoa, String>>() {
            @Override
            public void handle(CellEditEvent<Pessoa, String> event) {
                Pessoa pessoa = event.getRowValue();
                pessoa.setCpf(event.getNewValue());
            }
        });

        nascimentoTableColumn = new TableColumn<Pessoa, LocalDate>("Nascimento");
        nascimentoTableColumn.setCellValueFactory(new PropertyValueFactory<Pessoa, LocalDate>("nascimento"));

        pessoaTableView.getColumns().add(codigoTableColumn);
        pessoaTableView.getColumns().add(nomeTableColumn);
        pessoaTableView.getColumns().add(cpfTableColumn);
        pessoaTableView.getColumns().add(nascimentoTableColumn);

        pessoaTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        pessoaTableView.setPlaceholder(new Label("Não existem dados para serem exibidos."));

        pessoaTableView.setTableMenuButtonVisible(true);

        getChildren().add(pessoaTableView);
    }

    public void add(Pessoa pessoa) {
        pessoaTableView.getItems().add(pessoa);
    }
}
