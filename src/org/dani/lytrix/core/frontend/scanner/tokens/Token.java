package org.dani.lytrix.core.frontend.scanner.tokens;

import java.util.*;
import java.io.*;

public class Token {
    TokenType type;
    String lex;
    int row;
    int col;

    public Token(TokenType type, String lex, int row, int col) {
        this.type = type;
        this.lex = lex;
        this.row = row;
        this.col = col;
    }

    // display current token
    public String toString() {
        return type + " : " + lex + "\t [ Line : " + row + " | " + "Position " + col + " ]";
    }

    // Helper Functions for fetching particular data

    public final TokenType getType() {
        return this.type;
    }

    public final String getLex() {
        return this.lex;
    }

    public final int getLine() {
        return this.row;
    }

    public final int getPos() {
        return this.col;
    }

}
