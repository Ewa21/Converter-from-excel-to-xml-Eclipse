package sample.model;

public class Tag {

    public static final String ERROR_NAME="error";
    public static final String SKIP_NEXT="skip_next";
    public static final String SKIP="skip";
    public static final String FILE_NAME="plik";
    private String name;
    private String value;
    private String mandatory;
    private String description;
    private String skipSession;


    public Tag(String name, String value, String mandatory, String description, String skipSession) {
        this.name = name;
        this.value = value.replace("&", "&amp;");
        this.mandatory=mandatory;
        this.description=description;
        this.skipSession=skipSession;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public String getTag(){



        //Sekcja
        if(skipSession.equals("TRUE")){

            return SKIP;
        }
        else{
            if(value.equals("") && mandatory.equals("TRUE")){
                return ERROR_NAME;
            }
            else if(value.equals("") && mandatory.equals("FALSE")){
                return SKIP_NEXT;
            }
            else{

                if(value.equals(FILE_NAME)){
                  return FILE_NAME;
                }
                return getString();
            }
        }
    }


    public String getString() {
        String allTag = "<" + name + ">";
        if(!value.equals("")){
            allTag += value;
        }
        else{

            allTag+="\n";
            if(!description.equals("")){
                allTag+="<!-- "+description+" -->"+"\n";
            }
        }


        return allTag;
    }

    public void print(){
        System.out.println("name: "+name);
        System.out.println("value: "+value);
        System.out.println("mandatory: "+mandatory);
        System.out.println("description: "+description);
        System.out.println("section: "+skipSession);
    }
}
