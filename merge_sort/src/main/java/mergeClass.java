import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class mergeClass {

    private String pathToOutDirectory;
    private String pathToTmpDirectory;

    FileLister fileLister = new FileLister();
    public mergeClass(String pathToOutDirectory, String pathToTmpDirectory) {
        this.pathToOutDirectory = pathToOutDirectory;
        this.pathToTmpDirectory = pathToTmpDirectory;
    }

    String fileSeparator = System.getProperty("file.separator");



    public void mergeSort() throws IOException {

        Path path = Paths.get(pathToTmpDirectory);
        List<Path> paths = fileLister.listFiles(path);
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
        String tmp_file_name = "resultfile.txt";
        String relativePath = pathToOutDirectory
                + fileSeparator + tmp_file_name;
        File result_file = new File(relativePath);
        FileOutputStream fos = new FileOutputStream(result_file);
        PrintStream printStream = new PrintStream(fos);
        while(!fileVector.isEmpty()){
            sorting(fileVector,  printStream);
        }
    }

    public void fillInf(Path path,
                        Vector<FileOpener> fileVector) throws IOException {

        FileOpener new_fo = new FileOpener();
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
                    if (x.getTopNumber() < minimum.get()) {
                        minimum.getAndSet(x.getTopNumber());
                        idx.getAndSet(fileVector.indexOf(x));
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
}
