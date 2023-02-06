import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PagesHistoryLoggerTest {
    @Test
    public void changeTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String EXPECTED = """
                Здравствуйте, дорогая Ирина Алексеевна

                За последние сутки во вверенных Вам сайтах произошли следующие изменения:

                Исчезли следующие страницы: https://ru.wikipedia.org/\s
                Появились следующие новые страницы: https://docs.oracle.com/en/java/javase/18/docs/api/index.html\s
                Изменились следующие страницы: https://softaria.ru/\s

                С уважением,
                автоматизированная система
                мониторинга.""";

        String URL_DELETED = "https://ru.wikipedia.org/";
        String HTML_DELETED = """
                <!DOCTYPE html>
                <html class="client-nojs" lang="ru" dir="ltr">
                    <head>
                        <meta charset="UTF-8"/>
                        <title>Википедия — свободная энциклопедия</title>
                    </head>
                    <body class="skin-vector-legacy mediawiki ltr"><div id="mw-page-base" class="noprint"></div>
                    </body>
                </html>""";

        String URL_ADDED = "https://docs.oracle.com/en/java/javase/18/docs/api/index.html";
        String HTML_ADDED = """
                <!DOCTYPE HTML>
                <html lang="en">
                    <head>
                        <!-- Generated by javadoc (18) -->
                        <title>Overview (Java SE 18 &amp; JDK 18)</title>
                        <meta name="viewport" content="width=device-width, initial-scale=1">
                        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                        <meta name="description" content="module index">
                        <meta name="generator" content="javadoc/ModuleIndexWriter">
                    </head>
                    <body class="module-index-page">
                    </body>
                </html>""";

        String URL_MODIFIED = "https://softaria.ru/";
        String HTML_MODIFIED = """
                <!DOCTYPE html>
                <html lang="en">
                    <head>
                        <title>Softaria</title>
                        <meta charset="utf-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1">
                    </head>
                    <body class="page page--theme-main" data-body itescope itemtype="http://schema.org/WebPage">
                    </body>
                </html>""";

        PagesHistoryLogger logger = new PagesHistoryLogger();
        Field[] fields = logger.getClass().getFields();
        for (Field f: fields) {
            f.setAccessible(true);
        }
        Method getHashMapByDayBefore = logger.getClass().getDeclaredMethod("getHistoryDaysBefore", new Class[]{int.class});
        getHashMapByDayBefore.setAccessible(true);
        HashMap<String, String> yesterdayState = (HashMap<String, String>) getHashMapByDayBefore.invoke(logger,1);

        yesterdayState.put(URL_DELETED, HTML_DELETED);
        yesterdayState.put(URL_MODIFIED, HTML_MODIFIED);

        logger.removePage(URL_DELETED);
        logger.addPage(URL_ADDED, HTML_ADDED);
        logger.addPage(URL_MODIFIED, HTML_MODIFIED.replaceFirst("</body>",
                """
                        <b>
                                    Get Fun;)
                                 </b></body>"""));

        String res = MessagesGenerator.getSitesChangesMsg(logger, "Ирина Алексеевна", true);
        System.out.println("Result:\n\n" + res);
        assertEquals(EXPECTED, res);
    }

}