package payroll.DAO;
import java.sql.*;
import payroll.Model.*;
public class GraphHandler
{


public SalryGraphBean getTotalAmt(SalryGraphBean sgb)
{
SalryGraphBean gb=new SalryGraphBean();
//ArrayList<graphbean> list =new ArrayList<graphbean>();

try
		{
			Connection conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs =null;
			
			String year1=new java.text.SimpleDateFormat("yyyy").format(new java.util.Date());
		//String	year1=""+year;
			int mnth = 0;
			int amt = 0;
			


rs=st.executeQuery(" select distinct( DATEPART(mm,trndt)) as 'MONTH' ,sum(net_amt) AS 'TOTAL AMOUNT' from  ytdtran where trncd=999 and DATEPART(yyyy,trndt)='"+sgb.getYear()+"'  group by DATEPART(mm,trndt)");
//System.out.println(" year in DAO "+sgb.getYear());
while(rs.next())
{
mnth=rs.getInt(1)==0?0:rs.getInt(1);
amt=rs.getInt(2)==0?0:rs.getInt(2);
//list.add(gb);




switch(mnth)
{
case 1: gb.setJan(amt);
              break;

case 2: gb.setFeb(amt);
              break;

case 3: gb.setMar(amt);
              break;
case 4: gb.setApr(amt);
              break;

case 5: gb.setMay(amt);
              break;

case 6: gb.setJun(amt);
              break;
case 7: gb.setJul(amt);
              break;

case 8: gb.setAug(amt);
              break;

case 9: gb.setSep(amt);
              break;
case 10: gb.setOct(amt);
              break;

case 11: gb.setNov(amt);
              break;

case 12: gb.setDec(amt);
              break;
}


}
}
catch(Exception E)
{
	//UsrHandler.senderrormail(E,"GraphHandler.getTotalAmt");

System.out.println(E);
}

return gb;
}


}
