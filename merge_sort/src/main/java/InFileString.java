import java.nio.charset.StandardCharsets;

public class InFileString {

    String string;
    Integer number;

    public InFileString(String string) {
        this.string = string;
        this.number = string.length();
    }

    public void putString(String str){
        this.string = str;
        this.number = str.length();
    }
}
