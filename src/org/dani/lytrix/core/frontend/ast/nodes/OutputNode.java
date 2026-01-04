package org.dani.lytrix.core.frontend.ast.nodes;

//Required : Abstract Classes,Token class, NodeVisitor interface
import org.dani.lytrix.core.frontend.ast.baseNodes.*;
import org.dani.lytrix.core.frontend.scanner.tokens.*;
import org.dani.lytrix.core.frontend.ast.visitors.NodeVisitor;

//this class handles the print statement as node for lytrix
public class OutputNode extends AbstractStatement {
    private AbstractExpression arg;

    public OutputNode(AbstractExpression arg) {
        this.arg = arg;
    }

    // special function for displaying in string format for human readability
    public String toString() {
        if (arg == null)
            return "Output :\nwriteSc()";
        return "Output :\nwriteSc: \nargument: " + arg.toString();
    }

    // Node value retriever function for return object
    public AbstractExpression getArg() {
        return arg;
    }

    // accept function to verify logic and operation and call the next node visitor
    @Override
    public <R> R accept(NodeVisitor<R> tree) {
        return tree.visitOutput(this);
    }
}
