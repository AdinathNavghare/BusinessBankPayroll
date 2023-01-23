package payroll.Model;

public class OtherDetailBean 
{
	public String[] Field;
	
	public OtherDetailBean(int num)
	{
		Field= new String[num];
	}

	public String getField(int index) {
		return Field[index];
	}

	public void setField(String f,int index) {
		Field[index] = f;
	}
	
	
}
