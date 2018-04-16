package ahodanenok.arithmetic;

import ahodanenok.arithmetic.ast.Expression;
import ahodanenok.arithmetic.ast.FunctionExpression;
import ahodanenok.arithmetic.ast.NumberExpression;
import ahodanenok.arithmetic.ast.OperatorExpression;
import ahodanenok.arithmetic.exception.InvalidExpressionException;
import ahodanenok.arithmetic.exception.MismatchedParenthesisException;
import ahodanenok.arithmetic.exception.UnknownFunctionException;
import ahodanenok.arithmetic.exception.UnknownOperatorException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class PrefixNotationAstBuilder extends NotationAstBuilder {

    private List<Token> tokens;

    public PrefixNotationAstBuilder() {
        this.tokens = new ArrayList<Token>();
    }

    @Override
    public void addToken(Token token) {
        this.tokens.add(token);
    }

    @Override
    public Expression build() {
        LinkedList<Expression> expressions = new LinkedList<Expression>();
        LinkedList<TokenType> stack = new LinkedList<TokenType>();

        for (int i = tokens.size() - 1; i >= 0; i--) {
            Token token = tokens.get(i);
//            System.out.println(expressions);
//            System.out.println(stack);
//            System.out.println(token);
//            System.out.println();
            if (token.getType() == TokenType.NUMBER) {
                stack.push(token.getType());
                expressions.push(new NumberExpression(new BigDecimal(token.getValue())));
            } else if (token.getType() == TokenType.LP) {
                while (stack.size() > 0 && stack.peek() != TokenType.RP) stack.pop();

                if (stack.size() == 0) {
                    throw new MismatchedParenthesisException();
                } else {
                    stack.pop();
                    stack.push(TokenType.NUMBER);
                }
            } else if (token.getType() == TokenType.RP) {
                stack.push(token.getType());
            } else if (token.getType() == TokenType.OPERATOR) {
                int maxParametersCount = getNumbersCount(stack);
                int parametersCount = maxParametersCount;
                Operator op = null;
                while (parametersCount > 0) {
                    op = env.getOperator(token.getValue(), parametersCount);
                    if (op != null) break;
                    parametersCount--;
                }

                if (op != null) {
                    expressions.push(new OperatorExpression(op, pop(expressions, parametersCount)));
                    for (int k = 0; k < parametersCount - 1; k++) stack.pop();
                } else {
                    throw new UnknownOperatorException(token.getValue(), maxParametersCount);
                }
            } else if (token.getType() == TokenType.FUNCTION) {
                int maxParametersCount = getNumbersCount(stack);
                int parametersCount = maxParametersCount;
                Function fn = null;
                while (parametersCount > 0) {
                    fn = env.getFunction(token.getValue(), parametersCount);
                    if (fn != null) break;
                    parametersCount--;
                }

                if (fn != null) {
                    expressions.push(new FunctionExpression(fn, pop(expressions, parametersCount)));
                    for (int k = 0; k < parametersCount - 1; k++) stack.pop();
                } else {
                    throw new UnknownFunctionException(token.getValue(), maxParametersCount);
                }
            } else {
                throw new IllegalArgumentException("Unknown token type: " + token);
            }
        }

        if (expressions.size() > 1) {
            throw new InvalidExpressionException("Expression is not in prefix notation");
        }

        Expression expression = expressions.pop();
//        System.out.println(expression);
        return expression;
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

        assert count >= stack.size() || stack.get(count) != TokenType.NUMBER;
        return count;
    }

    private Expression[] pop(LinkedList<Expression> expressions, int n) {
        assert n >= 0;

        Expression[] result = new Expression[n];
        for (int i = 0; i < n; i++) {
            result[i] = expressions.pop();
        }

        return result;
    }
}
