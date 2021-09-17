import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
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
    private String command;
    private boolean isInt;
    private boolean lowHigh;

    public FileParser(String pathToDirectory, String pathToTDirectory, String cmd, boolean lowhigh, boolean isint) {
        this.pathToInputDirectory = pathToDirectory;
        this.pathToTmpDirectory = pathToTDirectory;
        this.command = cmd;
        this.lowHigh = lowhigh;
        this.isInt = isint;
    }

    public boolean containsNumber(String string){
        return string.matches(".*\\d+.*");
    }

    public List<String> openCurrentFiles() throws IOException {
        String[] sub_str = command.split(";");
        Path path = Paths.get(pathToInputDirectory);
        List<Path> paths = FileLister.listFiles(path);
        List<String> filesName = new LinkedList<String>();
        AtomicBoolean normalFile = new AtomicBoolean(false);
        paths.forEach(x->{
            for(int i = 0; i < sub_str.length; i++){
                if(sub_str[i].split("\\.").length < 2){
                    System.out.println("wrong input files");
                    System.exit(1);
                }
                if(x.getFileName().toString().equals(sub_str[i])){
                    filesName.add(sub_str[i]);
                    normalFile.getAndSet(true);
                }
            }
        });
        if(!normalFile.get()){
            System.out.println("no such input files");
            System.exit(1);
        }
        return filesName;
    }


    public void parse()throws IOException {

        List<String> filesName = openCurrentFiles();
        if(filesName == null){
            System.out.println("no such files");
            return;
        }
        AtomicInteger file_idx = new AtomicInteger();
        filesName.forEach(x->{
            boolean go_then = false;
            while(!go_then){
                String tmp_file_name = "tmpfile"+file_idx+".txt";
                String relativePath = "src\\main\\resources\\processFiles"
                        + fileSeparator + tmp_file_name;
                file_idx.getAndIncrement();
                File new_file = new File(relativePath);
                if(isInt) {
                    go_then = takeInf(x, new_file);
                }else{
                    go_then = takeInfString(x, new_file);
                }
            }
        });
    }

    public boolean takeInf(String fileName, File out_file){
        boolean result_check = true;
        File in_file = new File(pathToInputDirectory, fileName);
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
                        if(!containsNumber(sub_str[i])){
                            System.out.println("Check your file or your program key, you got no match with type of data");
                            System.exit(1);
                        }
                        int a = Integer.parseInt(sub_str[i]);
                        if (iter_counter != 5) {
                            tmp_arr[iter_counter] = a;
                            iter_counter++;
                            linesToSkip++;
                        } else {
                            result_check = false;
                        }
                    }
                    string = bufferedReader.readLine();
                }

                quickSort.qSort(tmp_arr,0,iter_counter-1);
                int[] reverse_arr = new int[iter_counter];
                if(!lowHigh){
                    for (int i = 0; i < iter_counter; i++){
                        reverse_arr[i] = tmp_arr[iter_counter-1-i];
                    }
                    tmp_arr = reverse_arr;
                }
                for(int i = 0; i < iter_counter; i++){
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

    public boolean takeInfString(String fileName, File out_file){
        boolean result_check = true;
        File in_file = new File(pathToInputDirectory, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(out_file);
            PrintStream printStream = new PrintStream(fos);
            InFileString[] tmp_arr = new InFileString[5];
            for (int i = 0; i < 5; i++){
                tmp_arr[i] = new InFileString("");
            }
            int iter_counter = 0;
            try {
                FileReader fileReader = new FileReader(in_file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String string = bufferedReader.readLine();
                for(int i = 0; i < linesToSkip; i++){
                    string = bufferedReader.readLine();
                }
                while (string != null && result_check) {
                    String a_str = string;
                    if (iter_counter != 5) {
                        tmp_arr[iter_counter].putString(a_str);
                        iter_counter++;
                        linesToSkip++;
                    } else {
                        result_check = false;
                    }
                    string = bufferedReader.readLine();
                }
                quickSort.qSortString(tmp_arr,0,iter_counter-1);
                InFileString[] reverse_arr = new InFileString[iter_counter];
                if(!lowHigh){
                    for (int i = 0; i < iter_counter; i++){
                        reverse_arr[i] = tmp_arr[iter_counter-1-i];
                    }
                    tmp_arr = reverse_arr;
                }
                for(int i = 0; i < iter_counter; i++){
                    printStream.println(tmp_arr[i].string);
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


}
