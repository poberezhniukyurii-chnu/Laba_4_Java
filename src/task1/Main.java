package task1;

import common.FileIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.text.Collator;
import java.util.*;

/**
 * ЛР4, завдання 1 (варіант 15): вилучити повторення рядків, відсортувати рядки
 * в алфавітному порядку, вивести результат у файл, який задає користувач.
 * Побережнюк Ю.В., група 241б.
 */
public class Main {
    public static void main(String[] args) {
        try {
            List<String> lines = new ArrayList<>();          // стандартний контейнер для рядків файлу
            try (BufferedReader reader = FileIO.openInput("Вхідний файл")) {
                String line;
                while ((line = reader.readLine()) != null) {  // послідовне читання
                    lines.add(line);
                }
            }
            System.out.println("Прочитано рядків: " + lines.size());

            // LinkedHashSet зберігає лише перше входження кожного рядка
            List<String> unique = new ArrayList<>(new LinkedHashSet<>(lines));
            System.out.println("Вилучено повторень: " + (lines.size() - unique.size()));

            // Collator з українською локаллю впорядковує за абеткою (Є, І, Ї на своїх місцях)
            Collator collator = Collator.getInstance(new Locale("uk", "UA"));
            Collections.sort(unique, collator);

            try (BufferedWriter writer = FileIO.openOutput("Вихідний файл")) {
                for (String s : unique) {
                    writer.write(s);
                    writer.newLine();
                }
            }
            System.out.println("Записано рядків: " + unique.size());
            System.out.println("Результат: " + unique);
        } catch (IOException e) {
            System.out.println("Помилка введення-виведення: " + e.getMessage());
        }
    }
}
