package org.dani.lytrix.core.frontend.ast.visitors;
import org.dani.lytrix.core.frontend.ast.expr.*;
//Required : ast child node classes
import org.dani.lytrix.core.frontend.ast.nodes.*;

//a standard interface for visitor functions for visitng respective nodes (implementation referral : Intepreter.java)
public interface NodeVisitor<R> {

R visitProgram(ProgramNode node);
R visitFunctionDeclr(FunctionDeclrNode node);
R visitFunctionBlock(FunctionBlockNode node);
R visitVarDeclr(VarDeclrNode node);
R visitVarAssign(VarAssignNode node);
R visitVarInit(VarInitNode node);
R visitInput(InputNode node);
R visitOutput(OutputNode node);
R visitReturn(ReturnNode node);

//
R visitLiteralExpression(LiteralExpression expr);
R visitIdentifierExpression(IdentifierExpression expr);
R visitBinaryExpression(BinaryExpression expr);
}
