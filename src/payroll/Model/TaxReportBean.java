package payroll.Model;

import java.sql.ResultSet;

import com.lowagie.text.Document;

public class TaxReportBean {
	public  int GENO = 0;
	public  String gnm = "";
	public  int c1 = 0;
	public  int c2 = 0;
	public  int c3 = 0;
	public  int c4 = 0;
	public  int c5 = 0;
	public  int ProjMonth = 0;
	public  String hdg_11 = "";
	public  String hdg_12 = "";
	public  String hdg_13 = "";
	public  int tot = 0;
	public  int min_pay = 0;
	public  ResultSet ytd = null;
	public  String ytdstr = "";
	public  String dline = "";
	public  int totamt = 0;
	public  int c3tot = 0;
	public  int c4tot = 0;
	public  int c5tot = 0;
	public  int maxhlint1 = 0;
	public  int maxhlint2 = 0;
	public  int reb88_per = 0;
	public  int reb88_max_amt = 0;
	public  int tax_cal = 0;
	public  int sercharge  =0;
	public  int net_tax = 0;
	public  int TAXPAID = 0;
	public  int c_ul0 = 0;
	public  int c_ul1 = 0;
	public  int c_ul2 = 0;
	public  int c_ul3 = 0;
	public  int c_ul4 = 0;
	
	public  int c_rs0 = 0;
	public  int c_rs1 = 0;
	public  int c_rs2 = 0;
	public  int c_rs3 = 0;
	
	public  int c_am0 = 0;
	public  int c_am1 = 0;
	public  int c_am2 = 0;
	public  int c_am3 = 0;
	
	public  int OTHER_DED  = 0;
	public  int DED80 = 0;
	public  String slbstr = "";
	public  int reb88 = 0;
	
	public  ResultSet slb = null;
	public  String str1 = "";
	public  int vperk_amt = 0;
	public  ResultSet trs = null;
	public  int totinc = 0;
	public  int totded = 0;
	public  int gross_tax_amt = 0;
	public Document doc;
	
