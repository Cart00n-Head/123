import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Игра Быки-Коровы");

        int gameNumber = 1;
        Date currentDate = new Date();

        try {
            File gameFile = new File("game.txt");
            if (gameFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(gameFile));
                String line;

                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Game №")) {
                        gameNumber = Integer.parseInt(line.substring(7, 8)) + 1;
                    }
                }
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        String answer = generateAnswer();
        boolean guessed = false;
        int guessCount = 0;

        while (!guessed) {

            System.out.print("Введите строку из четырех цифр: ");
            String guess = scanner.nextLine();
            guessCount++;

            if (!guess.matches("\\d{4}")) {
                System.out.println("Некорректный формат ввода!");
                continue;
            }

            int bulls = 0;
            int cows = 0;

            for (int i = 0; i < guess.length(); i++) {
                char c = guess.charAt(i);
                if (answer.indexOf(c) != -1) {
                    if (answer.charAt(i) == c) {
                        bulls++;
                    } else {
                        cows++;
                    }
                }
            }

            System.out.println(bulls + " бык" + (bulls == 1 ? "" : "а") + " " + cows + " коров" + (cows == 1 ? "а" : "ы"));

            try {
                FileWriter writer = new FileWriter("game.txt", true);
                if (guessCount == 1) {
                    writer.write("Game №" + gameNumber + " " + currentDate + " " + "Загаданная строка: " + answer + "\n");
                }
                writer.write("Запрос: " + guess + " Ответ: " + bulls + " " + (bulls == 1 ? "бык" : "быка") + " " + cows
                        + " " + (cows == 1 ? "корова" : "коровы") + "\n");
                if (bulls == 4) {
                    writer.write("Строка была угадана за " + guessCount + " попыт" + (guessCount == 1 ? "ку" : "ки") + "\n\n");
                }
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (bulls == 4) {
                System.out.println("Поздравляем, вы выиграли!");
                System.out.println("Количество попыток: " + guessCount);
                guessed = true;
            }
        }
        scanner.close();
    }

    public static String generateAnswer() {
        List<Integer> digits = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            digits.add(i);
        }
        Collections.shuffle(digits);
        String answer = "";
        for (int i = 0; i < 4; i++) {
            answer += digits.get(i);
        }
        return answer;
    }
}