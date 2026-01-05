package org.dani.lytrix.core.frontend.ast;
//Required : NodeVisitor interface 
import org.dani.lytrix.core.frontend.ast.visitors.NodeVisitor;


//Base and Parent class of all nodes 
//Creates the very own structure of Entire Program into AST 
//A template for its child classes with abstract method
public abstract class ASTNode {

    
    //......
    //
    //Bridge function that connects AST and interpreter.
    //(Powerful function yet looks simple lol)
    //
    public abstract <R> R accept(NodeVisitor<R> tree);

}
