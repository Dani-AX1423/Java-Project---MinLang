import java.nio.file.*;
import java.util.*;

enum TokenType
{
NUMBER,
PLUS,MINUS,
STAR,SLASH,
//LPAR,RPAR,
STRING,
IDENT,
KEY_PRINT,KEY_INPUT,KEY_LET,
ASSIGNMENT,
NEWLINE,
EOF
}
class Token
{
TokenType type;
String lex;
int row;
int col;
Token(TokenType type, String lex,int row,int col)
{
this.type=type;
this.lex=lex;
this.row=row;
this.col=col;
}
public String toString()
{ return type + " : " + lex + "\t[ row : " + row + " , column : " + col + " ] \n";}

}


class Lexer
{


String code;
int idx = 0 ;
int row=1;
int col=1;

ArrayList<Token> tokens = new ArrayList<Token>();
Lexer(String code)
{this.code=code;}


boolean isEOF()
{
return idx>=code.length();
}

boolean isEOL(char c)
{
return c=='\n';
}

char advance()
{
char c = peek();
if(isEOL(c))
{row++;col=1;}
else
{col++;}
idx++;
return c;
}

char peek()
{
if(isEOF())
{return '\0';} 
char c = code.charAt(idx);
return c;
}

boolean isOperator(char c)
{ return (c=='*'||c=='/'||c=='-'||c=='+'||c=='='); }

void addToken(TokenType type, String lex)
{
tokens.add(new Token(type,lex,row,col));
}

void readNumber()
{
StringBuilder curr_token = new StringBuilder();
curr_token.append(peek());
advance();
while(!isEOF() && Character.isDigit(peek()))
{curr_token.append(peek());advance();}
addToken(TokenType.NUMBER,curr_token.toString());
curr_token.setLength(0);
}

void readIdentifier()
{
StringBuilder curr_ident = new StringBuilder();
curr_ident.append(peek());
advance();
while(!isEOF() && (Character.isLetter(peek()) ||Character.isDigit(peek())))
{curr_ident.append(peek());advance();}
String id=curr_ident.toString();
if(id.equals("print"))
{addToken(TokenType.KEY_PRINT,id);}
else if(id.equals("let"))
{addToken(TokenType.KEY_LET,id);}
else if(id.equals("input"))
{addToken(TokenType.KEY_INPUT,id);}
else
{addToken(TokenType.IDENT,id);}
curr_ident.setLength(0);
}

void readString()
{
StringBuilder text = new StringBuilder();
while(peek()!='\'' && !isEOF())
{text.append(peek());advance();}
if(isEOF())
{System.out.println("Missing quotes");return;}
addToken(TokenType.STRING,text.toString());
}

public List<Token> lex()
{

while(!isEOF())
{
char c = peek();
if(c==' ')
{advance();continue;}
if(isEOL(c))
{addToken(TokenType.NEWLINE,"\\n");advance();continue;}
if(Character.isDigit(c))
{
readNumber();
continue;
}
else if(isOperator(c))
{
switch(c)
{
case '*':addToken(TokenType.STAR,"*");break;
case '/':addToken(TokenType.SLASH,"/");break;
case '-':addToken(TokenType.MINUS,"-");break;
case '+':addToken(TokenType.PLUS,"+");break;
case '=':addToken(TokenType.ASSIGNMENT,"=");break;
}
advance();
continue;
}
else if(Character.isLetter(c))
{
readIdentifier();
continue;
}
else if(c=='\'')
{
advance();
readString();
advance();
continue;
}

advance();


}
addToken(TokenType.EOF,"NULL");

return tokens;
}
}

//abstract classes representing statements and expressions
abstract class Stmt
{}
abstract class Expr
{}

