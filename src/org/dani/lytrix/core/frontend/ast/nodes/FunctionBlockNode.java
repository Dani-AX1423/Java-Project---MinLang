package org.dani.lytrix.core.frontend.ast.nodes;
//Required : Abstract Classes,Token class, NodeVisitor interface
import org.dani.lytrix.core.frontend.ast.baseNodes.*;
import org.dani.lytrix.core.frontend.scanner.tokens.*;
import org.dani.lytrix.core.frontend.ast.visitors.NodeVisitor;

import java.util.*;
import java.io.*;

//function block and its code are turned into nodes or tree here
public class FunctionBlockNode extends AbstractBlock {
private List<AbstractStatement> fnStmts;
private ReturnNode fnRet;
public FunctionBlockNode(List<AbstractStatement> fnStmts,ReturnNode fnRet)
{this.fnStmts = fnStmts;
this.fnRet = fnRet;}

//special functions for displaying in string format for human readability

public String toString()
{
StringBuilder blockStmts = new StringBuilder();
for(AbstractStatement stmt : fnStmts)
{
blockStmts.append("  ").append(stmt.toString()).append("\n");
}
blockStmts.append("  ").append(fnRet.toString());
return blockStmts.toString();
}

//node value retriever for return statement
public List<AbstractStatement> getStmts()
{return fnStmts;}
public ReturnNode getfnRet()
{return fnRet;}

//accept function to verify logic and operation and call the next node visitor
@Override
public <R> R accept(NodeVisitor<R> tree)
{return tree.visitFunctionBlock(this);}
}
