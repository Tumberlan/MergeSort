import java.io.IOException;

public class mainClass {

    public static void main(String[] args) throws IOException {


        String inputFilesPath = "src\\main\\resources\\inFiles";
        String processFilesPath = "src\\main\\resources\\processFiles";
        String outputFilesPath = "src\\main\\resources\\outFiles";

        int[] idxs = {0,1};
        boolean[] params = {true, true}; // first - lowHigh, second - isInt

        CheckKeys checkKeys = new CheckKeys();

        if(args.length < 1){
            System.out.println("No output file name");
            return;
        }
        if(args.length < 2){
            System.out.println("No input files names");
            return;
        }

        String strcheck1 = args[0];
        String strcheck2 = args[1];
        checkKeys.check(strcheck1,params, idxs);
        checkKeys.check(strcheck2,params, idxs);


        Cleaner cleaner = new Cleaner (processFilesPath);
        cleaner.clean();
        CommandTaker commandTaker = new CommandTaker();
        FileParser fileParser = new FileParser(inputFilesPath, processFilesPath, commandTaker.parseCommand(args,idxs[1]), params[0], params[1]);
        mergeClass mgClass = new mergeClass(outputFilesPath, processFilesPath, args[idxs[0]]);
        fileParser.parse();
        mgClass.mergeSort();
        System.out.println("Look at 'outFiles' directory");
    }
}
