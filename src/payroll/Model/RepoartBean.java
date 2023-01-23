package payroll.Model;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;

public class RepoartBean {

	public  int NoEmp;
	public  int BNoEmp;
	public  int[] Icode = new int[28];
	public  int[] Ecode = new int[28];
	public  int Kk;
	public  int[] Inc = new int[200];
	public  int[] ded = new int[301];
	public  int[][] bInc = new int[21][201];
	public  int[][] bDed = new int[21][301];
	
	public  int bCode;
	public  int[] RepTotINC = new int[201];
	public  int[] RepTotDED = new int[301];
	public  int[] BRepTotINC = new int[201];
	public  int[] BRepTotDED = new int[301];
	
	
	public  int[] BRepTotBNGD = new int[5];
	
	public  int[] PInc = new int[200];
	public  int[] PDed = new int[300];
	
	public  int[] PRepTotINC = new int[201];
	public  int[] PRepTotDED = new int[301];
	
	public  int[] TotBngd = new int[5];
	public  int[][] BtotBngd = new int[21][5];
	
	public  int[] RepTotBNGD = new int[5];
	public  int[] PTotBNGD = new int[5];
	public  int[] PRepTotBNGD = new int[5];
	
	public  String Str1;
	public  boolean payrep;
	
	public boolean Bar1 ;
	public boolean Bar2 ;
	
	
	public boolean isBar1() {
		return Bar1;
	}
	public void setBar1(boolean bar1) {
		Bar1 = bar1;
	}
	public boolean isBar2() {
		return Bar2;
	}
	public void setBar2(boolean bar2) {
		Bar2 = bar2;
	}
	//*****ADDED BY NAKUL FOR GETIING OBJ************
	public int[] getObjBRepTotDED() {
		return BRepTotDED;
	}
	public int[] getObjBRepTotINC() {
		return BRepTotINC;
	}
	public int[] getObjBRepTotBNGD() {
		return BRepTotBNGD;
	}
	public int[] getObjRepTotINC() {
		return RepTotINC;
	}
	public int[] getObjRepTotDED() {
		return RepTotDED;
	}
	public int[] getObjRepTotBNGD() {
		return RepTotBNGD;
	}
	
	public int[] getObjPRepTotINC() {
		return PRepTotINC;
	}
	public int[] getObjPRepTotDED() {
		return PRepTotDED;
	}
	public int[] getObjPRepTotBNGD() {
		return PRepTotBNGD;
	}
	//**************END*****************
	
