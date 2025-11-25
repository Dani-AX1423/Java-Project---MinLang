import java.io.*;
public class FileOperation
{
public static void main(String args[]) 
{
String home_dir = System.getProperty("user.home");
String dest_dir = "/Projects/MinLang/MinFiles/";
String gen_path = home_dir + dest_dir;
try
{
FileWriter fw = new FileWriter(gen_path + "SampleFiles/s1.min");
fw.write("print 'Hello Lyra' ");
fw.close();
}
catch(IOException e)
{System.out.println("Error Occured : " + e.getMessage());}
}
}
