package org.dani.lytrix.core.frontend.parser;

import org.dani.lytrix.core.frontend.scanner.tokens.*;
import org.dani.lytrix.core.frontend.scanner.Lexing_Process.*;
import org.dani.lytrix.core.frontend.ast.baseNodes.*;
import org.dani.lytrix.core.frontend.ast.expr.BinaryExpression;
import org.dani.lytrix.core.frontend.ast.expr.IdentifierExpression;
import org.dani.lytrix.core.frontend.ast.expr.LiteralExpression;
import org.dani.lytrix.core.frontend.ast.nodes.*;


//exception class:;
import org.dani.lytrix.core.errors.exceptions.SyntaxException;


//...
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
// sorry they are in different folders haha. 

//Main Parser class for parsing operation 
public class Parser {
    private List<Token> tokens;
    // private Token current;
    private int idx;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.idx = 0;
        // this.current = tokens.get(idx);
    }

    // Basic helper functions for iteration
    private Token peek() {
        return tokens.get(idx);
    }

    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    private Token previous() {
        return tokens.get(idx - 1);
    }

    private Token advance() {
        if (!isAtEnd()) {
            idx++;
        }
        return previous();
    }

    // Additional helper functions for syntax checking
    private boolean check(TokenType type) {
        if (isAtEnd())
            return false;
        return peek().getType() == type;
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                return true;
            }
        }
        return false;
    }

    private Token expect(TokenType type, String errorMessage) {
        if (check(type))
            return advance();
        else
        {    
            Token errTok = peek();
            throw new SyntaxException(errorMessage + " but received '" + errTok.getLex() + "'", errTok.getLine());
            /*throw new RuntimeException(errorMessage + " at Line : " + peek().getLine() + " .Position : "
                    + peek().getPos() + "\nBut Received : " + peek().getType());
            */
        }
    }
    //

    //
    // specific helper functions for useless things or is it?

    private boolean checkDType() {
        boolean dtype;
        dtype = match(TokenType.INT, TokenType.CHAR, TokenType.FLOAT, TokenType.DOUBLE, TokenType.STRING);
        return dtype;
    }

    private boolean checkLiteral() {
        boolean lit;
        lit = match(TokenType.INT_LIT, TokenType.CHAR_LIT, TokenType.FLOAT_LIT, TokenType.DOUBLE_LIT,
                TokenType.STRING_LIT);
        return lit;
    }

    private AbstractExpression fetchOPArg() {
        if (check(TokenType.RPARA)) {
            // advance();
            return null;
        } 
        return parseExpression();
    }

    // Main parser function that creates the AST
    public ProgramNode parse() {
        return parseProgram();
    }

    // Parsing Functions that parses program and creates respective nodes
    private ProgramNode parseProgram() {
        return new ProgramNode(parseFunction());
    }

    private FunctionDeclrNode parseFunction() {
        return parseMainFunction();
    }

    private FunctionDeclrNode parseMainFunction() {
        Token mType = expect(TokenType.VOID, "Function should have a return Type!");
        Token mName = expect(TokenType.BACKLINE, "Main function name should be \"BackLine\"!");
        expect(TokenType.LPARA, "Expected '('");
        expect(TokenType.RPARA, "Expected ')'");
        expect(TokenType.LCURL, "Expected '{'");
        FunctionBlockNode fnBlock = parseFunctionBody();
        expect(TokenType.RCURL, "Expected '}'");
        return new FunctionDeclrNode(mType, mName, fnBlock);
    }

    private FunctionBlockNode parseFunctionBody() {
        List<AbstractStatement> stmts = parseStatements();
        ReturnNode ret = parseReturn();
        return new FunctionBlockNode(stmts, ret);
    }

    private List<AbstractStatement> parseStatements() {
        List<AbstractStatement> stmts = new ArrayList<AbstractStatement>();
        while (!isAtEnd() && !check(TokenType.RETURN) && !check(TokenType.RCURL)) {
            AbstractStatement stmt = parseStatement();
            stmts.add(stmt);
        }
        return stmts;
    }

    private AbstractStatement parseStatement() {
        if (match(TokenType.WRITE_SC))
            return parseOutput();
        else if (checkDType())
            return parseVarDeclrOrInit();
        else if (check(TokenType.IDENT))
            return parseVarAssign();
        else
            throw new SyntaxException("Unexpected Statement - '" + peek().getLex() + "'", peek().getLine());
    }

    private AbstractExpression parseExpression() {
        return parseTerm();
    }

    private AbstractExpression parseTerm() {
        AbstractExpression expr = parseFactor();
        
        while(check(TokenType.PLUS) || check(TokenType.MINUS)) {
            Token operator = advance();
            AbstractExpression right = parseFactor();
            expr = new BinaryExpression(expr, operator, right);
        }
        return expr;

    }
    private AbstractExpression parseFactor() {
        AbstractExpression expr = parsePrimary();

        while(check(TokenType.STAR) || check(TokenType.SLASH) || check(TokenType.MOD)) {
            Token operator = advance();
            AbstractExpression right = parsePrimary();
            expr = new BinaryExpression(expr, operator, right);
        }
        return expr;
    }
    private AbstractExpression parsePrimary() {
        
        if(checkLiteral()) {
            Token literal = advance();
            return new LiteralExpression(literal);
        }
        if(check(TokenType.IDENT)) {
            Token identifier = advance();
            return new IdentifierExpression(identifier);
        }
        if(check(TokenType.LPARA)) {
            advance();
            AbstractExpression expr = parseExpression();
            expect(TokenType.RPARA, "Missing closing parentheses ')'");
            return expr;
        }
        
        throw new SyntaxException("Invalid expression - '" + peek().getLex() + "'", peek().getLine());
    }

    private AbstractStatement parseVarDeclrOrInit() {
        Token type = advance();
        Token identifier = expect(TokenType.IDENT, "Expected Identifier name.");
        AbstractExpression expr;
        if (check(TokenType.SEMI_COL)) {
            expect(TokenType.SEMI_COL, "Statement should be terminated by semicolon");
            return new VarDeclrNode(type, identifier);
        } else if (check(TokenType.ASSIGN)) {
            advance();
            if(check(TokenType.READ_SC)) {
                expr = parseInput();
            }
            else {
                expr = parseExpression();
                
            }
            expect(TokenType.SEMI_COL, "Statement should be terminated by semicolon");
            return new VarInitNode(type, identifier, expr);
            /* if (checkLiteral()) {
                //Token literal = advance();
                
                expect(TokenType.SEMI_COL, "Statement should be terminated by semicolon");
                return new VarInitNode(type, identifier, literal);
            }
            else if (check(TokenType.IDENT)) {
                Token identLiteral = advance();
                expect(TokenType.SEMI_COL, "Statement should be terminated by semicolon");
                return new VarInitNode(type, identifier, identLiteral);
            } else if (check(TokenType.READ_SC)) {
                return parseInput(type, identifier);
            } 
            else {
                throw new RuntimeException("Invalid literal object");
            }*/

        } else {
            throw new SyntaxException("Invalid variable declaration",peek().getLine());
        }

    }

    private AbstractStatement parseVarAssign() {
        Token identifier = advance();
        AbstractExpression expr;
        expect(TokenType.ASSIGN, "Expected assignment operator after identifier");
        if(check(TokenType.READ_SC)) {
                expr = parseInput();
                
        }
        else {
            expr = parseExpression();
            
        }
        expect(TokenType.SEMI_COL, "Statement should be terminated by semicolon"); 
        return new VarAssignNode(identifier, expr);
        /* if (checkLiteral()) {
            Token literal = advance();
            expect(TokenType.SEMI_COL, "Statement should be terminated by semicolon");
            return new VarAssignNode(identifier, literal);

        } else if (check(TokenType.IDENT)) {
            Token identLiteral = advance();
            expect(TokenType.SEMI_COL, "Statement should be terminated by semicolon");
            return new VarAssignNode(identifier, identLiteral);

        } else if (check(TokenType.READ_SC)) {
            return parseInput(null, identifier);
        }
        // else if () {} readSc operation hahahahahaha........... oh wait it got
        // implemented above :D
        else {
            throw new RuntimeException("Invalid literal object");
        } */

    }

    private InputNode parseInput() {
        // Token identifier = ident;
        expect(TokenType.READ_SC, "Expected readSc");
        expect(TokenType.LPARA, "Expected '(' ");
        if (checkDType()) {
            Token argType = advance();
            expect(TokenType.RPARA, "Expected ')' ");
            //expect(TokenType.SEMI_COL, "Statements should be terminated by semicolon");
            return new InputNode(argType);
        } else {
            throw new SyntaxException("Invalid type argument inside readSc()", peek().getLine());
        }
    }

    private OutputNode parseOutput() {
        expect(TokenType.WRITE_SC, "Expected writeSc");
        expect(TokenType.LPARA, "Expected '(' ");
        AbstractExpression arg = fetchOPArg();// expect(TokenType.STRING_LIT, "Expected String literal");
        expect(TokenType.RPARA, "Expected ')' ");
        expect(TokenType.SEMI_COL, "Statements should be terminated by semicolon");
        return new OutputNode(arg);
    }

    private ReturnNode parseReturn() {
        expect(TokenType.RETURN, "Expected return keyword!");
        Token retVal = expect(TokenType.EOM, "Main function must return special object \"EOM\"!");
        expect(TokenType.SEMI_COL, "Line should be terminated by semicolon(;).");
        return new ReturnNode(retVal);
    }

}
