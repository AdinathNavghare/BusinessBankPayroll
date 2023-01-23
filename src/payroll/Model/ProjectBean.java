package payroll.Model;

public class ProjectBean 
{
private int Site_ID;
String Project_Code,Site_City,Site_Name;

public String getProject_Code() {
	return Project_Code;
}

public void setProject_Code(String project_Code) {
	Project_Code = project_Code;
}

public String getSite_City() {
	return Site_City;
}

public void setSite_City(String site_City) {
	Site_City = site_City;
}

public String getSite_Name() {
	return Site_Name;
}

public void setSite_Name(String site_Name) {
	Site_Name = site_Name;
}

public int getSite_ID() {
	return Site_ID;
}

public void setSite_ID(int site_ID) {
	Site_ID = site_ID;
}
}
