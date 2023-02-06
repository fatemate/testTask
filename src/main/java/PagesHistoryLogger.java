import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

public class PagesHistoryLogger {
    private int historyDaysDeep = 2;
    private Calendar calendar;
    private HashMap<String, String>[] maps = new HashMap[historyDaysDeep];

    public PagesHistoryLogger() {

    }

    public PagesHistoryLogger(TimeZone timezone) {

    }

    public void addPage(String url, String html) {

    }

    public void removePage(String url) {

    }

    public ArrayList<String> getDifferencePages(int dayBeforeA, int dayBeforeB) {
        return null;
    }


    public ArrayList<String> getRemovedPages() {
        return null;
    }


    public ArrayList<String> getRemovedPages(int dayBeforeA, int dayBeforeB) {
        return null;
    }


    public ArrayList<String> getNewPages() {
        return null;
    }


    public ArrayList<String> getNewPages(int dayBeforeA, int dayBeforeB) {
        return null;
    }

    public ArrayList<String> getChangedPages(int dayBeforeA, int dayBeforeB) {
        return null;
    }

    public ArrayList<String> getChangedPages() {
        return null;
    }

    private HashMap<String, String> getHistoryDaysBefore(int numDaysBefore) {
        return maps[getIndexHistoryDaysBefore(numDaysBefore)];
    }

    private int getIndexHistoryDaysBefore(int numDaysBefore) {
        return 0;
    }

    private void updateCalendar() {

    }
}
