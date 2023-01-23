package payroll.Core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class FileDemo {
	static String line;
	public static final int max=150;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		line="";
		File file = new File("D:/Demo.txt");
		try
		{
			FileWriter FW = new FileWriter(file);
			BufferedWriter outfile =new BufferedWriter(FW);
						
			outfile.write("_________1_________2_________3_________4_________5_________6_________7_________8_________9_________10________11________12________13________14________15");
			outfile.newLine();
			outfile.write("123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
			outfile.newLine();
			outfile.newLine();
			
			line="Disha Technologies";
			outfile.write(allignCenter(line));
			
			outfile.newLine();
			line = "Software Solutions and Services";
			outfile.write(allignCenter(line));
			
			outfile.newLine();
			outfile.write(addDashedLine(1));
			outfile.newLine();
			line = "Place: Pune";
			outfile.write(line);
			outfile.write(allignRight("Date:  /  /    ")); //  dd/mm/yyyy
			outfile.newLine();
			outfile.write(addDashedLine(0));
			outfile.close();
			FW.close();
			//Utility.makeFile("D:/DemoTest.txt");		//Method for creating file for specified name
		}
		catch(Exception e)
		{
			System.out.println("Can not open file "+e);
		}
	}
	/*public static String addSpaces(String str, int num)
	{
		String result="";
		String temp="";
		for(int i=0;i<num-line.length()-1;i++)
		{
			temp+=" ";
		}
		result=temp+str;
		return result;
	}*/
	
	public static String allignCenter(String str)		// Aligning Text at Center of page
	{
		String result="";
		int len=max-str.length();
		for(int i=0;i<(len/2);i++)
		{
			result+=" ";
		}
		result+=str;
		return result;
	}
	public static String allignRight(String str)		// Aligning Text at Right of page
	{
		String result="";
		for(int i=0;i<(max-str.length()-line.length());i++)
		{
			result+=" ";
		}
		result+=str;
		return result;
	}
	public static String addDashedLine(int flag)		// Printing Dashed line
	{
		String result="";
		if(flag==0)			//Complete Line
		{
			for(int i=0;i<max;i++)
			{	
				result+="-";
			}
		}
		else if(flag==1)	//Under the Previous line printed
		{
			String temp = line.trim();
			for(int i =0; i<temp.length();i++)
			{
				result+="-";
			}
			result=allignCenter(result);
		}
		return result;
	}
}
