import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Util util = new Util();
        while (true) {
            System.out.print("Введите команду: ");
            String command = in.nextLine();
            switch (command){
                case "#write":
                    System.out.print("Введите ваши планы на сегодня: ");
                    String task = in.nextLine();
                    util.writeToFile(task);
                    break;
                case "#read":
                    util.readFromFile();
                    break;
                case "#search":
                    System.out.print("Введите дату: ");
                    String date = in.nextLine();
                    util.searchByDate(date);
                    break;
                case "#statictics":
                    System.out.println(util.getStatistics());
                    break;
            }
        }
    }
}