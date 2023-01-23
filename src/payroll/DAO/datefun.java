package payroll.DAO;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class datefun {
	public static long daysBtwTwoDates(Date date1, Date date2) 
    {
        // Create temporary calendar objects for the two dates and set the times
        // to 11:30:00 and time-zones to UTC as we are only concerned with
        // difference between days.
        Calendar tmpStartDate = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
        tmpStartDate.setTime(date1);
        tmpStartDate.set(Calendar.HOUR, 11);
        tmpStartDate.set(Calendar.MINUTE, 30);
        tmpStartDate.set(Calendar.SECOND, 0);
 
        Calendar tmpEndDate = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
        tmpEndDate.setTime(date2);
        tmpEndDate.set(Calendar.HOUR, 11);
        tmpEndDate.set(Calendar.MINUTE, 30);
        tmpEndDate.set(Calendar.SECOND, 0);
 
        long endDateL = tmpEndDate.getTimeInMillis()
                + tmpEndDate.getTimeZone().getOffset( tmpEndDate.getTimeInMillis()) ;
 
        //add 2000ms = 2s to cover the time taken between extracting 2 long numbers.
        long startDateL = tmpStartDate.getTimeInMillis()
                + tmpStartDate.getTimeZone().getOffset( tmpStartDate.getTimeInMillis())+2000;
         
        long noOfDays = 0;
        final long MILLIS_IN_DAY = 1000*60*60*24;
        for( ; startDateL < endDateL; startDateL+=MILLIS_IN_DAY, noOfDays++ );
         
        return noOfDays;
        //return (endDateL - startDateL) / MILLIS_IN_DAY;
    }

	public static void main(String[] args) {
		
		 DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

	        
	    Date start = null;
	    Date end = null;
		try {
			start = df.parse("13-MAY-2013");
			  end=df.parse("16-MAY-2013");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
       
       System.out.println(datefun.daysBtwTwoDates(start, end));
	}


}


 

