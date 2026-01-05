package org.dani.lytrix.core.frontend.ast.expr;
//Required : Abstract Classes,Token class, NodeVisitor interface
import org.dani.lytrix.core.frontend.ast.baseNodes.*;
import org.dani.lytrix.core.frontend.scanner.tokens.*;
import org.dani.lytrix.core.frontend.ast.visitors.*;

import java.util.*;
import java.io.*;


public class BinaryExpression extends AbstractExpression
{
    private final AbstractExpression leftExpr;
    private final Token operator;
    private final AbstractExpression rightExpr;

    public BinaryExpression(AbstractExpression leftExpr, Token operator, AbstractExpression rightExpr)
    {
        super(operator.getLine());
        this.leftExpr = leftExpr;
        this.operator = operator;
        this.rightExpr = rightExpr;
    }

    //attributes retriever functions;
    public AbstractExpression getLExpr() {
        return leftExpr;
    }
    public Token getOpt() {
        return operator;
    }
    public AbstractExpression getRExpr() {
        return rightExpr;
    }

    //special functions for displaying in string format for human readability
    public String toString() {
        return "BinaryExpr(" + leftExpr + " " + operator.getLex() + " " + rightExpr + ")";
    }


    //brdige function for expression 
    @Override
    public <R> R accept(NodeVisitor<R> tree) 
    {return tree.visitBinaryExpression(this);}
    
}
