package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.DAO.ConnectionManager;
import payroll.DAO.EmpAddrHandler;
import payroll.DAO.EmpAwrdHandler;
import payroll.DAO.EmpExperHandler;
import payroll.DAO.EmpFamHandler;
import payroll.DAO.EmpOffHandler;
import payroll.DAO.EmplQulHandler;
import payroll.DAO.EmployeeHandler;
import payroll.DAO.LookupHandler;
import payroll.DAO.RelieveInfoHandler;
import payroll.Model.EmpAddressBean;
import payroll.Model.EmpAwardBean;
import payroll.Model.EmpExperBean;
import payroll.Model.EmpFamilyBean;
import payroll.Model.EmpOffBean;
import payroll.Model.EmpQualBean;
import payroll.Model.EmployeeBean;
import payroll.Model.Lookup;
import payroll.Model.RelieveInfoBean;

/**
 * Servlet implementation class EmployeeServlet
 */
@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");

		if (action.equalsIgnoreCase("employee"))// here error action getting
												// null
		{
			EmployeeHandler emp = new EmployeeHandler();
			String EMPNO = (String) session.getAttribute("empno");
			EmployeeBean empbean = new EmployeeBean();
			empbean = emp.getEmployeeInformation(EMPNO);
			System.out.println("now in employee servlet");
			request.setAttribute("empbean", empbean);
			request.getRequestDispatcher("employee.jsp?action=showemp")
					.forward(request, response);
		} else if (action.equalsIgnoreCase("empQual")) {
			EmplQulHandler empqual = new EmplQulHandler();
			ArrayList<EmpQualBean> EmpQualList = new ArrayList<EmpQualBean>();
			String EMPNO = (String) session.getAttribute("empno");
			EmpQualList = empqual.getEmpQual(EMPNO);
			session.setAttribute("empQualList", EmpQualList);
			request.getRequestDispatcher("empQual.jsp?action=showemp").forward(
					request, response);
		} else if (action.equalsIgnoreCase("address")) {
			EmpAddrHandler empaddr = new EmpAddrHandler();
			ArrayList<EmpAddressBean> EmpAddrList = new ArrayList<EmpAddressBean>();
			String EMPNO = (String) session.getAttribute("empno");
			EmpAddrList = empaddr.getEmpAddress(EMPNO);
			session.setAttribute("empaddrList", EmpAddrList);
			request.getRequestDispatcher("empAddress.jsp?action=showemp")
					.forward(request, response);
		} else if (action.equalsIgnoreCase("family")) {
			EmpFamHandler empfam = new EmpFamHandler();
			ArrayList<EmpFamilyBean> EmpFamilyList = new ArrayList<EmpFamilyBean>();
			String EMPNO = (String) session.getAttribute("empno");
			EmpFamilyList = empfam.getEmpFamily(EMPNO);
			session.setAttribute("empfamilyList", EmpFamilyList);
			request.getRequestDispatcher("empFamily.jsp?action=showemp")
					.forward(request, response);
		} else if (action.equalsIgnoreCase("experience")) {
			EmpExperHandler empexper = new EmpExperHandler();
			ArrayList<EmpExperBean> EmpExperList = new ArrayList<EmpExperBean>();
			String EMPNO = (String) session.getAttribute("empno");
			EmpExperList = empexper.getEmpExper(EMPNO);
			session.setAttribute("empexperList", EmpExperList);
			request.getRequestDispatcher("empExper.jsp?action=showemp")
					.forward(request, response);
		} else if (action.equalsIgnoreCase("officialInfo")) {
			EmpOffHandler empoff = new EmpOffHandler();
			ArrayList<EmpOffBean> EmpOffList = new ArrayList<EmpOffBean>();
			String EMPNO = (String) session.getAttribute("empno");
			EmpOffList = empoff.getEmpOfficInfo(EMPNO);
			session.setAttribute("empoffList", EmpOffList);
			request.getRequestDispatcher("officialInfo.jsp?action=showemp")
					.forward(request, response);
		} else if (action.equalsIgnoreCase("awardInfo")) {
			EmpAwrdHandler empawrd = new EmpAwrdHandler();
			ArrayList<EmpAwardBean> EmpAwardList = new ArrayList<EmpAwardBean>();
			String EMPNO = (String) session.getAttribute("empno");
			EmpAwardList = empawrd.getEmpAwardInfo(EMPNO);
			session.setAttribute("empawardList", EmpAwardList);
			request.getRequestDispatcher("awardInfo.jsp?action=showemp")
					.forward(request, response);
		} else if (action.equalsIgnoreCase("Tempemployee")) {
			EmployeeHandler emp = new EmployeeHandler();
			String EMPNO = (String) session.getAttribute("empno");
			EmployeeBean empbean = new EmployeeBean();
			empbean = emp.getTempEmployeeInformation(EMPNO);
			request.setAttribute("empbean", empbean);
			request.getRequestDispatcher("TempEmployee.jsp?action=showemp")
					.forward(request, response);
		} else if (action.equalsIgnoreCase("saldetails")) {
			EmployeeHandler emp = new EmployeeHandler();
			String EMPNO = (String) session.getAttribute("empno");
			EmployeeBean empbean = new EmployeeBean();
			empbean = emp.getTempEmployeeInformation(EMPNO);
			request.setAttribute("empbean", empbean);
			request.getRequestDispatcher("SalTempEmployee.jsp?action=showemp")
					.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");

		if (action.equalsIgnoreCase("employee")) {
			EmployeeHandler emp = new EmployeeHandler();
			String EMPNO = (String) session.getAttribute("empno");
			EmployeeBean empbean = new EmployeeBean();
			empbean = emp.getEmployeeInformation(EMPNO);
			request.setAttribute("empbean", empbean);
			request.getRequestDispatcher("employee.jsp?action=showemp")
					.forward(request, response);
		} else if (action.equalsIgnoreCase("empQual")) {
			ArrayList<EmpQualBean> EmpQualList = new ArrayList<EmpQualBean>();
			EmplQulHandler empqual = new EmplQulHandler();
			String EMPNO = (String) session.getAttribute("empno");
			EmpQualList = empqual.getEmpQual(EMPNO);
			request.setAttribute("empQualList", EmpQualList);
			request.getRequestDispatcher("empQual.jsp?action=showemp").forward(
					request, response);
		} else if (action.equalsIgnoreCase("editempQual")) {
			EmplQulHandler empqual = new EmplQulHandler();
			EmpQualBean empQualBean = new EmpQualBean();
			empQualBean.setSRNO((request.getParameter("srNo") == null ? 0
					: Integer.parseInt(request.getParameter("srNo"))));
			empQualBean
					.setEMPNO(Integer.parseInt(request.getParameter("empNo")));
			empQualBean.setDEGREE(Integer.parseInt(request
					.getParameter("EDEGREE")));
			empQualBean.setINST(request.getParameter("eUniversity"));
			empQualBean
					.setPASSYEAR(request.getParameter("ePassyear") == null ? 0
							: Integer.parseInt(request
									.getParameter("ePassyear")));
			empQualBean.setCLASS(request.getParameter("eGrade"));
			empQualBean.setPERCENT(Float.parseFloat(request
					.getParameter("ePercent")));
			empQualBean.setREM(request.getParameter("eRemark"));
			empqual.updateEmpQual(empQualBean);// call updateEmpQual method for
												// update info of qualification
			ArrayList<EmpQualBean> EmpQualList1 = new ArrayList<EmpQualBean>();
			String EMPNO = (String) session.getAttribute("empno");
			EmpQualList1 = empqual.getEmpQual(EMPNO);// call getEmpQual method
														// for getting info of
														// qualification
			session.setAttribute("empQualList", EmpQualList1);
			request.getRequestDispatcher("empQual.jsp?action=showemp").forward(
					request, response);
		} else if (action.equalsIgnoreCase("editempExper")) {
			EmpExperHandler empexper = new EmpExperHandler();
			EmpExperBean empexpbean = new EmpExperBean();
			empexpbean
					.setEMPNO(Integer.parseInt(request.getParameter("empNo")));
			empexpbean.setSRNO(Integer.parseInt(request.getParameter("srNo")));
			empexpbean.setORGNAME(request.getParameter("eOrgName") == "" ? ""
					: request.getParameter("eOrgName"));
			empexpbean.setPOST(request.getParameter("eDesg") == "" ? ""
					: request.getParameter("eDesg"));
			// empexpbean.setSALARY(Integer.parseInt(request.getParameter("eSalary")==""?"0":request.getParameter("eSalary")));
			empexpbean.setSALARY(request.getParameter("eSalary") == "" ? "0"
					: request.getParameter("eSalary"));
			empexpbean
					.setFROMDT(request.getParameter("eFrmDate") == "" ? "GETDATE()"
							: request.getParameter("eFrmDate"));
			empexpbean
					.setTODT(request.getParameter("eToDate") == "" ? "GETDATE()"
							: request.getParameter("eToDate"));
			empexper.updateEmpExp(empexpbean);// call updateEmpExp method for
												// update info of Experience
			ArrayList<EmpExperBean> EmpExperList = new ArrayList<EmpExperBean>();
			String EMPNO = (String) session.getAttribute("empno");
			EmpExperList = empexper.getEmpExper(EMPNO);// call getEmpExper info
														// for getting info of
														// experience
			session.setAttribute("empexperList", EmpExperList);
			request.getRequestDispatcher("empExper.jsp?action=showemp")
					.forward(request, response);
		} else if (action.equalsIgnoreCase("editempFamily")) {
			EmpFamHandler empfam = new EmpFamHandler();
			EmpFamilyBean empfamliybean = new EmpFamilyBean();
			LookupHandler lkhp = new LookupHandler();
			empfamliybean.setSRNO(Integer.parseInt(request
					.getParameter("esrNo")));
			empfamliybean.setEMPNO(Integer.parseInt(request
					.getParameter("eempNo")));
			empfamliybean.setNAME(request.getParameter("eName") == "" ? ""
					: request.getParameter("eName"));
			empfamliybean.setGENDER(request.getParameter("eGender") == "" ? ""
					: request.getParameter("eGender"));
			empfamliybean
					.setOCCUPATION(request.getParameter("eOccup") == "" ? ""
							: request.getParameter("eOccup"));
			empfamliybean
					.setDEPENDYN(request.getParameter("eDepend") == "" ? ""
							: request.getParameter("eDepend"));
			// empfamliybean.setDOB(request.getParameter("eDOB")==""?"GETDATE()":request.getParameter("eDOB"));
			empfamliybean.setDOB(request.getParameter("eDOB") == "" ? ""
					: request.getParameter("eDOB"));
			empfamliybean.setQUALI(Integer.parseInt(request
					.getParameter("eQuali") == "" ? "0" : request
					.getParameter("eQuali")));
			empfamliybean.setRELATION(Integer.parseInt(request
					.getParameter("eRelation") == "" ? "0" : request
					.getParameter("eRelation")));
			empfam.updateEmpFamily(empfamliybean);// call updateEmpFamily for
													// updating info
			ArrayList<EmpFamilyBean> EmpFamilyList = new ArrayList<EmpFamilyBean>();
			String EMPNO = (String) session.getAttribute("empno");
			EmpFamilyList = empfam.getEmpFamily(EMPNO);
			session.setAttribute("empfamilyList", EmpFamilyList);
			request.getRequestDispatcher("empFamily.jsp?action=showemp")
					.forward(request, response);
		} else if (action.equalsIgnoreCase("editempAddress")) {
			EmpAddrHandler empaddr = new EmpAddrHandler();
			EmpAddressBean empaddreebean = new EmpAddressBean();
			empaddreebean.setEMPNO(Integer.parseInt(request
					.getParameter("empNo")));
			
			empaddreebean.setID(Integer.parseInt(request.getParameter("addressID")));
			empaddreebean.setADDRTYPE(request.getParameter("addrtype"));
			empaddreebean.setADDR1(request.getParameter("eLine1"));
			empaddreebean.setADDR2(request.getParameter("eLine2"));
			empaddreebean.setADDR3(request.getParameter("eLine3"));
			empaddreebean.setCITY(Integer.parseInt(request
					.getParameter("eCity")));
			empaddreebean.setSTATE(Integer.parseInt(request
					.getParameter("eState")));
			empaddreebean.setPIN(Integer.parseInt(request
					.getParameter("ePincode")));
			empaddreebean.setTELNO(Long.parseLong(request
					.getParameter("eContact")));
			empaddr.updateEmpAddress(empaddreebean);
			ArrayList<EmpAddressBean> EmpAddrList = new ArrayList<EmpAddressBean>();
			String EMPNO = (String) session.getAttribute("empno");
			EmpAddrList = empaddr.getEmpAddress(EMPNO);
			session.setAttribute("empaddrList", EmpAddrList);
			request.getRequestDispatcher("empAddress.jsp?action=showemp")
					.forward(request, response);
		} else if (action.equalsIgnoreCase("empAwardEdit")) {
			EmpAwrdHandler empawrd = new EmpAwrdHandler();
			EmpAwardBean empawardbeen = new EmpAwardBean();
			empawardbeen.setEFFDATE(request.getParameter("eEffDate"));
			empawardbeen.setEMPNO(Integer.parseInt(request
					.getParameter("eempNo")));
			empawardbeen.setMDESC(request.getParameter("eDescrp"));
			empawardbeen.setORDER_NO(request.getParameter("eOrderNo"));
			empawardbeen.setSRNO(Integer.parseInt(request
					.getParameter("eTranSerNO")));
			empawardbeen.setTRNCD(Integer.parseInt(request
					.getParameter("etranCode")));
			empawardbeen.setAMOUNT(request.getParameter("eamount"));
			empawrd.updateAwardInfo(empawardbeen);
			ArrayList<EmpAwardBean> EmpAwardList = new ArrayList<EmpAwardBean>();
			String EMPNO = (String) session.getAttribute("empno");
			EmpAwardList = empawrd.getEmpAwardInfo(EMPNO);
			session.setAttribute("empawardList", EmpAwardList);
			request.getRequestDispatcher("awardInfo.jsp?action=showemp")
					.forward(request, response);
		} else if (action.equalsIgnoreCase("addemployee")) {
			
			String empcd = request.getParameter("empcd");
			EmployeeHandler emp = new EmployeeHandler();
			EmployeeBean empbean = new EmployeeBean();
			empbean.setFNAME(request.getParameter("FEmpname") == null ? ""
					: request.getParameter("FEmpname"));
			empbean.setMNAME(request.getParameter("MEmpname") == null ? ""
					: request.getParameter("MEmpname"));
			empbean.setLNAME(request.getParameter("LEmpname") == null ? ""
					: request.getParameter("LEmpname"));
			String empName = request.getParameter("FEmpname") == null ? ""
					: request.getParameter("FEmpname") + " "
							+ request.getParameter("LEmpname") == null ? ""
							: request.getParameter("LEmpname");
			empbean.setSALUTE(Integer.parseInt(request.getParameter("aSALUTE")));
			empbean.setBGRP(request.getParameter("bldgrp"));
			empbean.setBPLACE(request.getParameter("placedob"));
			empbean.setCASTCD(Integer
					.parseInt(request.getParameter("cast2") == null ? "0"
							: request.getParameter("cast2")));
			empbean.setCATEGORYCD(Integer.parseInt(request
					.getParameter("category2") == null ? "0" : request
					.getParameter("category2")));
			empbean.setDEPAMT(Integer
					.parseInt(request.getParameter("Depamnt") == "" ? "0"
							: request.getParameter("Depamnt")));
			empbean.setDISABILYN(request.getParameter("phandicap2"));
			empbean.setDOB(request.getParameter("dob"));
			empbean.setDOJ(request.getParameter("jdate"));
			empbean.setDOL(request.getParameter("ldate") == null ? null
					: request.getParameter("ldate"));
			empbean.setDRVLISCNNO(request.getParameter("drvLicNumber"));
			empbean.setGENDER(request.getParameter("gender"));
			empbean.setHEIGHT(request.getParameter("height2") != "" ? Integer
					.parseInt((request.getParameter("height2"))) : 0);
			empbean.setIDENTITY(request.getParameter("identity")); // UAN ON THE
																	// PLACE OF
																	// IDENTITY
			empbean.setMARRIED(request.getParameter("marrystatus"));// status
			empbean.setMARRIEDDT(request.getParameter("marrydate"));
			empbean.setMLWFNO(request.getParameter("mlwfno"));// number
			
			//add emp type
			System.out.println("request.getParameter(emptype)"+request.getParameter("emptype"));
			empbean.setEMPTYPE(Integer.parseInt(request.getParameter("emptype")));
			
			
			empbean.setOTHERDTL(request.getParameter("odetail"));
			empbean.setPANNO(request.getParameter("panno"));
			empbean.setPASSPORTNO(request.getParameter("passportno"));
			empbean.setPFNO(request.getParameter("pfno"));// number
			empbean.setPFNOMINEE(request.getParameter("pfNominee"));
			empbean.setPFNOMIREL(request.getParameter("NomRel"));
			empbean.setPFOPENDT(request.getParameter("pfjDate"));
			empbean.setRELEGENCD(Integer.parseInt(request
					.getParameter("relegion") == null ? "0" : request
					.getParameter("relegion")));
			empbean.setRESIDSTAT(Integer.parseInt(request
					.getParameter("residancy") == null ? "0" : request
					.getParameter("residancy")));
			empbean.setRESNLFTCD(Integer.parseInt(request
					.getParameter("lreason") == null ? "0" : request
					.getParameter("lreason")));
			empbean.setSBOND(request.getParameter("SecBond"));
			empbean.setEMPCODE(request.getParameter("empcd"));
			empbean.setLEAVEDATE((request.getParameter("leavedate") == "" ? "1": request.getParameter("leavedate")));
			empbean.setRETIREMENTDATE((request.getParameter("anotation") == null ? "--": request.getParameter("anotation")));

			empbean.setSTATUS(request.getParameter(""));
			empbean.setVEHICLEDES(request.getParameter("vehicalname2"));
			empbean.setWEIGHT(request.getParameter("weight2") != "" ? Integer
					.parseInt((request.getParameter("weight2"))) : 0);
			// empbean.setEMPTYPE(Integer.parseInt(request.getParameter("emptype")==null?"0":request.getParameter("emptype")));

			empbean.setAADHAARNUM(request.getParameter("aadharNumber"));
			empbean.setCONFIRM_DATE(request.getParameter("confirmdate")== "" ? "1": request.getParameter("confirmdate"));
		//	empbean.setRETIREMENT_EXT_PERIOD(request.getParameter("extperdate")== "" ? request.getParameter("anotation"): request.getParameter("extperdate"));
			empbean.setFILENUMBER(request.getParameter("fileNumber"));
			empbean.setPROB_START_DATE(request.getParameter("prob_start_date")== "" ? "": request.getParameter("prob_start_date"));
			empbean.setPROB_END_DATE(request.getParameter("prob_end_date")== "" ? "": request.getParameter("prob_end_date"));
/*			empbean.setLIC_GRAT_NO(Integer.parseInt(request
					.getParameter("licGratNumber")==null?"":request.getParameter("licGratNumber")));
			empbean.setLIC_LEAVE_ENCASH_NO(Integer.parseInt(request
					.getParameter("leaveEncashNo")==null?"":request.getParameter("leaveEncashNo")));
			empbean.setLIC_GROUP_INSURANCE_NO(Integer.parseInt(request
							.getParameter("groupInsuranceNo")==null?"":request.getParameter("groupInsuranceNo")));
			empbean.setLIC_GRAT_POLICY_NO(Integer.parseInt(request
					.getParameter("gratPolicyNo")==null?"":request.getParameter("gratPolicyNo")));
			empbean.setLIC_LEAVE_ENCASH_POLICY_NO(Integer.parseInt(request
					.getParameter("leaveEncashPolicyNo")==null?"":request.getParameter("leaveEncashPolicyNo")));*/
			empbean.setLIC_GRAT_NO(Integer.parseInt(request.getParameter("licGratNumber")==""?"0":request.getParameter("licGratNumber")));
			empbean.setLIC_LEAVE_ENCASH_NO(Integer.parseInt(request.getParameter("leaveEncashNo")==""?"0":request.getParameter("leaveEncashNo")));
			empbean.setLIC_GROUP_INSURANCE_NO(Integer.parseInt(request.getParameter("groupInsuranceNo")==""?"0":request.getParameter("groupInsuranceNo")));
			empbean.setLIC_GRAT_POLICY_NO(Integer.parseInt(request.getParameter("gratPolicyNo")==""?"0":request.getParameter("gratPolicyNo")));
			empbean.setLIC_LEAVE_ENCASH_POLICY_NO(Integer.parseInt(request.getParameter("leaveEncashPolicyNo")==""?"0":request.getParameter("leaveEncashPolicyNo")));
			
			
			
			//empbean.setBGRP("blood group ********===="+request.getParameter("bldgrp"));
			//System.out.println("leaveEncashPolicyNo is "+request.getParameter("leaveEncashPolicyNo"));
			String empnumber = emp.insertEmployeeInfo(empbean);

			// String empcd = EmployeeHandler.getcodeserv();
			session.setAttribute("empcode", empcd);
			session.setAttribute("empno", empnumber);// here set new employee
														// number in
														// session..........
			session.setAttribute("empname", empName);

			// insert initial record in emptran
			EmpOffHandler eoh = new EmpOffHandler();
			EmpOffBean eob = new EmpOffBean();
			eob.setEMPNO(Integer.parseInt(empnumber));
			eob.setEFFDATE(request.getParameter("jdate"));
			eob.setPrj_srno(Integer
					.parseInt(request.getParameter("prj") == null ? "0"
							: request.getParameter("prj")));
			eob.setORDER_DT(request.getParameter("jdate"));
			// eoh.insertInitialTran(eob);
			System.out.println("in serv code" + empcd);
			request.getRequestDispatcher("empQual.jsp?action=addemp").forward(
					request, response);
		} else if (action.equalsIgnoreCase("addQualification")) {
			EmplQulHandler empqual = new EmplQulHandler();
			String EMPNO = (String) session.getAttribute("empno");
			EmpQualBean empqualbean = new EmpQualBean();
			empqualbean.setEMPNO(Integer.parseInt(request
					.getParameter("aempNo")));
			empqualbean.setDEGREE(Integer.parseInt(request
					.getParameter("aRDEGREE") == "" ? "0" : request
					.getParameter("aRDEGREE")));
			empqualbean.setPERCENT(Float.parseFloat(request
					.getParameter("aPercent") == "" ? "0" : request
					.getParameter("aPercent")));
			empqualbean.setINST(request.getParameter("aUniName") == "" ? ""
					: request.getParameter("aUniName"));
			empqualbean.setCLASS(request.getParameter("aGrade") == "" ? ""
					: request.getParameter("aGrade"));
			empqualbean.setPASSYEAR(Integer.parseInt(request
					.getParameter("aPassyear") == "" ? "0" : request
					.getParameter("aPassyear")));
			empqualbean.setREM(request.getParameter("aRemark") == "" ? ""
					: request.getParameter("aRemark"));
			String moreAdd = request.getParameter("moreAdd");
			empqual.insertQuali(empqualbean);
			try {
				String qual = request.getParameter("qual");
				if (qual.equalsIgnoreCase("addmore")) {
					ArrayList<EmpQualBean> EmpQualList1 = new ArrayList<EmpQualBean>();
					EmpQualList1 = empqual.getEmpQual(EMPNO);
					session.setAttribute("empQualList", EmpQualList1);
					request.getRequestDispatcher("empQual.jsp?action=showemp")
							.forward(request, response);
				}
			} catch (Exception e) {
				ArrayList<EmpQualBean> EmpQualList1 = new ArrayList<EmpQualBean>();
				EmpQualList1 = empqual.getEmpQual(EMPNO);
				session.setAttribute("empQualList", EmpQualList1);
				if (moreAdd.equalsIgnoreCase("NO")) {
					request.getRequestDispatcher("empAddress.jsp?action=addemp")
							.forward(request, response);
				} else {
					request.getRequestDispatcher("empQual.jsp?action=addemp")
							.forward(request, response);
				}
			}
		} else if (action.equalsIgnoreCase("addFamily")) {
			EmpFamHandler empfam = new EmpFamHandler();
			EmpFamilyBean addFamilyBean = new EmpFamilyBean();
			addFamilyBean.setGENDER(request.getParameter("rGender") == "" ? ""
					: request.getParameter("rGender"));
			addFamilyBean.setNAME(request.getParameter("rName") == "" ? ""
					: request.getParameter("rName"));
			addFamilyBean
					.setOCCUPATION(request.getParameter("rOccup") == "" ? ""
							: request.getParameter("rOccup"));
			addFamilyBean.setRELATION(Integer.parseInt(request
					.getParameter("rRelation") == "" ? "0" : request
					.getParameter("rRelation")));
			addFamilyBean
					.setDEPENDYN(request.getParameter("rDepend") == "" ? ""
							: request.getParameter("rDepend"));
			// addFamilyBean.setDOB(request.getParameter("rDOB")==""?"GETDATE()":request.getParameter("rDOB"));
			addFamilyBean.setDOB(request.getParameter("rDOB") == "" ? ""
					: request.getParameter("rDOB"));
			addFamilyBean.setQUALI(Integer.parseInt(request
					.getParameter("rQuali") == "" ? "0" : request
					.getParameter("rQuali")));
			addFamilyBean.setEMPNO(Integer.parseInt(request
					.getParameter("aempNo")));
			String moreAdd = request.getParameter("moreAdd");
			empfam.insertFamily(addFamilyBean);
			ArrayList<EmpFamilyBean> EmpFamilyList = new ArrayList<EmpFamilyBean>();
			try {
				String family = request.getParameter("family");
				if (family.equalsIgnoreCase("addmore")) {
					String EMPNO = (String) session.getAttribute("empno");
					EmpFamilyList = empfam.getEmpFamily(EMPNO);
					session.setAttribute("empfamilyList", EmpFamilyList);
					response.sendRedirect("empFamily.jsp?action=showemp");
				}

			} catch (Exception e) {
				if (moreAdd.equalsIgnoreCase("NO")) {
					request.getRequestDispatcher("empExper.jsp?action=addemp")
							.forward(request, response);
				} else {
					String EMPNO = (String) session.getAttribute("empno");
					EmpFamilyList = empfam.getEmpFamily(EMPNO);
					session.setAttribute("empfamilyList", EmpFamilyList);
					request.getRequestDispatcher("empFamily.jsp?action=addemp")
							.forward(request, response);
				}
			}
		} else if (action.equalsIgnoreCase("addExper")) {
			EmpExperHandler empexper = new EmpExperHandler();
			EmpExperBean addexpbean = new EmpExperBean();
			addexpbean
					.setEMPNO(Integer.parseInt(request.getParameter("aempNo")));
			addexpbean.setORGNAME(request.getParameter("aexOrgName") == "" ? ""
					: request.getParameter("aexOrgName"));
			addexpbean.setPOST(request.getParameter("aexDesg") == "" ? ""
					: request.getParameter("aexDesg"));
			// addexpbean.setSALARY(Integer.parseInt(request.getParameter("aexSalary")==""?"0":request.getParameter("aexSalary")));
			addexpbean.setSALARY(request.getParameter("aexSalary") == "" ? "0"
					: request.getParameter("aexSalary"));
			/*
			 * addexpbean.setFROMDT(request.getParameter("aexFrmDate")==""?
			 * "GETDATE()":request.getParameter("aexFrmDate"));
			 * addexpbean.setTODT
			 * (request.getParameter("aexToDate")==""?"GETDATE()"
			 * :request.getParameter("aexToDate"));
			 */
			addexpbean.setFROMDT(request.getParameter("aexFrmDate") == "" ? ""
					: request.getParameter("aexFrmDate"));
			addexpbean.setTODT(request.getParameter("aexToDate") == "" ? ""
					: request.getParameter("aexToDate"));

			String moreAdd = request.getParameter("moreAdd");
			empexper.insertExper(addexpbean);
			ArrayList<EmpExperBean> EmpExperList = new ArrayList<EmpExperBean>();
			try {
				String exper = request.getParameter("exper");
				if (exper.equalsIgnoreCase("addmore")) {
					String EMPNO = (String) session.getAttribute("empno");
					EmpExperList = empexper.getEmpExper(EMPNO);
					session.setAttribute("empexperList", EmpExperList);
					response.sendRedirect("empExper.jsp?action=showemp");
				}
			} catch (Exception e) {
				if (moreAdd.equalsIgnoreCase("NO")) {
					request.getRequestDispatcher(
							"officialInfo.jsp?action=addemp").forward(request,
							response);
				} else {
					String EMPNO = (String) session.getAttribute("empno");
					EmpExperList = empexper.getEmpExper(EMPNO);
					session.setAttribute("empexperList", EmpExperList);
					request.getRequestDispatcher("empExper.jsp?action=addemp")
							.forward(request, response);
				}
			}
		} else if (action.equalsIgnoreCase("addAddress")) {
			EmpAddrHandler empaddr = new EmpAddrHandler();
			EmpAddressBean presentaddrbean = new EmpAddressBean();
			String addrlist = request.getParameter("addrlist") == null ? ""
					: request.getParameter("addrlist");
			presentaddrbean
					.setADDR1(request.getParameter("prLine1") == null ? ""
							: request.getParameter("prLine1"));
			presentaddrbean
					.setADDR2(request.getParameter("prLine2") == null ? ""
							: request.getParameter("prLine2"));
			presentaddrbean
					.setADDR3(request.getParameter("prLine3") == null ? ""
							: request.getParameter("prLine3"));
			presentaddrbean.setADDRTYPE("CA");
			presentaddrbean
					.setSTATE(request.getParameter("prstate") != "" ? Integer
							.parseInt(request.getParameter("prstate")) : 0);
			presentaddrbean
					.setCITY(request.getParameter("prCity") != "" ? Integer
							.parseInt(request.getParameter("prCity")) : 0);
			presentaddrbean
					.setEMPNO(request.getParameter("aempNo") != "" ? Integer
							.parseInt(request.getParameter("aempNo")) : 0);
			presentaddrbean
					.setPIN(request.getParameter("prPincode") != "" ? Integer
							.parseInt(request.getParameter("prPincode")) : 0);
			presentaddrbean.setTELNO(Long.parseLong(request
					.getParameter("prContact") == "" ? "0" : request
					.getParameter("prContact")));
			EmpAddressBean parmntaddrbean = new EmpAddressBean();
			parmntaddrbean
					.setADDR1(request.getParameter("parLine1") == null ? ""
							: request.getParameter("parLine1"));
			parmntaddrbean
					.setADDR2(request.getParameter("parLine2") == null ? ""
							: request.getParameter("parLine2"));
			parmntaddrbean
					.setADDR3(request.getParameter("parLine3") == null ? ""
							: request.getParameter("parLine3"));
			parmntaddrbean.setADDRTYPE("PA");
			parmntaddrbean
					.setCITY(request.getParameter("parCity") != "" ? Integer
							.parseInt(request.getParameter("parCity")) : 0);
			parmntaddrbean
					.setSTATE(request.getParameter("parstate") != "" ? Integer
							.parseInt(request.getParameter("parstate")) : 0);
			parmntaddrbean
					.setEMPNO(request.getParameter("aempNo") != "" ? Integer
							.parseInt(request.getParameter("aempNo")) : 0);
			parmntaddrbean
					.setPIN(request.getParameter("parPincode") != "" ? Integer
							.parseInt(request.getParameter("parPincode")) : 0);
			parmntaddrbean.setTELNO(Long.parseLong(request
					.getParameter("parContact") == "" ? "0" : request
					.getParameter("parContact")));
			empaddr.insertAddress(presentaddrbean, parmntaddrbean);
			ArrayList<EmpAddressBean> EmpAddrList = new ArrayList<EmpAddressBean>();
			String EMPNO = (String) session.getAttribute("empno");
			EmpAddrList = empaddr.getEmpAddress(EMPNO);
			session.setAttribute("empaddrList", EmpAddrList);
			if (addrlist.equalsIgnoreCase("zero")) {
				request.getRequestDispatcher("empAddress.jsp?action=showemp")
						.forward(request, response);
			} else {
				response.sendRedirect("empFamily.jsp?action=addemp");
			}
		} else if (action.equalsIgnoreCase("addOffcInfo")) {

			String uid = session.getAttribute("EMPNO").toString();

			EmpOffHandler empoff = new EmpOffHandler();
			EmpOffBean empoffbean = new EmpOffBean();
			
			System.out.println("MANAGER ID : "+request.getParameter("mngrid"));
		//	System.out.println("MANAGER ID INT : "+Integer.parseInt(request.getParameter("mngrid")));
			
			// int mng=Integer.parseInt(request.getParameter("mngrid")) ;
			// String mng=request.getParameter("mngrid") ;
			if (request.getParameter("mngrid") == null
				//	|| request.getParameter("mngrid") == "" || mng==0) {
					|| request.getParameter("mngrid") == "" || (request.getParameter("mngrid").equalsIgnoreCase("0"))) {
				
				empoffbean.setManagerId(0);
			} else {
				String mngr = request.getParameter("mngrid");
				String mngrid[] = mngr.split(":");
				empoffbean.setManagerId(Integer.parseInt(mngrid[2]));
			}
			empoffbean.setACNO(request.getParameter("saccntNo") == null ? ""
					: request.getParameter("saccntNo"));
			empoffbean.setPrj_srno(request.getParameter("TraCode") == "" ? 0
					: Integer.parseInt(request.getParameter("TraCode")));
			empoffbean.setPrj_name(request.getParameter("prjname") == null ? ""
					: request.getParameter("prjname"));
			empoffbean.setDEPT(request.getParameter("Depart") == "" ? 0
					: Integer.parseInt(request.getParameter("Depart")));
			empoffbean.setDESIG(request.getParameter("Desgn") == "" ? 0
					: Integer.parseInt(request.getParameter("Desgn")));
			empoffbean.setEFFDATE(request.getParameter("EffDate") == null ? ""
					: request.getParameter("EffDate"));
			empoffbean.setEMPNO(request.getParameter("empNo") == "" ? 0
					: Integer.parseInt(request.getParameter("empNo")));
			// empoffbean.setGRADE(request.getParameter("grade")==""?0:Integer.parseInt(request.getParameter("grade")));
			empoffbean.setMDESC(request.getParameter("Descrp") == null ? ""
					: request.getParameter("Descrp"));
			empoffbean
					.setORDER_DT(request.getParameter("OrderDate") == null ? ""
							: request.getParameter("OrderDate"));
			empoffbean.setORDER_NO(request.getParameter("OrderNo") == null ? ""
					: request.getParameter("OrderNo"));
			empoffbean.setSTATUS(1);
			// empoffbean.setTRNCD(Integer.parseInt(request.getParameter("TraCode")==""?"0":request.getParameter("TraCode")));
			empoffbean.setTRNCD(Integer.parseInt(request
					.getParameter("TraCode") == "" ? "0" : request
					.getParameter("TraCode")));
			empoffbean.setBANK_NAME(request.getParameter("BankName") == "" ? 0
					: Integer.parseInt(request.getParameter("BankName")));
			// empoffbean.setPrj_code(new
			// EmpOffHandler().getDescrption("PRJ_CODE", "PRJ", "PRJ_SRNO",
			// Integer.parseInt(request.getParameter("eprjname")==null?"0":request.getParameter("eprjname"))));
			empoff.insertOfficInfo(empoffbean, uid);
			try {
				String offinfo = request.getParameter("offinfo");
				if (offinfo.equalsIgnoreCase("addmore")) {
					ArrayList<EmpOffBean> EmpOffList = new ArrayList<EmpOffBean>();
					String EMPNO = (String) session.getAttribute("empno");
					EmpOffList = empoff.getEmpOfficInfo(EMPNO);
					session.setAttribute("empoffList", EmpOffList);
					response.sendRedirect("officialInfo.jsp?action=showemp");
				}
			} catch (Exception e) {
				request.getRequestDispatcher("awardInfo.jsp?action=addemp")
						.forward(request, response);
			}
		}

		else if (action.equalsIgnoreCase("Relinfo")) {
			System.out.println("Aj..");
			RelieveInfoHandler relhr = new RelieveInfoHandler();
			RelieveInfoBean relbean = new RelieveInfoBean();
			String EMPNO = (String) session.getAttribute("empno");
			System.out.println("Aj1.. "+EMPNO);
			relbean.setEMPNO(Integer.parseInt(EMPNO));
			relbean.setRESGN_DATE(request.getParameter("rDate") == null ? ""
					: request.getParameter("rDate"));
			relbean.setRESGN_ACCTD_DATE(request.getParameter("raDate") == null ? ""
					: request.getParameter("raDate"));
			relbean.setREASON(request.getParameter("aReason") == null ? ""
					: request.getParameter("aReason"));
			relbean.setNTC_PERIOD(request.getParameter("rPeriod") == null ? ""
					: request.getParameter("rPeriod"));
			relbean.setTERMINATE(request.getParameter("term") == null ? ""
					: request.getParameter("term"));
			relbean.setDEATH(request.getParameter("death") == null ? ""
					: request.getParameter("death"));
			relbean.setLEFT_DATE(request.getParameter("lDate") == null ? ""
					: request.getParameter("lDate"));
			relbean.setLEFT_REASON(request.getParameter("left_reason") == null ? "": request.getParameter("left_reason"));
			
			System.out.println("AA:  "+request.getParameter("left_reason") == null ? "": request.getParameter("left_reason"));
			String act = request.getParameter("act");
			String check = "2";
			if (act.equalsIgnoreCase("ADD")) {

				relhr.insertreliev(relbean);
				check = "1";
			} else {
				relhr.updateRelinfo(relbean);
				check = "1";
			}
			try {
				String relieveinfo = request.getParameter("relieveinfo");

				ArrayList<RelieveInfoBean> Emprellist = new ArrayList<RelieveInfoBean>();
				session.setAttribute("emprellist", Emprellist);
				response.sendRedirect("RelievingNew.jsp?EMPNO=" + EMPNO
						+ "&check=" + check);

			} catch (Exception e) {
				check = "2";
				request.getRequestDispatcher(
						"RelievingNew.jsp?EMPNO=" + EMPNO + "&check=" + check)
						.forward(request, response);
			}

		}

		else if (action.equalsIgnoreCase("addAwardInfo")) {

			EmpAwrdHandler empawrd = new EmpAwrdHandler();
			EmpAwardBean empawardbean = new EmpAwardBean();
			empawardbean.setEFFDATE(request.getParameter("aEffDate") == "" ? ""
					: request.getParameter("aEffDate"));
			empawardbean.setEMPNO(Integer.parseInt(request
					.getParameter("aempNo")));
			empawardbean
					.setORDER_NO(request.getParameter("aOrderNo") == "" ? ""
							: request.getParameter("aOrderNo"));
			String moreAdd = request.getParameter("moreAdd");
			empawardbean.setMDESC(request.getParameter("aDescrp") == "" ? ""
					: request.getParameter("aDescrp"));
			empawardbean.setAMOUNT(request.getParameter("aamount") == "" ? ""
					: request.getParameter("aamount"));
			empawardbean.setTRNCD(Integer.parseInt(request
					.getParameter("atranCode") == "" ? "0" : request
					.getParameter("atranCode")));
			empawrd.insertAwardInfo(empawardbean);
			ArrayList<EmpAwardBean> EmpAwardList = new ArrayList<EmpAwardBean>();
			try {
				String award = request.getParameter("award");
				if (award.equalsIgnoreCase("addmore")) {
					String EMPNO = (String) session.getAttribute("empno");
					EmpAwardList = empawrd.getEmpAwardInfo(EMPNO);
					session.setAttribute("empawardList", EmpAwardList);
					response.sendRedirect("awardInfo.jsp?action=showemp");
				}
			} catch (Exception e) {
				if (moreAdd.equalsIgnoreCase("NO")) {
					request.getRequestDispatcher(
							"otherDetail.jsp?action=addemp").forward(request,
							response);
				} else {
					String EMPNO = (String) session.getAttribute("empno");
					EmpAwardList = empawrd.getEmpAwardInfo(EMPNO);
					session.setAttribute("empawardList", EmpAwardList);
					request.getRequestDispatcher("awardInfo.jsp?action=addemp")
							.forward(request, response);
				}
			}
		} else if (action.equalsIgnoreCase("editoffInfo")) {
			EmpOffHandler empoff = new EmpOffHandler();
			EmpOffBean empoffbean = new EmpOffBean();
			empoffbean.setACNO(request.getParameter("esaccntNo"));

			empoffbean
					.setPrj_code((request.getParameter("eprjname") == null ? ""
							: request.getParameter("eprjname")));
			empoffbean
					.setDEPT(Integer
							.parseInt(request.getParameter("eDepart") == "" ? "0"
									: request.getParameter("eDepart")));
			empoffbean.setBANK_NAME(Integer.parseInt(request
					.getParameter("eBankName") == "" ? "0" : request
					.getParameter("eBankName")));
			empoffbean
					.setDESIG(Integer.parseInt(request.getParameter("eDesgn")));
			empoffbean.setEFFDATE(request.getParameter("eEffDate"));
			empoffbean
					.setEMPNO(Integer.parseInt(request.getParameter("eempNo")));
			empoffbean.setSRNO(Integer.parseInt(request
					.getParameter("eTrnsrNo")));
			// empoffbean.setGRADE(Integer.parseInt(request.getParameter("egrade")==null?"0":request.getParameter("egrade")));
			empoffbean.setMDESC(request.getParameter("eDescrp"));
			empoffbean.setORDER_DT(request.getParameter("eOrderDate"));
			empoffbean.setORDER_NO(request.getParameter("eOrderNo"));
			empoffbean.setSTATUS(1);
			empoffbean.setTRNCD(Integer.parseInt(request
					.getParameter("eTraCode")));
			// empoffbean.setPrj_code(new
			// EmpOffHandler().getDescrption("PRJ_CODE", "PRJ", "PRJ_SRNO",
			// Integer.parseInt(request.getParameter("eprjname")==null?"0":request.getParameter("eprjname"))));
			empoff.updateOffinfo(empoffbean);
			ArrayList<EmpOffBean> EmpOffList = new ArrayList<EmpOffBean>();
			String EMPNO = (String) session.getAttribute("empno");
			EmpOffList = empoff.getEmpOfficInfo(EMPNO);
			session.setAttribute("empoffList", EmpOffList);
			request.getRequestDispatcher("officialInfo.jsp?action=showemp")
					.forward(request, response);
		} else if (action.equalsIgnoreCase("checkempcd")) {
			System.out.println("akshay");
			Integer flags = null;
			try {
				Connection conn = ConnectionManager.getConnection();
				String empcd = request.getParameter("empcd");
				String overlap = "select  count(*) as count from empmast where EMPCODE  ='"
						+ empcd + "' ";
				System.out.println("akshay" + overlap);
				Statement stmt = conn.createStatement();
				ResultSet rslt = stmt.executeQuery(overlap);
				while (rslt.next()) {
					conn.commit();
					flags = rslt.getInt("count");
				}
				conn.close();

				PrintWriter out1 = response.getWriter();
				response.setContentType("text/html");
				out1.write(flags.toString());

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		else if (action.equalsIgnoreCase("editemployee")) {
			System.out.println("Blood Group in servlet::  "
					+ request.getParameter("bldgrp"));
			EmployeeHandler emp = new EmployeeHandler();
			EmployeeBean empbeanedit = new EmployeeBean();
			LookupHandler lkhp = new LookupHandler();
			Lookup lookupBean = new Lookup();
			
			empbeanedit.setRETIREMENT_EXT_PERIOD(request.getParameter("extperdate")== "" ? request.getParameter("anotation"): request.getParameter("extperdate"));
			empbeanedit.setFNAME(request.getParameter("FEmpname"));
			empbeanedit.setMNAME(request.getParameter("MEmpname"));
			empbeanedit.setLNAME(request.getParameter("LEmpname"));
			empbeanedit.setSALUTE(Integer.parseInt(request
					.getParameter("aSALUTE")));
			
			/*
			lookupBean = lkhp.getLookup("BLOOD_GRP-"
					+ request.getParameter("bldgrp"));*/
		
			
	        // empbeanedit.getBGRP(); 
		   
			
			System.out.println(" bd** "+request.getParameter(String.valueOf("bldgrp"))==null?"0":request.getParameter(String.valueOf("bldgrp")));
			
			empbeanedit.setBGRP(request.getParameter(String.valueOf("bldgrp"))==null?"0":request.getParameter(String.valueOf("bldgrp")));
			
			
			
			empbeanedit.setBPLACE(request.getParameter("placedob"));
			
			lookupBean = lkhp.getLookup("CASTE-"
					+ request.getParameter("cast2"));
			
			//empbeanedit.setCASTCD(lookupBean.getLKP_SRNO());
			empbeanedit.setCASTCD(Integer.parseInt(request.getParameter("cast2")));
			
			lookupBean = lkhp.getLookup("CATE-"
					+ request.getParameter("category2"));
			
			//empbeanedit.setCATEGORYCD(lookupBean.getLKP_SRNO());
			
			empbeanedit.setCATEGORYCD(Integer.parseInt(request.getParameter("category2")));
			
			empbeanedit.setDEPAMT(Integer.parseInt(request
					.getParameter("Depamnt")));
			empbeanedit.setDISABILYN(request.getParameter("phandicap2"));
			empbeanedit.setDOB(request.getParameter("dob"));
			empbeanedit.setDOJ(request.getParameter("jdate"));
			empbeanedit.setDOL(request.getParameter("ldate"));
			empbeanedit.setDRVLISCNNO(request.getParameter("drvLicNumber"));
			empbeanedit.setEMPNO(Integer.parseInt(session.getAttribute("empno")
					.toString()));
			empbeanedit.setGENDER(request.getParameter("gender"));
			empbeanedit.setHEIGHT(Double.parseDouble(request
					.getParameter("height2")));
			empbeanedit.setIDENTITY(request.getParameter("identity"));
			empbeanedit.setMARRIED(request.getParameter("marrystatus"));// status
			empbeanedit.setMARRIEDDT(request.getParameter("marrydate"));
			empbeanedit.setMLWFNO(request.getParameter("mlwfno"));// number
			
			System.out.println("request.getParameter)"+request.getParameter("emptype"));
			empbeanedit.setEMPTYPE(Integer.parseInt(request.getParameter("emptype")));// number
			
			empbeanedit.setOTHERDTL(request.getParameter("odetail"));
			empbeanedit.setPANNO(request.getParameter("panno"));
			empbeanedit.setPASSPORTNO(request.getParameter("passportno"));
			empbeanedit.setPFNO(request.getParameter("pfno"));// number
			empbeanedit.setPFNOMINEE(request.getParameter("pfNominee"));
			empbeanedit.setPFNOMIREL(request.getParameter("NomRel"));
			empbeanedit.setPFOPENDT(request.getParameter("pfjDate"));
			lookupBean = lkhp.getLookup("RELIG-"
					+ request.getParameter("relegion"));
			//empbeanedit.setRELEGENCD(lookupBean.getLKP_SRNO());
			empbeanedit.setRELEGENCD(Integer.parseInt(request.getParameter("relegion")));
			
			
			lookupBean = lkhp.getLookup("RESICD-"
					+ request.getParameter("residancy"));
			empbeanedit.setRESIDSTAT(Integer.parseInt(request
					.getParameter("residancy")));
			empbeanedit.setSBOND(request.getParameter("SecBond"));
			empbeanedit.setSTATUS(request.getParameter("statusemp"));
			empbeanedit.setVEHICLEDES(request.getParameter("vehicalname2"));
			empbeanedit.setWEIGHT(Double.parseDouble(request
					.getParameter("weight2")));
			/*
			 * lookupBean=lkhp.getLookup("ET-"+request.getParameter("emptype"));
			 * empbeanedit.setEMPTYPE(lookupBean.getLKP_SRNO());
			 */
			empbeanedit.setAADHAARNUM(request.getParameter("aadharNumber"));

			empbeanedit.setLEAVEDATE((request.getParameter("leavedate") == "" ? "1": request.getParameter("leavedate")));
			empbeanedit.setCONFIRM_DATE(request.getParameter("confirmdate")== "" ? "1": request.getParameter("confirmdate"));
		//	empbeanedit.setRETIREMENT_EXT_PERIOD(request.getParameter("extperdate")== "" ? "1": request.getParameter("extperdate"));
			empbeanedit.setFILENUMBER(request.getParameter("fileNumber"));
			empbeanedit.setRETIREMENTDATE(request.getParameter("anotation"));
			empbeanedit.setPROB_START_DATE((request.getParameter("prob_start_date") == "" ? "": request.getParameter("prob_start_date")));
			empbeanedit.setPROB_END_DATE((request.getParameter("prob_end_date") == "" ? "": request.getParameter("prob_end_date")));
			/*		System.out.println(Integer.parseInt(request
					.getParameter("licGratNumber")==null?"0":request.getParameter("licGratNumber")));
			empbeanedit.setLIC_GRAT_NO(Integer.parseInt(request
					.getParameter("licGratNumber")==null?"0":request.getParameter("licGratNumber")));
			empbeanedit.setLIC_LEAVE_ENCASH_NO(Integer.parseInt(request
					.getParameter("leaveEncashNo")==null?"0":request.getParameter("leaveEncashNo")));
			empbeanedit.setLIC_GROUP_INSURANCE_NO(Integer.parseInt(request
							.getParameter("groupInsuranceNo")==null?"0":request.getParameter("groupInsuranceNo")));
			empbeanedit.setLIC_GRAT_POLICY_NO(Integer.parseInt(request
					.getParameter("gratPolicyNo")==null?"0":request.getParameter("gratPolicyNo")));
			empbeanedit.setLIC_LEAVE_ENCASH_POLICY_NO(Integer.parseInt(request
					.getParameter("leaveEncashPolicyNo")==null?"0":request.getParameter("leaveEncashPolicyNo")));
*/
					
				empbeanedit.setLIC_GRAT_NO(Integer.parseInt(request.getParameter("licGratNumber")==""?"0":request.getParameter("licGratNumber")));
				empbeanedit.setLIC_LEAVE_ENCASH_NO(Integer.parseInt(request.getParameter("leaveEncashNo")==""?"0":request.getParameter("leaveEncashNo")));
				empbeanedit.setLIC_GROUP_INSURANCE_NO(Integer.parseInt(request.getParameter("groupInsuranceNo")==""?"0":request.getParameter("groupInsuranceNo")));
				empbeanedit.setLIC_GRAT_POLICY_NO(Integer.parseInt(request.getParameter("gratPolicyNo")==""?"0":request.getParameter("gratPolicyNo")));
				empbeanedit.setLIC_LEAVE_ENCASH_POLICY_NO(Integer.parseInt(request.getParameter("leaveEncashPolicyNo")==""?"0":request.getParameter("leaveEncashPolicyNo")));
				
			
			boolean flag = emp.updateEmployeeInfo(empbeanedit);
			if (flag)
				request.getRequestDispatcher(
						"EmployeeServlet?action=employee&flag=1").forward(
						request, response);
			else
				request.getRequestDispatcher(
						"EmployeeServlet?action=employee&flag=2").forward(
						request, response);
		} else if (action.equalsIgnoreCase("tempempsearch")) {
			EmployeeHandler emp = new EmployeeHandler();
			EmployeeBean empbean = new EmployeeBean();

			/*
			 * String EMPNO=request.getParameter("EMPNO"); StringTokenizer st =
			 * new StringTokenizer(EMPNO,":"); while(st.hasMoreTokens()) {
			 * EMPNO=st.nextToken(); }
			 * 
			 * session.setAttribute("empno", EMPNO);
			 * empbean=emp.getEmployeeInformation(EMPNO);
			 * request.setAttribute("empbean", empbean);
			 * request.getRequestDispatcher("EmpInfo.jsp").forward(request,
			 * response);
			 */
			String[] employ = request.getParameter("EMPNO").split(":");
			String EMPCODE = employ[1].trim();
			String EMPNO = employ[2].trim();

			session.setAttribute("empcode", EMPCODE);
			session.setAttribute("empno", EMPNO);
			empbean = emp.getTempEmployeeInformation(EMPNO);

			request.setAttribute("empbean", empbean);

			request.getRequestDispatcher("TempEmpInfo.jsp").forward(request,
					response);
		} else if (action.equalsIgnoreCase("addtempemployee")) {
			// String empcd =request.getParameter("empcd") ;
			EmployeeHandler emp = new EmployeeHandler();
			EmployeeBean empbean = new EmployeeBean();
			empbean.setFNAME(request.getParameter("FEmpname") == null ? ""
					: request.getParameter("FEmpname"));
			empbean.setMNAME(request.getParameter("MEmpname") == null ? ""
					: request.getParameter("MEmpname"));
			empbean.setLNAME(request.getParameter("LEmpname") == null ? ""
					: request.getParameter("LEmpname"));
			String empName = request.getParameter("FEmpname") == null ? ""
					: request.getParameter("FEmpname")
							+ " "
							+ (request.getParameter("LEmpname") == null ? ""
									: request.getParameter("LEmpname"));
			empbean.setSALUTE(Integer.parseInt(request.getParameter("aSALUTE")));
			empbean.setBGRP(request.getParameter("bldgrp"));
			empbean.setBPLACE(request.getParameter("placedob"));
			empbean.setCASTCD(Integer
					.parseInt(request.getParameter("cast2") == null ? "0"
							: request.getParameter("cast2")));
			empbean.setCATEGORYCD(Integer.parseInt(request
					.getParameter("category2") == null ? "0" : request
					.getParameter("category2")));
			empbean.setDEPAMT(Integer
					.parseInt(request.getParameter("Depamnt") == "" ? "0"
							: request.getParameter("Depamnt")));
			empbean.setDISABILYN(request.getParameter("phandicap2"));
			empbean.setDOB(request.getParameter("dob"));
			empbean.setDOJ(request.getParameter("jdate"));
			empbean.setDOL(request.getParameter("ldate") == null ? null
					: request.getParameter("ldate"));
			empbean.setDRVLISCNNO(request.getParameter("drvLicNumber"));
			empbean.setGENDER(request.getParameter("gender"));
			empbean.setHEIGHT(request.getParameter("height2") != "" ? Integer
					.parseInt((request.getParameter("height2"))) : 0);
			empbean.setIDENTITY(request.getParameter("identity")); // UAN ON THE
																	// PLACE OF
																	// IDENTITY
			empbean.setMARRIED(request.getParameter("marrystatus"));// status
			empbean.setMARRIEDDT(request.getParameter("marrydate"));
			empbean.setMLWFNO(request.getParameter("mlwfno"));// number
			empbean.setOTHERDTL(request.getParameter("odetail"));
			empbean.setPANNO(request.getParameter("panno"));
			empbean.setPASSPORTNO(request.getParameter("passportno"));
			empbean.setPFNO(request.getParameter("pfno"));// number
			empbean.setPFNOMINEE(request.getParameter("pfNominee"));
			empbean.setPFNOMIREL(request.getParameter("NomRel"));
			empbean.setPFOPENDT(request.getParameter("pfjDate"));
			empbean.setRELEGENCD(Integer.parseInt(request
					.getParameter("relegion") == null ? "0" : request
					.getParameter("relegion")));
			empbean.setRESIDSTAT(Integer.parseInt(request
					.getParameter("residancy") == null ? "0" : request
					.getParameter("residancy")));
			empbean.setRESNLFTCD(Integer.parseInt(request
					.getParameter("lreason") == null ? "0" : request
					.getParameter("lreason")));
			empbean.setSBOND(request.getParameter("SecBond"));
			empbean.setEMPCODE(request.getParameter("empcd"));
			empbean.setLEAVEDATE((request.getParameter("leavedate") == null ? "--"
					: request.getParameter("leavedate")));
			empbean.setRETIREMENTDATE((request.getParameter("anotation") == null ? "--"
					: request.getParameter("anotation")));

			empbean.setSTATUS(request.getParameter(""));
			empbean.setVEHICLEDES(request.getParameter("vehicalname2"));
			empbean.setWEIGHT(request.getParameter("weight2") != "" ? Integer
					.parseInt((request.getParameter("weight2"))) : 0);
			// empbean.setEMPTYPE(Integer.parseInt(request.getParameter("emptype")==null?"0":request.getParameter("emptype")));

			empbean.setAADHAARNUM(request.getParameter("aadharNumber"));

			String empnumber = emp.inserttempempEmployeeInfo(empbean);
			String empcd = String.valueOf("T" + empnumber);
			// String empcd = EmployeeHandler.getcodeserv();
			session.setAttribute("empcode", empcd);
			session.setAttribute("empno", empnumber);// here set new employee
														// number in
														// session..........
			session.setAttribute("empname", empName);

			// insert initial record in emptran
			/*
			 * EmpOffHandler eoh=new EmpOffHandler(); EmpOffBean eob=new
			 * EmpOffBean(); eob.setEMPNO(Integer.parseInt(empnumber));
			 * eob.setEFFDATE(request.getParameter("jdate"));
			 * eob.setPrj_srno(Integer
			 * .parseInt(request.getParameter("prj")==null
			 * ?"0":request.getParameter("prj")));
			 * eob.setORDER_DT(request.getParameter("jdate"));
			 */
			// eoh.insertInitialTran(eob);
			System.out.println("in serv code" + empcd);
			// response.sendRedirect("TempEmpSearch.jsp?flag=1");
			response.sendRedirect("SalTempEmployee.jsp?action=addemp");
		}

		else if (action.equalsIgnoreCase("edittempemployee")) {
			EmployeeHandler emp = new EmployeeHandler();
			EmployeeBean empbeanedit = new EmployeeBean();
			LookupHandler lkhp = new LookupHandler();
			Lookup lookupBean = new Lookup();
			empbeanedit.setFNAME(request.getParameter("FEmpname"));
			empbeanedit.setMNAME(request.getParameter("MEmpname"));
			empbeanedit.setLNAME(request.getParameter("LEmpname"));
			empbeanedit.setSALUTE(Integer.parseInt(request
					.getParameter("aSALUTE")));
			empbeanedit.setBGRP(request.getParameter("bldgrp"));
			empbeanedit.setBPLACE(request.getParameter("placedob"));
			lookupBean = lkhp.getLookup("CASTE-"
					+ request.getParameter("cast2"));
			empbeanedit.setCASTCD(lookupBean.getLKP_SRNO());
			lookupBean = lkhp.getLookup("CATE-"
					+ request.getParameter("category2"));
			empbeanedit.setCATEGORYCD(lookupBean.getLKP_SRNO());
			empbeanedit.setDEPAMT(Integer.parseInt(request
					.getParameter("Depamnt")));
			empbeanedit.setDISABILYN(request.getParameter("phandicap2"));
			empbeanedit.setDOB(request.getParameter("dob"));
			empbeanedit.setDOJ(request.getParameter("jdate"));
			empbeanedit.setDOL(request.getParameter("ldate"));
			empbeanedit.setDRVLISCNNO(request.getParameter("drvLicNumber"));
			empbeanedit.setEMPNO(Integer.parseInt(session.getAttribute("empno")
					.toString()));
			empbeanedit.setGENDER(request.getParameter("gender"));
			empbeanedit.setHEIGHT(Double.parseDouble(request
					.getParameter("height2")));
			empbeanedit.setIDENTITY(request.getParameter("identity"));
			empbeanedit.setMARRIED(request.getParameter("marrystatus"));// status
			empbeanedit.setMARRIEDDT(request.getParameter("marrydate"));
			empbeanedit.setMLWFNO(request.getParameter("mlwfno"));// number
			empbeanedit.setOTHERDTL(request.getParameter("odetail"));
			empbeanedit.setPANNO(request.getParameter("panno"));
			empbeanedit.setPASSPORTNO(request.getParameter("passportno"));
			empbeanedit.setPFNO(request.getParameter("pfno"));// number
			empbeanedit.setPFNOMINEE(request.getParameter("pfNominee"));
			empbeanedit.setPFNOMIREL(request.getParameter("NomRel"));
			empbeanedit.setPFOPENDT(request.getParameter("pfjDate"));
			lookupBean = lkhp.getLookup("RELIG-"
					+ request.getParameter("relegion"));
			empbeanedit.setRELEGENCD(lookupBean.getLKP_SRNO());
			lookupBean = lkhp.getLookup("RESICD-"
					+ request.getParameter("residancy"));
			empbeanedit.setRESIDSTAT(Integer.parseInt(request
					.getParameter("residancy")));
			empbeanedit.setSBOND(request.getParameter("SecBond"));
			empbeanedit.setSTATUS(request.getParameter("statusemp"));
			empbeanedit.setVEHICLEDES(request.getParameter("vehicalname2"));
			empbeanedit.setWEIGHT(Double.parseDouble(request
					.getParameter("weight2")));
			/*
			 * lookupBean=lkhp.getLookup("ET-"+request.getParameter("emptype"));
			 * empbeanedit.setEMPTYPE(lookupBean.getLKP_SRNO());
			 */
			empbeanedit.setAADHAARNUM(request.getParameter("aadharNumber"));

			empbeanedit
					.setLEAVEDATE((request.getParameter("leavedate") == null ? "--"
							: request.getParameter("leavedate")));

			boolean flag = emp.updateTempEmployeeInfo(empbeanedit);
			if (flag)
				response.sendRedirect("SalTempEmployee.jsp?action=showemp");
			// request.getRequestDispatcher("SalTempEmployee.jsp?action=showwmp").forward(request,
			// response);
			else
				request.getRequestDispatcher(
						"EmployeeServlet?action=Tempemployee&flag=2").forward(
						request, response);
		} else if (action.equalsIgnoreCase("Tempemployee")) {
			EmployeeHandler emp = new EmployeeHandler();
			String EMPNO = (String) session.getAttribute("empno");
			EmployeeBean empbean = new EmployeeBean();
			empbean = emp.getTempEmployeeInformation(EMPNO);
			request.setAttribute("empbean", empbean);
			request.getRequestDispatcher("TempEmployee.jsp?action=showemp")
					.forward(request, response);
		} else if (action.equalsIgnoreCase("addsaldetails")) {
			EmployeeHandler salemp = new EmployeeHandler();
			int empno = Integer.parseInt(request.getParameter("aempNo"));
			String sal = request.getParameter("sal");
			int absentdays = Integer
					.parseInt(request.getParameter("ABSENTDAY"));
			String deduction = request.getParameter("DEDUCTION");
			String ptamt = request.getParameter("PTAMT");
			String month = request.getParameter("MONTH");
			System.out.println(month);
			String netamt = request.getParameter("NETAMT");
			String itax = request.getParameter("TAX");
			String flag = salemp.InsertSalaryDetails(empno, sal, absentdays,
					deduction, ptamt, month, itax, netamt);
			if (flag.equalsIgnoreCase("true")) {
				response.sendRedirect("TempEmpSearch.jsp?flag=1");
			} else {
				response.sendRedirect("TempEmpSearch.jsp?flag=2");
			}

		} else if (action.equalsIgnoreCase("editsaldetails")) {
			EmployeeHandler salemp = new EmployeeHandler();
			int empno = Integer.parseInt(request.getParameter("aempNo"));
			String sal = request.getParameter("sal");
			int absentdays = Integer
					.parseInt(request.getParameter("ABSENTDAY"));
			String deduction = request.getParameter("DEDUCTION");
			String ptamt = request.getParameter("PTAMT");
			String month = request.getParameter("MONTH");
			String netamt = request.getParameter("NETAMT");
			String itax = request.getParameter("TAX");
			String flag = salemp.InsertSalaryDetails(empno, sal, absentdays,
					deduction, ptamt, month, itax, netamt);
			if (flag.equalsIgnoreCase("true")) {
				response.sendRedirect("TempEmpSearch.jsp?flag=3");
			} else {
				response.sendRedirect("TempEmpSearch.jsp?flag=4");
			}

		}
		else if(action.equalsIgnoreCase("retExtDate"))
		{
			Integer flag = null;
			String retirementExtDate =request.getParameter("annotationDT");
			int EMPNO =Integer.parseInt(session.getAttribute("empno").toString());
			EmployeeHandler emphandler=new EmployeeHandler();
			   try
			   {
				   flag=emphandler.checkRetiredate(retirementExtDate,EMPNO);
				    PrintWriter out1 = response.getWriter();
					response.setContentType("text/html");
					out1.write(flag.toString());
					 System.out.println("falg12  "+flag);
		    } 
			   	catch (java.text.ParseException e) {
					e.printStackTrace();
				}
		}
		else if(action.equalsIgnoreCase("checkRetExtDate"))
		{
			Integer flag = null;
			int EMPNO =Integer.parseInt(session.getAttribute("empno").toString());
			EmployeeHandler emphandler=new EmployeeHandler();
			   try
			   {
				    flag=emphandler.checkRetireExtensiondate(EMPNO);
				    PrintWriter out1 = response.getWriter();
					response.setContentType("text/html");
					out1.write(flag.toString());
					 System.out.println("falg13  "+flag);
		      } 
			   	catch (java.text.ParseException e) {
					e.printStackTrace();
				}
		}
		
		else if(action.equalsIgnoreCase("chkEffectbetDate"))
		{
			String flag="";
			int EMPNO =Integer.parseInt(session.getAttribute("empno").toString());
			String effdate=request.getParameter("cureffdate");
			System.out.println("EMPNO: "+EMPNO+" effdate: "+effdate);
			EmployeeHandler emphandler=new EmployeeHandler();
			   try
			   {
				    flag=emphandler.chkEffectbetDate(EMPNO);
				    PrintWriter out1 = response.getWriter();
					response.setContentType("text/html");
					out1.write(flag.toString());
					 System.out.println("falg13  "+flag);
		      } 
			   	catch (java.text.ParseException e) {
					e.printStackTrace();
				}
		}
		
		
		else {
			EmployeeHandler emp = new EmployeeHandler();
			EmployeeBean empbean = new EmployeeBean();
			
			/*
			 * String EMPNO=request.getParameter("EMPNO"); StringTokenizer st =
			 * new StringTokenizer(EMPNO,":"); while(st.hasMoreTokens()) {
			 * EMPNO=st.nextToken(); }
			 * 
			 * session.setAttribute("empno", EMPNO);
			 * empbean=emp.getEmployeeInformation(EMPNO);
			 * request.setAttribute("empbean", empbean);
			 * request.getRequestDispatcher("EmpInfo.jsp").forward(request,
			 * response);
			 */
			String[] employ = request.getParameter("EMPNO").split(":");
			String EMPCODE = employ[1].trim();
			String EMPNO = employ[2].trim();

			session.setAttribute("empcode", EMPCODE);
			session.setAttribute("empno", EMPNO);
			empbean = emp.getEmployeeInformation(EMPNO);
			
			request.setAttribute("empbean", empbean);
			
			request.getRequestDispatcher("EmpInfo.jsp").forward(request,
					response);
		}
	}
}