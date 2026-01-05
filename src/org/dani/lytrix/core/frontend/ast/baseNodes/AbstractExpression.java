package org.dani.lytrix.core.frontend.ast.baseNodes;


//Required : Parent AST abstract class,
import org.dani.lytrix.core.frontend.ast.ASTNode;

//Abstract class node for Expressions
public abstract class AbstractExpression extends ASTNode 
{
    private final int line;
    protected AbstractExpression(int line) {
        this.line=line;

    }

    public final int getLine() {
        return line;
    }
    //....
}
