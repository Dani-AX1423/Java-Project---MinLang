package org.dani.lytrix.core.frontend.ast.nodes;

//Required : Abstract Classes,Token class, NodeVisitor interface
import org.dani.lytrix.core.frontend.ast.baseNodes.*;
import org.dani.lytrix.core.frontend.scanner.tokens.*;
import org.dani.lytrix.core.frontend.ast.visitors.NodeVisitor;


//this handles the node for variable declaration 
public class VarAssignNode extends AbstractStatement
{
    
    private Token identifier;
    private Token literal;

    public VarAssignNode(Token identifier, Token literal) {
        this.identifier = identifier;
        this.literal=literal;
    }

    // special function for displaying in string format for human readability
    public String toString() {
        return "VarAssign :\n" + "Name: " + identifier.getLex() + "\tValue: " + literal.getLex();
    }

    // Node value retriever function for return object
    public Token getIdentifier()
    {return identifier;}
    public Token getLiteral() 
    {return literal;}   


    //bridging function - accept()
    @Override
    public <R> R accept(NodeVisitor<R> tree)
    {return tree.visitVarAssign(this);}
    

}