	public int getNoEmp() {
		return NoEmp;
	}
	public void setNoEmp(int noEmp) {
		NoEmp = noEmp;
	}
	public int getBNoEmp() {
		return BNoEmp;
	}
	public void setBNoEmp(int bNoEmp) {
		BNoEmp = bNoEmp;
	}
	public int getIcode(int index) {
		return Icode[index];
	}
	public void setIcode(int icode,int index) {
		Icode[index] = icode;
	}
	public int getEcode(int index) {
		return Ecode[index];
	}
	public void setEcode(int ecode,int index) {
		Ecode[index] = ecode;
	}
	public int getKk() {
		return Kk;
	}
	public void setKk(int kk) {
		Kk = kk;
	}
	public int getInc(int index) {
		return Inc[index];
	}
	public void setInc(int inc,int index) {
		Inc[index] = inc;
	}
	public int getDed(int index) {
		return ded[index];
	}
	public void setDed(int ded,int index) {
		this.ded[index] = ded;
	}
	public int getbInc(int index1,int index2) {
		return bInc[index1][index2];
	}
	public void setbInc(int bInc,int index1,int index2) {
		this.bInc[index1][index2] = bInc;
	}
	public int getbDed(int index1,int index2) {
		return bDed[index1][index2];
	}
	public void setbDed(int bDed,int index1,int index2) {
		this.bDed[index1][index2] = bDed;
	}
	public int getbCode() {
		return bCode;
	}
	public void setbCode(int bCode) {
		this.bCode = bCode;
	}
	public int getRepTotINC(int index) {
		return RepTotINC[index];
	}
	public void setRepTotINC(int repTotINC,int index) {
		RepTotINC[index] = repTotINC;
	}
	public int getRepTotDED(int index) {
		return RepTotDED[index];
	}
	public void setRepTotDED(int repTotDED,int index) {
		RepTotDED[index] = repTotDED;
	}
	public int getBRepTotINC(int index) {
		return BRepTotINC[index];
	}
	public void setBRepTotINC(int bRepTotINC,int index) {
		BRepTotINC[index] = bRepTotINC;
	}
	public int getBRepTotDED(int index) {
		return BRepTotDED[index];
	}
	public void setBRepTotDED(int bRepTotDED,int index) {
		BRepTotDED[index] = bRepTotDED;
	}
	public int getBRepTotBNGD(int index) {
		return BRepTotBNGD[index];
	}
	public void setBRepTotBNGD(int bRepTotBNGD,int index) {
		BRepTotBNGD[index] = bRepTotBNGD;
	}
	public int getPInc(int index) {
		return PInc[index];
	}
	public void setPInc(int pInc,int index) {
		PInc[index] = pInc;
	}
	public int getPDed(int index) {
		return PDed[index];
	}
	public void setPDed(int pDed,int index) {
		PDed[index] = pDed;
	}
	public int getPRepTotINC(int index) {
		return PRepTotINC[index];
	}
	public void setPRepTotINC(int pRepTotINC,int index) {
		PRepTotINC[index] = pRepTotINC;
	}
	public int getPRepTotDED(int index) {
		return PRepTotDED[index];
	}
	public void setPRepTotDED(int pRepTotDED,int index) {
		PRepTotDED[index] = pRepTotDED;
	}
	public int getTotBngd(int index) {
		return TotBngd[index];
	}
	public void setTotBngd(int totBngd,int index) {
		TotBngd[index] = totBngd;
	}
	public int getBtotBngd(int index1,int index2) {
		return BtotBngd[index1][index2];
	}
	public void setBtotBngd(int btotBngd,int index1,int index2) {
		BtotBngd[index1][index2] = btotBngd;
	}
	public int getRepTotBNGD(int index) {
		return RepTotBNGD[index];
	}
	public void setRepTotBNGD(int repTotBNGD,int index) {
		RepTotBNGD[index] = repTotBNGD;
	}
	public int getPTotBNGD(int index) {
		return PTotBNGD[index];
	}
	public void setPTotBNGD(int pTotBNGD,int index) {
		PTotBNGD[index] = pTotBNGD;
	}
	public int getPRepTotBNGD(int index) {
		return PRepTotBNGD[index];
	}
	public void setPRepTotBNGD(int pRepTotBNGD,int index) {
		PRepTotBNGD[index] = pRepTotBNGD;
	}
	public String getStr1() {
		return Str1;
	}
	public void setStr1(String str1) {
		Str1 = str1;
	}
	public boolean isPayrep() {
		return payrep;
	}
	public void setPayrep(boolean payrep) {
		this.payrep = payrep;
	}
	
	//******************Get & Set for ReportDAO.java file variable
	
	
	public  final int pre_Addr = 7;
	public  final int  per_Addr = 8;
	public  final int Cpt = 9;
	public  final int  Dept_cd = 3;
	public  final int NDesig_cd = 2;
	public  final int Branch_cd = 1;
	public  final String CityCd = "CITY";
	public  final String DistCd = "DIST";
	public  final String StateCd = "STATE";
	public  final int Ac_no = 4;
	
	//*********************
	
	public  Connection Cn;
	public  ResultSet RS;
	public  ResultSet Day;	
	public  ResultSet Gl;
	public  ResultSet usr;
	
	//*************************************
	
	public  int  LInd;
	public  String MSQL;
	public  String amd;
	public  String G_UserId;
	public  int G_UserLvl;
	public  boolean BgnTrn;
	public  boolean DayOk;
	public  Date WDt;
	public  String mOpt;
	
	//Variable added by  S.s Desai
	public  Date p_trxDt;
	public  String p_Mode;
	public  int p_empNo;
	public  Date p_LevfDt;
	public  Date p_LevtDt;
	public  String p_Liv;
	public  String p_AppNo;
	public  String p_EmpNm;
	
	//********************
	public  int g_Branch;
	public  String g_Module;
	public  String g_File;
	public  String g_Opt;
	public  Date g_Bdate;
	public  Date g_Edate;
	public  String G_Bgc;
	public  String G_Bgn;
	public  String g_Fld;
	public  String g_Subsys;
	public  String g_Tbl;
	public  String g_GlName;
	public  int g_GlNo;
	
	//*************************
	public  FileWriter Fp;// Type Change From Int to File Writer For Java
	public  String FileName;
	public  int PageLen;
	public  String SearchStr;
	public  boolean PressCan;
	public  boolean Foot_Fl;
	public  int Foot_Ctr;
	public  long Fpos;
	public  int PageNo;
	public  int BrnNo;
	public  int LineLen;
	public  int LnCount;
	public  int LineSpace;
	
	public  String Hdg1;
	public  String Hdg2;
	public  String Hdg3;
	public  String Hdg4;
	public  String Hdg5;
	public  String Hdg6;
	public  String Hdg7;
	
	public  String Foot1;
	public  String Foot2;   //+"63524"; remain
	
	public  String UrepName;
	
