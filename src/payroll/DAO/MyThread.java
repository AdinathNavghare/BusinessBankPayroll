package payroll.DAO;

import java.util.concurrent.CountDownLatch;

import payroll.Core.Calculate;

public class MyThread implements Runnable
{
    CountDownLatch latch;
    String date;
    String empList;
    String UID;
   public int flag;
    public MyThread(CountDownLatch latch,String dt, String empLst,String uid) 
    {
        this.latch = latch;
        this.date=dt;
        this.empList=empLst;
        this.UID=uid;
    }
    @Override
    public void run() 
    {
        try 
        {
            latch.await();          //The thread keeps waiting till it is informed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Do the actual thing
       flag= Calculate.pay_cal(date, empList,UID);
        System.out.println("EXCECUTING THREAD "+empList);
        
    }
}