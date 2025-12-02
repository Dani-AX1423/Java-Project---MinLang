package org.dani.lytrix.LyTrix_R.backend.interpreter;
//import org.dani.lytrix.core.frontend.parser.*;
import org.dani.lytrix.core.frontend.scanner.tokens.*;
import org.dani.lytrix.core.frontend.ast.ASTNode;
import org.dani.lytrix.core.frontend.ast.baseNodes.*;
import org.dani.lytrix.core.frontend.ast.nodes.*;
import org.dani.lytrix.core.frontend.ast.visitors.NodeVisitor;

//...
public class Interpreter implements NodeVisitor<Object>
{
private ASTNode tree;
private String retMain;
//ProgramNode tree;
public Interpreter(ASTNode tree)
{this.tree=tree;}

//Visitor functions to visit each node at a time.
//visits the entire program (initiater)
public Object visitProgram(ProgramNode node) 
{return node.getfn().accept(this);}

//visits functions declarations (modules of program/thread)
public Object visitFunctionDeclr(FunctionDeclrNode node)
{return node.getfnBlock().accept(this);}

//visits Each and respective function block of code (Block executor)
public Object visitFunctionBlock(FunctionBlockNode node) {
for(AbstractStatement stmt : node.getStmts())
{stmt.accept(this);}
return node.getfnRet().accept(this);
}

public Object visitOutput(OutputNode node) {
Token value = node.getArg();
String str = value.getLex();
System.out.println(str);
return null;
}

//visits the terminator of functions (return validator)
public Object visitReturn(ReturnNode node)
{
Token value = node.getRetVal();
String retVal = value.getLex();
if(retVal.equals("EOM")) {
this.retMain = retVal;
return null;
}
return retVal;
}


//special string printer to indicate the end of execution of program 
public String End()
{return "\n\nProgram Executed!\nRecieved Special object <" + this.retMain + ".>\n\n";}


//Main interpret function that executes tree and its nodes (Chain reaction)
public void interpret()
{
tree.accept(this);
}

}
