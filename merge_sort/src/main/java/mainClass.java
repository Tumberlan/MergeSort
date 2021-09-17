import java.io.IOException;

public class mainClass {

    public static void main(String[] args) throws IOException {
        String inputFilesPath = "src\\main\\resources\\inFiles";
        String processFilesPath = "src\\main\\resources\\processFiles";
        String outputFilesPath = "src\\main\\resources\\outFiles";
        Cleaner cleaner = new Cleaner (processFilesPath);
        cleaner.clean();
        if(args.length < 1){
            System.out.println("No output file name");
            return;
        }
        if(args.length < 2){
            System.out.println("No input files names");
            return;
        }
        CommandTaker commandTaker = new CommandTaker();
        FileParser fileParser = new FileParser(inputFilesPath,processFilesPath, commandTaker.parseCommand(args));
        mergeClass mgClass = new mergeClass(outputFilesPath, processFilesPath, args[0]);
        fileParser.parse();
        mgClass.mergeSort();
        System.out.println("Look at 'outFiles' directory");
    }
}
