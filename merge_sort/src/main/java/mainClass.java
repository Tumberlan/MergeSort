import java.io.IOException;

public class mainClass {

    public static void main(String[] args) throws IOException {


        String inputFilesPath = "src\\main\\resources\\inFiles";
        String processFilesPath = "src\\main\\resources\\processFiles";
        String outputFilesPath = "src\\main\\resources\\outFiles";

        int[] idxs = {0,1};
        boolean[] params = {true, true, false}; // first - lowHigh, second - isInt

        CheckKeys checkKeys = new CheckKeys();

        if(args.length < 2){
            System.out.println("wrong arguments");
            return;
        }

        String strcheck1 = args[0];
        String strcheck2 = args[1];
        checkKeys.check(strcheck1,params, idxs);
        checkKeys.check(strcheck2,params, idxs);

        if(!params[2]){
            System.out.println("Please choose key '-s' or '-i'");
            return;
        }
        if(args.length < 3){
            System.out.println("few arguments");
            return;
        }
        if(args[idxs[0]].split("\\.").length<2){
            System.out.println("Wrong file names");
            return;
        }
        Cleaner cleaner = new Cleaner (processFilesPath);
        cleaner.clean();
        CommandTaker commandTaker = new CommandTaker();
        FileParser fileParser = new FileParser(inputFilesPath, processFilesPath, commandTaker.parseCommand(args,idxs[1]), params[0], params[1]);
        mergeClass mgClass = new mergeClass(outputFilesPath, processFilesPath, args[idxs[0]], params[0], params[1]);
        fileParser.parse();
        mgClass.sort();
        System.out.println("Look at 'src/main/resources/outFiles' directory");
    }
}
