package sample;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import sample.model.DataConverter;
import sample.model.FilePDF;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private String excelPath="";
    private String schemePath="";
    private List<FilePDF> pdfs= new ArrayList<>();
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button generateButton;
    @FXML
    private Label excelLabel;
    @FXML
    private Label pdf1Label;
    @FXML
    private Label pdf2Label;
    @FXML
    private Label pdf3Label;
    @FXML
    private Label pdf4Label;
    @FXML
    private Label pdf5Label;
    @FXML
    private TextField pdf1Text;
    @FXML
    private TextField pdf2Text;
    @FXML
    private TextField pdf3Text;
    @FXML
    private TextField pdf4Text;
    @FXML
    private TextField pdf5Text;
    @FXML
    private TextArea textArea;


    @FXML
    public void getPdfFiles(ActionEvent event) {

        File selectedFile;
        FilePDF filePDF;
        String header;
        String buttonId;
        String path;
        Label label;
        TextField textField;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select PDF file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
                selectedFile = fileChooser.showOpenDialog(null);
        buttonId=((Button)event.getSource()).getId();
        System.out.println(buttonId);
        label= getLabel(buttonId);
        if (selectedFile != null) {
            header= getTextField(buttonId).getText();
            filePDF = new FilePDF(selectedFile,header);
            pdfs.add(filePDF);
            label.setText("Wybrano plik: " + selectedFile.getName());
            if(buttonId.equals("pdf1Button")) {
                generateButton.setDisable(false);
            }
        } else {
            label.setText("Bląd podczas wybierania pliku.");
        }


    }
    private TextField getTextField(String buttonName){

        switch (buttonName){
            case "pdf1Button":
                return pdf1Text;
            case "pdf2Button":
                return pdf2Text;
            case "pdf3Button":
                return pdf3Text;
            case "pdf4Button":
                return pdf4Text;
            case "pdf5Button":
                return pdf5Text;
        }
        return null;
    }

    private Label getLabel(String buttonName){

        switch (buttonName){
            case "pdf1Button":
                return pdf1Label;
            case "pdf2Button":
                return pdf2Label;
            case "pdf3Button":
                return pdf3Label;
            case "pdf4Button":
                return pdf4Label;
            case "pdf5Button":
                return pdf5Label;
        }
        return null;
    }

    @FXML
    public void getExcelFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Excel file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            excelPath=selectedFile.getAbsolutePath();
            excelLabel.setText("Wybrano plik: " + selectedFile.getName());

        } else {
            excelLabel.setText("Bląd podczas wybierania pliku.");
        }
    }
    @FXML
    public void convert() {


         StringBuilder text=new StringBuilder();
        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                try {
                    return DataConverter.getInstance().convert(excelPath,pdfs,text, schemePath);

                }
                catch(InvalidFormatException e){
                    System.out.println("Błąd "+ e.getMessage());
                    return null;
                }
            }
        };

        progressBar.progressProperty().bind(task.progressProperty());
        progressBar.setVisible(true);
        task.setOnSucceeded(e -> {progressBar.setVisible(false); textArea.setText(text.toString());});

        task.setOnFailed(e -> {progressBar.setVisible(false); textArea.setText(text.toString());});


        new Thread(task).start();
    }



}

