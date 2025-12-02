package org.dani.lytrix.core.frontend.scanner.Lexing_Process;

import java.util.*;
import java.io.*;
import org.dani.lytrix.core.frontend.scanner.tokens.Token;
import org.dani.lytrix.core.frontend.scanner.tokens.TokenType;
//import org.dani.lytrix.core.frontend.scanner.tokens.*;

public class LexHelperFunctions
{
protected String SrcCode;
protected int idx;
protected int R;
protected int C;
protected LexHelperFunctions(String SrcCode)
{
this.SrcCode=SrcCode;
this.idx=0;
this.R=1;
this.C=1;
}


//Helper functions definitons //
//End of file reached or not
protected boolean isEOF() {
return idx>=SrcCode.length();
}

//walk in and retriever functions
protected char peek() {
char c;
if(isEOF()) return '\0'; 

else c = SrcCode.charAt(idx);
return c;
}
protected char advance() {

if(isEOF()) return '\0';
char c=peek();
if(c=='\n') {R++;C=1;}
else C++;

idx++;

return c;
}



//current character checker functions
protected boolean isAlpha(char c) {
return Character.isAlphabetic(c);
}
protected boolean isQuot(char c) {
return c=='\"';
}
protected boolean isSpace(char c) {
return Character.isWhitespace(c);
}
//0.0.1 - 0.0.3 versions don't require this
protected boolean isAlnum(char c) {
return (Character.isAlphabetic(c) || Character.isDigit(c));
}
protected boolean isDigit(char c) {
return Character.isDigit(c);
}





//Iterator function to read Token and strings using loop
protected String readToken() {
char c = peek();
StringBuilder curr = new StringBuilder();
while(!isEOF() && isAlnum(c))
{
curr.append(c);
advance();
c = peek();
}

return curr.toString();
}

protected String readString() {
char c = peek();
StringBuilder curr = new StringBuilder();
if(isQuot(c))
{advance();c=peek();}
while(!isEOF())
{
if(isQuot(c))
{advance();break;}
curr.append(c);
advance();
c = peek();
}
return curr.toString();
}
}
