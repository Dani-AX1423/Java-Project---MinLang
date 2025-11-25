import java.io.*;
import java.util.*;
public class ReadInput
{
public static void main(String args[])
{
String home_dest = System.getProperty("user.home");
String dest_dir = "/Projects/MinLang/MinFiles/SampleFiles/";
String gen_path = home_dest + dest_dir; //generak path for minfiles
Scanner sc = new Scanner(System.in);
List<String> varName =  new ArrayList<String>(); //List variable for storing variable names
List<String> varValue = new ArrayList<String>(); //List variable for storing variable values
HashMap<String,String> Variables = new HashMap<>();
try{
//Creating file object variable from a file in  gen_path location 
BufferedReader br = new BufferedReader(new FileReader(gen_path+"s2.min"));
String stat=""; // statement variable that holds each statement or code


while((stat=br.readLine())!=null)//Iterate and reading through each line in the s1.min file
{
if(stat.startsWith("input ")) //checks if it is  input keyword
{
stat=stat.substring(6).trim();
String val = sc.next();
Variables.put(stat,val);
}

else if(stat.startsWith("print ")) //checks for print statment
{
stat = stat.substring(6); //ignoring print keyword 
String parts[] = stat.split(","); //splitting the entire statement as it may have multiple data values to prin
for(String part : parts) //loop to read each set of the data (either maybe a text or variable call)
{
if(part.startsWith("'")) //if it is a text then print the value inside ''
{String text = part.substring(part.indexOf("'") + 1, part.lastIndexOf("'"));
System.out.print(text+" ");}
else //if it is a variable call , then search it in list variable and find its position and print the value stored in the variable
{String varCall = part.trim();
System.out.print(Variables.get(varCall)+" ");}
}
System.out.println();//indicating one statement line is over so default inebreak 
}

else if(stat.startsWith("let "))//if it is variable declaration
{
String vname = stat.substring(stat.indexOf(" ")+1,stat.indexOf("=")).trim();
 //read variable name store the variable name alone in list variable
String Value = stat.substring(stat.indexOf("=")+1).trim(); //read its value and store it in another list variable
if (Value.startsWith("'") && Value.endsWith("'")) //if the variable value is a string, read value between ''
{
Variables.put(vname,Value.substring(1,Value.length()-1));
}
else //if the data value is normal integer or other type of data then simply store it in list variable
{
    Variables.put(vname,Value);
}

}
}
}
catch(IOException e)
{System.out.println("Error : " + e.getMessage());}
}
}
