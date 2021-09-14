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
        System.out.println(filesCounter.get() + " files");
        FileOpener[] fileOpeners = new FileOpener[filesCounter.get()];
        AtomicInteger fileIdx = new AtomicInteger();
        Vector<FileOpener> fileVector = new Vector<>();
        System.out.println("Vector added");
        paths.forEach(x->{
            try {
                fillInf(x, fileOpeners,fileIdx, fileVector);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Vector updated");
        String tmp_file_name = "resultfile.txt";
        String relativePath = "src\\main\\resources\\outFiles"
                + fileSeparator + tmp_file_name;
        File result_file = new File(relativePath);
        System.out.println("Ready to sort");
        System.out.println("Vector got " + fileVector.size() + " elements");
        FileOutputStream fos = new FileOutputStream(result_file);
        PrintStream printStream = new PrintStream(fos);
        while(!fileVector.isEmpty()){
            sorting(fileOpeners, fileVector, result_file, printStream);
        }

    }

    public void fillInf(Path path, FileOpener[] fileOpeners, AtomicInteger idx,
                        Vector<FileOpener> fileVector) throws IOException {
        //fileOpeners[idx.get()].setFile(new File(pathToTmpDirectory, path.getFileName().toString()));
        //idx.getAndIncrement();
        FileOpener new_fo = new FileOpener();
        new_fo.setFile(new File(pathToTmpDirectory, path.getFileName().toString()));
        System.out.println("filename: " + path.getFileName().toString());
        fileVector.add(new_fo);
    }

    public void sorting(FileOpener[] fileOpeners, Vector<FileOpener> fileVector,
                        File outFile, PrintStream printStream) throws IOException {

        boolean[] isEmpty = new boolean[fileVector.size()];
        for(int i = 0; i < fileVector.size(); i++){
            isEmpty[i] = false;
        }
        AtomicInteger minimum = new AtomicInteger();
        AtomicBoolean firstTry = new AtomicBoolean(true);
        AtomicInteger idx = new AtomicInteger();
        System.out.println("size: " + fileVector.size());
        fileVector.forEach(x->{
            System.out.println("Top number: " + x.getTopNumber());
            Integer new_number = x.getTopNumber();
            System.out.println("Top number2: " + new_number);
            if(new_number == null){
                System.out.println("idx: " + fileVector.indexOf(x)+"  GAY");
                isEmpty[fileVector.indexOf(x)] = true;
            }else{
                System.out.println("try: " + firstTry.get());
                if (firstTry.get()) {
                    System.out.println("idx: " + fileVector.indexOf(x)+ " ASS");
                    System.out.println("top number: " + x.getTopNumber() +
                            "; minimum: " + minimum.get());
                    minimum.getAndSet(x.getTopNumber());
                    System.out.println(x.getTopNumber() + " " + fileVector.size());
                    firstTry.getAndSet(false);
                    idx.getAndSet(fileVector.indexOf(x));
                    System.out.println("idx: " +  idx.get());
                } else {
                    System.out.println("top number: " + x.getTopNumber() +
                            "; minimum: " + minimum.get());
                    if (x.getTopNumber() < minimum.get()) {
                        System.out.println("idx: " + fileVector.indexOf(x)+ " SHIT");
                        minimum.getAndSet(x.getTopNumber());
                        idx.getAndSet(fileVector.indexOf(x));
                    }
                }
            }
        });

        //System.out.println(minimum.get());

        System.out.println("EMPTY: ");
        for(int i = 0; i < fileVector.size(); i++){
            System.out.println(isEmpty[i]);
        }
        System.out.println("Last idx: " + idx.get());
        fileVector.get(idx.get()).readNextNumber();
        System.out.println(fileVector.get(idx.get()).getTopNumber());

        for(int i = 0; i < fileVector.size(); i++){
            if(isEmpty[i]){
                fileVector.remove(i);
            }
        }


        if(!firstTry.get()) {
            printStream.println(minimum.get());
        }
        /*
        boolean isChanged = false;
        boolean firstTry = true;
        boolean[] isEmpty = new boolean[fileOpeners.length];
        for(int i = 0; i < fileOpeners.length; i++){
            isEmpty[i] = false;
        }
        int min_number = 0;
        for(int i = 0; i < fileOpeners.length; i++){
            Integer newNumber = fileOpeners[i].getTopNumber();
            if(firstTry)
            if(fileOpeners[i].getTopNumber() < min_number){
                min_number = fileOpeners[i].getTopNumber();
                isChanged = true;
            }
        }
        if(!isChanged){
            fileOpeners[0].readNextNumber();
        }*/
    }
}
