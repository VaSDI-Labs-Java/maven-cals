package ru.vadimushka_d;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * App класс
 */
public class App {
    private static final Scanner sc = new Scanner(System.in);

    private static final Map<String, Boolean> commands = new HashMap<>();
    private static final Map<Integer, String> sessions = new HashMap<>();

    private static final String INPUT_FILE = "input_exp_cals.txt";
    private static final String OUTPUT_FILE = "output_result_cals.txt";

    public static void main(String[] args) {
        initOptions();
        BufferedWriter buff = null;

        while (true) {
            System.out.println("Предоставляю вам консольный вариант калькулятора\n" +
                    "\tКоманды:\n" +
                    "\t\tfile_input - Читает выражения из файла.\n" +
                    "\t\tkeyboard - Читает выражение с клавиатуры.\n" +
                    "\t\tfile_output - Записывает выражение и ответ в файл.\n" +
                    "\t\tmonitor - Выводит выражение и ответ на экран.\n" +
                    "\tУправление калькулятором:\n" +
                    "\t\texit - выход из калькулятора\n" +
                    "\t\tcontinue - новый цикл работы калькулятора.");
            System.out.println("Теперь произойдет инициализация калькулятора.");
            sessionsConstruct("Выберите тип вводной информации (default: keyboard): ", "file_input", "keyboard", 1);
            sessionsConstruct("Введите тип вывода (default: monitor): ", "file_output", "monitor", 2);

            if (!commands.get(sessions.get(2))) {
                try {
                    buff = new BufferedWriter(new FileWriter(OUTPUT_FILE));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (commands.get(sessions.get(1))) {
                System.out.print("Введите строку в формате ([1 число][пробел][знак операций][пробел][2 число]): ");

                while (true) {
                    int status;

                    System.out.print("\n>: ");
                    String exp = sc.nextLine();

                    if (sessions.get(2).contains("monitor")) {
                        status = Monitor(exp);
                    } else {
                        status = FileOutput(exp, buff);
                    }

                    if (status == 0)
                        break;
                }
            } else {
                ArrayList<String> expData = FileInput();

                for (String exp : expData) {
                    if (sessions.get(2).contains("monitor")) {
                        Monitor(exp);
                    } else {
                        FileOutput(exp, buff);
                    }
                }
            }

            if (inputCommand()) {
                if (!commands.get(sessions.get(2))) {
                    try {
                        assert buff != null;
                        buff.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
        System.out.println("Программа закрыта");
    }

    private static void initOptions() {
        commands.put("keyboard", true);
        commands.put("file_input", false);

        commands.put("monitor", true);
        commands.put("file_output", false);

        commands.put("exit", true);
        commands.put("continue", false);
    }

    /*
     * //TODO нужно придумать нормальные имена
     */
    private static void sessionsConstruct(String answer, String input, String output, int position) {
        System.out.print(answer);
        String temp_command = sc.nextLine();
        if (temp_command.contains(input)) {
            sessions.put(position, temp_command);
        } else if (temp_command.contains(output)) {
            sessions.put(position, temp_command);
        } else {
            sessions.put(position, output);
        }
    }

    private static boolean inputCommand() {
        System.out.print("Введите команду: ");
        return sc.nextLine().contains("exit");
    }

    private static int Monitor(String exp) {
        int code = 1;

        if (isMatches(exp)) {
            System.out.print(exp + " = " + Calculus(exp));
        } else if (exp.contains("exit")) {
            code = 0;
        } else {
            System.out.print("Да ты ошибся батенька..");
        }

        return code;
    }

    private static int FileOutput(String exp, BufferedWriter buff) {
        int code = 1;

        if (isMatches(exp)) {
            try {
                buff.write(exp + " = " + Calculus(exp) + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (exp.contains("exit")) {
            code = 0;
        } else {
            System.out.print("Да ты ошибся батенька..");
        }

        return code;
    }

    @NotNull
    private static ArrayList<String> FileInput() {
        ArrayList<String> expData = new ArrayList<>();
        String temp;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(INPUT_FILE));

            while ((temp = bufferedReader.readLine()) != null) {
                if (isMatches(temp)) {
                    expData.add(temp);
                } else {
                    expData.add("Да ты ошибся батенька..");
                }
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return expData;
    }

    private static String Calculus(String exp) {
        String result;
        String[] words = exp.split(" ");

        result = switch (TypeOperator.getOperator(words[1])) {
            case Addition -> Cals.Sum(words[0], words[2]);
            case Subtraction -> Cals.Subtraction(words[0], words[2]);
            case Multiplication -> Cals.Multiplication(words[0], words[2]);
            case Division -> Cals.Division(words[0], words[2]);
            case POW -> Cals.Pow(words[0], words[2]);
            case Remainder -> Cals.Remainder(words[0], words[2]);
            default -> "Знак операции не тот - возможны только = (+ - / * ^ %)";
        };
        return result;
    }

    private static Boolean isMatches(String text) {
        return text.matches("([-+])?\\d+(\\.?\\d+)?\\s\\W\\s([-+])?\\d+(\\.?\\d+)?");
    }
}
