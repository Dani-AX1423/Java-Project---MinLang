package org.dani.lytrix.core.frontend.ast.expr;
//Required : Abstract Classes,Token class, NodeVisitor interface
import org.dani.lytrix.core.frontend.ast.baseNodes.*;
import org.dani.lytrix.core.frontend.scanner.tokens.*;
import org.dani.lytrix.core.frontend.ast.visitors.*;

import java.util.*;
import java.io.*;


public final class IdentifierExpression extends AbstractExpression 
{
    private final Token identifier;

    public IdentifierExpression(Token identifier)
    {
        this.identifier=identifier;
    }

    //special functions for displaying in string format for human readability
    public String toString() {
        return "IdentifierExpr(" + identifier.getLex() + ")";
    }

    //attribute retriever func
    public Token getIdentifier() {
        return identifier;
    }

    public <R> R accept(NodeVisitor<R> tree)
    {return tree.visitIdentifierExpression(this);}
    
}
