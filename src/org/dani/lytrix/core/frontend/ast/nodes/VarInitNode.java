package org.dani.lytrix.core.frontend.ast.nodes;

//Required : Abstract Classes,Token class, NodeVisitor interface
import org.dani.lytrix.core.frontend.ast.baseNodes.*;
import org.dani.lytrix.core.frontend.scanner.tokens.*;
import org.dani.lytrix.core.frontend.ast.visitors.NodeVisitor;


//this handles the node for variable declaration  + initialization
public class VarInitNode extends AbstractStatement
{
    private Token type;
    private Token identifier;
    private AbstractExpression expr;

    public VarInitNode(Token type, Token identifier, AbstractExpression expr) {
        this.type = type;
        this.identifier = identifier;
        this.expr=expr;
    }

    // special function for displaying in string format for human readability
    public String toString() {
        return "VarInit : \nType: " + type.getLex() + "\tName: " + identifier.getLex() + "\tValue: " + expr.toString();
    }

    // Node value retriever function for return object
    public Token getDType() 
    {return type;}
    public Token getIdentifier()
    {return identifier;}
    public AbstractExpression getExpr()
    {return expr;}

    
    //bridging function - accept()
    @Override
    public <R> R accept(NodeVisitor<R> tree)
    {return tree.visitVarInit(this);}
    

}