package payroll.Model;

public class DemoEmpBean {

private int EMP_ID;
private String EMP_NAME;
private  String EMP_DESIGNATION;


public int getEMPID() {
	return EMP_ID;
}
public void setEMPID(int Emp_id)
{
	EMP_ID=Emp_id;
}

public String getEMPNAME()
{
	return EMP_NAME;
}
public void setEMPNAME(String emp_name)
{
	EMP_NAME=emp_name;
}


public String getEMPDESIGNATION()
{
	return EMP_DESIGNATION;
}

public void setEMPDESIGNATION(String emp_desig)
{
	EMP_DESIGNATION=emp_desig;
}
}
