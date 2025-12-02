package org.dani.lytrix.core.frontend.ast.nodes;
//Required : Abstract Classes,Token class, NodeVisitor interface
import org.dani.lytrix.core.frontend.ast.baseNodes.*;
import org.dani.lytrix.core.frontend.scanner.tokens.*;
import org.dani.lytrix.core.frontend.ast.visitors.NodeVisitor;

//Function declaration node to pack functions(main for now)
public class FunctionDeclrNode extends AbstractFunctionDeclr {
private Token fnType;
private Token MainName;
private FunctionBlockNode fnBlock;
public FunctionDeclrNode(Token fnType,Token MainName,FunctionBlockNode fnBlock)
{this.fnType=fnType;this.MainName=MainName;this.fnBlock = fnBlock;}

//special functions for displaying in string format for human readability
private String indent(String text)
{return " " + text.replace("\n","\n  ");}
public String toString()
{String declr = "Function Declaration :\n" + "Type : " + fnType.getLex() + " | Identifer : " + MainName.getLex();
return declr + "\n" + indent(fnBlock.toString());}

//node value retriever function
public FunctionBlockNode getfnBlock()
{return fnBlock;}

//accept function to verify logic and operation and call the next node visitor
@Override
public <R> R accept(NodeVisitor<R> tree)
{return tree.visitFunctionDeclr(this);}
}
