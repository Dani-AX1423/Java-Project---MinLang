package org.dani.lytrix.LyTrix_R.backend.interpreter;

import java.util.*;
import java.io.*;
//

//import org.dani.lytrix.core.frontend.parser.*;
import org.dani.lytrix.core.frontend.scanner.tokens.*;

//import javax.management.RuntimeErrorException;
import org.dani.lytrix.core.frontend.ast.ASTNode;
import org.dani.lytrix.core.frontend.ast.baseNodes.*;
import org.dani.lytrix.core.frontend.ast.nodes.*;
import org.dani.lytrix.core.frontend.ast.visitors.NodeVisitor;

//...
public class Interpreter implements NodeVisitor<Object> {
    private ASTNode tree;
    private String retMain;
    Map<String, Object> variables;
    Map<String, TokenType> types;

    // ProgramNode tree;
    public Interpreter(ASTNode tree) {
        this.tree = tree;
        this.variables = new HashMap<String, Object>();
        this.types = new HashMap<String, TokenType>();

    }

    private Object convertLiteral(TokenType type, Token literal) {
        switch (type) {
            case INT:
                if (literal.getType() == TokenType.INT_LIT) {
                    return Integer.parseInt(literal.getLex());
                }
                break;
            case CHAR:
                if (literal.getType() == TokenType.CHAR_LIT) {
                    return literal.getLex().charAt(0);
                }
                break;
            case STRING:
                if (literal.getType() == TokenType.STRING_LIT) {
                    return literal.getLex();
                } else if (literal.getType() == TokenType.CHAR_LIT) {
                    return literal.getLex();
                }
                break;
            case FLOAT:
                if (literal.getType() == TokenType.FLOAT_LIT) {
                    return Float.parseFloat(literal.getLex());
                } else if (literal.getType() == TokenType.DOUBLE_LIT) {
                    return Float.parseFloat(literal.getLex());
                } else if (literal.getType() == TokenType.INT_LIT) {
                    return Float.parseFloat(literal.getLex());
                }
                break;
            case DOUBLE:
                if (literal.getType() == TokenType.DOUBLE_LIT) {
                    return Double.parseDouble(literal.getLex());
                } else if (literal.getType() == TokenType.INT_LIT) {
                    return Double.parseDouble(literal.getLex());
                }
                break;
        }
        throw new RuntimeException("Variable Type mismatch!\nCannot assign " + literal.getLex() + " to " + type);

    }

    private Object StringToLiteral(Token literal) {
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
                throw new RuntimeException("Invalid literal for output");
        }
    }

    // Visitor functions to visit each node at a time.
    // visits the entire program (initiater)
    public Object visitProgram(ProgramNode node) {
        return node.getfn().accept(this);
    }

    // visits functions declarations (modules of program/thread)
    public Object visitFunctionDeclr(FunctionDeclrNode node) {
        return node.getfnBlock().accept(this);
    }

    // visits Each and respective function block of code (Block executor)
    public Object visitFunctionBlock(FunctionBlockNode node) {
        for (AbstractStatement stmt : node.getStmts()) {
            stmt.accept(this);
        }
        return node.getfnRet().accept(this);
    }

    public Object visitVarDeclr(VarDeclrNode node) {

        TokenType varType = node.getDType().getType();
        String varName = node.getIdentifier().getLex();
        if (types.containsKey(varName)) {
            throw new RuntimeException("Variable is already declared with name : " + varName);
        }

        types.put(varName, varType);
        variables.put(varName, null);

        return null;
    }

    public Object visitVarInit(VarInitNode node) {

        String varName = node.getIdentifier().getLex();
        if (types.containsKey(varName)) {
            throw new RuntimeException("Variable is already declared with name : " + varName);
        }
        TokenType varType = node.getDType().getType();
        Token curr_lit = node.getLiteral();
        Object varValue = convertLiteral(varType, curr_lit);

        types.put(varName, varType);
        variables.put(varName, varValue);

        return null;
    }

    public Object visitVarAssign(VarAssignNode node) {

        String varName = node.getIdentifier().getLex();
        Token curr_lit = node.getLiteral();

        if (types.containsKey(varName)) {
            TokenType varType = types.get(varName);
            Object varValue = convertLiteral(varType, curr_lit);
            // types.replace(varName, varType);
            variables.replace(varName, varValue);
        } else {
            throw new RuntimeException("Variable is not declared with the name : " + varName);
        }

        return null;
    }

    public Object visitOutput(OutputNode node) {
        Token arg = node.getArg();
        
        if(arg==null)
        {
            System.out.println();
        }
        else 
        {
        TokenType argType = arg.getType();
        Object argValue;
        if (argType == TokenType.STRING_LIT || argType == TokenType.CHAR_LIT) {
            argValue = StringToLiteral(arg);
            System.out.println(argValue);
        } else if (argType == TokenType.INT_LIT || argType == TokenType.FLOAT_LIT || argType == TokenType.DOUBLE_LIT) {
            argValue = StringToLiteral(arg);
            System.out.println(argValue);
        }

    

        // Variable printing or calling
        else if (argType == TokenType.IDENT) {
            String identifier = arg.getLex();
            if (variables.containsKey(identifier)) {
                Object literal = variables.get(identifier);
                System.out.println(literal);
            } else {
                throw new RuntimeException("Variable with name : " + identifier + " not found.");
            }
        }
    }
        return null;
    }

    // visits the terminator of functions (return validator)
    public Object visitReturn(ReturnNode node) {
        Token value = node.getRetVal();
        String retVal = value.getLex();
        if (retVal.equals("EOM")) {
            this.retMain = retVal;
            return null;
        }
        return retVal;
    }

    // special string printer to indicate the end of execution of program
    public String End() {
        return "\n\nProgram Executed!\nRecieved Special object <" + this.retMain + ".>\n\n";
    }

    // Main interpret function that executes tree and its nodes (Chain reaction)
    public void interpret() {
        tree.accept(this);
    }

}
