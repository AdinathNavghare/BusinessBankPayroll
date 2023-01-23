package payroll.Controller;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateExample {

	/**
	 * @param fromdate 
	 * @param args
	 * 
	 * 
	 */
	
	public static Date dateformat(String fromdate)throws ParseException
	{
		 DateFormat formatter = null;
	        Date convertedDate = null;
            
	       // String ddMMMyy = "14-Sep-11";
	        formatter = new SimpleDateFormat("dd-MMM-yy");
	        convertedDate = (Date) formatter.parse(fromdate);
	        System.out.println("Date from dd-MMM-yy String in Java : " + convertedDate);
		
		return convertedDate;
	}
	  
}
