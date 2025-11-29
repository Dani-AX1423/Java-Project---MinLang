package org.dani.lytrix.core.frontend.parser;

import org.dani.lytrix.core.frontend.scanner.tokens.*;
import org.dani.lytrix.core.frontend.scanner.Lexing_Process.*;
import org.dani.lytrix.core.frontend.ast.baseNodes.*;
import org.dani.lytrix.core.frontend.ast.nodes.*;

import java.util.*;
import java.io.*;

/*
//abstract classes representing structures of program and its sub-parts
abstract class AbstractProgram
{
//...
}
abstract class AbstractFunctionDeclr 
{
//...
}
abstract class AbstractBlock 
{
//...
}
abstract class AbstractReturn 
{
//...
}

*/

//child classes implemented from abstract class for usage






//Main Parser class for parsing operation 
public class Parser 
{
private List<Token> tokens;
//private Token current;
private int idx;


public Parser(List<Token> tokens)
{
this.tokens = tokens;
this.idx =0;
//this.current = tokens.get(idx);
}



private Token peek() {
return tokens.get(idx);
}
private boolean isAtEnd() {
return peek().getType()==TokenType.EOF;
}
private Token previous() {
return tokens.get(idx-1);
}
private Token advance() {
if(!isAtEnd())
{
idx++;
}
return previous();
}

private boolean check(TokenType type) {
if(isAtEnd()) return false;
return peek().getType() == type;
}
private boolean match(TokenType... types) {
for(TokenType type : types)
{ 
if(check(type)) 
{advance();return true;}
}
return false;
}
private Token expect(TokenType type,String errorMessage) {
if(check(type)) return advance();
else throw new RuntimeException(errorMessage + " at Line : " + peek().getLine() + " .Position : " + peek().getPos() + "\nBut Received : " + peek().getType());
}


//Parsing Functions
public ProgramNode parse()
{return parseProgram();}
private ProgramNode parseProgram()
{
return new ProgramNode(parseFunction());
}

private FunctionDeclrNode parseFunction()
{
Token mType = expect(TokenType.VOID,"Function should have a return Type!");
Token mName = expect(TokenType.BACKLINE,"Main function name should be \"BackLine\"!");
expect(TokenType.LPARA,"Expected '('");
expect(TokenType.RPARA,"Expected ')'");
expect(TokenType.LCURL,"Expected '{'");
FunctionBlockNode fnBlock = parseFunctionBody();
expect(TokenType.RCURL,"Expected '}'");
return new FunctionDeclrNode(mType,mName,fnBlock);
}

private FunctionBlockNode parseFunctionBody()
{
//no feature to write code inside function
return new FunctionBlockNode(parseReturn());
}

private ReturnNode parseReturn()
{
expect(TokenType.RETURN,"Expected return keyword!");
Token retVal = expect(TokenType.EOM,"Main function must return special object \"EOM\"!");
expect(TokenType.SEMI_COL,"Line should be terminated by semicolon(;).");
return new ReturnNode(retVal);
}


}
