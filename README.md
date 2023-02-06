# Тестовое задание
## Техническое задание
Есть две хэш таблицы.
Ключами являются URLы. Значениями - html код страниц, находящихся по этим урлам.
Обе таблицы отражают состояние всех страниц некоторого заданного множества веб сайтов.
Первая таблица - состояние этих сайтов на вчера. Вторая - на сегодня.

Задача - написать код который бы, пользуясь этими таблицами составил
письмо следующего формата:

>Здравствуйте, дорогая и.о. секретаря
>
>За последние сутки во вверенных Вам сайтах произошли следующие изменения:
>
>Исчезли следующие страницы:{здесь список урлов}
>Появились следующие новые страницы {здесь список урлов}
>Изменились следующие страницы {здесь список урлов}
>
>С уважением,
>автоматизированная система
>мониторинга.

## Уточнения
Для начала стоит выяснить с помощью каких конкретно классов должны хранится ключи и значения хэш-таблицы. Например java.net.URL для ключей? Или что-то другое? В общем для упрощения были использованы банальные строки как для ключа, так и для значения.
Также было бы неплохо узнать какие есть ограничения по памяти и быстродействию (что важнее для данной задачи).

Вообще для решения подобной задачи было бы эффективнее хранить не полный "слепок" страниц за предыдущий день, а вести историю изминений. Это потребовало бы меньше памяти для хранения (не было бы дублирования кода страниц) и облегчило бы выполнение поставленной задачи. Достаточно было бы пройти по списку изминений и сформировать сообщение.