package ahodanenok.arithmetic;

import ahodanenok.arithmetic.ast.Expression;

class Debug {

    private static final boolean debug = Boolean.valueOf(System.getProperty("arithmetic.debug"));
    private static final StringBuilder indent;
    static {
        if (debug) {
            indent = new StringBuilder();
        } else {
            indent = null;
        }
    }

    static void logExpression(Expression expression) {
        if (!debug) return;
        // todo: impl
    }

    static void newLine() {
        if (!debug) return;
        System.out.println();
    }

    static void indent() {
        if (!debug) return;
        indent.append("    ");
    }

    static void unindent() {
        if (!debug) return;
        indent.delete(indent.length() - 4, indent.length());
    }

    static void log(String msg, Object... args) {
        if (!debug) return;
        System.out.println(indent + String.format(msg, args));
    }

    static void logToken(Token token) {
        logToken(null, token);
    }

    static void logToken(String msg, Token token) {
        if (!debug) return;

        String str = "Token("
            + token.getType()
            + (token.getValue() != null ? ", " + token.getValue() : "")
            + ")";

        if (msg != null) {
            System.out.println(indent + msg + " | " + str);
        } else {
            System.out.println(indent + str);
        }
    }

    static void logEnv(Env env) {
        if (!debug) return;

        System.out.println(indent + "Operators: ");
        indent();
        if (env.operators.size() == 0) {
            System.out.println(indent + "-- none --");
        } else {
            for (Operator op : env.operators) {
                System.out.println(indent + op.getIdentifier() + "(" + op.getParametersCount() + ")");
            }
        }
        unindent();

        System.out.println(indent + "Function: ");
        indent();
        if (env.functions.size() == 0) {
            System.out.println(indent + "-- none --");
        } else {
            for (Function fn : env.functions) {
                System.out.println(indent + fn.getIdentifier() + "(" + fn.getParametersCount() + ")");
            }
        }
        unindent();
    }
}
