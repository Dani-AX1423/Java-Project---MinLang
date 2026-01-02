package org.dani.lytrix.core.api.cli;
//Essential packages io,utilities are imported 
import java.util.*;
import java.io.*;


//Required : Program file reader class, Tokens classes , Lexing classes, Main Parser class, ASTNode parent class , interpreter class 
import org.dani.lytrix.core.frontend.scanner.streams.SourceReader;
import org.dani.lytrix.core.frontend.scanner.tokens.*;
//import org.dani.lytrix.core.frontend.scanner.tokens.TokenType;
import org.dani.lytrix.core.frontend.scanner.Lexing_Process.*;
//import org.dani.lytrix.core.frontend.scanner.Lexing_Process.LexHelperFunctions;
import org.dani.lytrix.core.frontend.parser.*;
import org.dani.lytrix.LyTrix_R.backend.interpreter.*;
import org.dani.lytrix.core.frontend.ast.ASTNode;


//Main class that actually reads program file and runs and gives output for code
public class LytrixRunner
{
public static void main(String args[])
{
Scanner sc = new Scanner(System.in); //input class 
String path; //path of file
String SrcCode;//program code stores as string
System.out.print("Enter the correct path of file : ");
path = sc.nextLine();
SourceReader sr = new SourceReader(path); //Program file reader class
try {
SrcCode = sr.CodeReader(); //reads programfile under given name and processes each line as strings and returns it
Lexer lexical = new Lexer(SrcCode); //lexer class for lexing
List<Token> tokens = new ArrayList<Token>();
tokens = lexical.getTokens(); //Token list which stores lexed tokens
Parser pars = new Parser(tokens);
ASTNode AST= pars.parse(); //AST node obtained by parsing operation
System.out.println("\nTokens:\n");
for(Token token:tokens)
{System.out.println(token);} //prints each token
System.out.println("\nParsed values : \n" + AST); //prints AST in human readable form (special helper : toString() function)
Interpreter i = new Interpreter(AST); //interpreter class initialization
System.out.println("\n\nOutput : ");
i.interpret();//call interpret function that visits and gives output for ast
System.out.println(i.End());
}
catch(IOException e) //error message if file not found
{System.out.println("File error! : " + e.getMessage());}


}
}


