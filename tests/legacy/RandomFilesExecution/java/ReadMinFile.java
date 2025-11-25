import java.io.*;
public class ReadMinFile
{
public static void main(String args[])
{
String home_dir = System.getProperty("user.home");
String dest_dir = "/Projects/MinLang/MinFiles/";
String gen_path = home_dir + dest_dir;
try
{
FileReader fr = new FileReader(gen_path + "SampleFiles/s1.min");
BufferedReader br = new BufferedReader(fr);
String c;
while((c=br.readLine())!=null)
{
System.out.println(c);
}
}
catch(IOException e)
{System.out.println("Error : "+e.getMessage());}
}
}
