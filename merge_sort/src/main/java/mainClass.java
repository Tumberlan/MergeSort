import java.io.IOException;

public class mainClass {

    public static void main(String[] args) throws IOException {
        String inputFilesPath = "src\\main\\resources\\inFiles";
        String processFilesPath = "src\\main\\resources\\processFiles";
        String outputFilesPath = "src\\main\\resources\\outFiles";
        Cleaner cleaner = new Cleaner (processFilesPath);
        cleaner.clean();
        CommandTaker commandTaker = new CommandTaker();
        FileParser fileParser = new FileParser(inputFilesPath,processFilesPath, commandTaker.takeCommand());
        mergeClass mgClass = new mergeClass(outputFilesPath, processFilesPath);
        fileParser.parse();
        mgClass.mergeSort();
        System.out.println("Look at 'outFiles' directory");
    }
}
