package ru.vadimushka_d;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * App класс
 */
public class App {
    private static Scanner sc = new Scanner(System.in);

    private static Map<String, Boolean> commands = new HashMap<>();
    private static Map<Integer, String> sessions = new HashMap<>();

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
            initSessions();

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
                    String exp;

                    System.out.print("\n>: ");
                    exp = sc.nextLine();

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

    private static void initSessions() {
        System.out.print("Выберите тип вводной информации (default: keyboard): ");
        String temp_command;

        temp_command = sc.nextLine();
        if (temp_command.contains("file_input"))
            sessions.put(1, temp_command);
        else if (temp_command.contains("keyboard"))
            sessions.put(1, temp_command);
        else
            sessions.put(1, "keyboard");


        System.out.print("Введите тип вывода (default: monitor): ");
        temp_command = sc.nextLine();
        if (temp_command.contains("file_output"))
            sessions.put(2, temp_command);
        else if (temp_command.contains("monitor"))
            sessions.put(2, temp_command);
        else
            sessions.put(2, "monitor");

    }

    private static boolean inputCommand() {
        Boolean bool = false;
        String temp_command;

        System.out.print("Введите команду: ");

        if (sc.hasNextLine()) {
            temp_command = sc.nextLine();
            if (temp_command.contains("exit")) {
                bool = true;
            } else if (temp_command.contains("continue")) {
                bool = false;
            }
        }
        return bool;
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

        switch (TypeOperator.getOperator(words[1])) {
            case Addition:
                result = Cals.Sum(words[0], words[2]);
                break;
            case Subtraction:
                result = Cals.Subtraction(words[0], words[2]);
                break;
            case Multiplication:
                result = Cals.Multiplication(words[0], words[2]);
                break;
            case Division:
                result = Cals.Division(words[0], words[2]);
                break;
            case POW:
                result = Cals.Pow(words[0], words[2]);
                break;
            case Remainder:
                result = Cals.Remainder(words[0], words[2]);
                break;
            default:
                result = "Знак операции не тот - возможны только = (+ - / * ^ %)";
                break;
        }
        return result;
    }

    private static Boolean isMatches(String text) {
        return text.matches("([-+])?\\d+(\\.?\\d+)?\\s\\W\\s([-+])?\\d+(\\.?\\d+)?");
    }
}
