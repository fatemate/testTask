public class MessagesGenerator {
    public static String getSitesChangesMsg(PagesHistoryLogger logger, String name, boolean isFemale) {
        String res;

        if (isFemale)
            res = "Здравствуйте, дорогая " + name;
        else
            res = "Здравствуйте, дорогой " + name;

        res += "\n\n" +
                "За последние сутки во вверенных Вам сайтах произошли следующие изменения:" +
                "\n\n";

        res += "Исчезли следующие страницы: ";
        for (String url: logger.getRemovedPages())
            res += url + " ";

        res += "\n" +
                "Появились следующие новые страницы: ";
        for (String url: logger.getNewPages())
            res += url + " ";

        res += "\n" +
                "Изменились следующие страницы: ";
        for (String url: logger.getChangedPages())
            res += url + " ";

        res += "\n\n" +
                "С уважением,\n" +
                "автоматизированная система\n" +
                "мониторинга.";

        return res;
    }
}
