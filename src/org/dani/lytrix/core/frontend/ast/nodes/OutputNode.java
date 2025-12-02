package org.dani.lytrix.core.frontend.ast.nodes;
//Required : Abstract Classes,Token class, NodeVisitor interface
import org.dani.lytrix.core.frontend.ast.baseNodes.*;
import org.dani.lytrix.core.frontend.scanner.tokens.*;
import org.dani.lytrix.core.frontend.ast.visitors.NodeVisitor;

//
public class OutputNode extends AbstractStatement
{
Token value;
public OutputNode(Token value)
{this.value=value;}

//special function for displaying in string format for human readability
public String toString()
{return "writeSc : \"" + value.getLex() + "\"";}

//Node value retriever function for return object 
public Token getArg()
{return value;}

//accept function to verify logic and operation and call the next node visitor
@Override
public <R> R accept(NodeVisitor<R> tree)
{return tree.visitOutput(this);}
}
