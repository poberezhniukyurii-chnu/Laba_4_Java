package task2;

import common.FileIO;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * ЛР4, завдання 2 (варіант 5): посимвольний запис з консолі у файл.
 * Закінчення введення — Ctrl+Z (у Windows: Ctrl+Z, потім Enter; у Linux/macOS — Ctrl+D).
 * Побережнюк Ю.В., група 241б.
 */
public class Main {
    public static void main(String[] args) {
        try (BufferedWriter writer = FileIO.openOutput("Файл для запису")) {
            System.out.println("Вводьте текст. Закінчення введення — Ctrl+Z:");
            int ch;
            long chars = 0, lines = 0;
            while ((ch = FileIO.CONSOLE.read()) != -1) {     // читаємо по одному символу
                if (ch == '\r') continue;                      // кінець рядка запишемо як \n
                writer.write(ch);                              // і по одному символу записуємо
                chars++;
                if (ch == '\n') lines++;
            }
            writer.flush();
            System.out.println("Записано символів: " + chars + ", рядків: " + lines);
        } catch (IOException e) {
            System.out.println("Помилка введення-виведення: " + e.getMessage());
        }
    }
}
