package org.dani.lytrix.core.frontend.ast.nodes;

//Required : Abstract Classes,Token class, NodeVisitor interface
import org.dani.lytrix.core.frontend.ast.baseNodes.*;
import org.dani.lytrix.core.frontend.scanner.tokens.*;
import org.dani.lytrix.core.frontend.ast.visitors.NodeVisitor;


//Node which packs all nodes together and starts the chain of node operation
public class ProgramNode extends AbstractProgram {
private FunctionDeclrNode fnMain;
public ProgramNode(FunctionDeclrNode fnMain)
{this.fnMain = fnMain;}

//special functions for displaying in string format for human readability
private String indent(String text)
{return " " + text.replace("\n","\n  ");}
public String toString()
{return "Program : \n" + indent(fnMain.toString());}

//Returns sub-tree or node of main()
public FunctionDeclrNode getfn()
{return fnMain;}

//accept function to verify logic and operation and call the next node visitor
@Override
public <R> R accept(NodeVisitor<R> tree)
{return tree.visitProgram(this);}
}
