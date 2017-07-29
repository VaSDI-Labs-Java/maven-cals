package ru.vadimushka_d;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Вспомогательный класс с описанием всем методов для выполения арифметических операций
 *
 * @author Вадим Дунькин
 * @version 1.0
 */
class Cals {
    /**
     * Метод сложения чисел
     *
     * @param a слагаемое
     * @param b слагаемое
     * @return возвращает сумму
     */
    static double Sum(double a, double b) {
        return a + b;
    }

    /**
     * Метод вычитания чисел
     *
     * @param a вычитаемое
     * @param b вычитатель
     * @return возвращает результат вычитания
     */
    static double Subtraction(double a, double b) {
        return a - b;
    }

    /**
     * Метод умножения чисел
     *
     * @param a множитель
     * @param b множитель
     * @return возвращает произведение
     */
    static double Multiplication(double a, double b) {
        return a * b;
    }

    /**
     * Метод деления цельночисленных чисел
     *
     * @param a делимое
     * @param b делитель
     * @return возвращает результат деления
     */
    static int Division(int a, int b) {
        int res = 1;
        try {
            res = a / b;
        } catch (ArithmeticException e) {
            System.out.println("Нельзя делить на ноль");
        }

        return res;
    }

    /**
     * Метод деления вещественных чисел
     *
     * @param a делимое
     * @param b делитель
     * @return возвращает результат деления
     */
    static double Division(double a, double b) {
        double res = 1.0;

        try {
            res = a / b;
        } catch (ArithmeticException e) {
            System.out.println("Нельзя делить на ноль");
        }

        return new BigDecimal(res).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Метод нахождения остатка
     *
     * @param a 1 число
     * @param b 2 число
     * @return возвращает остаток от деления
     */
    static double Remainder(double a, double b) {
        return a % b;
    }

    /**
     * Метод возведения в степень
     *
     * @param base     основа
     * @param exponent степень возведения
     * @return результат
     */
    static double Pow(double base, double exponent) {
        double res = 1.0;

        if (exponent < 0) {
            exponent = -exponent;

            for (int i = 0; i < exponent; i++) {
                res = res * base;
            }

            res = 1 / res;
        } else {
            for (int i = 0; i < exponent; i++) {
                res = res * base;
            }
        }

        return res;
    }
}
