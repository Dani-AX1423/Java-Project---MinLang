package org.dani.lytrix.core.frontend.ast.nodes;
import org.dani.lytrix.core.frontend.ast.baseNodes.*;
import org.dani.lytrix.core.frontend.scanner.tokens.*;
public class FunctionDeclrNode extends AbstractFunctionDeclr {
private Token fnType;
private Token MainName;
private FunctionBlockNode fnBlock;
public FunctionDeclrNode(Token fnType,Token MainName,FunctionBlockNode fnBlock)
{this.fnType=fnType;this.MainName=MainName;this.fnBlock = fnBlock;}

private String indent(String text)
{return " " + text.replace("\n","\n  ");}
public String toString()
{String declr = "Function Declaration :\n" + "Type : " + fnType.getLex() + " | Identifer : " + MainName.getLex();
return declr + "\n" + indent(fnBlock.toString());}
}
