public class Statistic {
    private final Integer countRecord;
    private final Integer countChars;
    private final String mostActiveDay;

    public Statistic(Integer countRecord, Integer countChars, String mostActiveDay) {
        this.countRecord = countRecord;
        this.countChars = countChars;
        this.mostActiveDay = mostActiveDay;
    }
    @Override
    public String toString() {
        return "Кол-во записей: " + countRecord + "\n" +
                "Кол-во символов в контенте: " + countChars + "\n" +
                "Самый активный день по записям: " + mostActiveDay;
    }
}
