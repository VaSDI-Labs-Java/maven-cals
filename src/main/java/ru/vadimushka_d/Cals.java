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
     * @param a слагаемое
     * @param b слагаемое
     * @return String result
     */
    static String Sum(String a, String b) {
        double a_double = Double.parseDouble(a);
        double b_double = Double.parseDouble(b);

        double result = Sum(a_double, b_double);
        if (result % 1 == 0) {
            return String.valueOf((long) result);
        }
        return String.valueOf(result);
    }

    /**
     * @param a слагаемое
     * @param b слагаемое
     * @return Double result
     */
    static double Sum(double a, double b) {
        return a + b;
    }

    /**
     * @param a вычитаемое
     * @param b вычитатель
     * @return String result
     */
    static String Subtraction(String a, String b){
        double a_double = Double.parseDouble(a);
        double b_double = Double.parseDouble(b);

        double result = Subtraction(a_double, b_double);
        if (result % 1 == 0) {
            return String.valueOf((long) result);
        }
        return String.valueOf(result);
    }

    /**
     * @param a вычитаемое
     * @param b вычитатель
     * @return Double result
     */
    static double Subtraction(double a, double b) {
        return a - b;
    }

    /**
     * @param a множитель
     * @param b множитель
     * @return String result
     */
    static String Multiplication(String a, String b){
        double a_double = Double.parseDouble(a);
        double b_double = Double.parseDouble(b);

        double result = Multiplication(a_double, b_double);
        if (result % 1 == 0) {
            return String.valueOf((long) result);
        }
        return String.valueOf(result);
    }

    /**
     * @param a множитель
     * @param b множитель
     * @return Double result
     */
    static double Multiplication(double a, double b) {
        return a * b;
    }

    /**
     * @param a делимое
     * @param b делитель
     * @return String result
     */
    static String Division(String a, String b){
        double a_double = Double.parseDouble(a);
        double b_double = Double.parseDouble(b);

        double result = Division(a_double, b_double);
        if (result % 1 == 0) {
            return String.valueOf((long) result);
        }
        return String.valueOf(result);
    }

    /**
     * @param a делимое
     * @param b делитель
     * @return Double result
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
     * @param a 1 число
     * @param b 2 число
     * @return String result
     */
    static String Remainder(String a, String b){
        double a_double = Double.parseDouble(a);
        double b_double = Double.parseDouble(b);

        double result = Remainder(a_double, b_double);
        if (result % 1 == 0) {
            return String.valueOf((long) result);
        }
        return String.valueOf(result);
    }

    /**
     * @param a 1 число
     * @param b 2 число
     * @return Double result
     */
    static double Remainder(double a, double b) {
        return a % b;
    }

    /**
     * @param a основа
     * @param b степень возведения
     * @return String result
     */
    static String Pow(String a, String b){
        double a_double = Double.parseDouble(a);
        double b_double = Double.parseDouble(b);

        double result = Pow(a_double, b_double);
        if (result % 1 == 0) {
            return String.valueOf((long) result);
        }
        return String.valueOf(result);
    }

    /**
     * Метод возведения в степень
     *
     * @param base     основа
     * @param exponent степень возведения
     * @return Double result
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
