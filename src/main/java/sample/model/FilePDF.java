package sample.model;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

public class FilePDF {

    private File file;
    private String path;
    private String name;
    private String nameWithoutSpaces;
    private String header;

    public FilePDF(File file, String header) {

        this.file=file;
        this.path = file.getAbsolutePath();
        this.name= file.getName();
        this.name = name.replace("&", "&amp;"); 
        nameWithoutSpaces = name.replaceAll("\\s+","");
        this.header=header;
    }


    public String getBase64() {



        try {
            byte[] input_file = Files.readAllBytes(Paths.get(path ));
            byte[] encodedBytes = Base64.getEncoder().encode(input_file);
            String encodedString = new String(encodedBytes);
            return encodedString;

        } catch (IOException e) {
            System.out.println("Reading file failed: " + e.getMessage());
            return null;
        }

    }
    public String getTags(){
            String string = "<tns:DodatkoweInformacjeIObjasnienia>\n";
            string+="<dtsf:Opis>";
            string+=header;
            string+="</dtsf:Opis>\n";
            string+="<dtsf:Plik>\n";
            string+="<dtsf:Nazwa>";
            string+=nameWithoutSpaces;
            string+="</dtsf:Nazwa>\n";
            string+="<dtsf:Zawartosc>";
            string+=getBase64();
            string+="</dtsf:Zawartosc>\n";
            string+="</dtsf:Plik>\n";
            string+="</tns:DodatkoweInformacjeIObjasnienia>\n";



          return string;
        }
    }
