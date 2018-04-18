package ahodanenok.arithmetic;

import ahodanenok.arithmetic.ast.Expression;

class Debug {

    private static final boolean DEBUG_ENABLED = Boolean.valueOf(System.getProperty("arithmetic.debug"));
    private static final StringBuilder INDENT;
    private static final String INDENT_STR = "  ";

    static {
        if (DEBUG_ENABLED) {
            INDENT = new StringBuilder();
        } else {
            INDENT = null;
        }
    }

    static void logExpression(Expression expression) {
        if (!DEBUG_ENABLED) return;

        System.out.println(INDENT + expression.toString());
        indent();
        for (Expression arg : expression.getArgs()) {
            logExpression(arg);
        }
        unindent();
    }

    static void newLine() {
        if (!DEBUG_ENABLED) return;
        System.out.println();
    }

    static void indent() {
        if (!DEBUG_ENABLED) return;
        INDENT.append(INDENT_STR);
    }

    static void unindent() {
        if (!DEBUG_ENABLED) return;
        INDENT.delete(INDENT.length() - INDENT_STR.length(), INDENT.length());
    }

    static void log(String msg, Object... args) {
        if (!DEBUG_ENABLED) return;
        System.out.println(INDENT + String.format(msg, args));
    }

    static void logToken(Token token) {
        logToken(null, token);
    }

    static void logToken(String msg, Token token) {
        if (!DEBUG_ENABLED) return;

        String str = "Token("
            + token.getType()
            + (token.getValue() != null ? ", " + token.getValue() : "")
            + ")";

        if (msg != null) {
            System.out.println(INDENT + msg + " | " + str);
        } else {
            System.out.println(INDENT + str);
        }
    }

    static void logEnv(Env env) {
        if (!DEBUG_ENABLED) return;

        System.out.println(INDENT + "Operators: ");
        indent();
        if (env.operators.size() == 0) {
            System.out.println(INDENT + "-- none --");
        } else {
            for (Operator op : env.operators) {
                System.out.println(INDENT + op.getIdentifier() + " (arity = " + (op.isVariableArgs() ? "<varargs>" : op.getParametersCount()) + ")");
            }
        }
        unindent();

        System.out.println(INDENT + "Function: ");
        indent();
        if (env.functions.size() == 0) {
            System.out.println(INDENT + "-- none --");
        } else {
            for (Function fn : env.functions) {
                System.out.println(INDENT + fn.getIdentifier() + " (arity = " + (fn.isVariableArgs() ? "<varargs>" : fn.getParametersCount()) + ")");
            }
        }
        unindent();
    }
}
