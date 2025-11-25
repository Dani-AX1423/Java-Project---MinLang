import java.io.*;
import java.util.*;
public class ReadExpression
{
public static boolean isOperator(char op)
{
if(op=='*') return true;
else if(op=='/') return true;
else if(op=='+') return true;
else if(op=='-') return true;
else return false;
}
public static int precedence(String op)
{
if(op.equals("*") || op.equals("/")) return 2;
else if(op.equals("-") || op.equals("+")) return 1;
else return 0;
}
public static ArrayList<String> tokenize(String exp)
{
StringBuilder temp=new StringBuilder();
char ch;
ArrayList<String> tokens = new ArrayList<String>();
for(int i=0;i<exp.length();i++)
{

ch=exp.charAt(i);
if(Character.isDigit(ch))
{temp.append(ch);}
if(!Character.isDigit(ch))
{
if(temp.length()>0)
{tokens.add(temp.toString());
temp.setLength(0);}
if(isOperator(ch))
{tokens.add(Character.toString(ch));continue;}
}
}
if(temp.length()>0)
{tokens.add(temp.toString());}
return tokens;
}
public static String evaluate(ArrayList<String> tokens)
{int i=0;
String token=tokens.get(i);
ArrayList<String> pass1 = new ArrayList<String>();
ArrayList<String> pass2 = new ArrayList<String>();
while(i<tokens.size())
{

token=tokens.get(i);
if(!isOperator(token.charAt(0)))
{pass1.add(token);
i++;continue;}
else if(isOperator(token.charAt(0)))
{
try
{if(i-1>=0 && i+1<=tokens.size()-1 && precedence(token)==2 )
{
int left = Integer.valueOf(pass1.get(pass1.size()-1));
int right = Integer.valueOf(tokens.get(i+1));
pass1.remove(pass1.size()-1);
if(token.equals("*"))
{
pass1.add(String.valueOf(left*right));
//pass1.add(String.valueOf(Integer.valueOf(tokens.get(i-1))*Integer.valueOf(tokens.get(i+1))));
}
else if(token.equals("/"))
{
pass1.add(String.valueOf(left/right));
//pass1.add(String.valueOf(Integer.valueOf(tokens.get(i-1))/Integer.valueOf(tokens.get(i+1))));
}
i=i+2;
}
else
{pass1.add(token);i++;}}
catch(NumberFormatException e)
{System.out.println("error");}
}
}
i=0;
token =pass1.get(i);
while(i<pass1.size())
{

token=pass1.get(i);
if(!isOperator(token.charAt(0)))
{
pass2.add(token);
i++;
}
else if(isOperator(token.charAt(0)))
{
try
{if(i-1>=0 && i+1<=pass1.size()-1 && precedence(token)==1)
{
int left = Integer.valueOf(pass2.get(pass2.size()-1));
int right = Integer.valueOf(pass1.get(i+1));
pass2.remove(pass2.size()-1);

if(token.equals("-"))
{
pass2.add(String.valueOf(left-right));
//pass1.add(String.valueOf(Integer.valueOf(tokens.get(i-1))-Integer.valueOf(tokens.get(i+1))));
}
else if(token.equals("+"))
{
pass2.add(String.valueOf(left+right));
//pass1.add(String.valueOf(Integer.valueOf(tokens.get(i-1))+Integer.valueOf(tokens.get(i+1))));
}
i=i+2;
}
else
{i++;}
}

catch(NumberFormatException e)
{System.out.println("Error");i++;}
}


//System.out.println(pass2);
//System.out.println(token);
}
System.out.println(pass1);
return pass2.get(0);
}
public static void main(String args[]) 
{
String exp = "273 + 42 * 8";
System.out.println("Tokens : "+ tokenize(exp));
System.out.println("Evaluated : "+ evaluate(tokenize(exp)));
}
}
