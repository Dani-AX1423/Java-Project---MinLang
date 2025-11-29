package org.dani.lytrix.core.frontend.ast.nodes;
import org.dani.lytrix.core.frontend.ast.baseNodes.*;
import org.dani.lytrix.core.frontend.scanner.tokens.*;
public class ProgramNode extends AbstractProgram {
private FunctionDeclrNode fnMain;
public ProgramNode(FunctionDeclrNode fnMain)
{this.fnMain = fnMain;}

private String indent(String text)
{return " " + text.replace("\n","\n  ");}
public String toString()
{return "Program : \n" + indent(fnMain.toString());}
}
