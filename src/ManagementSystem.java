import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class ManagementSystem {
    
    private List<Otdel> otdels;
    private Collection<Sotrudnik> sotrudniks;
    
        // Для шаблона Singletone статическая переменная
    private static ManagementSystem instance;

    // закрытый конструктор
    private ManagementSystem() {
        loadOtdels();
        loadSotrudniks();
    }
    
    // метод getInstance - проверяtт, инициализирована ли статическая
    // переменная (в случае надобности делает это) и возвращает ее
    public static synchronized ManagementSystem getInstance() {
        if (instance == null) {
            instance = new ManagementSystem();
        }
        return instance;
    }

    // Метод, который вызывается при запуске класса
    public static void main(String[] args) {
        // Этот код позволяет нам перенаправить стандартный вывод в файл
        // Т.к. на экран выводится не совсем удобочитаемая кодировка, 
        // файл в данном случае более удобен
        try {
            System.setOut(new PrintStream("out.txt"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return;
        }

        ManagementSystem ms = ManagementSystem.getInstance();

        // Просмотр полного списка групп
        printString("Полный список групп");
        printString("*******************");
        List<Otdel> allOtdels = ms.getOtdels();
        for (Otdel gi : allOtdels) {
            printString(gi);
        }
        printString();

        // Просмотр полного списка студентов
        printString("Полный список студентов");
        printString("***********************");
        Collection<Sotrudnik> allSotrudniks = ms.getAllSotrudniks();
        for (Sotrudnik si : allSotrudniks) {
            printString(si);
        }
        printString();

        // Вывод списков студентов по группам
        printString("Список студентов по группам");
        printString("***************************");
        List<Otdel> otdels = ms.getOtdels();
        // Проверяем все группы
        for (Otdel gi : otdels) {
            printString("---> Группа:" + gi.getNameOtdel());
            // Получаем список студентов для конкретной группы
            Collection<Sotrudnik> sotrudniks = ms.getSotrudnikFromOtdel(gi, 2006);
            for (Sotrudnik si : sotrudniks) {
                printString(si);
            }
        }
        printString();

        // Создадим нового студента и добавим его в список
        Sotrudnik s = new Sotrudnik();
        s.setPersonID(5);
        s.setPersonName("Игорь");
        s.setPersonPatronymicName("Владимирович");
        s.setPersonSurName("Перебежкин");
        s.setSex('М');
        Calendar c = Calendar.getInstance();
        c.set(1991, 8, 31);
        s.setDateOfBirth(c.getTime());
        s.setOtdelID(1);
        s.setEducationYear(2006);
        printString("Добавление студента: ");
        printString("       " + s);
        printString("********************");
        ms.insertSotrudnik(s);
        printString("--->> Полный список студентов после добавления: ");
        allSotrudniks = ms.getAllSotrudniks();
        for (Sotrudnik si : allSotrudniks) {
            printString(si);
        }
        printString();

        // Изменим данные о студенте - Перебежкин станет у нас Новоперебежкиным
        // Но все остальное будет таким же - создаем студента с таким же ИД
        s = new Sotrudnik();
        s.setPersonID(5);
        s.setPersonName("Игорь");
        s.setPersonPatronymicName("Владимирович");
        s.setPersonSurName("Новоперебежкин");
        s.setSex('М');
        c = Calendar.getInstance();
        c.set(1991, 8, 31);
        s.setDateOfBirth(c.getTime());
        s.setOtdelID(1);
        s.setEducationYear(2006);
        printString("Редактирование данных студента: ");
        printString("       " + s);
        printString("*******************************");
        ms.updateSotrudnik(s);
        printString("--->> Полный список студентов после редактирования ");
        allSotrudniks = ms.getAllSotrudniks();
        for (Sotrudnik si : allSotrudniks) {
            printString(si);
        }
        printString();

        // Удалим нашего студента
        printString("Удаление студента: ");
        printString("       " + s);
        printString("******************");
        ms.deleteSotrudnik(s);
        printString("--->> Полный список студентов после удаления ");
        allSotrudniks = ms.getAllSotrudniks();
        for (Sotrudnik si : allSotrudniks) {
            printString(si);
        }
        printString();

        // Здесь мы переводим всех студентов одной группы в другую
        // Мы знаем, что у нас 2 группы
        // Не совсем элегантное решение, но пока сделаем так
        Otdel g1 = otdels.get(0);
        Otdel g2 = otdels.get(1);
        printString("Перевод студентов из 1-ой во 2-ю группу ");
        printString("***************************************");
        ms.moveSotrudniksToGroup(g1, 2006, g2, 2007);
        printString("--->> Полный список студентов после перевода");
        allSotrudniks = ms.getAllSotrudniks();
        for (Sotrudnik si : allSotrudniks) {
            printString(si);
        }
        printString();

        // Удаляем студентов из группы
        printString("Удаление студентов из группы: ");
        printString("       " + g2 + " в 2006 году");
        printString("*****************************");
        ms.removeSotrudniksFromOtdel(g2, 2006);
        printString("--->> Полный список студентов после удаления");
        allSotrudniks = ms.getAllSotrudniks();
        for (Iterator i = allSotrudniks.iterator(); i.hasNext();) {
            printString(i.next());
        }
        printString();
    }

    // Метод создает две группы и помещает их в коллекцию для групп
    public void loadOtdels() {
        // Проверяем - может быть наш список еще не создан вообще
        if (otdels == null) {
            otdels = new ArrayList<Otdel>();
        } else {
            otdels.clear();
        }
        Otdel g = null;

        g = new Otdel();
        g.setOtdelID(1);
        g.setNameOtdel("Первая");
        g.setBoss("Доктор Борменталь");
        g.setSpeciality("Создание собачек из человеков");
        otdels.add(g);

        g = new Otdel();
        g.setOtdelID(2);
        g.setNameOtdel("Вторая");
        g.setBoss("Профессор Преображенский");
        g.setSpeciality("Создание человеков из собачек");
        otdels.add(g);
    }

    // Метод создает несколько студентов и помещает их в коллекцию
    public void loadSotrudniks() {
        if (sotrudniks == null) {
            // Мы используем коллекцию, которая автоматически сортирует свои элементы
            sotrudniks = new TreeSet<Sotrudnik>();
        } else {
            sotrudniks.clear();
        }

        Sotrudnik s = null;
        Calendar c = Calendar.getInstance();

        // Вторая группа
        s = new Sotrudnik();
        s.setPersonID(1);
        s.setPersonName("Иван");
        s.setPersonPatronymicName("Сергеевич");
        s.setPersonSurName("Степанов");
        s.setSex('М');
        c.set(1990, 3, 20);
        s.setDateOfBirth(c.getTime());
        s.setOtdelID(2);
        s.setEducationYear(2006);
        sotrudniks.add(s);

        s = new Sotrudnik();
        s.setPersonID(2);
        s.setPersonName("Наталья");
        s.setPersonPatronymicName("Андреевна");
        s.setPersonSurName("Чичикова");
        s.setSex('Ж');
        c.set(1990, 6, 10);
        s.setDateOfBirth(c.getTime());
        s.setOtdelID(2);
        s.setEducationYear(2006);
        sotrudniks.add(s);

        // Первая группа
        s = new Sotrudnik();
        s.setPersonID(3);
        s.setPersonName("Петр");
        s.setPersonPatronymicName("Викторович");
        s.setPersonSurName("Сушкин");
        s.setSex('М');
        c.set(1991, 3, 12);
        s.setDateOfBirth(c.getTime());
        s.setEducationYear(2006);
        s.setOtdelID(1);
        sotrudniks.add(s);

        s = new Sotrudnik();
        s.setPersonID(4);
        s.setPersonName("Вероника");
        s.setPersonPatronymicName("Сергеевна");
        s.setPersonSurName("Ковалева");
        s.setSex('Ж');
        c.set(1991, 7, 19);
        s.setDateOfBirth(c.getTime());
        s.setEducationYear(2006);
        s.setOtdelID(1);
        sotrudniks.add(s);
    }

    // Получить список групп
    public List<Otdel> getOtdels() {
        return otdels;
    }

    // Получить список всех студентов
    public Collection<Sotrudnik> getAllSotrudniks() {
        return sotrudniks;
    }

    // Получить список студентов для определенной группы
    public Collection<Sotrudnik> getSotrudnikFromOtdel(Otdel otdel, int year) {
        Collection<Sotrudnik> l = new TreeSet<Sotrudnik>();
        for (Sotrudnik si : sotrudniks) {
            if (si.getOtdelID() == otdel.getOtdelID() && si.getEducationYear() == year) {
                l.add(si);
            }
        }
        return l;
    }

    // Перевести студентов из одной группы с одним годом обучения в другую группу с другим годом обучения
    public void moveSotrudniksToGroup(Otdel oldOtdel, int oldYear, Otdel newOtdel, int newYear) {
        for (Sotrudnik si : sotrudniks) {
            if (si.getOtdelID() == oldOtdel.getOtdelID() && si.getEducationYear() == oldYear) {
                si.setOtdelID(newOtdel.getOtdelID());
                si.setEducationYear(newYear);
            }
        }
    }

    // Удалить всех студентов из определенной группы
    public void removeSotrudniksFromOtdel(Otdel otdel, int year) {
        // Мы создадим новый список студентов БЕЗ тех, кого мы хотим удалить.
        // Возможно не самый интересный вариант. Можно было бы продемонстрировать
        // более элегантный метод, но он требует погрузиться в коллекции более глубоко
        // Здесь мы не ставим себе такую цель
        Collection<Sotrudnik> tmp = new TreeSet<Sotrudnik>();
        for (Sotrudnik si : sotrudniks) {
            if (si.getOtdelID() != otdel.getOtdelID() || si.getEducationYear() != year) {
                tmp.add(si);
            }
        }
        sotrudniks = tmp;
    }

    // Добавить студента
    public void insertSotrudnik(Sotrudnik sotrudnik) {
        // Просто добавляем объект в коллекцию
        sotrudniks.add(sotrudnik);
    }

    // Обновить данные о студенте
    public void updateSotrudnik(Sotrudnik sotrudnik) {
        // Надо найти нужного студента (по его ИД) и заменить поля
        Sotrudnik updStudent = null;
        for (Sotrudnik si : sotrudniks) {
            if (si.getPersonID() == sotrudnik.getPersonID()) {
                // Вот этот студент - запоминаем его и прекращаем цикл
                updStudent = si;
                break;
            }
        }
        updStudent.setPersonName(sotrudnik.getPersonName());
        updStudent.setPersonPatronymicName(sotrudnik.getPersonPatronymicName());
        updStudent.setPersonSurName(sotrudnik.getPersonSurName());
        updStudent.setSex(sotrudnik.getSex());
        updStudent.setDateOfBirth(sotrudnik.getDateOfBirth());
        updStudent.setOtdelID(sotrudnik.getOtdelID());
        updStudent.setEducationYear(sotrudnik.getEducationYear());
    }

    // Удалить студента
    public void deleteSotrudnik(Sotrudnik sotrudnik) {
        // Надо найти нужного студента (по его ИД) и удалить
        Sotrudnik delSotrudnik = null;
        for (Sotrudnik si : sotrudniks) {
            if (si.getPersonID() == sotrudnik.getPersonID()) {
                // Вот этот студент - запоминаем его и прекращаем цикл
                delSotrudnik = si;
                break;
            }
        }
        sotrudniks.remove(delSotrudnik);
    }

    // Этот код позволяет нам изменить кодировку
    // Такое может произойти если используется IDE - например NetBeans.
    // Тогда вы получаете просто одни вопросы, что крайне неудобно читать
    public static void printString(Object s) {
        //System.out.println(s.toString());
        try {
            System.out.println(new String(s.toString().getBytes("windows-1251"), "windows-1251"));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    public static void printString() {
        System.out.println();
    }
}   
    
