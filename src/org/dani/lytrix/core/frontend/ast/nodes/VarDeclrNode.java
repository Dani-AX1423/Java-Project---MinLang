package org.dani.lytrix.core.frontend.ast.nodes;

//Required : Abstract Classes,Token class, NodeVisitor interface
import org.dani.lytrix.core.frontend.ast.baseNodes.*;
import org.dani.lytrix.core.frontend.scanner.tokens.*;
import org.dani.lytrix.core.frontend.ast.visitors.NodeVisitor;


//this handles the node for variable declaration 
public class VarDeclrNode extends AbstractStatement
{
    private Token type;
    private Token identifier;

    public VarDeclrNode(Token type, Token identifier) {
        this.type = type;
        this.identifier = identifier;
    }

    // special function for displaying in string format for human readability
    public String toString() {
        return "VarDeclr : \nType: " + type.getLex() + "\tName: " + identifier.getLex();
    }

    // Node value retriever function for return object
    public Token getDType() 
    {return type;}
    public Token getIdentifier()
    {return identifier;}
        

    //bridging function - accept()
    @Override
    public <R> R accept(NodeVisitor<R> tree)
    {return tree.visitVarDeclr(this);}
    

}