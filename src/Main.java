import java.util.*;

public class Main {
    public static void main(String[] args) { //3+3+(2+2*2)
        charToArray(chars);
        cleanArray(arrayChar);
        System.out.println(arrayChar);
        abc(arrayChar);
        System.out.println(arrayChar);
    }

    public static Scanner scanner = new Scanner(System.in);
    public static String formula = scanner.next();
    public static char[] chars = formula.toCharArray();
    public static ArrayList<String> arrayChar = new ArrayList<>();
    public static ArrayList<String> subArray = new ArrayList<>();

    public static void charToArray(char[] chars) { // преобразование массива char в массив ArrayList с объединением чисел
        StringBuilder counter = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != '+' && chars[i] != '-' && chars[i] != '/' && chars[i] != '*' && chars[i] != '('
                    && chars[i] != ')' && chars[i] != '^' && chars[i] != '√') {
                counter.append(chars[i]);
                if (i == chars.length - 1) {
                    arrayChar.add(counter.toString());
                }
            } else {
                arrayChar.add(counter.toString());
                counter = new StringBuilder(String.valueOf(chars[i]));
                arrayChar.add(counter.toString());
                counter = new StringBuilder();
            }
        }
    }

    public static void abc(ArrayList<String> s) { //подсчет формулы с учетом приоритета знаков
        double result;
        while (s.size() > 1) {
            if (getIndex(s, "(") > 0) {
                double subArrayResult;
                for (int i = getIndex(s, "(") + 1; i < getIndex(s, ")"); i++) {
                    subArray.add(s.get(i));
                }
                abc(subArray);
                subArrayResult = Double.parseDouble(subArray.get(0));
                removeSubArray(s);
                arrayChar.set(getIndex(arrayChar, "("), String.valueOf(subArrayResult));
            } else if (getIndex(s, "-") == 0 || getIndex(s, "-") == getIndex(s, "(") + 1) {
                result = 0 - Double.parseDouble(s.get(getIndex(s, "-") + 1));
                s.set(getIndex(s, "-") + 1, String.valueOf(result));
                s.remove(getIndex(s, "-"));
            } else if (getIndex(s, "^") > 0 && getIndex(s, "√") >= 0) {
                if (getIndex(s, "^") < getIndex(s, "√")) {
                    result = pow(s);
                    overwriting(s, getIndex(s, "^"), result);
                } else {
                    result = sqrt(s);
                    s.set(getIndex(s, "√") + 1, String.valueOf(result));
                    s.remove(getIndex(s, "√"));
                }

            } else if (getIndex(s, "^") > 0) {
                result = pow(s);
                overwriting(s, getIndex(s, "^"), result);

            } else if (getIndex(s, "√") >= 0) {
                result = sqrt(s);
                s.set(getIndex(s, "√") + 1, String.valueOf(result));
                s.remove(getIndex(s, "√"));

            } else if (getIndex(s, "*") > 0 && getIndex(s, "/") > 0) {
                if (getIndex(s, "*") < getIndex(s, "/")) {
                    result = multiplication(s);
                    overwriting(s, getIndex(s, "*"), result);

                } else {
                    result = division(s);
                    overwriting(s, getIndex(s, "/"), result);
                }

            } else if (getIndex(s, "*") > 0) {
                result = multiplication(s);
                overwriting(s, getIndex(s, "*"), result);

            } else if (getIndex(s, "/") > 0) {
                result = division(s);
                overwriting(s, getIndex(s, "/"), result);

            } else if (getIndex(s, "+") > 0 && getIndex(s, "-") > 0) {
                if (getIndex(s, "+") < getIndex(s, "-")) {
                    result = addition(s);
                    overwriting(s, getIndex(s, "+"), result);

                } else {
                    result = subtraction(s);
                    overwriting(s, getIndex(s, "-"), result);
                }

            } else if (getIndex(s, "+") > 0) {
                result = addition(s);
                overwriting(s, getIndex(s, "+"), result);

            } else if (getIndex(s, "-") >= 0) {
                result = subtraction(s);
                overwriting(s, getIndex(s, "-"), result);
            }
        }
    }

    public static void removeSubArray(ArrayList<String> strings) {
        for (int i = getIndex(strings, "(") + 1; i <= getIndex(strings, ")") + 1; ) {
            strings.remove(i);
        }
    }

    public static double multiplication(ArrayList<String> strings) { //умножение
        return Double.parseDouble(strings.get(getIndex(strings, "*") - 1)) *
                Double.parseDouble(strings.get(getIndex(strings, "*") + 1));
    }

    public static double addition(ArrayList<String> strings) { //сложение
        return Double.parseDouble(strings.get(getIndex(strings, "+") - 1)) +
                Double.parseDouble(strings.get(getIndex(strings, "+") + 1));
    }

    public static double subtraction(ArrayList<String> strings) { //вычетание
        return Double.parseDouble(strings.get(getIndex(strings, "-") - 1)) -
                Double.parseDouble(strings.get(getIndex(strings, "-") + 1));
    }

    public static double division(ArrayList<String> strings) { //деление
        return Double.parseDouble(strings.get(getIndex(strings, "/") - 1)) /
                Double.parseDouble(strings.get(getIndex(strings, "/") + 1));
    }

    public static double pow(ArrayList<String> strings) { //степень
        return Math.pow(Double.parseDouble(strings.get(getIndex(strings, "^") - 1)),
                Double.parseDouble(strings.get(getIndex(strings, "^") + 1)));
    }

    public static double sqrt(ArrayList<String> strings) { //квадратный корень
        return Math.sqrt(Double.parseDouble(strings.get(getIndex(strings, "√") + 1)));
    }

    public static void overwriting(ArrayList<String> strings, int i, double result) { //удаление использованных чисел и замена их на результат выражения
        strings.set(i, String.valueOf(result));
        strings.remove(i + 1);
        strings.remove(i - 1);
    }

    public static int getIndex(ArrayList<String> strings, String search) { //поиск индекса заданного символа
        int index = Integer.MAX_VALUE;
        for (int i = 0; i < strings.size(); i++) {
            if (Objects.equals(strings.get(i), search)) {
                if (i < index) {
                    index = i;
                }
            }
        }
        if (index > strings.size()) {
            index = -1;
        }
        return index;
    }

    public static void cleanArray(ArrayList<String> strings) {
        strings.removeAll(Arrays.asList("", null));
    }
}