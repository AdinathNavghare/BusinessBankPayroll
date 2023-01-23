package payroll.Model;

public class ExtraFieldBean 
{
int FieldNo;
String FieldName;
String FieldDesc;
int type;
String ColumnName;
	public int getFieldNo() {
		return FieldNo;
	}
	public void setFieldNo(int fieldNo) {
		FieldNo = fieldNo;
	}
	public String getFieldName() {
		return FieldName;
	}
	public void setFieldName(String fieldName) {
		FieldName = fieldName;
	}
	public String getFieldDesc() {
		return FieldDesc;
	}
	public void setFieldDesc(String fieldDesc) {
		FieldDesc = fieldDesc;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getColumnName() {
		return ColumnName;
	}
	public void setColumnName(String columnName) {
		ColumnName = columnName;
	}

}
