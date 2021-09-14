import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileParser {

    //private static final int = 12;
    FileLister fileLister = new FileLister();
    private String pathToInputDirectory;
    private String pathToTmpDirectory;
    private int linesToSkip = 0;
    String fileSeparator = System.getProperty("file.separator");
    private QuickSort quickSort = new QuickSort();

    public FileParser(String pathToDirectory, String pathToTDirectory) {
        this.pathToInputDirectory = pathToDirectory;
        this.pathToTmpDirectory = pathToTDirectory;
    }

    public void parse()throws IOException {
        Path path = Paths.get(pathToInputDirectory);
        List<Path> paths = FileLister.listFiles(path);
        AtomicInteger file_idx = new AtomicInteger();
        paths.forEach(x -> {
            System.out.println(x.getFileName().toString());
            System.out.println(pathToInputDirectory);
            boolean go_then = false;
            while(!go_then){
                String tmp_file_name = "tmpfile"+file_idx+".txt";
                String relativePath = "src\\main\\resources\\processFiles"
                        + fileSeparator + tmp_file_name;
                file_idx.getAndIncrement();
                File new_file = new File(relativePath);
                go_then = takeInf(x,new_file);
            }
        });
    }

    public void printInf(Path path) throws IOException {
        File file = new File(pathToInputDirectory, path.getFileName().toString());
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String string = bufferedReader.readLine();
            System.out.println(path);
            System.out.println("File text: ");
            while (string != null){
                String parse_symbol = " ";
                String[] sub_str = string.split(parse_symbol);

                for(int i = 0; i < sub_str.length;i++){
                    int a  = Integer.parseInt(sub_str[i]);
                    System.out.println("It has: "+(a+1));
                }
                System.out.println(string);
                string = bufferedReader.readLine();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean takeInf(Path path, File out_file){
        boolean result_check = true;
        File in_file = new File(pathToInputDirectory, path.getFileName().toString());
        try {
            FileOutputStream fos = new FileOutputStream(out_file);
            PrintStream printStream = new PrintStream(fos);
            int[] tmp_arr = new int[5];
            int iter_counter = 0;
            try {
                FileReader fileReader = new FileReader(in_file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String string = bufferedReader.readLine();
                for(int i = 0; i < linesToSkip; i++){
                    string = bufferedReader.readLine();
                }
                while (string != null && result_check) {
                    String parse_symbol = " ";
                    String[] sub_str = string.split(parse_symbol);
                    for (int i = 0; i < sub_str.length; i++) {
                        int a = Integer.parseInt(sub_str[i]);
                        if (iter_counter != 5) {
                            tmp_arr[iter_counter] = a;
                            iter_counter++;
                            linesToSkip++;
                        } else {
                            result_check = false;
                        }
                    }
                    System.out.println(string);
                    string = bufferedReader.readLine();
                }
                quickSort.qSort(tmp_arr,0,iter_counter-1);
                System.out.println("Sorted: ");
                for(int i = 0; i < iter_counter; i++){
                    System.out.println(tmp_arr[i]);
                    printStream.println(tmp_arr[i]);
                }
                if(!result_check){
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        linesToSkip = 0;
        return true;
    }

    public void parseOriginalFiles(){

    }

}
