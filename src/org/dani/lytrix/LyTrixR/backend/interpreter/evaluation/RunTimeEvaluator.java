package org.dani.lytrix.LyTrixR.backend.interpreter.evaluation;

import java.util.*;
import java.io.*;
//

//exception class;
import org.dani.lytrix.core.errors.exceptions.InterpreterException;
//import org.dani.lytrix.core.frontend.parser.*;
import org.dani.lytrix.core.frontend.scanner.tokens.*;

//import javax.management.RuntimeErrorException;
import org.dani.lytrix.core.frontend.ast.ASTNode;
import org.dani.lytrix.core.frontend.ast.baseNodes.*;
import org.dani.lytrix.core.frontend.ast.expr.BinaryExpression;
import org.dani.lytrix.core.frontend.ast.expr.IdentifierExpression;
import org.dani.lytrix.core.frontend.ast.expr.LiteralExpression;
import org.dani.lytrix.core.frontend.ast.nodes.*;
import org.dani.lytrix.core.frontend.ast.visitors.NodeVisitor;

//...
//..
//
public abstract class RunTimeEvaluator {

    private static final Scanner input = new Scanner(System.in);
    // Main class attributes:
    protected Map<String, Object> variables;
    protected Map<String, TokenType> types;

    // simple constructor initializing variable and type tables
    protected RunTimeEvaluator() {
        this.variables = new HashMap<String, Object>();
        this.types = new HashMap<String, TokenType>();
    }

    // Special backend processing functions are declared in this layer...
    // Checks if token is literal
    protected boolean validateLiteral(TokenType curr) {
        switch (curr) {
            case INT_LIT:
                return true;
            case CHAR_LIT:
                return true;
            case STRING_LIT:
                return true;
            case FLOAT_LIT:
                return true;
            case DOUBLE_LIT:
                return true;
            default:
                return false;
        }
    }

    // type conversion : implicit converter function
    /*
     * protected Object parseLiteral(TokenType type, Token literal) {
     * switch (type) {
     * case INT:
     * if (literal.getType() == TokenType.INT_LIT) {
     * return Integer.parseInt(literal.getLex());
     * } else if (literal.getType() == TokenType.FLOAT_LIT) {
     * return Integer.parseInt(literal.getLex());
     * } else if (literal.getType() == TokenType.DOUBLE_LIT) {
     * return Integer.parseInt(literal.getLex());
     * }
     * break;
     * case CHAR:
     * if (literal.getType() == TokenType.CHAR_LIT) {
     * return literal.getLex().charAt(0);
     * }
     * break;
     * case STRING:
     * if (literal.getType() == TokenType.STRING_LIT) {
     * return literal.getLex();
     * } else if (literal.getType() == TokenType.CHAR_LIT) {
     * return literal.getLex();
     * }
     * break;
     * case FLOAT:
     * if (literal.getType() == TokenType.FLOAT_LIT) {
     * return Float.parseFloat(literal.getLex());
     * } else if (literal.getType() == TokenType.DOUBLE_LIT) {
     * return Float.parseFloat(literal.getLex());
     * } else if (literal.getType() == TokenType.INT_LIT) {
     * return Float.parseFloat(literal.getLex());
     * }
     * break;
     * case DOUBLE:
     * if (literal.getType() == TokenType.FLOAT_LIT) {
     * return Double.parseDouble(literal.getLex());
     * } else if (literal.getType() == TokenType.DOUBLE_LIT) {
     * return Double.parseDouble(literal.getLex());
     * } else if (literal.getType() == TokenType.INT_LIT) {
     * return Double.parseDouble(literal.getLex());
     * }
     * break;
     * }
     * throw new RuntimeException("Variable Type mismatch!\nCannot assign " +
     * literal.getLex() + " to " + type);
     * 
     * }
     */

    // converts the string lexeme to corresponding datatype object
    protected Object StringToLiteral(Token literal) {
        switch (literal.getType()) {
            case INT_LIT:
                return Integer.parseInt(literal.getLex());
            case FLOAT_LIT:
                return Float.parseFloat(literal.getLex());
            case DOUBLE_LIT:
                return Double.parseDouble(literal.getLex());
            case CHAR_LIT:
                return literal.getLex().charAt(0);
            case STRING_LIT:
                return literal.getLex();
            default:
                throw new InterpreterException("Invalid literal for output", literal.getLine());
        }
    }

    //
    // read specific type of data from user
    protected Object readUserInput(Token type) {
        Object inputObject;
        try {
            switch (type.getType()) {
                case INT:
                    inputObject = Integer.parseInt(input.nextLine());
                    break;
                case CHAR:
                    inputObject = input.nextLine().charAt(0);
                    break;
                case STRING:
                    inputObject = input.nextLine();
                    break;
                case FLOAT:
                    inputObject = Float.parseFloat(input.nextLine());
                    break;
                case DOUBLE:
                    inputObject = Double.parseDouble(input.nextLine());
                    break;
                default:
                    input.close();
                    throw new InterpreterException("Unsupported input type: " + type.getLex(), type.getLine());

            }
        } catch (NumberFormatException e) {
            throw new InterpreterException("Unsupported input type: " + type.getLex(), type.getLine());

        }
        return inputObject;
    }

