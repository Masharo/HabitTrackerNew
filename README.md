# HabitTracker

### [Задача 1](../../tree/task1) [17.03.2022] <img src="/img/y.png" width="25">

> Собрать приложение, содержащее два экрана:
>
> * На первом экране список, каждый элемент которого имеет название, описание, приоритет, тип, периодичность и цвет.
> * Изначально список пустой. Должна быть кнопка добавления нового элемента (элемента FAB), нажатие на которую открывает новый экран.
> * На экране создания/редактирования привычки добавить Color Picker

### [Задача 2](../../tree/task2) [24.03.2022] <img src="/img/y.png" width="25">

> * Перевести экраны приложения на фрагменты
> * Добавить боковую навигацию с экранами: домашний экран, экран информации о приложении
> * Для экрана списка привычек сделать экран с ВьюПейджером для переключения между хорошими и плохими привычками

### [Задача 3](../../tree/task3) [07.04.2022] <img src="/img/y.png" width="25">

> * Завести 2 AndroidX ViewModel - одну для списка, другую для экрана создания/редактирования
> * Вынести хранение привычки в некую модель, устройство придумайте сами, но данные не должны лежать в Activity/Fragment
> * Сделать Bottom sheet с фрагментом, в котором будет фильтрация по названию (т.е. поиск), сортировка по дате создания и/или другие фильтрации и сортировки.
> Этот фрагмент должен использовать ту же ViewModel, что и фрагмент со списком.



### [Задача 4](../../tree/task4) [21.04.2022] <img src="/img/y.png" width="25">

> * Создать базу данных на Room
> * Вынести хранение привычек в эту базу
> * Для получения данных использовать LiveData
> * Используйте allowMainThreadQueries() при создании базы

### [Задача 5](../../tree/task5) [28.04.2022] <img src="/img/y.png" width="25">

> * Усовершенствовать ваше приложение, сделав сохранение в базу данных в параллельном потоке с помощью корутин (для чтения из базы оставить использование лайв даты)

### [Задача 6](../../tree/task6) [12.05.2022] <img src="/img/y.png" width="25">

> Реализовать механизм выгрузки данных из сервера в локальную базу и
> наоборот.
>
> Описание бэкенда https://doublet.app/droid/8/api
> Команда для телеграм-бота @DoubleDroidStudentBot для генерации токена для сервера: /generate_server_token
>
> Бонус:
> * В случае ошибки необходимо повторить попытку через определенный
> промежуток времени, до тех пор пока не выполниться запрос.
> * В боковом меню, в шапке, добавить аватарку, загружаемую с интернета.
> Добавить placeholders на предзагрузку и на случай ошибки. Фото
> должно быть в круглой форме.

### Задача 7 [26.05.2022] <img src="/img/y.png" width="25">

> Перевести проект на использование Clean Architecture:
>
> 1. Сделать в проекте 3 модуля;
> 2. Связывать их через Dagger;
> 3. Прокидывать данные внутри приложения через корутины и Flow;
> 4. Реализовать логику учета следованию привычкам:
>       * На элементе в списке привычек добавить кнопку выполнения
>   действия привычки;
>       * У плохих привычек, если их выполняли за указанный период менее
>   часто, чем можно, при нажатии выводите в Toast “Можете выполнить
>   еще столько-то раз”, если больше - выводите “Хватит это делать”
>       * Для хорошей привычки аналогично выводите “Стоит выполнить еще
>   столько то раз” если выполнили меньше за указанный период, иначе
>   выводите “You are breathtaking!”
>
> 5. На доп. балл: сделать субкомпонент и инжектить ViewModel во фрагмент.

### Задача 8 [02.06.2022] <img src="/img/n.png" width="25">

> * Написать Unit тесты для одного из модулей приложения
> * Написать Ui тест для одного из экранов
