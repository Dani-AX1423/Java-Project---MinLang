package org.dani.lytrix.core.frontend.ast.nodes;

//Required : Abstract Classes,Token class, NodeVisitor interface
import org.dani.lytrix.core.frontend.ast.baseNodes.*;
import org.dani.lytrix.core.frontend.scanner.tokens.*;
import org.dani.lytrix.core.frontend.ast.visitors.NodeVisitor;

//this class handles the print statement as node for lytrix
public class InputNode extends AbstractExpression {
    private final Token argType;

    public InputNode(Token argType) {
        this.argType = argType;
    }

    // special function for displaying in string format for human readability
    public String toString() {
        return "Input : \n" + "Type : " + argType.getLex();
    }

    // Node variables retriever function for return object  
    public Token getArgType() {
        return argType;
    }
    
    // accept function to verify logic and operation and call the next node visitor
    @Override
    public <R> R accept(NodeVisitor<R> tree) {
        return tree.visitInput(this);
    }
}
