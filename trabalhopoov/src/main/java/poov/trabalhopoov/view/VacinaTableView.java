package poov.trabalhopoov.view;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import poov.trabalhopoov.models.Vacina;

public class VacinaTableView extends VBox {

    private TableView<Vacina> vacinaTableView;
    private TableColumn<Vacina, Long> codigoTableColumn;
    private TableColumn<Vacina, String> nomeTableColumn;
    private TableColumn<Vacina, String> descricaoTableColumn;

    public VacinaTableView() {
        buildUI();
    }

    private void buildUI() {

        vacinaTableView = new TableView<Vacina>();
        vacinaTableView.setEditable(true);

        codigoTableColumn = new TableColumn<Vacina, Long>("Código");
        codigoTableColumn.setCellValueFactory(new PropertyValueFactory<Vacina, Long>("codigo"));

        nomeTableColumn = new TableColumn<Vacina, String>("Nome");
        nomeTableColumn.setCellValueFactory(new PropertyValueFactory<Vacina, String>("nome"));
        nomeTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nomeTableColumn.setOnEditCommit(new EventHandler<CellEditEvent<Vacina, String>>() {
            @Override
            public void handle(CellEditEvent<Vacina, String> event) {
                Vacina vacina = event.getRowValue();
                vacina.setNome(event.getNewValue());
            }
        });

        descricaoTableColumn = new TableColumn<Vacina, String>("Descrição");
        descricaoTableColumn.setCellValueFactory(new PropertyValueFactory<Vacina, String>("descricao"));
        descricaoTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descricaoTableColumn.setOnEditCommit(new EventHandler<CellEditEvent<Vacina, String>>() {
            @Override
            public void handle(CellEditEvent<Vacina, String> event) {
                Vacina vacina = event.getRowValue();
                vacina.setDescricao(event.getNewValue());
            }
        });

        vacinaTableView.getColumns().add(codigoTableColumn);
        vacinaTableView.getColumns().add(nomeTableColumn);
        vacinaTableView.getColumns().add(descricaoTableColumn);

        vacinaTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        vacinaTableView.setPlaceholder(new Label("Não existem dados para serem exibidos."));

        vacinaTableView.setTableMenuButtonVisible(true);

        getChildren().add(vacinaTableView);
    }

    public void add(Vacina vacina) {
        vacinaTableView.getItems().add(vacina);
    }
}
