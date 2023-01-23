package payroll.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.DAO.EmpOffHandler;
import payroll.DAO.Form16Handler;
import payroll.DAO.TranHandler;
import payroll.Model.Form16Bean;
import payroll.Model.TranBean;
import payroll.Model.TransactionBean;

import com.ibm.icu.text.SimpleDateFormat;

/**
 * Servlet implementation class Form16Servlet
 */
@WebServlet("/Form16Servlet")
public class Form16Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Form16Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session= request.getSession();	
		TransactionBean trbn = new TransactionBean();

		EmpOffHandler emp = new EmpOffHandler();
	    TranHandler trn = new TranHandler();
	    ArrayList<TranBean> trlist = new ArrayList<TranBean>();
	    String EMPNO = request.getParameter("no");
	   
		trbn=emp.getInfoEmpTran(EMPNO);
	    trlist=trn.getTranInfo(EMPNO,"tran");//tran :-fire query to tran table
	    request.setAttribute("empno", EMPNO);
	    request.setAttribute("trbn",trbn); 
	    session.setAttribute("trlist",trlist);
		request.getRequestDispatcher("Form16Entry.jsp?action=getdata").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session= request.getSession();	
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		TransactionBean trbn = new TransactionBean();
		
		EmpOffHandler emp = new EmpOffHandler();
	    TranHandler trn = new TranHandler();

	    if(action.equalsIgnoreCase("create"))
	    {
	    	String gi =request.getParameter("hdnbt");
	    	int max = 49;
			String [] prefix = new String[max];	
			
			//prefix[1]="fgais1";			// 561 
			//prefix[2]="flaeus";			// 562
			//prefix[3]="fheus";			// 563
			prefix[1]="fscr";			// 564
			//prefix[5]="fbasic";			// 101																	
			prefix[2]="frp";			// 565 
			//prefix[7]="fhrar";			// 103
			//prefix[3]="fta";			// 521
			prefix[3]="fcea";			// 522
			prefix[4]="fcha";			// 523
			prefix[5]="fmbr";			// 524
			prefix[6]="flta";			// 525 
			//prefix[13]="fdus16";		// 566
			//prefix[14]="fpt";			// 202 
			//prefix[15]="fea";			// 567
			//prefix[16]="fiuhs";			// 568
			prefix[7]="firsac1";		// 526
			//prefix[18]="fios";			// 581
			prefix[8]="fifd";			// 527
			prefix[9]="fird";			// 528
			prefix[10]="finsc";			// 529
			prefix[11]="fipost";		// 530
			prefix[12]="fisb";			// 531
			prefix[13]="fos";			// 569
			
			prefix[14]="facomdation";	// 571
			prefix[15]="falb";			// 532
			prefix[16]="furent";		// 533 
			prefix[17]="ftaxlocal";		// 534
			prefix[18]="fihousingloan";	// 535
			//prefix[31]="fgrossti";		// 572
			//prefix[32]="flesschapter";	// 573
			prefix[19]="fpfvpf";		// 536
			prefix[20]="flicp";			// 537
			prefix[21]="fnsci";			// 538
			prefix[22]="fhomeloan";		// 539
			prefix[23]="ftutionfees";	// 540
			prefix[24]="fmutualfund";	// 541 
			prefix[25]="ffd";			// 542
			prefix[26]="ftaxsaving";	// 543
			prefix[27]="fotehrinvest";	// 544
			prefix[28]="fpensionplan";	// 545
			prefix[29]="fpensionschemeemp";		// 546 
			prefix[30]="fpensionschemeel";		// 547
			prefix[31]="frges";					// 548
			prefix[32]="fmiphelath";			// 549
			prefix[33]="fmipparents";			// 550
			prefix[34]="fdonation";				// 551
			prefix[35]="f80dd";					// 552
			prefix[36]="f80ddb";				// 553
			prefix[37]="f80e";					// 554 
			prefix[38]="f80gg";					// 555
			prefix[39]="f80gga";				// 556
			prefix[40]="f80ggc";				// 557
			prefix[41]="f80u";					// 558
			prefix[42]="f80tta";				// 559
			prefix[43]="fhousepr";				// 570
			prefix[44]="tbr";				// 587
			prefix[45]="scr";
			prefix[46]="fnsulip";
			prefix[47]="fnsdep";
			prefix[48]="fnssads";// 588
			/*prefix[57]="ftotalincome";			// 574
			prefix[58]="ftaxableincome";		// 560
			prefix[59]="ftaxtotalincome";		// 575
			prefix[60]="frebateus";				// 576
			prefix[61]="fafterrebate";			// 577
			prefix[62]="feducess";				// 578
			prefix[63]="ftaxpayble";			// 579
			prefix[64]="ftds";					// 228
			prefix[65]="fbalance";				// 580
*/			
			int empno = Integer.parseInt(request.getParameter("empno1"));
			int[] codes = {0,564,565,522,523,524,525,526,527,528,
					       529,530,531,569,571,532,533,534,535,501,537,538,539,540,541,542,543,
					       544,545,546,547,548,549,550,551,552,553,554,555,556,557,558,559,570,587,588,511,512,513};
			String year=session.getAttribute("year").toString();
			year="30-Apr-"+year;
			float[] value = new float[max];
			int SRNO = 0;
			for(int i=1; i<max; i++)
			{
				value[i] = Float.parseFloat(request.getParameter(prefix[i])==""?"0":request.getParameter(prefix[i]));
			}
			
			Form16Bean FBN = new Form16Bean();
	    	Form16Handler FH = new Form16Handler();
	    	if(gi.equalsIgnoreCase("Finalize")){
	    	for(int i=1; i<max; i++)
			{
	    		SRNO= SRNO+1;
				Date dt = new Date();
				String date = new SimpleDateFormat("dd-MMM-yyyy").format(dt);
				FBN.setTRNDT(year);  //	PROBLEMATIC
				FBN.setEMPNO(request.getParameter("empno1")==""?0:Integer.parseInt(request.getParameter("empno1")));
				FBN.setTRNCD(codes[i]);
				FBN.setSRNO(SRNO);
				FBN.setINP_AMT(value[i]);
				FBN.setCAL_AMT(value[i]);
				FBN.setADJ_AMT(0);
				FBN.setARR_AMT(0);
				FBN.setNET_AMT(value[i]);
				FBN.setCF_SW("F");
				FBN.setUSRCODE("System");
				FBN.setUPDDT(date);
				FBN.setSTATUS("F16");
				/*if(FH.getAvailability(empno, codes[i]))
				{
					FH.updateValue(FBN);
				}
				else
				{
					FH.addValue(FBN);
				}*/
				FH.addValue(FBN); 
			}
	    	} else {
	    		for(int i=1; i<max; i++)
				{
		    		//SRNO= SRNO+1;
					Date dt = new Date();
					String date = new SimpleDateFormat("dd-MMM-yyyy").format(dt);
				
					FBN.setTRNDT(year);  //	PROBLEMATIC
					FBN.setEMPNO(request.getParameter("empno1")==""?0:Integer.parseInt(request.getParameter("empno1")));
					FBN.setTRNCD(codes[i]);
					FBN.setSRNO(0);
					FBN.setINP_AMT(value[i]);
					FBN.setCAL_AMT(value[i]);
					FBN.setADJ_AMT(0);
					FBN.setARR_AMT(0);
					FBN.setNET_AMT(value[i]);
					FBN.setCF_SW("P");
					FBN.setUSRCODE("System");
					FBN.setUPDDT(date);
					FBN.setSTATUS("F16");
					FH.addValue(FBN); 
					
				}
	    	}
	    	request.getRequestDispatcher("Form16Entry.jsp?action=getdata&msg=Record Updated Successfully.").forward(request, response);
	    }
				
		else if(action.equalsIgnoreCase("trnlist"))
		{
			String EMPNO;
			//String list=request.getParameter("list")==null?"":request.getParameter("list");
			EMPNO=request.getParameter("EMPNO");
			String[] employ= EMPNO.split(":");
		 	EMPNO=employ[2];
			
			ArrayList<TranBean> trlist = new ArrayList<TranBean>();
			trbn=emp.getInfoEmpTran(EMPNO);
		    trlist=trn.getTranInfo(EMPNO,"tran");// tran :-fire query to tran table
		    session.setAttribute("trbn",trbn); 
		    session.setAttribute("empno1",EMPNO); 
		    session.setAttribute("trlist",trlist);
		    String year=session.getAttribute("year").toString();
		    Map<Integer,Form16Bean> list = new HashMap<Integer, Form16Bean>(); 
		    Form16Handler fnh = new Form16Handler();
		    list=fnh.getForm16Value(EMPNO,year);
		    session.setAttribute("fbn", list);
		    
		     /*ArrayList<Form16Bean> fblist = new ArrayList<Form16Bean>();
		    Form16Handler fnh = new Form16Handler();
		    fblist=fnh.getForm16Value(EMPNO);
		    request.setAttribute("fblist", fblist);
		    session.setAttribute("fblist", fblist);*/
		    
		    request.getRequestDispatcher("Form16Entry.jsp?action=getdata").forward(request, response);	       
		}
	    
		else if(action.equalsIgnoreCase("insert"))
		{

	    	String gi =request.getParameter("hdnbt");
	    	int max = 50;
			String [] prefix = new String[max];	
			
			//prefix[1]="fgais1";			// 561 
			//prefix[2]="flaeus";			// 562
			//prefix[3]="fheus";			// 563
			prefix[1]="fscr";			// 564
			//prefix[5]="fbasic";			// 101																	
			prefix[2]="frp";			// 565 
			//prefix[7]="fhrar";			// 103
			//prefix[3]="fta";			// 521
			prefix[3]="fcea";			// 522
			prefix[4]="fcha";			// 523
			prefix[5]="fmbr";			// 524
			prefix[6]="flta";			// 525 
			//prefix[13]="fdus16";		// 566
			//prefix[14]="fpt";			// 202 
			//prefix[15]="fea";			// 567
			//prefix[16]="fiuhs";			// 568
			prefix[7]="firsac1";		// 526
			//prefix[18]="fios";			// 581
			prefix[8]="fifd";			// 527
			prefix[9]="fird";			// 528
			prefix[10]="finsc";			// 529
			prefix[11]="fipost";		// 530
			prefix[12]="fisb";			// 531
			prefix[13]="fos";			// 569
			
			prefix[14]="facomdation";	// 571
			prefix[15]="falb";			// 532
			prefix[16]="furent";		// 533 
			prefix[17]="ftaxlocal";		// 534
			prefix[18]="fihousingloan";	// 535
			//prefix[31]="fgrossti";		// 572
			//prefix[32]="flesschapter";	// 573
			prefix[19]="fpfvpf";		// 536
			prefix[20]="flicp";			// 537
			prefix[21]="fnsci";			// 538
			prefix[22]="fhomeloan";		// 539
			prefix[23]="ftutionfees";	// 540
			prefix[24]="fmutualfund";	// 541 
			prefix[25]="ffd";			// 542
			prefix[26]="ftaxsaving";	// 543
			prefix[27]="fotehrinvest";	// 544
			prefix[28]="fpensionplan";	// 545
			prefix[29]="fpensionschemeemp";		// 546 
			prefix[30]="fpensionschemeel";		// 547
			prefix[31]="frges";					// 548
			prefix[32]="fmiphelath";			// 549
			prefix[33]="fmipparents";			// 550
			prefix[34]="fdonation";				// 551
			prefix[35]="f80dd";					// 552
			prefix[36]="f80ddb";				// 553
			prefix[37]="f80e";					// 554 
			prefix[38]="f80gg";					// 555
			prefix[39]="f80gga";				// 556
			prefix[40]="f80ggc";				// 557
			prefix[41]="f80u";					// 558
			prefix[42]="f80tta";				// 559
			prefix[43]="fhousepr";				// 570
			prefix[44]="tbr";				// 587
			prefix[45]="scr";
			prefix[46]="fnsulip";
			prefix[47]="fnsdep";
			prefix[48]="fnssads";
			prefix[49]="stdded";// 588
			/*prefix[57]="ftotalincome";			// 574
			prefix[58]="ftaxableincome";		// 560
			prefix[59]="ftaxtotalincome";		// 575
			prefix[60]="frebateus";				// 576
			prefix[61]="fafterrebate";			// 577
			prefix[62]="feducess";				// 578
			prefix[63]="ftaxpayble";			// 579
			prefix[64]="ftds";					// 228
			prefix[65]="fbalance";				// 580
*/			
			int empno = Integer.parseInt(request.getParameter("empno1"));
			int[] codes = {0,564,565,522,523,524,525,526,527,528,
					       529,530,531,569,571,532,533,534,535,501,537,538,539,540,541,542,543,
					       544,545,546,547,548,549,550,551,552,553,554,555,556,557,558,559,570,587,588,511,512,513,500};
			String year=session.getAttribute("year").toString();
			year="30-Apr-"+year;
			float[] value = new float[max];
			int SRNO = 0;
			for(int i=1; i<max; i++)
			{
				value[i] = Float.parseFloat(request.getParameter(prefix[i])==""?"0":request.getParameter(prefix[i]));
				System.out.println("code-"+prefix[i]+"--val---"+value[i]);
			}
			
			Form16Bean FBN = new Form16Bean();
	    	Form16Handler FH = new Form16Handler();
	    	if(gi.equalsIgnoreCase("Finalize")){
	    	for(int i=1; i<max; i++)
			{
	    		SRNO= SRNO+1;
				Date dt = new Date();
				String date = new SimpleDateFormat("dd-MMM-yyyy").format(dt);
				FBN.setTRNDT(year);  //	PROBLEMATIC
				FBN.setEMPNO(request.getParameter("empno1")==""?0:Integer.parseInt(request.getParameter("empno1")));
				FBN.setTRNCD(codes[i]);
				FBN.setSRNO(SRNO);
				FBN.setINP_AMT(value[i]);
				FBN.setCAL_AMT(value[i]);
				FBN.setADJ_AMT(0);
				FBN.setARR_AMT(0);
				FBN.setNET_AMT(value[i]);
				FBN.setCF_SW("F");
				FBN.setUSRCODE("System");
				FBN.setUPDDT(date);
				FBN.setSTATUS("F16");
				/*if(FH.getAvailability(empno, codes[i]))
				{
					FH.updateValue(FBN);
				}
				else
				{
					FH.addValue(FBN);
				}*/
				FH.addValue(FBN); 
				
			}
    		response.sendRedirect("Form16Search.jsp?action=getdata&empno1=A:B:"+request.getParameter("empno1")+"&year="+session.getAttribute("year").toString()+"&msg=0");
	    	//request.getRequestDispatcher("Form16Search.jsp?msg=0").forward(request, response);
	    	} else {
	    		for(int i=1; i<max; i++)
				{
		    		//SRNO= SRNO+1;
					Date dt = new Date();
					String date = new SimpleDateFormat("dd-MMM-yyyy").format(dt);
				
					FBN.setTRNDT(year);  //	PROBLEMATIC
					FBN.setEMPNO(request.getParameter("empno1")==""?0:Integer.parseInt(request.getParameter("empno1")));
					FBN.setTRNCD(codes[i]);
					FBN.setSRNO(0);
					FBN.setINP_AMT(value[i]);
					FBN.setCAL_AMT(value[i]);
					FBN.setADJ_AMT(0);
					FBN.setARR_AMT(0);
					FBN.setNET_AMT(value[i]);
					FBN.setCF_SW("P");
					FBN.setUSRCODE("System");
					FBN.setUPDDT(date);
					FBN.setSTATUS("F16");
					FH.addValue(FBN); 
					
				}
	    		response.sendRedirect("Form16Search.jsp?action=getdata&empno1=A:B:"+request.getParameter("empno1")+"&year="+session.getAttribute("year").toString()+"&msg=1");
	    		//request.getRequestDispatcher("Form16Search.jsp?msg=1").forward(request, response);
	    	}
	    	
	    
		}
	}
}
