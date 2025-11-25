import java.io.*;
public class  ReadPrintStat
{
public static void main(String args[])
{
String home_dir = System.getProperty("user.home");
String dest_dir = "/Projects/MinLang/MinFiles/SampleFiles/";
String gen_path = home_dir + dest_dir;
try
{
BufferedReader br = new BufferedReader(new FileReader(gen_path+"s1.min"));
String c,stat="";
while((c=br.readLine())!=null)
{stat+=c;}
if(stat.startsWith("print "))
{
String arguments = stat.substring(stat.indexOf("'")+1,stat.lastIndexOf("'"));
System.out.println(arguments);
}
}
catch(IOException e)
{System.out.println("Error : " + e.getMessage());}
}
}