//Main parser class that performs parsing
class Parser
{
List<Token> tokens;
int idx=0;
public Parser(List<Token> tokens)
{this.tokens=tokens;}

Token peek(){
if(idx>=tokens.size()) return new Token(TokenType.EOF,"NULL",-1,-1);
else return tokens.get(idx);
}
Token previous()
{return tokens.get(Math.max(0,idx-1));}

Token advance(){
if(!isAtEnd()) idx++;
return previous();
}
boolean isAtEnd()
{
return peek().type==TokenType.EOF;
}
boolean match(TokenType type){
if(isAtEnd()) return false;
if(peek().type==type) {advance();return true;}
else return false;
}
Token expect(TokenType type,String msg)
{
if(peek().type==type) return advance();
Token t = peek();
throw new RuntimeException("Error at line : " + t.row + "\nColumn : " + t.col + "\nFound : "+t.type+"\tLex : " + t.lex + "\nMessage : "+msg);
}

public Expr parsePrimary()
{
Token t = peek();
if(t.type==TokenType.NUMBER)
{advance();
return new NumberExpr(t.lex);}
else if(t.type==TokenType.STRING)
{advance();
return new StringExpr(t.lex);}
else if(t.type==TokenType.IDENT)
{advance();
return new VarExpr(t.lex);}
else
{throw new RuntimeException("Expected value but found : "+ t.type);}
}
public Expr parseTerm()
{
Expr left,right;
left = parseFactor();
right=null;
Token op=peek();
while(op.type==TokenType.STAR || op.type==TokenType.SLASH)
{
advance();
right = parseFactor();
left = new BinaryExpr(left,op,right);
op=peek();
}
return left;
}
public Expr parseFactor()
{
return parsePrimary();
}
public Expr parseExpression()
{
Expr left,right;
left = parseTerm();
right=null;
Token op=peek();
while(op.type==TokenType.PLUS || op.type==TokenType.MINUS)
{
advance();
right=parseTerm();
left= new BinaryExpr(left,op,right);
op=peek();
}
return left;
//return parsePrimary();
}

public Stmt parsePrint()
{
expect(TokenType.KEY_PRINT,"Expected print");
Expr expr = parseExpression();
return new PrintStmt(expr); 
}
public Stmt parseInput()
{
expect(TokenType.KEY_INPUT,"Expected input");
Token varName = expect(TokenType.IDENT,"Expected variable name");
return new InputStmt(varName.lex);
}
public Stmt parseLet()
{
expect(TokenType.KEY_LET,"Expected let");
Token varName = expect(TokenType.IDENT,"Expected variable name");
expect(TokenType.ASSIGNMENT,"Expected assignment operator");
Expr expr = parseExpression();
return new LetStmt(varName.lex,expr);
}
List<Stmt> parse()
{
List<Stmt> statements = new ArrayList<Stmt>();
while(!isAtEnd())
{Token t = peek();
if (t.type == TokenType.NEWLINE)
{advance();
continue;}
if(t.type==TokenType.KEY_PRINT)
{statements.add(parsePrint());}
else if(t.type==TokenType.KEY_INPUT)
{statements.add(parseInput());}
else if(t.type==TokenType.KEY_LET)
{statements.add(parseLet());}
else
{Token curr = peek();
throw new RuntimeException("Unexpected token at row " + curr.row + " col " + curr.col + ": " + curr.type + " lex : " + curr.lex);}
}
return statements;

}
}

class PrintStmt extends Stmt
{
Expr expr;
PrintStmt(Expr expr)
{this.expr=expr;}
}

class LetStmt extends Stmt
{
String name;
Expr expr;
LetStmt(String name, Expr expr)
{this.name=name;this.expr=expr;}
}

class InputStmt extends Stmt
{
String name;
InputStmt(String name)
{this.name=name;}
}


class NumberExpr extends Expr {
    String value;
    NumberExpr(String value) {this.value = value;}
}

class StringExpr extends Expr {
    String value;
    StringExpr(String value) {this.value = value;}
}

