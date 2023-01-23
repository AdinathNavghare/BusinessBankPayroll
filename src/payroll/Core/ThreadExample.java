package payroll.Core;

/* 
Threads are needed when you want to perfom actions over and over again. 
We want to show the current time and update that every second. 
*/ 
 
import java.awt.*; 
import java.applet.*; 
// We need Date to get the current time. 
import java.util.Date;

// Implement Runnable, this will make it possible for threads 
// to call the run method when activated. 
    public class ThreadExample extends Applet implements Runnable 
    { 
         // Define your thread. 
         Thread clockThread; 
         // This textfield will show the time. 
         TextField clockField; 
         // Date will give us the current hours, minutes and seconds 
         Date date; 
         // This variable will remain true for as long 
         // we want the thread to run. 
         boolean running = true;
        public static String empno="00";

     public    ThreadExample(String eno)
         {
        	 empno=eno;
         }
         
         
         public void setEmpno(String eno)
        		 {
        	 empno=eno;
        		 }
         
         
         public void init() 
         { 
              // a standard layout to place just one textfield 
              setLayout(new BorderLayout()); 
               clockField = new TextField(); 
              add(clockField,"Center"); 
              // Create the thread. 
              clockThread= new Thread(this); 
              // and let it start running 
              clockThread.start(); 
         }

         // Very important. You do not want your thread to keep running when 
         // the applet is deactivated (eg. user left page) 
         public void destroy() 
         { 
              // will cause thread to stop looping 
              running = false; 
              // destroy it. 
              clockThread = null; 
         } 
 

     // The method that will be called when you have a thread. 
     // You always need this when you implement Runnable (use a thread) 
     public void run() 
     { 
    	 //System.out.println(empno+"...........Completed----------------------------------------------"); 
          // loop until told to stop 
         int x=1; 
    	 while (x<5) 
          { 
               // Construct the current date. 
               date = new Date(); 
               // Get the hours, minutes and hours 
               String time = date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
        	  
        	  //String time =  empno;
               // Put that result in the textfield 
               clockField.setText(time+"---"+empno); 
               //Now the reason for threads 
               try 
               { 
                     // Wait 500milliseconds before continuing 
                    clockThread.sleep(1000); 
                    
               } 
               catch (InterruptedException e) 
               { 
                    System.out.println(e); 
                } 
               // he has wait and will now restart his actions.
               
              // System.out.println(empno+"...........Completed");
               x++;
          } 
     }
     
     
    /* public static void main(String []arg)
     {
    	
    	 
    	 for(int i=0;i<15;i++)
    	 {
    	 
    	 ThreadExample t5= new ThreadExample();
    	 t5.empno=""+i;    	 
    	 t5.start();
    	 
    	 }
     }*/
    
}