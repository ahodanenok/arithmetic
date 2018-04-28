package ahodanenok.arithmetic;

import ahodanenok.arithmetic.ast.Expression;
import ahodanenok.arithmetic.ast.FunctionExpression;
import ahodanenok.arithmetic.ast.NumberExpression;
import ahodanenok.arithmetic.ast.OperatorExpression;
import ahodanenok.arithmetic.exception.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class PrefixNotationAstBuilder extends NotationAstBuilder {

    private List<Token> tokens;

    PrefixNotationAstBuilder() {
        this.tokens = new ArrayList<Token>();
    }

    @Override
    void addToken(Token token) {
        this.tokens.add(token);
    }

    @Override
    Expression build() {
        LinkedList<Expression> expressions = new LinkedList<Expression>();
        LinkedList<TokenType> stack = new LinkedList<TokenType>();

        Debug.log("PrefixNotationAstBuilder.build");
        Debug.indent();
        for (int i = tokens.size() - 1; i >= 0; i--) {
            Token token = tokens.get(i);
            if (token.getType() == TokenType.NUMBER) {
                Debug.logToken("push", token);

                stack.push(token.getType());
                expressions.push(new NumberExpression(numberFactory.create(token.getValue())));

                Debug.log("stack after push: %s", stack.toString());
            } else if (token.getType() == TokenType.LP) {
                Debug.logToken("pop", token);
                Debug.log("stack before pop: %s", stack.toString());

                // pop everything until right parenthesis,
                // then replace it with a number as a placeholder for this pair of parenthesis
                while (stack.size() > 0 && stack.peek() != TokenType.RP) stack.pop();

                if (stack.size() == 0) {
                    throw new MismatchedParenthesisException();
                } else {
                    stack.pop();
                    stack.push(TokenType.NUMBER);
                }

                Debug.log("stack after pop: %s", stack);
            } else if (token.getType() == TokenType.RP) {
                Debug.logToken("push", token);
                stack.push(token.getType());
                Debug.log("stack after push: %s", stack.toString());
            } else if (token.getType() == TokenType.OPERATOR) {
                Debug.logToken(token);

                int maxParametersCount = getNumbersCount(stack);
                int parametersCount = maxParametersCount;
                Operator op = null;
                while (parametersCount > 0) {
                    Debug.log("maching operator: %s, %d", token.getValue(), parametersCount);
                    op = env.getOperator(token.getValue(), parametersCount);
                    if (op != null) break;
                    parametersCount--;
                }

                if (op != null) {
                    assert op.isVariableArgs() || op.getParametersCount() == parametersCount : "operator arity doesn't match";
                    Debug.log("found a match: (%s, %d)", op.getIdentifier(), op.getParametersCount());
                    reduceOperator(op, parametersCount, expressions);
                    // leave one number as a placeholder for the result of this operator
                    for (int k = 0; k < parametersCount - 1; k++) stack.pop();
                } else {
                    throw new UnknownOperatorException(token.getValue(), maxParametersCount);
                }
            } else if (token.getType() == TokenType.FUNCTION) {
                Debug.logToken(token);

                int maxParametersCount = getNumbersCount(stack);
                int parametersCount = maxParametersCount;
                Function fn = null;
                while (parametersCount > 0) {
                    Debug.log("maching function: %s, %d", token.getValue(), parametersCount);
                    fn = env.getFunction(token.getValue(), parametersCount);
                    if (fn != null) break;
                    parametersCount--;
                }

                if (fn != null) {
                    assert fn.isVariableArgs() || fn.getParametersCount() == parametersCount : "function arity doesn't match";
                    Debug.log("found a match: (%s, %d)", fn.getIdentifier(), fn.getParametersCount());
                    reduceFunction(fn, parametersCount, expressions);
                    // leave one number as a placeholder for the result of this function
                    for (int k = 0; k < parametersCount - 1; k++) stack.pop();
                } else {
                    throw new UnknownFunctionException(token.getValue(), maxParametersCount);
                }
            } else {
                Debug.log("error: unknown token: %s", token);
                throw new InvalidSyntaxException("Unknown syntax: " + token.getValue());
            }
        }

        if (expressions.size() > 1) {
            throw new InvalidSyntaxException("Expression is not in prefix notation");
        }

        assert stack.size() == 1 && stack.peek() == TokenType.NUMBER : "stack must contain one number";
        assert expressions.size() == 1 : "only one node must be in ex pressions stack";
        Debug.unindent();
        return expressions.pop();
    }

    private int getNumbersCount(LinkedList<TokenType> stack) {
        int count = 0;
        for (TokenType type : stack) {
            if (type == TokenType.NUMBER) {
                count++;
            } else {
                break;
            }
        }

        assert count >= stack.size() || stack.get(count) != TokenType.NUMBER : "top element is not number";
        return count;
    }

    private void reduceOperator(Operator op, int arity, LinkedList<Expression> expressions) {
        assert arity >= 0: "arity must be >= 0";

        if (arity > expressions.size()) {
            throw new NotEnoughArguments(op, arity, expressions.size());
        }

        Expression[] args = new Expression[arity];
        for (int i = 0; i < arity; i++) {
            args[i] = expressions.pop();
        }

        Debug.log("reduce operator: %s, arity=%d", op.getIdentifier(), arity);
        expressions.push(new OperatorExpression(op, args));
    }

    private void reduceFunction(Function fn, int arity, LinkedList<Expression> expressions) {
        assert arity >= 0: "arity must be >= 0";

        if (arity > expressions.size()) {
            throw new NotEnoughArguments(fn, arity, expressions.size());
        }

        Expression[] args = new Expression[arity];
        for (int i = 0; i < arity; i++) {
            args[i] = expressions.pop();
        }

        Debug.log("reduce function: %s, arity=%d", fn.getIdentifier(), arity);
        expressions.push(new FunctionExpression(fn, args));
    }
}
