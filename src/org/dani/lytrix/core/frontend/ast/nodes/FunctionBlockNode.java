package org.dani.lytrix.core.frontend.ast.nodes;
import org.dani.lytrix.core.frontend.ast.baseNodes.*;
import org.dani.lytrix.core.frontend.scanner.tokens.*;
public class FunctionBlockNode extends AbstractBlock {
private ReturnNode fnRetMain;
public FunctionBlockNode(ReturnNode fnRetMain)
{this.fnRetMain = fnRetMain;}

private String indent(String text)
{return " " + text.replace("\n","\n  ");}
public String toString()
{return "Function Block : \n" + indent(fnRetMain.toString());}
}
