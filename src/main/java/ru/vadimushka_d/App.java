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

    private static Map<String, Boolean> commands = new HashMap<String, Boolean>();
    private static Map<Integer, String> sessions = new HashMap<Integer, String>();

    private static final String INPUT_FILE = "input_exp_cals.txt";
    private static final String OUTPUT_FILE = "output_result_cals.txt";

    public static void main(String[] args) {
        initOptions();
        Boolean command;
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
                System.out.println("Введите строку в формате:");

                while (true) {
                    int status;
                    String exp;

                    System.out.print("[1 число][пробел][знак операций][пробел][2 число]: ");
                    exp = sc.nextLine();

                    if (sessions.get(2).contains("monitor")) {
                        status = Monitor(exp);
                    } else {
                        status = FileOutput(exp, buff);
                    }

                    if (status == 0)
                        break;
                    else if (status == 1)
                        System.out.println("-----------------------------------------------");
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


            command = inputCommand();

            if (command) {
                if (!commands.get(sessions.get(2))) {
                    try {
                        assert buff != null;
                        buff.close();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
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
        System.out.println("Выберите тип вводной информации:");
        String temp_command;

        temp_command = sc.nextLine();
        if (temp_command.contains("file_input"))
            sessions.put(1, temp_command);
        else if (temp_command.contains("keyboard"))
            sessions.put(1, temp_command);
        else
            sessions.put(1, "keyboard");


        System.out.println("Введите тип вывода:");
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
            System.out.println(exp + " = " + Calculus(exp));
        } else if (exp.contains("exit")) {
            code = 0;
        } else {
            System.out.println("Да ты ошибся батенька..");
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
                System.err.println("---");
                e.fillInStackTrace();
            }
        } else if (exp.contains("exit")) {
            code = 0;
        } else {
            System.out.println("Да ты ошибся батенька..");
        }

        return code;
    }

    private static ArrayList<String> FileInput() {
        ArrayList<String> expData = new ArrayList<String>();
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
        TypeOperator operator = TypeOperator.getOperator(words[1]);

        double number_first = Double.valueOf(words[0]);
        double number_second = Double.valueOf(words[2]);

        switch (operator) {
            case Addition:
                result = String.valueOf(Cals.Sum(number_first, number_second));
                break;
            case Subtraction:
                result = String.valueOf(Cals.Subtraction(number_first, number_second));
                break;
            case Multiplication:
                result = String.valueOf(Cals.Multiplication(number_first, number_second));
                break;
            case Division:
                if (words[0].contains(",") || words[1].contains(",")) {
                    result = String.valueOf(Cals.Division(number_first, number_second));
                } else {
                    result = String.valueOf(Cals.Division(Integer.valueOf(words[0]), Integer.valueOf(words[2])));
                }
                break;
            case POW:
                result = String.valueOf(Cals.Pow(number_first, number_second));
                break;
            case Remainder:
                result = String.valueOf(Cals.Remainder(number_first, number_second));
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
