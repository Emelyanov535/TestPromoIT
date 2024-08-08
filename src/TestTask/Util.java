package TestTask;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Util {
    private final Crypto crypto = new Crypto();
    public void writeToFile(String task){
        Record record = new Record(task);
        String encrypted = crypto.encrypt(record.toString());
        try (FileWriter writer = new FileWriter("data.txt", true)) {
            writer.write(encrypted + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void readFromFile(){
        try(BufferedReader reader = new BufferedReader (new FileReader("data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = crypto.decrypt(line);
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void searchByDate(String date){
        try(BufferedReader reader = new BufferedReader (new FileReader("data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = crypto.decrypt(line);
                if(getDateFromRecord(line).equals(date)){
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getStatistics(){
        int countRecord = 0;
        int countChars = 0;
        Map<String, Integer> dateCounts = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = crypto.decrypt(line);
                countRecord++;
                countChars += line.length();
                String date = getDateFromRecord(line);

                dateCounts.put(date, dateCounts.getOrDefault(date, 0) + 1);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        String mostActiveDay = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : dateCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostActiveDay = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        Statistic statistic = new Statistic(countRecord, countChars, mostActiveDay);
        return statistic.toString();
    }

    private String getDateFromRecord(String line){
        return line.substring(0, line.indexOf(' '));
    }
}
