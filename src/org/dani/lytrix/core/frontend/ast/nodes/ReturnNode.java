package org.dani.lytrix.core.frontend.ast.nodes;
import org.dani.lytrix.core.frontend.ast.baseNodes.*;
import org.dani.lytrix.core.frontend.scanner.tokens.*;
public class ReturnNode extends AbstractReturn {
Token value;
public ReturnNode(Token value)
{this.value=value;}

public String toString()
{return "Return: \n" + "Value : " + value.getLex();}
}