class VarExpr extends Expr {
    String name;
    VarExpr(String name) {this.name = name;}
}
class BinaryExpr extends Expr {
    Token op;
    Expr left;
    Expr right;
    BinaryExpr(Expr left,Token op,Expr right) {
    this.op=op;
    this.left=left;
    this.right=right;
    }
}
class DummyExpr extends Expr {
    String value;
    DummyExpr(String value) {this.value=value;}
}

class Interpreter
{
Object value;
Stmt stmt;
Map<String,Object> Variables = new HashMap<String,Object>();

public Interpreter()
{this.value=null;this.stmt=null;}

void executeStmt(Stmt statement)
{
Scanner sc = new Scanner(System.in);
if(statement instanceof LetStmt)
{
LetStmt s;
s = (LetStmt) statement;
value = evalExpr(s.expr);
Variables.put(s.name,value);
}
else if(statement instanceof PrintStmt)
{
PrintStmt s;
s = (PrintStmt) statement;
value = evalExpr(s.expr);
System.out.println(value);
}
else if(statement instanceof InputStmt)
{
InputStmt s;
s = (InputStmt) statement;
value = sc.nextLine();
Variables.put(s.name,value);
}
else
{throw new RuntimeException("Unknown Statement : "+statement);}
}
void execute(List<Stmt> statements)
{for(Stmt statement:statements)
{executeStmt(statement);}
}
Object evalExpr(Expr E)
{
if(E instanceof NumberExpr num)
{return Integer.parseInt(num.value);}
else if(E instanceof StringExpr str)
{return str.value;}
else if(E instanceof VarExpr varName)
{return Variables.get(varName.name);}
else if(E instanceof BinaryExpr Bin)
{
int L = (int)evalExpr(Bin.left);
int R = (int)evalExpr(Bin.right);
String Op=Bin.op.lex;
switch(Op)
{
case "*" : return L*R;
case "/" : return L/R;
case "-" : return L-R;
case "+" : return L+R;
default: throw new RuntimeException("Unknown operator : "+Op);
}
}
else
{return null;}
}

}


public class MinCompiler {

    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            System.out.println("Usage:");
            System.out.println("  compile <file.min>");
            System.out.println("  run <executable>");
            return;
        }

        String command = args[0];

        if (command.equals("compile")) {
            compileMinLang(args[1]);
        }
        else if (command.equals("run")) {
            runExecutable(args[1]);
        }
        else {
            System.out.println("Unknown command: " + command);
        }
    }


    // -------------------------- COMPILATION PIPELINE --------------------------
    static void compileMinLang(String filename) throws Exception {

        String code = Files.readString(Path.of(filename));

        // 1. LEXER
        Lexer lexer = new Lexer(code);
        List<Token> tokens = lexer.lex();

        // 2. PARSER
        Parser parser = new Parser(tokens);
        List<Stmt> program = parser.parse();

        // 3. IR BUILDER
        IRBuilder irb = new IRBuilder();
        String ir = irb.gen(program);

        String llFile = filename.replace(".min", ".ll");
        String sFile = filename.replace(".min", ".s");
        String exeFile = filename.replace(".min", "");

        // Write IR
        Files.writeString(Path.of(llFile), ir);
        System.out.println("Generated LLVM IR → " + llFile);

        // Compile IR -> Assembly
        Process llc = new ProcessBuilder("llc", llFile).inheritIO().start();
        llc.waitFor();
        System.out.println("Generated Assembly → " + sFile);

        // Assembly -> Executable
        Process clang = new ProcessBuilder("clang", sFile, "-o", exeFile).inheritIO().start();
        clang.waitFor();
        System.out.println("Generated Executable → " + exeFile);

        System.out.println("Compilation finished.");
    }


    // -------------------------- RUN EXECUTABLE --------------------------
    static void runExecutable(String exeName) throws Exception {
        Process run = new ProcessBuilder("./" + exeName).inheritIO().start();
        run.waitFor();
    }
}

