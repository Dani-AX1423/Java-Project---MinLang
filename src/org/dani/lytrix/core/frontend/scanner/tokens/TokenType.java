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

//Other
STRING,
IDENT,
//return key
RETURN,

EOM, //Special return value





//end of file and end of line
EOF,
//EOL,

}