	public int getGENO() {
		return GENO;
	}
	public void setGENO(int gENO) {
		GENO = gENO;
	}
	public String getGnm() {
		return gnm;
	}
	public void setGnm(String gnm) {
		this.gnm = gnm;
	}
	public int getC1() {
		return c1;
	}
	public void setC1(int c1) {
		this.c1 = c1;
	}
	public int getC2() {
		return c2;
	}
	public void setC2(int c2) {
		this.c2 = c2;
	}
	public int getC3() {
		return c3;
	}
	public void setC3(int c3) {
		this.c3 = c3;
	}
	public int getC4() {
		return c4;
	}
	public void setC4(int c4) {
		this.c4 = c4;
	}
	public int getC5() {
		return c5;
	}
	public void setC5(int c5) {
		this.c5 = c5;
	}
	public int getProjMonth() {
		return ProjMonth;
	}
	public void setProjMonth(int projMonth) {
		ProjMonth = projMonth;
	}
	public String getHdg_11() {
		return hdg_11;
	}
	public void setHdg_11(String hdg_11) {
		this.hdg_11 = hdg_11;
	}
	public String getHdg_12() {
		return hdg_12;
	}
	public void setHdg_12(String hdg_12) {
		this.hdg_12 = hdg_12;
	}
	public String getHdg_13() {
		return hdg_13;
	}
	public void setHdg_13(String hdg_13) {
		this.hdg_13 = hdg_13;
	}
	public int getTot() {
		return tot;
	}
	public void setTot(int tot) {
		this.tot = tot;
	}
	public int getMin_pay() {
		return min_pay;
	}
	public void setMin_pay(int min_pay) {
		this.min_pay = min_pay;
	}
	public ResultSet getYtd() {
		return ytd;
	}
	public void setYtd(ResultSet ytd) {
		this.ytd = ytd;
	}
	public String getYtdstr() {
		return ytdstr;
	}
	public void setYtdstr(String ytdstr) {
		this.ytdstr = ytdstr;
	}
	public String getDline() {
		return dline;
	}
	public void setDline(String dline) {
		this.dline = dline;
	}
	public int getTotamt() {
		return totamt;
	}
	public void setTotamt(int totamt) {
		this.totamt = totamt;
	}
	public int getC3tot() {
		return c3tot;
	}
	public void setC3tot(int c3tot) {
		this.c3tot = c3tot;
	}
	public int getC4tot() {
		return c4tot;
	}
	public void setC4tot(int c4tot) {
		this.c4tot = c4tot;
	}
	public int getC5tot() {
		return c5tot;
	}
	public void setC5tot(int c5tot) {
		this.c5tot = c5tot;
	}
	public int getMaxhlint1() {
		return maxhlint1;
	}
	public void setMaxhlint1(int maxhlint1) {
		this.maxhlint1 = maxhlint1;
	}
	public int getMaxhlint2() {
		return maxhlint2;
	}
	public void setMaxhlint2(int maxhlint2) {
		this.maxhlint2 = maxhlint2;
	}
	public int getReb88_per() {
		return reb88_per;
	}
	public void setReb88_per(int reb88_per) {
		this.reb88_per = reb88_per;
	}
	public int getReb88_max_amt() {
		return reb88_max_amt;
	}
	public void setReb88_max_amt(int reb88_max_amt) {
		this.reb88_max_amt = reb88_max_amt;
	}
	public int getTax_cal() {
		return tax_cal;
	}
	public void setTax_cal(int tax_cal) {
		this.tax_cal = tax_cal;
	}
	public int getSercharge() {
		return sercharge;
	}
	public void setSercharge(int sercharge) {
		this.sercharge = sercharge;
	}
	public int getNet_tax() {
		return net_tax;
	}
	public void setNet_tax(int net_tax) {
		this.net_tax = net_tax;
	}
	public int getTAXPAID() {
		return TAXPAID;
	}
	public void setTAXPAID(int tAXPAID) {
		TAXPAID = tAXPAID;
	}
	public int getC_ul0() {
		return c_ul0;
	}
	public void setC_ul0(int c_ul0) {
		this.c_ul0 = c_ul0;
	}
	public int getC_ul1() {
		return c_ul1;
	}
	public void setC_ul1(int c_ul1) {
		this.c_ul1 = c_ul1;
	}
	public int getC_ul2() {
		return c_ul2;
	}
	public void setC_ul2(int c_ul2) {
		this.c_ul2 = c_ul2;
	}
	public int getC_ul3() {
		return c_ul3;
	}
	public void setC_ul3(int c_ul3) {
		this.c_ul3 = c_ul3;
	}
	public int getC_ul4() {
		return c_ul4;
	}
	public void setC_ul4(int c_ul4) {
		this.c_ul4 = c_ul4;
	}
	public int getC_rs0() {
		return c_rs0;
	}
	public void setC_rs0(int c_rs0) {
		this.c_rs0 = c_rs0;
	}
	public int getC_rs1() {
		return c_rs1;
	}
	public void setC_rs1(int c_rs1) {
		this.c_rs1 = c_rs1;
	}
	public int getC_rs2() {
		return c_rs2;
	}
	public void setC_rs2(int c_rs2) {
		this.c_rs2 = c_rs2;
	}
	public int getC_rs3() {
		return c_rs3;
	}
	public void setC_rs3(int c_rs3) {
		this.c_rs3 = c_rs3;
	}
	public int getC_am0() {
		return c_am0;
	}
	public void setC_am0(int c_am0) {
		this.c_am0 = c_am0;
	}
	public int getC_am1() {
		return c_am1;
	}
	public void setC_am1(int c_am1) {
		this.c_am1 = c_am1;
	}
	public int getC_am2() {
		return c_am2;
	}
	public void setC_am2(int c_am2) {
		this.c_am2 = c_am2;
	}
	public int getC_am3() {
		return c_am3;
	}
	public void setC_am3(int c_am3) {
		this.c_am3 = c_am3;
	}
	public int getOTHER_DED() {
		return OTHER_DED;
	}
	public void setOTHER_DED(int oTHER_DED) {
		OTHER_DED = oTHER_DED;
	}
	public int getDED80() {
		return DED80;
	}
	public void setDED80(int dED80) {
		DED80 = dED80;
	}
	public String getSlbstr() {
		return slbstr;
	}
	public void setSlbstr(String slbstr) {
		this.slbstr = slbstr;
	}
	public int getReb88() {
		return reb88;
	}
	public void setReb88(int reb88) {
		this.reb88 = reb88;
	}
	public ResultSet getSlb() {
		return slb;
	}
	public void setSlb(ResultSet slb) {
		this.slb = slb;
	}
	public String getStr1() {
		return str1;
	}
	public void setStr1(String str1) {
		this.str1 = str1;
	}
	public int getVperk_amt() {
		return vperk_amt;
	}
	public void setVperk_amt(int vperk_amt) {
		this.vperk_amt = vperk_amt;
	}
	public ResultSet getTrs() {
		return trs;
	}
	public void setTrs(ResultSet trs) {
		this.trs = trs;
	}
	public int getTotinc() {
		return totinc;
	}
	public void setTotinc(int totinc) {
		this.totinc = totinc;
	}
	public int getTotded() {
		return totded;
	}
	public void setTotded(int totded) {
		this.totded = totded;
	}
	public int getGross_tax_amt() {
		return gross_tax_amt;
	}
	public void setGross_tax_amt(int gross_tax_amt) {
		this.gross_tax_amt = gross_tax_amt;
	}
	public Document getDoc() {
		return doc;
	}
	public void setDoc(Document doc) {
		this.doc = doc;
	}
	
	
	
}
