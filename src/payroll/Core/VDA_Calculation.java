package payroll.Core;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import payroll.DAO.ConnectionManager;
import payroll.DAO.EmpOffHandler;
import payroll.DAO.GradeHandler;
import payroll.DAO.TranHandler;
import payroll.DAO.VdaDAO;
import payroll.Model.DAGradeBean;
import payroll.Model.TranBean;
import payroll.Model.TransactionBean;
import payroll.Model.VdaBean;

public class VDA_Calculation 
{

public static ErrorLog el=new ErrorLog();
	
	
	
	
public static void vdaCalc(String empno,String date,String type)
{
}		//end of method	   vdarnd	()			
			   			
public static float onAmount(int trncd, int empno, String BgnDate,
		int empType, float WrkDays, Connection Cn,String table) // added one parameter WrkDays
{
	return WrkDays;
}


//------------- Method to check for ChkSlab ------------------------------
	public static float checkSlab(int trncd, String dt, float WrkAmt,
			int empType, int empno, Connection Cn) 
{
				return empno;
}


	public static void vda_Calc_new(String date,String type)
	{
		
	}//END OF MY FUNCTION
	
	//program for round off according to namco by hrishi
public static  double round(double number) {

		         String mvda= Double.toString(number);
		         
		         String[] mvda1=mvda.split("\\.");
		         
		         System.out.println("mvda1 "+mvda1[0]);
	             String mvda2=mvda1[0];
		               
		         int integer = (int)number;         
	             
	             double decimal = (10 * number - 10 * integer)/10;

	            System.out.println(twoDecimalPlaces(decimal)); 
	            
	            BigDecimal twoDecimalValue=twoDecimalPlaces(decimal);
	            String last_decimal_value=twoDecimalValue.toString();
	            last_decimal_value= last_decimal_value.substring(2,4);
	                
	            int frac= Integer.parseInt(last_decimal_value);
	            int frac1= frac%10;
	               
	            
	            if(frac1 == 1){
	                 frac = frac - 1;
	            }
	            if(frac1 == 2){
	                 frac = frac - 2;
	            }
	            if(frac1 == 3){
	                 frac = frac + 2;
	            }
	            if(frac1 == 4){
	                 frac = frac + 1;
	            }
	            if(frac1 == 6){
	                 frac = frac - 1;
	            }
	            if(frac1 == 7){
	                 frac = frac - 2;
	            }
	            if(frac1 == 8){
	                 frac = frac + 2;
	            }
	            if(frac1 == 9){
	                 frac = frac + 1;
	            }
	            else{
	            	frac = frac+1;
	            }
	            
	            String mvdaTotal= mvda2+"."+frac;
	            
	            double roundedMvda= Double.parseDouble(mvdaTotal);
				
	            return roundedMvda; 
	           }
	   
	   private static final int DECIMAL_PLACES = 2;
	   private static BigDecimal twoDecimalPlaces(final double d) {
		   
	    return new java.math.BigDecimal(d).setScale(DECIMAL_PLACES, java.math.RoundingMode.HALF_UP);
	}
	   // end round off
	
