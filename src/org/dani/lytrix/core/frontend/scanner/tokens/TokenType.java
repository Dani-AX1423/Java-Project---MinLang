package org.dani.lytrix.core.frontend.scanner.tokens;


import java.util.*;
import java.io.*;

public enum TokenType
{
VOID, //void data type
BACKLINE, //custom mainfunction name

//symbols of structure (), {}
LPARA,RPARA,
LCURL,RCURL,
SEMI_COL,

//Statements
WRITE_SC,


//Types
INT,
CHAR,
STRING,
FLOAT,
DOUBLE,

//Operators
ASSIGN,


//Other
INT_LIT,
FLOAT_LIT,
DOUBLE_LIT,
CHAR_LIT,
STRING_LIT,

IDENT,
//return key
RETURN,

EOM, //Special main function return value





//end of file and end of line
EOF,
//EOL,

}
