import java.io.IOException;

public class mainClass {
    public static void main(String[] args) throws IOException {
        System.out.println("123");
        FileParser fileParser = new FileParser("src\\main\\resources\\inFiles",
                "src\\main\\resources\\processFiles");
        mergeClass mgClass = new mergeClass("src\\main\\resources\\outFiles",
                "src\\main\\resources\\processFiles");

        fileParser.parse();
        mgClass.mergeSort();
    }
}
