import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileOpenerString {
    private File file;
    private String string = null;
    private Integer number = null;
    private BufferedReader bufferedReader;

    public FileOpenerString(){}

    public void setFile(File file) throws IOException {
        this.file = file;
        FileReader fileReader = new FileReader(this.file);
        this.bufferedReader = new BufferedReader(fileReader);
        String str = this.bufferedReader.readLine();
        if (str != null) {
            this.string = str;
            this.number = str.length();
        }
    }

    public Integer getTopNumber() {
        return number;
    }

    public String getTopString(){
        return string;
    }

    public void readNextNumber() throws IOException {
        String new_str = this.bufferedReader.readLine();
        if(new_str == null){
            this.string = null;
            this.number = null;
            return;
        }
        this.string = new_str;
        this.number = new_str.length();
    }

    public void close() throws IOException {
        bufferedReader.close();
    }
}