    // Process rhs to resolve + convert it for assignment, declaration, reassignment
    // of variables;
    protected Object processAtomicValue(Token token) {
        if (token.getType() == TokenType.IDENT) {
            if (!variables.containsKey(token.getLex()))
                throw new InterpreterException("Variable not declared: " + token.getLex(), token.getLine());
            return variables.get(token.getLex());
        }

        if (validateLiteral(token.getType())) {
            return StringToLiteral(token);
        }

        throw new InterpreterException("Invalid atomic value", token.getLine());
    }

    //
    //
    protected TokenType atomicType(Token token) {
        switch (token.getType()) {
            case INT_LIT:
                return TokenType.INT;
            case FLOAT_LIT:
                return TokenType.FLOAT;
            case DOUBLE_LIT:
                return TokenType.DOUBLE;
            case IDENT:
                if (!types.containsKey(token.getLex()))
                    throw new InterpreterException("Type not found for variable: " + token.getLex(), token.getLine());
                return types.get(token.getLex());

            default:
                throw new InterpreterException("Invalid atomic token", token.getLine());
        }
    }

    //
    //
    protected TokenType exprType(AbstractExpression expr) {

        if (expr instanceof LiteralExpression) {
            return atomicType(((LiteralExpression) expr).getLiteral());
        }

        if (expr instanceof IdentifierExpression) {
            return atomicType(((IdentifierExpression) expr).getIdentifier());
        }

        if (expr instanceof BinaryExpression) {
            BinaryExpression b = (BinaryExpression) expr;
            TokenType left = exprType(b.getLExpr());
            TokenType right = exprType(b.getRExpr());
            return promote(left, right);
        }

        if (expr instanceof InputNode) {
            return ((InputNode) expr).getArgType().getType();
        }

        throw new InterpreterException("Unknown expression type", expr.getLine());
    }

    protected Object evalExpr(Object lVal, TokenType lType, Token op, Object rVal, TokenType rType) {
        TokenType resultType = promote(lType, rType);

        switch (resultType) {
            case DOUBLE: {
                double a = ((Number) lVal).doubleValue();
                double b = ((Number) rVal).doubleValue();
                return evalDouble(a, b, op);
            }
            case FLOAT: {
                float a = ((Number) lVal).floatValue();
                float b = ((Number) rVal).floatValue();
                return evalFloat(a, b, op);
            }
            case INT: {
                int a = ((Number) lVal).intValue();
                int b = ((Number) rVal).intValue();
                return evalInt(a, b, op);
            }
            default:
                throw new InterpreterException("Invalid numeric type", op.getLine());
        }
    }

    //
    // ..
    private Object evalDouble(double a, double b, Token op) {
        switch (op.getType()) {
            case PLUS:
                return a + b;
            case MINUS:
                return a - b;
            case STAR:
                return a * b;
            case SLASH:
                return a / b;
            case MOD:
                return a % b;
            default:
                throw new InterpreterException("Invalid operator", op.getLine());
        }
    }

    private Object evalFloat(float a, float b, Token op) {
        switch (op.getType()) {
            case PLUS:
                return a + b;
            case MINUS:
                return a - b;
            case STAR:
                return a * b;
            case SLASH:
                return a / b;
            case MOD:
                return a % b;
            default:
                throw new InterpreterException("Invalid operator", op.getLine());
        }
    }

    private Object evalInt(int a, int b, Token op) {
        if (op.getType() == TokenType.SLASH && b == 0)
            throw new RuntimeException("Division by zero");

        switch (op.getType()) {
            case PLUS:
                return a + b;
            case MINUS:
                return a - b;
            case STAR:
                return a * b;
            case SLASH:
                return a / b;
            case MOD:
                return a % b;
            default:
                throw new InterpreterException("Invalid operator", op.getLine());
        }
    }

    //

    //
    private TokenType promote(TokenType a, TokenType b) {
        if (a == TokenType.DOUBLE || b == TokenType.DOUBLE)
            return TokenType.DOUBLE;
        if (a == TokenType.FLOAT || b == TokenType.FLOAT)
            return TokenType.FLOAT;
        return TokenType.INT;
    }

    //
    protected Object promoteValue(TokenType target, Object value, int line) {

        if (!(value instanceof Number))
            throw new RuntimeException("Cannot convert non-numeric value");

        Number n = (Number) value;

        switch (target) {
            case DOUBLE:
                return n.doubleValue();
            case FLOAT:
                return n.floatValue();
            case INT:
                return n.intValue();
            default:
                throw new InterpreterException("Invalid conversion", line);
        }
    }

}