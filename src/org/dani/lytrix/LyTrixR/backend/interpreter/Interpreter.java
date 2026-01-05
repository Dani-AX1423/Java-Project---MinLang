package org.dani.lytrix.LyTrixR.backend.interpreter;

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
import org.dani.lytrix.LyTrixR.backend.interpreter.evaluation.*;

//...
//..
//
//Main interpreter class that processes the generated ast code by parser.
//Job : traverses through AST nodes, calls respective visitor functions , produces result
// THIS IS WHERE ABSTRACTION turns into MEANINGFUL OUTPUT . THIS IS WHERE MAGIC HAPPENS
public class Interpreter extends RunTimeEvaluator implements NodeVisitor<Object> {
    private ASTNode tree;
    private String retMain;

    // ProgramNode tree;
    public Interpreter(ASTNode tree) {
        super();
        this.tree = tree;

    }

    //
    // exported the helper functions to RunTimeEvaluator file

    //
    //
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

    //
    // ...
    // ..
    // ..
    // Expression visitor functions
    public Object visitLiteralExpression(LiteralExpression expr) {
        Token literal = expr.getLiteral();
        return processAtomicValue(literal);
    }

    public Object visitIdentifierExpression(IdentifierExpression expr) {
        Token identifier = expr.getIdentifier();
        return processAtomicValue(identifier);
    }

    public Object visitBinaryExpression(BinaryExpression expr) {
        Object leftVal = expr.getLExpr().accept(this);
        Object rightVal = expr.getRExpr().accept(this);

        TokenType leftType = exprType(expr.getLExpr());
        TokenType rightType = exprType(expr.getRExpr());

        return evalExpr(leftVal, leftType, expr.getOpt(), rightVal, rightType);
    }

    //
    // 3 types of Variable statement processor functions
    // Variable declaration:
    public Object visitVarDeclr(VarDeclrNode node) {

        TokenType varType = node.getDType().getType();
        String varName = node.getIdentifier().getLex();
        if (types.containsKey(varName)) {
            throw new InterpreterException("Variable is already declared with name : " + varName, node.getIdentifier().getLine());
        }

        types.put(varName, varType);
        variables.put(varName, null);

        return null;
    }

    // variable initialisation
    public Object visitVarInit(VarInitNode node) {

        String varName = node.getIdentifier().getLex();
        TokenType varType = node.getDType().getType();
        AbstractExpression expr = node.getExpr();

        if (types.containsKey(varName))
            throw new InterpreterException("Variable already declared: " + varName, node.getIdentifier().getLine());

        Object value = expr.accept(this);
        TokenType exprType = exprType(expr);

        if (varType != exprType)
            value = promoteValue(varType, value, expr.getLine());

        types.put(varName, varType);
        variables.put(varName, value);

        return null;
    }

    // variable assignment or reassignment
    public Object visitVarAssign(VarAssignNode node) {

        String varName = node.getIdentifier().getLex();
        
        AbstractExpression expr = node.getExpr();

        if(!variables.containsKey(varName))
            throw new InterpreterException("variable not found with name : " + varName, node.getIdentifier().getLine());

        Object value = expr.accept(this);
        TokenType exprType = exprType(expr);
        TokenType varType = types.get(varName);
        if (varType != exprType)
            value = promoteValue(varType, value, expr.getLine());

        //types.put(varName, varType);
        variables.put(varName, value);

        return null;
    }

    //
    // Input statement node processing function
    public Object visitInput(InputNode node) {
        Token type = node.getArgType();
        Object value = readUserInput(type);
        return value;
        

    }

    //
    // Output statement node processing function
    public Object visitOutput(OutputNode node) {
        AbstractExpression expr = node.getArg();

        if(expr==null) {
            System.out.println();
            return null;
        }
        else {
            Object value = expr.accept(this);
            System.out.println(value);
            return null;
        }
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
