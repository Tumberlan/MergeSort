import java.util.Scanner;

public class CommandTaker {

    Scanner scanner = new Scanner(System.in);
    public String takeCommand(){
        System.out.println("Please type file names separated by commas without spaces");
        StringBuilder stringBuilder = new StringBuilder();
        String command = scanner.nextLine();
        while (!command.equals("end")){
            String[] sub_str = command.split(",");
            for(int i = 0; i < sub_str.length; i++){
                stringBuilder.append(sub_str[i]).append(";");
            }
            command = scanner.nextLine();
        }
        return stringBuilder.toString();
    }
}
