package payroll.Core;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class HeaderAndFooter_AccountWise extends PdfPageEventHelper {
	
	protected Phrase footer;
    protected Phrase header;
    PdfTemplate total;
	private String lable1;
	private String label2;
	
    
    private static Font headerFont = new Font(Font.COURIER, 9,
            Font.NORMAL);

    private static Font footerFont = new Font(Font.TIMES_ROMAN, 26,
            Font.BOLD);

    public void onOpenDocument(PdfWriter writer, Document document) {
    	total = writer.getDirectContent().createTemplate(50, 50);
    }
    
    HeaderAndFooter_AccountWise (String lbl,String lbl2)
    {
    	System.out.println("inside ctor"+lbl2);
    	lable1=lbl;
    	label2=lbl2;
    	
    }
    
    
    @SuppressWarnings("static-access")
	@Override
    public void onStartPage(PdfWriter writer, Document document) {
    
    	 /*if(PdfNumber.NUMBER>1){
			 PdfPTable datatab1;
	    	 PdfPTable datatot1;*/
    System.out.println("inside startpage");

		Rectangle rec = new  Rectangle(100,100);
    	Font f = new Font(Font.HELVETICA,26);
		Font f1 = new Font(Font.HELVETICA,23);
		Font f2 = new Font(Font.HELVETICA,32);
		Font fbold = new Font(Font.HELVETICA, 22, Font.BOLD);
		PdfPTable main1 = new PdfPTable(6);
				main1.setSpacingBefore(20);
				
				
					try {
						Paragraph para = new Paragraph();
						para = new Paragraph(new Phrase(label2,new Font(Font.TIMES_ROMAN,26)));
						para.setAlignment(Element.ALIGN_CENTER);
						para.setSpacingAfter(5);
						document.add(para);
					
						main1.setWidthPercentage(new float[]{ 10f, 30.0f, 15.0f, 15.0f, 15.0f, 15.0f }, rec);

					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				main1.setHorizontalAlignment(Element.ALIGN_LEFT);
				PdfPCell maincell1 ;
				
				
				maincell1 = new PdfPCell(new Phrase("EMP CODE",fbold));
				maincell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				maincell1.setFixedHeight(70);
				maincell1.disableBorderSide(rec.BOTTOM);
				main1.addCell(maincell1);
				
				maincell1 = new PdfPCell(new Phrase("EMP NAME",fbold));
				maincell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				maincell1.disableBorderSide(rec.BOTTOM);
				main1.addCell(maincell1);
				
				maincell1 = new PdfPCell(new Phrase("Account Number",fbold));
				maincell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				main1.addCell(maincell1);
				
				maincell1 = new PdfPCell(new Phrase("Total Income",fbold));
				maincell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				main1.addCell(maincell1);
				
				
				PdfPCell maincell24 = new PdfPCell(new Phrase("Total Deduction",fbold));
				maincell24.setHorizontalAlignment(Element.ALIGN_CENTER);
				main1.addCell(maincell24);
				
				PdfPCell maincell25 = new PdfPCell(new Phrase("Net Pay",fbold));
				maincell25.setHorizontalAlignment(Element.ALIGN_CENTER);
				main1.addCell(maincell25);
				
				
				try {
					  if(writer.getPageNumber()!=1 )
					document.add(main1);
					//  document.add(main2);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
		 }
    	
    
    public void onEndPage(PdfWriter writer, Document document) {
    	
    	PdfPTable table = new PdfPTable(3);
    	try{
    		
    		table.setWidths(new int[]{24,24,2});
    		table.setTotalWidth(2770);
    		table.setLockedWidth(true);
    		table.getDefaultCell().setFixedHeight(30);
    		table.getDefaultCell().setBorder(Rectangle.BOTTOM);
    		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
    		table.addCell(new Phrase(lable1+"( "+ReportDAO.getSysDate()+" )",footerFont));
    		table.addCell(new Phrase(String.format("Page %d of  ", writer.getPageNumber()),footerFont));
    		PdfPCell cell = new PdfPCell(Image.getInstance(total));
    		cell.setBorder(0);
    		//cell.setFixedHeight(72f);
    		table.addCell(cell);
    		table.writeSelectedRows(0, -1, 10, 60, writer.getDirectContent());
    		
    		
    	} 
    	catch(DocumentException de){
    		throw new ExceptionConverter(de);
    	}

    }
    
    public void onCloseDocument(PdfWriter writer, Document document) {
    	ColumnText.showTextAligned(total, Element.ALIGN_BOTTOM, new Phrase(String.valueOf(writer.getPageNumber()-1),footerFont),15,20,0);
    }
    
}