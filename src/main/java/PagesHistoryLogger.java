import java.util.*;

public class PagesHistoryLogger {
    private int historyDaysDeep = 2;
    private Calendar calendar;
    private HashMap<String, String>[] maps = new HashMap[historyDaysDeep];


    /**
     *   The default constructor uses a TimeZone based on the time zone where the program is running.
     * */
    public PagesHistoryLogger() {
        this(TimeZone.getDefault());
    }

    public PagesHistoryLogger(TimeZone timezone) {
        for (int i = 0; i < historyDaysDeep; i++)
            maps[i] = new HashMap<String, String>();
        this.calendar = Calendar.getInstance(timezone);
    }


    /**
     * Adds a new page or replaces a page with the given key (url).
     */
    public void addPage(String url, String html) {
        updateCalendar();
        getHistoryDaysBefore(0).put(url, html);
    }


    /**
     * Removes the page for the given key (url) if present.
     */
    public void removePage(String url) {
        updateCalendar();
        getHistoryDaysBefore(0).remove(url);
    }


    /**
     * Returns the difference between page sets A and B (A\B).
     * Set A is the set of pages {dayBeforeA} days before today.
     * Set B is the set of pages {dayBeforeB} days before today.
     * If you swap elements, you can get both new and deleted pages.
     */
    public ArrayList<String> getDifferencePages(int dayBeforeA, int dayBeforeB) {
        ArrayList<String> difference = new ArrayList<String>();
        HashMap<String, String> setA = getHistoryDaysBefore(dayBeforeA);

        for (String s: getHistoryDaysBefore(dayBeforeB).keySet())
            if (!setA.containsKey(s))
                difference.add(s);

        return difference;
    }


    public ArrayList<String> getRemovedPages() {
        return getDifferencePages(0, 1);
    }


    public ArrayList<String> getRemovedPages(int dayBeforeA, int dayBeforeB) {
        if (dayBeforeA < dayBeforeB)
            return getDifferencePages(dayBeforeA, dayBeforeB);
        else
            return getDifferencePages(dayBeforeB, dayBeforeA);
    }


    public ArrayList<String> getNewPages() {
        return getDifferencePages(1, 0);
    }


    public ArrayList<String> getNewPages(int dayBeforeA, int dayBeforeB) {
        if (dayBeforeA < dayBeforeB)
            return getDifferencePages(dayBeforeB, dayBeforeA);
        else
            return getDifferencePages(dayBeforeA, dayBeforeB);
    }


    /**
     * Returns the URLs of pages whose content has changed.
     * The sequence of the arguments doesn't matter.
     */
    public ArrayList<String> getChangedPages(int dayBeforeA, int dayBeforeB) {
        ArrayList<String> changedPages = new ArrayList<String>();
        HashMap<String, String> stateA = getHistoryDaysBefore(dayBeforeA);

        for (Map.Entry<String, String> entry: getHistoryDaysBefore(dayBeforeB).entrySet())
            if (stateA.containsKey(entry.getKey())) {
                if (!entry.getValue().equals(stateA.get(entry.getKey())))
                    changedPages.add(entry.getKey());
            }

        return changedPages;
    }

    public ArrayList<String> getChangedPages() {
        return getChangedPages(0, 1);
    }


    /**
     *   Returns a HashMap that contains the condition of pages for certain day until today.
     *   If the argument is 0, than function will return the condition of pages for today.
     *   The argument must be between 0 and historyDaysDeep otherwise the function will throw an IllegalArgumentException.
     * */
    private HashMap<String, String> getHistoryDaysBefore(int numDaysBefore) {
        return maps[getIndexHistoryDaysBefore(numDaysBefore)];
    }


    /**
     *   Returns the index in a circular buffer (holding the page states for a specific day), corresponding to a specific day up to today.
     *   If the argument is 0, then the function will return the index of the element corresponding to today.
     *   The argument must be between 0 and historyDaysDeep otherwise the function will throw an IllegalArgumentException.
     * */
    private int getIndexHistoryDaysBefore(int numDaysBefore) {
        if (numDaysBefore > historyDaysDeep || numDaysBefore < 0)
            throw new IllegalArgumentException("Out of history deep range!\n"
                    + "HISTORY_DEEP range: [ 0; "
                    + historyDaysDeep
                    + "], argument: "
                    + numDaysBefore);
        return (((calendar.get(Calendar.DAY_OF_YEAR) % historyDaysDeep) - numDaysBefore) + historyDaysDeep) % historyDaysDeep;
    }

    private void updateCalendar() {
        calendar.setTimeInMillis(System.currentTimeMillis());
    }
}
