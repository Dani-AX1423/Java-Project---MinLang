package org.dani.lytrix.core.frontend.scanner.Lexing_Process;

import java.util.*;
import java.io.*;
import org.dani.lytrix.core.frontend.scanner.tokens.Token;
import org.dani.lytrix.core.frontend.scanner.tokens.TokenType;
//import org.dani.lytrix.core.frontend.scanner.tokens.*;

public class Lexer extends LexHelperFunctions {
    List<Token> Tokens = new ArrayList<Token>();

    public Lexer(String SrcCode) {
        super(SrcCode);
    }

    // Helper functions to retrive token
    private Token genToken(TokenType type, String lex, int R, int C) {
        return new Token(type, lex, R, C);
    }

    // function that appends new tokens into list
    private void addToken(Token t) {
        // Token tok = genToken(type,lex,R,C);
        Tokens.add(t);
    }

    // Special helper functions
    // Token Validation functions for classification
    private boolean checkDType(String curr) {
        if ((curr.equals("void")) || (curr.equals("int")) || (curr.equals("char")) || (curr.equals("float"))
                || (curr.equals("double")) || (curr.equals("string")))
            return true;
        else
            return false;
    }

    private boolean checkLiteral(String curr) {
        if (curr == null)
            return false;

        try {
            Integer.parseInt(curr);
            return true;
        } catch (NumberFormatException e1) {
            try {
                Float.parseFloat(curr);
                return true;
            } catch (NumberFormatException e2) {
                try {
                    Double.parseDouble(curr);
                    return true;
                } catch (NumberFormatException e3) {
                    return false;
                }
            }

        }
    }

    private boolean checkBackLine(String curr) {
        if (curr.equals("BackLine"))
            return true;
        else
            return false;
    }

    private boolean checkIO(String curr) {
        if (curr.equals("writeSc"))
            return true;
        else
            return false;
    }

    private boolean checkReturn(String curr) {
        if (curr.equals("return"))
            return true;
        else
            return false;
    }

    private boolean checkEOM(String curr) {
        if (curr.equals("EOM"))
            return true;
        else
            return false;
    }

    // Special classifier functions for returning correct TokenType
    // classify data types
    private TokenType classifyDType(String type) {
        if (type.equals("void"))
            return TokenType.VOID;
        else if (type.equals("int"))
            return TokenType.INT;
        else if (type.equals("char"))
            return TokenType.CHAR;
        else if (type.equals("float"))
            return TokenType.FLOAT;
        else if (type.equals("double"))
            return TokenType.DOUBLE;
        else if (type.equals("string"))
            return TokenType.STRING;
        else
            throw new RuntimeException("Error! invalid data type");
    }

    // classify literals
    private TokenType classifyNumLiteral(String lit) {
        if (lit.contains(".")) {
            if (lit.length() <= 7) {
                return TokenType.FLOAT_LIT;
            } else {
                return TokenType.DOUBLE_LIT;
            }
        } else {
            return TokenType.INT_LIT;
        }
    }

    // Token identifier function that
    private Token identifyString(String curr) {
        if (checkDType(curr))
            return genToken(classifyDType(curr), curr, R, C);
        else if (checkBackLine(curr))
            return genToken(TokenType.BACKLINE, curr, R, C);
        else if (checkIO(curr))
            return genToken(TokenType.WRITE_SC, curr, R, C);
        else if (checkReturn(curr))
            return genToken(TokenType.RETURN, curr, R, C);
        else if (checkEOM(curr))
            return genToken(TokenType.EOM, curr, R, C);
        else if (checkLiteral(curr))
            return genToken(classifyNumLiteral(curr), curr, R, C);
        else {
            return genToken(TokenType.IDENT, curr, R, C);
        }
        // throw new RuntimeException("Error!Invalid token");
    }



    // read one token at a time
    private Token getToken() {
        char c = peek();
        while (!isEOF() && Character.isWhitespace(c)) {
            advance();
            c = peek();
        }
        if (isEOF())
            return genToken(TokenType.EOF, "\\n", R, C);
        else if (isAlpha(c)) {
            String curr = readToken();
            return identifyString(curr);
        } else if (c == '(') {
            advance();
            return genToken(TokenType.LPARA, Character.toString(c), R, C);
        } else if (c == ')') {
            advance();
            return genToken(TokenType.RPARA, Character.toString(c), R, C);
        } else if (c == '{') {
            advance();
            return genToken(TokenType.LCURL, Character.toString(c), R, C);
        } else if (c == '=') {
            advance();
            return genToken(TokenType.ASSIGN, Character.toString(c), R, C);
        } else if (c == '}') {
            advance();
            return genToken(TokenType.RCURL, Character.toString(c), R, C);
        } else if (c == ';') {
            advance();
            return genToken(TokenType.SEMI_COL, Character.toString(c), R, C);
        }
        // Literal Checker
        else if (isDigit(c)) {
            String curr = readNumber();
            return identifyString(curr);
        } else if (isSQuot(c)) {
            String curr = readChar();
            return genToken(TokenType.CHAR_LIT, curr, R, C);
        } else if (isDQuot(c)) {
            String curr = readString();
            return genToken(TokenType.STRING_LIT, curr, R, C);
        }

        // Invalid token
        else {
            throw new RuntimeException("Invalid Token" + C);
        }
    }

    // Final function that fetches all the availbale tokens from program
    public List<Token> getTokens() {
        Token t;
        while (true) {
            t = getToken();
            addToken(t);
            if (t.getType() == TokenType.EOF)
                break;
        }
        return Tokens;
    }

}
