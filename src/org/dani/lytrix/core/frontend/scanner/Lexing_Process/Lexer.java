package org.dani.lytrix.core.frontend.scanner.Lexing_Process;

import java.util.*;
import java.io.*;
import org.dani.lytrix.core.frontend.scanner.tokens.Token;
import org.dani.lytrix.core.frontend.scanner.tokens.TokenType;
//import org.dani.lytrix.core.frontend.scanner.tokens.*;

public class Lexer extends LexHelperFunctions
{
List<Token> Tokens = new ArrayList<Token>(); 
public Lexer(String SrcCode)
{
super(SrcCode);
}

//Helper functions to retrive token
private Token genToken(TokenType type,String lex,int R,int C)
{return new Token(type,lex,R,C);}
//function that appends new tokens into list
private void addToken(Token t) {
//Token tok = genToken(type,lex,R,C);
Tokens.add(t);
}


//Special helper functions
//Token Classification functions
private boolean checkDType(String curr) {
if(curr.equals("void")) return true;
else return false;
}
private boolean checkBackLine(String curr) {
if(curr.equals("BackLine")) return true;
else return false;
}
private boolean checkIO(String curr) {
if(curr.equals("writeSc")) return true;
else return false;
}
private boolean checkReturn(String curr) {
if(curr.equals("return")) return true;
else return false;
}
private boolean checkEOM(String curr) {
if(curr.equals("EOM")) return true;
else return false;
}

//classify data types
private TokenType classifyDType(String type)
{
if(type.equals("void")) return TokenType.VOID;
else throw new RuntimeException("Error! invalid data type");
}

//Token Classifier
private Token identifyString(String curr)
{
if(checkDType(curr)) return genToken(classifyDType(curr),curr,R,C);
else if(checkBackLine(curr)) return genToken(TokenType.BACKLINE,curr,R,C);
else if(checkIO(curr)) return genToken(TokenType.WRITE_SC,curr,R,C);
else if(checkReturn(curr)) return genToken(TokenType.RETURN,curr,R,C);
else if(checkEOM(curr)) return genToken(TokenType.EOM,curr,R,C);
else {
addToken(genToken(TokenType.IDENT,curr,R,C));
throw new RuntimeException("Error!Invalid token");
}
}

//read one token at a time
private Token getToken()
{
char c=peek();
while(!isEOF() && Character.isWhitespace(c)) {
advance();
c=peek();
}
if(isEOF()) return genToken(TokenType.EOF,"\\n",R,C);
else if(isAlpha(c))
{String curr = readToken();
return identifyString(curr);}
else if(c=='(')
{advance();
return genToken(TokenType.LPARA,Character.toString(c),R,C);}
else if(isQuot(c))
{String curr = readString();
return genToken(TokenType.STRING,curr,R,C);}
else if(c==')')
{advance();
return genToken(TokenType.RPARA,Character.toString(c),R,C);}
else if(c=='{')
{advance();
return genToken(TokenType.LCURL,Character.toString(c),R,C);}
else if(c=='}')
{advance();
return genToken(TokenType.RCURL,Character.toString(c),R,C);}
else if(c==';')
{advance();
return genToken(TokenType.SEMI_COL,Character.toString(c),R,C);}
else 
{throw new RuntimeException("Invalid Token" + C);}
}


//Final function that fetches all the availbale tokens from program
public List<Token> getTokens()
{
Token t;
while(true)
{
t = getToken();
addToken(t);
if(t.getType()==TokenType.EOF) break;
}
return Tokens;
}

}
