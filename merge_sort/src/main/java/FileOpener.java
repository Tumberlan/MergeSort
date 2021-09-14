import java.io.*;

public class FileOpener {
    private File file;
    private Integer number = null;
    private BufferedReader bufferedReader;

    public FileOpener(){}

    public File getFile() {
        return file;
    }

    public void setFile(File file) throws IOException {
        this.file = file;
        FileReader fileReader = new FileReader(this.file);
        System.out.println(file.getName());
        this.bufferedReader = new BufferedReader(fileReader);
        String str = this.bufferedReader.readLine();
        System.out.println(str);
        if (str != null) {
            this.number = Integer.parseInt(str);
        }
        System.out.println("number: " + this.number);
    }

    public Integer getTopNumber() {
        return number;
    }

    public void readNextNumber() throws IOException {
        String new_str = this.bufferedReader.readLine();
        if(new_str == null){
            this.number = null;
            return;
        }
        this.number = Integer.parseInt(new_str);
    }
}
