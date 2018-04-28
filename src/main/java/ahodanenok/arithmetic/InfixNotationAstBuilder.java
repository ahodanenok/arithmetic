package ahodanenok.arithmetic;

import ahodanenok.arithmetic.ast.Expression;
import ahodanenok.arithmetic.ast.FunctionExpression;
import ahodanenok.arithmetic.ast.NumberExpression;
import ahodanenok.arithmetic.ast.OperatorExpression;
import ahodanenok.arithmetic.exception.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class InfixNotationAstBuilder extends NotationAstBuilder {

    private List<Token> tokens;

    InfixNotationAstBuilder() {
        this.tokens = new ArrayList<Token>();
    }

    @Override
    void addToken(Token token) {
        this.tokens.add(token);
    }

    @Override
    Expression build() {
        LinkedList<Expression> expressions = new LinkedList<Expression>();
        LinkedList<Token> stack = new LinkedList<Token>();
        LinkedList<Integer> arityStack = new LinkedList<Integer>();

        Token prevToken = null;
        int currentArity = -1;

        Debug.log("InfixNotationAstBuilder.build");
        Debug.indent();
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (token.getType() == TokenType.NUMBER) {
                Debug.logToken("number", token);

                expressions.push(new NumberExpression(numberFactory.create(token.getValue())));

                Debug.log("arity: %s", arityStack);
                Debug.newLine();
            } else if (token.getType() == TokenType.LP) {
                Debug.logToken("push", token);

                stack.push(token);

                Debug.log("stack after push: %s", stack);
                Debug.log("arity: %s", arityStack);
                Debug.newLine();
            } else if (token.getType() == TokenType.RP) {
                Debug.logToken(token);
                Debug.log("stack before pop: %s", stack);
                Debug.log("arity: %s", arityStack);

                arityStack.push(currentArity);
                // reduce all between parenthesis
                while (stack.size() > 0 && stack.peek().getType() != TokenType.LP) {
                    Token t = stack.pop();
                    if (t.getType() == TokenType.FUNCTION) {
                        reduceFunction(t.getValue(), arityStack.pop(), expressions);
                    } else if (t.getType() == TokenType.OPERATOR) {
                        reduceOperator(t.getValue(), arityStack.pop(), expressions);
                    } else {
                        throw new IllegalStateException("can't happen: token=" + t);
                    }
                }

                if (stack.size() == 0) {
                    throw new MismatchedParenthesisException();
                } else {
                    stack.pop();
                    // restore arity of the operator or function before parenthesis
                    currentArity = arityStack.pop();
                    if (prevToken != null
                            && (prevToken.getType() == TokenType.RP || prevToken.getType() == TokenType.NUMBER)) {
                        currentArity = Math.max(currentArity, 1);
                    }
                }

                Debug.log("stack after pop: %s", stack);
                Debug.log("arity: %s", arityStack);
                Debug.newLine();
            } else if (token.getType() == TokenType.OPERATOR) {
                Debug.logToken(token);

                arityStack.push(currentArity);

                // only unary and binary operators are supported
                // this conditions imply that current operator is unary
                if (prevToken == null
                        || prevToken.getType() == TokenType.LP
                        || prevToken.getType() == TokenType.COMMA
                        || prevToken.getType() == TokenType.OPERATOR) {
                    currentArity = 1;
                } else {
                    currentArity = 2;
                }

                Debug.log("currentArity: %d", currentArity);
                Debug.log("operator: %s", token.getValue());
                while (stack.size() > 0 && stack.peek().getType() != TokenType.LP) {
                    Token t = stack.peek();
                    if (t.getType() == TokenType.FUNCTION) {
                        reduceFunction(stack.pop().getValue(), arityStack.pop(), expressions);
                    } else if (t.getType() == TokenType.OPERATOR) {
                        Operator stackOp = getOperator(t.getValue(), arityStack.peek());
                        Debug.log("operator at the top of the stack: %s", stackOp);

                        Operator op = getOperator(token.getValue(), currentArity);
                        Debug.log("current operator: %s", op);
                        if ((stackOp.getPrecedence() > op.getPrecedence() && currentArity > 1)
                                || (stackOp.getPrecedence() == op.getPrecedence() && stackOp.isLeftAssociative() && currentArity > 1)) {
                            reduceOperator(stack.pop().getValue(), arityStack.pop(), expressions);
                        } else {
                            break;
                        }
                    } else {
                        throw new IllegalStateException("can't happen: token=" + t);
                    }
                }

                Debug.logToken("push", token);

                stack.push(token);

                Debug.log("stack after push: %s", stack);
                Debug.log("arity: %s", arityStack);
                Debug.log("currentArity: %d", currentArity);
                Debug.newLine();
            } else if (token.getType() == TokenType.FUNCTION) {
                Debug.logToken(token);
                Debug.log("arity: %s", arityStack);
                Debug.log("currentArity: %d", currentArity);

                arityStack.push(currentArity);
                currentArity = 0;

                Debug.logToken("push", token);

                stack.push(token);

                Debug.log("stack after push: %s", stack);
                Debug.log("arity: %s", arityStack);
                Debug.log("currentArity: %d", currentArity);
                Debug.newLine();
            } else if (token.getType() == TokenType.COMMA) {
                Debug.logToken(token);

                // reduce previous argument
                Token t = stack.peek();
                if (t.getType() == TokenType.FUNCTION) {
                    reduceFunction(stack.pop().getValue(), currentArity, expressions);
                    currentArity = arityStack.pop();
                } else if (t.getType() == TokenType.OPERATOR) {
                    reduceOperator(stack.pop().getValue(), currentArity, expressions);
                    currentArity = arityStack.pop();
                }

                // if currentArity = 0, then this is the comma after the first argument
                // and this implies that function has at least 2 parameters
                if (currentArity == 0) {
                    currentArity = 2;
                } else {
                    currentArity++;
                }

                // todo: check this condition
//                throw new InvalidSyntaxException("No function arguments before comma");

                Debug.log("stack after push: %s", stack);
                Debug.log("arity: %s", arityStack);
                Debug.log("currentArity: %d", currentArity);
                Debug.newLine();
            } else {
                Debug.log("error: unknown token: %s", token);
                throw new InvalidSyntaxException("Unknown syntax: " + token.getValue());
            }

            prevToken = token;
        }

        arityStack.push(currentArity);
        // reduce remaining expressions
        while (stack.size() > 0) {
            Token t = stack.pop();
            if (t.getType() == TokenType.FUNCTION) {
                reduceFunction(t.getValue(), arityStack.pop(), expressions);
            } else if (t.getType() == TokenType.OPERATOR) {
                reduceOperator(t.getValue(), arityStack.pop(), expressions);
            } else {
                throw new IllegalStateException("can't happen: token=" + t);
            }
        }

        if (expressions.size() > 1) {
            throw new InvalidSyntaxException("Expression is not in infix notation");
        }

        assert stack.size() == 0 : "stack size must be zero after build";
        assert expressions.size() == 1 : "only one node must be in expressions stack";
        Debug.unindent();
        return expressions.pop();
    }

    private void reduceOperator(String identifier, int arity, LinkedList<Expression> expressions) {
        assert arity >= 0: "arity must be >= 0";

        Operator op = getOperator(identifier, arity);
        if (arity > expressions.size()) {
            throw new NotEnoughArguments(op, arity, expressions.size());
        }

        Expression[] args = new Expression[arity];
        for (int n = arity - 1; n >= 0; n--) {
            args[n] = expressions.pop();
        }

        Debug.log("reduce operator: %s, arity=%d", identifier, arity);
        expressions.push(new OperatorExpression(op, args));
    }

    private Operator getOperator(String identifier, int arity) {
        assert arity >= 0: "arity must be >= 0";
        Operator op = env.getOperator(identifier, arity);
        if (op != null) {
            return op;
        }

        throw new UnknownOperatorException(identifier, arity);
    }

    private void reduceFunction(String identifier, int arity, LinkedList<Expression> expressions) {
        assert arity >= 0: "arity must be >= 0";

        Function fn = getFunction(identifier, arity);
        if (arity > expressions.size()) {
            throw new NotEnoughArguments(fn, arity, expressions.size());
        }

        Expression[] args = new Expression[arity];
        for (int n = arity - 1; n >= 0; n--) {
            args[n] = expressions.pop();
        }

        Debug.log("reduce function: %s, arity=%d", identifier, arity);
        expressions.push(new FunctionExpression(fn, args));
    }

    private Function getFunction(String identifier, int arity) {
        assert arity >= 0: "arity must be >= 0";
        Function fn = env.getFunction(identifier, arity);
        if (fn != null) {
            return fn;
        }

        throw new UnknownFunctionException(identifier, arity);
    }
}
