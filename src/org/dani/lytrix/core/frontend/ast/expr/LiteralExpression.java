package org.dani.lytrix.core.frontend.ast.expr;
//Required : Abstract Classes,Token class, NodeVisitor interface
import org.dani.lytrix.core.frontend.ast.baseNodes.*;
import org.dani.lytrix.core.frontend.scanner.tokens.*;
import org.dani.lytrix.core.frontend.ast.visitors.*;

import java.util.*;
import java.io.*;

public final class LiteralExpression extends AbstractExpression {
    
    private final Token literal;

    public LiteralExpression(Token literal)
    {
        super(literal.getLine());
        this.literal=literal;
    }

    //special functions for displaying in string format for human readability
    public String toString() {
        return "LiteralExpr(type=" + literal.getType() + ", value=" + literal.getLex() + ")";
    }

    //attribute retriever func
    public Token getLiteral() {
        return literal;
    }


    //bridging function for expression
    @Override
    public <R> R accept(NodeVisitor<R> tree)
    {return tree.visitLiteralExpression(this);}
}
