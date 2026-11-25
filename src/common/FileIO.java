package common;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Спільні засоби введення-виведення для завдань ЛР4 (виконання «додаткових зауважень»):
 * шлях до файлу вводиться з консолі; при відсутності вхідного файлу — повідомлення і повторний
 * запит; при відсутності вихідного — запит на створення; для наявного вихідного файлу — вибір
 * між перезаписом і дописуванням у кінець; консольний і файловий ввід-вивід буферизовані.
 * Побережнюк Ю.В., група 241б.
 */
public final class FileIO {
    /** Буферизоване введення з консолі. */
    public static final BufferedReader CONSOLE = new BufferedReader(new InputStreamReader(System.in));

    private FileIO() {
    }

    /** Виводить запит і читає рядок з консолі (null — кінець введення). */
    public static String ask(String prompt) throws IOException {
        System.out.print(prompt);
        System.out.flush();
        String line = CONSOLE.readLine();
        return line == null ? null : line.trim();
    }

    /** Відкриває існуючий текстовий файл для послідовного читання, повторюючи запит,
     *  доки не буде введено шлях до наявного файлу. */
    public static BufferedReader openInput(String title) throws IOException {
        while (true) {
            String path = ask(title + " — введіть шлях до файлу: ");
            if (path == null) throw new EOFException("введення перервано");
            File f = new File(path);
            if (f.isFile()) {
                return new BufferedReader(
                        new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8));
            }
            System.out.println("Файл \"" + path + "\" не знайдено. Спробуйте ще раз.");
        }
    }

    /** Відкриває файл для запису: пропонує створити відсутній файл, а для існуючого —
     *  перезаписати його або дописати в кінець. */
    public static BufferedWriter openOutput(String title) throws IOException {
        while (true) {
            String path = ask(title + " — введіть шлях до файлу: ");
            if (path == null) throw new EOFException("введення перервано");
            File f = new File(path);
            boolean append = false;
            if (!f.exists()) {
                String ans = ask("Файл \"" + path + "\" не існує. Створити його? (т/н): ");
                if (ans == null || !ans.toLowerCase().startsWith("т")) {
                    continue;                                  // повторний запит шляху
                }
            } else if (f.isDirectory()) {
                System.out.println("\"" + path + "\" — це каталог, а не файл.");
                continue;
            } else {
                String ans = ask("Файл існує. 1 — перезаписати, 2 — дописати в кінець: ");
                append = "2".equals(ans);
            }
            System.out.println(append ? "Дані буде дописано в кінець файлу."
                                      : "Файл буде записано заново.");
            return new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(f, append), StandardCharsets.UTF_8));
        }
    }
}
