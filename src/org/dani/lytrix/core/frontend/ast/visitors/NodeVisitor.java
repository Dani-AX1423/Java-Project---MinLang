package org.dani.lytrix.LyTrix_R.backend.interpreter;
//Required : ast child node classes
import org.dani.lytrix.core.frontend.ast.nodes.*;

//a standard interface for visitor functions for visitng respective nodes (implementation referral : Intepreter.java)
public interface NodeVisitor<R> {

R visitProgram(ProgramNode node);
R visitFunctionDeclr(FunctionDeclrNode node);
R visitFunctionBlock(FunctionBlockNode node);
R visitReturn(ReturnNode node);

}
