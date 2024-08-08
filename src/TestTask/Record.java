package TestTask;

import java.time.LocalDate;

public class Record {
    private final LocalDate date;
    private final String task;

    public Record(String task){
        this.date = LocalDate.now();
        this.task = task;
    }

    public String toString(){
        return date + " " + task;
    }
}