	//********************************************************
	
	//variables added by nakul patil for println function
	public  String ststr = "";


	public Connection getCn() {
		return Cn;
	}
	public void setCn(Connection cn) {
		Cn = cn;
	}
	public ResultSet getRS() {
		return RS;
	}
	public void setRS(ResultSet rS) {
		RS = rS;
	}
	public ResultSet getDay() {
		return Day;
	}
	public void setDay(ResultSet day) {
		Day = day;
	}
	public ResultSet getGl() {
		return Gl;
	}
	public void setGl(ResultSet gl) {
		Gl = gl;
	}
	public ResultSet getUsr() {
		return usr;
	}
	public void setUsr(ResultSet usr) {
		this.usr = usr;
	}
	public int getLInd() {
		return LInd;
	}
	public void setLInd(int lInd) {
		LInd = lInd;
	}
	public String getMSQL() {
		return MSQL;
	}
	public void setMSQL(String mSQL) {
		MSQL = mSQL;
	}
	public String getAmd() {
		return amd;
	}
	public void setAmd(String amd) {
		this.amd = amd;
	}
	public String getG_UserId() {
		return G_UserId;
	}
	public void setG_UserId(String g_UserId) {
		G_UserId = g_UserId;
	}
	public int getG_UserLvl() {
		return G_UserLvl;
	}
	public void setG_UserLvl(int g_UserLvl) {
		G_UserLvl = g_UserLvl;
	}
	public boolean isBgnTrn() {
		return BgnTrn;
	}
	public void setBgnTrn(boolean bgnTrn) {
		BgnTrn = bgnTrn;
	}
	public boolean isDayOk() {
		return DayOk;
	}
	public void setDayOk(boolean dayOk) {
		DayOk = dayOk;
	}
	public Date getWDt() {
		return WDt;
	}
	public void setWDt(Date wDt) {
		WDt = wDt;
	}
	public String getmOpt() {
		return mOpt;
	}
	public void setmOpt(String mOpt) {
		this.mOpt = mOpt;
	}
	public Date getP_trxDt() {
		return p_trxDt;
	}
	public void setP_trxDt(Date p_trxDt) {
		this.p_trxDt = p_trxDt;
	}
	public String getP_Mode() {
		return p_Mode;
	}
	public void setP_Mode(String p_Mode) {
		this.p_Mode = p_Mode;
	}
	public int getP_empNo() {
		return p_empNo;
	}
	public void setP_empNo(int p_empNo) {
		this.p_empNo = p_empNo;
	}
	public Date getP_LevfDt() {
		return p_LevfDt;
	}
	public void setP_LevfDt(Date p_LevfDt) {
		this.p_LevfDt = p_LevfDt;
	}
	public Date getP_LevtDt() {
		return p_LevtDt;
	}
	public void setP_LevtDt(Date p_LevtDt) {
		this.p_LevtDt = p_LevtDt;
	}
	public String getP_Liv() {
		return p_Liv;
	}
	public void setP_Liv(String p_Liv) {
		this.p_Liv = p_Liv;
	}
	public String getP_AppNo() {
		return p_AppNo;
	}
	public void setP_AppNo(String p_AppNo) {
		this.p_AppNo = p_AppNo;
	}
	public String getP_EmpNm() {
		return p_EmpNm;
	}
	public void setP_EmpNm(String p_EmpNm) {
		this.p_EmpNm = p_EmpNm;
	}
	public int getG_Branch() {
		return g_Branch;
	}
	public void setG_Branch(int g_Branch) {
		this.g_Branch = g_Branch;
	}
	public String getG_Module() {
		return g_Module;
	}
	public void setG_Module(String g_Module) {
		this.g_Module = g_Module;
	}
	public String getG_File() {
		return g_File;
	}
	public void setG_File(String g_File) {
		this.g_File = g_File;
	}
	public String getG_Opt() {
		return g_Opt;
	}
	public void setG_Opt(String g_Opt) {
		this.g_Opt = g_Opt;
	}
	public Date getG_Bdate() {
		return g_Bdate;
	}
	public void setG_Bdate(Date g_Bdate) {
		this.g_Bdate = g_Bdate;
	}
	public Date getG_Edate() {
		return g_Edate;
	}
	public void setG_Edate(Date g_Edate) {
		this.g_Edate = g_Edate;
	}
	public String getG_Bgc() {
		return G_Bgc;
	}
	public void setG_Bgc(String g_Bgc) {
		G_Bgc = g_Bgc;
	}
	public String getG_Bgn() {
		return G_Bgn;
	}
	public void setG_Bgn(String g_Bgn) {
		G_Bgn = g_Bgn;
	}
	public String getG_Fld() {
		return g_Fld;
	}
	public void setG_Fld(String g_Fld) {
		this.g_Fld = g_Fld;
	}
	public String getG_Subsys() {
		return g_Subsys;
	}
	public void setG_Subsys(String g_Subsys) {
		this.g_Subsys = g_Subsys;
	}
	public String getG_Tbl() {
		return g_Tbl;
	}
	public void setG_Tbl(String g_Tbl) {
		this.g_Tbl = g_Tbl;
	}
	public String getG_GlName() {
		return g_GlName;
	}
	public void setG_GlName(String g_GlName) {
		this.g_GlName = g_GlName;
	}
	public int getG_GlNo() {
		return g_GlNo;
	}
	public void setG_GlNo(int g_GlNo) {
		this.g_GlNo = g_GlNo;
	}
	public FileWriter getFp() {
		return Fp;
	}
	public void setFp(FileWriter fp) {
		Fp = fp;
	}
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public int getPageLen() {
		return PageLen;
	}
	public void setPageLen(int pageLen) {
		PageLen = pageLen;
	}
	public String getSearchStr() {
		return SearchStr;
	}
	public void setSearchStr(String searchStr) {
		SearchStr = searchStr;
	}
	public boolean isPressCan() {
		return PressCan;
	}
	public void setPressCan(boolean pressCan) {
		PressCan = pressCan;
	}
	public boolean isFoot_Fl() {
		return Foot_Fl;
	}
	public void setFoot_Fl(boolean foot_Fl) {
		Foot_Fl = foot_Fl;
	}
	public int getFoot_Ctr() {
		return Foot_Ctr;
	}
	public void setFoot_Ctr(int foot_Ctr) {
		Foot_Ctr = foot_Ctr;
	}
	public long getFpos() {
		return Fpos;
	}
	public void setFpos(long fpos) {
		Fpos = fpos;
	}
	public int getPageNo() {
		return PageNo;
	}
	public void setPageNo(int pageNo) {
		PageNo = pageNo;
	}
	public int getBrnNo() {
		return BrnNo;
	}
	public void setBrnNo(int brnNo) {
		BrnNo = brnNo;
	}
	public int getLineLen() {
		return LineLen;
	}
	public void setLineLen(int lineLen) {
		LineLen = lineLen;
	}
	public int getLnCount() {
		return LnCount;
	}
	public void setLnCount(int lnCount) {
		LnCount = lnCount;
	}
	public String getHdg1() {
		return Hdg1;
	}
	public void setHdg1(String hdg1) {
		Hdg1 = hdg1;
	}
	public String getHdg2() {
		return Hdg2;
	}
	public void setHdg2(String hdg2) {
		Hdg2 = hdg2;
	}
	public String getHdg3() {
		return Hdg3;
	}
	public void setHdg3(String hdg3) {
		Hdg3 = hdg3;
	}
	public String getHdg4() {
		return Hdg4;
	}
	public void setHdg4(String hdg4) {
		Hdg4 = hdg4;
	}
	public String getHdg5() {
		return Hdg5;
	}
	public void setHdg5(String hdg5) {
		Hdg5 = hdg5;
	}
	public String getHdg6() {
		return Hdg6;
	}
	public void setHdg6(String hdg6) {
		Hdg6 = hdg6;
	}
	public String getHdg7() {
		return Hdg7;
	}
	public void setHdg7(String hdg7) {
		Hdg7 = hdg7;
	}
	public String getFoot1() {
		return Foot1;
	}
	public void setFoot1(String foot1) {
		Foot1 = foot1;
	}
	public String getFoot2() {
		return Foot2;
	}
	public void setFoot2(String foot2) {
		Foot2 = foot2;
	}
	public String getUrepName() {
		return UrepName;
	}
	public void setUrepName(String urepName) {
		UrepName = urepName;
	}
	public String getStstr() {
		return ststr;
	}
	public void setStstr(String ststr) {
		this.ststr = ststr;
	}
	public int getPre_Addr() {
		return pre_Addr;
	}
	public int getPer_Addr() {
		return per_Addr;
	}
	public int getCpt() {
		return Cpt;
	}
	public int getDept_cd() {
		return Dept_cd;
	}
	public int getNDesig_cd() {
		return NDesig_cd;
	}
	public int getBranch_cd() {
		return Branch_cd;
	}
	public String getCityCd() {
		return CityCd;
	}
	public String getDistCd() {
		return DistCd;
	}
	public String getStateCd() {
		return StateCd;
	}
	public int getAc_no() {
		return Ac_no;
	}
	
	public int getLineSpace() {
		return LineSpace;
	}
	public void setLineSpace(int lineSpace) {
		LineSpace = lineSpace;
	}

	
	
	
	
}
