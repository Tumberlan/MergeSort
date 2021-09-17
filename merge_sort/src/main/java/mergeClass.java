import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class mergeClass {

    private String pathToOutDirectory;
    private String pathToTmpDirectory;
    private String outFileName;
    private boolean lowHigh;
    private boolean isInt;
    public mergeClass(String pathToOutDirectory, String pathToTmpDirectory, String outputFileName, boolean lowhigh, boolean isint) {
        this.pathToOutDirectory = pathToOutDirectory;
        this.pathToTmpDirectory = pathToTmpDirectory;
        this.outFileName = outputFileName;
        this.lowHigh = lowhigh;
        this.isInt = isint;
    }

    String fileSeparator = System.getProperty("file.separator");


    public void sort() throws IOException {
        if(isInt){
            mergeSort();
        }else{
            mergeSortString();
        }
    }

    public void mergeSort() throws IOException {

        Path path = Paths.get(pathToTmpDirectory);
        List<Path> paths = FileLister.listFiles(path);
        AtomicInteger filesCounter = new AtomicInteger();
        paths.forEach(x->filesCounter.getAndIncrement());
        Vector<FileOpener> fileVector = new Vector<>();
        paths.forEach(x->{
            try {
                fillInf(x,  fileVector);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        String tmp_file_name = outFileName;
        String relativePath = pathToOutDirectory
                + fileSeparator + tmp_file_name;
        File result_file = new File(relativePath);
        FileOutputStream fos = new FileOutputStream(result_file);
        PrintStream printStream = new PrintStream(fos);
        while(!fileVector.isEmpty()){
            sorting(fileVector,  printStream);
        }
    }

    public void mergeSortString() throws IOException {

        Path path = Paths.get(pathToTmpDirectory);
        List<Path> paths = FileLister.listFiles(path);
        AtomicInteger filesCounter = new AtomicInteger();
        paths.forEach(x->filesCounter.getAndIncrement());
        Vector<FileOpenerString> fileVector = new Vector<>();
        paths.forEach(x->{
            try {
                fillInfString(x,  fileVector);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        String tmp_file_name = outFileName;
        String relativePath = pathToOutDirectory
                + fileSeparator + tmp_file_name;
        File result_file = new File(relativePath);
        FileOutputStream fos = new FileOutputStream(result_file);
        PrintStream printStream = new PrintStream(fos);
        while(!fileVector.isEmpty()){
            sortingString(fileVector,  printStream);
        }
    }

    public void fillInf(Path path,
                        Vector<FileOpener> fileVector) throws IOException {

        FileOpener new_fo = new FileOpener();
        new_fo.setFile(new File(pathToTmpDirectory, path.getFileName().toString()));
        fileVector.add(new_fo);
    }

    public void fillInfString(Path path,
                        Vector<FileOpenerString> fileVector) throws IOException {

        FileOpenerString new_fo = new FileOpenerString();
        new_fo.setFile(new File(pathToTmpDirectory, path.getFileName().toString()));
        fileVector.add(new_fo);
    }

    public void sorting(Vector<FileOpener> fileVector,
                         PrintStream printStream) throws IOException {

        boolean[] isEmpty = new boolean[fileVector.size()];
        for(int i = 0; i < fileVector.size(); i++){
            isEmpty[i] = false;
        }
        AtomicInteger minimum = new AtomicInteger();
        AtomicBoolean firstTry = new AtomicBoolean(true);
        AtomicInteger idx = new AtomicInteger();
        fileVector.forEach(x->{
            Integer new_number = x.getTopNumber();
            if(new_number == null){
                isEmpty[fileVector.indexOf(x)] = true;
            }else{
                if (firstTry.get()) {
                    minimum.getAndSet(x.getTopNumber());
                    firstTry.getAndSet(false);
                    idx.getAndSet(fileVector.indexOf(x));
                } else {
                    if(lowHigh) {
                        if (x.getTopNumber() < minimum.get()) {
                            minimum.getAndSet(x.getTopNumber());
                            idx.getAndSet(fileVector.indexOf(x));
                        }
                    }else{
                        if (x.getTopNumber() > minimum.get()) {
                            minimum.getAndSet(x.getTopNumber());
                            idx.getAndSet(fileVector.indexOf(x));
                        }
                    }
                }
            }
        });
        fileVector.get(idx.get()).readNextNumber();

        for(int i = 0; i < fileVector.size(); i++){
            if(isEmpty[i]){
                fileVector.get(i).close();
                fileVector.remove(i);
            }
        }
        if(!firstTry.get()) {
            printStream.println(minimum.get());
        }
    }

    public void sortingString(Vector<FileOpenerString> fileVector,
                              PrintStream printStream) throws IOException {
        boolean[] isEmpty = new boolean[fileVector.size()];
        for(int i = 0; i < fileVector.size(); i++){
            isEmpty[i] = false;
        }
        AtomicInteger minimum = new AtomicInteger();
        AtomicReference<String> minStr = new AtomicReference<String>();
        AtomicBoolean firstTry = new AtomicBoolean(true);
        AtomicInteger idx = new AtomicInteger();
        fileVector.forEach(x->{
            String new_str = x.getTopString();
            if(new_str == null){
                isEmpty[fileVector.indexOf(x)] = true;
            }else{
                if (firstTry.get()) {
                    minimum.getAndSet(x.getTopNumber());
                    minStr.getAndSet(x.getTopString());
                    firstTry.getAndSet(false);
                    idx.getAndSet(fileVector.indexOf(x));
                } else {
                    if(lowHigh) {
                        if (x.getTopNumber() < minimum.get()) {
                            minimum.getAndSet(x.getTopNumber());
                            minStr.getAndSet(x.getTopString());
                            idx.getAndSet(fileVector.indexOf(x));
                        }
                    }else{
                        if (x.getTopNumber() > minimum.get()) {
                            minimum.getAndSet(x.getTopNumber());
                            minStr.getAndSet(x.getTopString());
                            idx.getAndSet(fileVector.indexOf(x));
                        }
                    }
                }
            }
        });
        fileVector.get(idx.get()).readNextNumber();

        for(int i = 0; i < fileVector.size(); i++){
            if(isEmpty[i]){
                fileVector.get(i).close();
                fileVector.remove(i);
            }
        }
        if(!firstTry.get()) {
            printStream.println(minStr.get());
        }

    }



}