 public static int vda_Calc_new1(String date,String type){
	return 0;
	 
 }   
	   
	   
//public static int vda_Calc_latest(String date,String type){
 public static int vda_Calc_latest(String date){

System.out.println("Hello.........vda_Calc_latest");
//System.out.println("VDA Date :"+date+" type :"+type);
			
		   int flag=0;
		 			
 			try
		 	{
 				EmpOffHandler EOH=new EmpOffHandler();
		 		TranHandler TH=new TranHandler();
		 		GradeHandler GH=new GradeHandler();		
		 		
		 		DAGradeBean dagbean = new DAGradeBean();
 				
		 		Connection con=ConnectionManager.getConnection();
		 		VdaBean vdaBean=new VdaBean();
		 		VdaDAO VD=new VdaDAO();

		 		vdaBean= VD.getTOP3VdaForLatest(date);  
		 		
		 		double fixbasic = vdaBean.getFixBasic();
		 		double dadivisor = vdaBean.getDaDivisor();
		 		double cpiindex = vdaBean.getCPIValue();
		 		double cfpiindex = vdaBean.getCFPIValue();
		 		
		 		double mban=200;
		 		double avg_ind;
		 		double basic=0.00;
		 		
		 		double mda=0.00;
		 		double mdanew=0.00;
		 		double mdavalue=0.00;
		 		double mbasic=0.00;
		 		
		 		double da=0.00;
		 		double mvda=0.00;
		 		double hra=0.00;
		 				
		 		
		 		String empno="";
		 		
		 		avg_ind=vdaBean.getPrev_vdaIndexAvg();    
		 		
		 		System.out.println("avg_ind :"+avg_ind);
		 		System.out.println("fixbasic"+vdaBean.getFixBasic());
		 		System.out.println("cpiindex"+vdaBean.getCPIValue());
		 		System.out.println("cfpiindex"+vdaBean.getCFPIValue());
		 		
		 		/*//Step - 1
		 		mda = avg_ind*cfpiindex;
		 		
		 		//Step - 2
		 		mda = mda*cpiindex;
		 		
		 		//Step - 3
 				mda = mda - mban;
 				System.out.println("mda3 :"+mda);
		 		mdanew = mda;
		 		
		 		//Step - 4
 						//Step - 4(i)
 						mda = (mda*55/100);
 						
 						//Step - 4(ii)
 						mda = (mda*fixbasic/100);
 				*/
 						
 						
		 		//Step - 5
 						String query="";
 						//query+="SELECT DISTINCT(EMPNO) FROM PAYTRAN ORDER BY EMPNO";
 						query+="SELECT DISTINCT(EMPNO) FROM PAYTRAN where EMPNO NOT IN (158, 176) ORDER BY EMPNO";
 						PreparedStatement pst=con.prepareStatement(query);
 						ResultSet all=pst.executeQuery();
 						while(all.next())
 						{
 							String gradeCode=all.getString(1);
 							System.out.println("gradeCode : "+ gradeCode);
 							empno=gradeCode;
 							System.out.println("empno :"+ empno);
 							TransactionBean tbean = EOH.getInfoEmpTran(empno);
 							gradeCode=""+tbean.getGrade();
 						
 							//ArrayList<TranBean>  empCTC=TH.getCTCDISPLAY(Integer.parseInt(empno));
 							
 							ArrayList<TranBean>  empCTC=TH.getPaytranValue(Integer.parseInt(empno));
 				 		
 							for(TranBean tb : empCTC)
 				 			{	
 								if(tb.getTRNCD()==101)
 								{ 
 									//basic=tb.getINP_AMT();
 									basic=tb.getCAL_AMT();
 								}
 								System.out.println("basic :"+basic);
 								
 				 			}
 							
 							if(basic<500)
 							{
 								
 								System.out.println("basic <500 :"+basic);
 							
 								//Step - 1
 	 					 		mda = avg_ind*cfpiindex;
 	 					 		
 	 					 		//Step - 2
 	 					 		mda = mda*cpiindex;
 	 					 		
 	 					 		//Step - 3
 	 			 				mda = mda - mban;
 	 			 				mdanew = mda;
 	 					 		
 	 					 		//Step - 4
 	 			 						//Step - 4(i)
 	 			 						mda = (mda*55/100);
 	 			 						
 	 			 						//Step - 4(ii)
 	 			 						mda = (mda*basic/100);
 	 			 				
 	 							mdavalue = mda;			
 	 							mdavalue=Math.round(mdavalue);
 	 							
 	 							System.out.println("mdavalue : "+mdavalue);
 	 						}
 							
 							else
 							{
 								System.out.println("basic >500 :"+basic);
 								
 								//Step - 1
 	 					 		mda = avg_ind*cfpiindex;
 	 					 		
 	 					 		//Step - 2
 	 					 		mda = mda*cpiindex;
 	 					 		
 	 					 		//Step - 3
 	 			 				mda = mda - mban;
 	 			 				mdanew = mda;
 	 					 		
 	 					 		//Step - 4
 	 			 						//Step - 4(i)
 	 			 						mda = (mda*55/100);
 	 			 						
 	 			 						//Step - 4(ii)
 	 			 						mda = (mda*fixbasic/100);
 	 			 				
 	 							mbasic = basic-fixbasic;
 	 	 						
 	 	 					//Step - 5(i)
 	 							mdanew = (mdanew*20/100);
 	 							
 	 							//Step - 5(ii)
 	 							mdanew = (mdanew*mbasic/100);
 	 							
 	 							//Step - 6
 	 							mdavalue = mda + mdanew;			
 	 							//mdavalue=Math.round(mdavalue+0.09);
 	 							
 	 							mdavalue=Math.round(mdavalue);
 	 							
 	 							System.out.println("mdavalue > 500 :"+mdavalue);
 							}
 										
 							
 							ArrayList<DAGradeBean> gradeList =new ArrayList<DAGradeBean>();
 				 			
 				 			gradeList=GH.getGradeDetails(gradeCode);
 				 			
 				 			int srno;
 				 			
 				 			
 				 		/*	for(DAGradeBean gBean: gradeList)
 				 			 {
 				 				 srno=gBean.getSerialNumber();
 				 			//	// basic=gBean.getBasic();
 				 				 da=gBean.getDaValue();
 				 		*/		
 				 				try
 				 				{
 				 					
 				 					//REPLACE VDA to MVDA in VDATRAN............
 				 					Statement st1=con.createStatement();
 				 					/*st1.executeUpdate(" UPDATE DA_GRADE_MASTER SET DA="+mdavalue+",DA_TYPE=0,DA_VAL="+mdavalue+"  " +
 				 									" WHERE  SRNO="+srno+" AND GRADE_CODE="+gradeCode+"   " +
 				 									" AND START_DATE='"+gBean.getStartDate()+"'");*/
 				 					
/* 				 					st1.executeUpdate(" UPDATE DA_GRADE_MASTER SET DA="+mdavalue+",DA_TYPE=0,DA_VAL="+mdavalue+"  " +
			 									" WHERE  SRNO="+srno+" AND START_DATE='"+gBean.getStartDate()+"'");
*/ 				 					
 				 					
		 					/***********************************Working Query Start*************************************************************************************/
 				 					
 				 					//st1.executeUpdate("UPDATE paytran SET INP_AMT="+mdavalue+" WHERE EMPNO="+empno+" AND TRNCD=102");	    
 				 					st1.executeUpdate("UPDATE paytran SET INP_AMT="+mdavalue+", CAL_AMT="+mdavalue+", NET_AMT="+mdavalue+" WHERE EMPNO="+empno+" AND TRNCD=102");
 				 					
 				 					System.out.println("UPDATE paytran SET INP_AMT="+mdavalue+", CAL_AMT="+mdavalue+", NET_AMT="+mdavalue+" WHERE EMPNO="+empno+" AND TRNCD=102");
		 					/***********************************Working Query End**************************************************************************************/
 				 					
 				 					//System.out.println("UPDATE paytran SET INP_AMT="+mdavalue+" WHERE EMPNO="+empno+" AND TRNCD=102");
 				 						
 								/*    String daApplicableDate=gBean.getStartDate();
 				 					
 				 					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
 				 					  
 				 							Date tempDate=new Date();
 				 							tempDate=simpleDateFormat.parse(daApplicableDate);
 				 						  Calendar calendar = Calendar.getInstance();
 				 						  calendar.setTime(tempDate);
 				 						  calendar.set(Calendar.DAY_OF_MONTH, 1);
 				 					String firstMonth=simpleDateFormat.format(calendar.getTime());	 
 				 					st1.executeUpdate("UPDATE DAMAST SET CALC_FLAG=1 WHERE MONTH=' "+firstMonth+" '  ");
 				 					
 				 							 simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
 				 							 tempDate=new Date();
 				 								tempDate=simpleDateFormat.parse(firstMonth);
 				 								   calendar = Calendar.getInstance();
 				 								  calendar.setTime(tempDate);
 				 								  calendar.add(Calendar.MONTH, 1);
 				 								   calendar.set(Calendar.DAY_OF_MONTH, 1);
 				 							String secondMonth=simpleDateFormat.format(calendar.getTime());	   
 				 							st1.executeUpdate("UPDATE DAMAST SET CALC_FLAG=1 WHERE MONTH=' "+secondMonth+" '  ");
 				 							
 				 								tempDate=simpleDateFormat.parse(secondMonth);
 				 								   calendar = Calendar.getInstance();
 				 								  calendar.setTime(tempDate);
 				 								  calendar.add(Calendar.MONTH, 1);
 				 								   calendar.set(Calendar.DAY_OF_MONTH, 1);
 				 							String thirdMonth=simpleDateFormat.format(calendar.getTime());	  
 				 							st1.executeUpdate("UPDATE DAMAST SET CALC_FLAG=1 WHERE MONTH='"+thirdMonth+"' ");*/
 				 								}
 				 				catch(Exception e)
 				 				{
 				 					System.out.println("erorrrrrrrrrrrr--------------------------------");
 				 					e.printStackTrace();
 				 					el.errorLog("VDA_Calculation.java _ new_vda_cal1() into desig wise ", e.toString());
 				 				}
 				 				
 				 			// }

		 		
 				 			flag=1;
 				 			}
 		}
		 			catch(Exception e)
		 			{
		 				e.printStackTrace();
		 				el.errorLog("VDA_Calculation.java _ new_vda_cal1()", e.toString());
		 			}
		 			return flag;
	   }
	   
}//end of class