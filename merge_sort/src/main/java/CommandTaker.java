import java.util.Scanner;

public class CommandTaker {

    Scanner scanner = new Scanner(System.in);

    public String parseCommand(String[] args){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 1; i < args.length; i++){
            stringBuilder.append(args[i]).append(";");
        }

        return stringBuilder.toString();

    }

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
