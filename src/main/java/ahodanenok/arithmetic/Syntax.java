package ahodanenok.arithmetic;

class Syntax {

    static boolean isNumberStart(char ch) {
        return isNumberPart(ch);
    }

    static boolean isNumberPart(char ch) {
        return ch == '0'
            || ch == '1'
            || ch == '2'
            || ch == '3'
            || ch == '4'
            || ch == '5'
            || ch == '6'
            || ch == '7'
            || ch == '8'
            || ch == '9';
    }

    static boolean isOperatorStart(char ch) {
        return isOperatorPart(ch);
    }

    static boolean isOperatorPart(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    static boolean isFunctionStart(char ch) {
        return Character.isLetter(ch);
    }

    static boolean isFunctionPart(char ch) {
        return Character.isLetter(ch) || Character.isDigit(ch);
    }

    static void checkNumber(CharSequence num, char newCh) {

    }
}
