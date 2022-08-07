package sample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import static java.lang.Math.ceil;

/**
 * This is to create a PDF file.
 */
public class PDFCreator {
    private final static String[] HEADER_ARRAY = {"S.No.", "Description", "Qty","Type", "Amount"};
    public final static Font SMALL_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.BOLD);
    public final static Font SMALL_BOLD1 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.BOLD);
    public final static Font SMALL_BOLD2 = new Font(Font.FontFamily.TIMES_ROMAN, 30,
            Font.BOLD);
    public final static Font NORMAL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.NORMAL);
    public final static Font NORMAL_FONT1 = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.UNDERLINE);
    private static int tSrno=0;
    private static double tAmount=0,tQty=0;

    public static void addMetaData(Document document, String sqlXMLFileName) {
        document.addTitle("Invoice");
        document.addSubject("");
        document.addAuthor("Author Name");
    }
    public static void addContent(Document document, List<Item> dataObjList,String pdfFilename,String name,String now,String mno,String ddate,double t,double adv,double sOff) throws DocumentException {
        generateHeader(document,pdfFilename,name,now,mno,ddate);
        Paragraph paragraph = new Paragraph();
        paragraph.setFont(NORMAL_FONT);
        createReportTable(paragraph, dataObjList,document,pdfFilename,name,now,mno,ddate,t,adv,sOff);
    }
    public static void generateHeader(Document document,String pdfFilename,String name,String now,String mno,String ddate) throws DocumentException {
        Paragraph preDetails = new Paragraph();
        addEmptyLine(preDetails, 5);
        preDetails.setFont(NORMAL_FONT);
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setLockedWidth(true);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        PdfPCell cell1 = new PdfPCell(new Phrase("Invoice No.:-"+pdfFilename, SMALL_BOLD));
        cell1.setBorder(Rectangle.NO_BORDER);
        cell1.setHorizontalAlignment(0);
        table.addCell(cell1);
        cell1 = new PdfPCell(new Phrase("Date:"+now, SMALL_BOLD));
        cell1.setBorder(Rectangle.NO_BORDER);
        cell1.setHorizontalAlignment(2);
        table.addCell(cell1);
        Rectangle page = document.getPageSize();
        table.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
        preDetails.add(table);
        document.add(preDetails);

        Paragraph preDetails1 = new Paragraph();
        addEmptyLine(preDetails, 2);
        preDetails.setFont(NORMAL_FONT);
        PdfPTable table1 = new PdfPTable(2);
        table1.setWidthPercentage(100);
        table1.setLockedWidth(true);
        table1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        cell1 = new PdfPCell(new Phrase("Shri.:-"+name, SMALL_BOLD));
        cell1.setBorder(Rectangle.NO_BORDER);
        cell1.setHorizontalAlignment(0);
        table1.addCell(cell1);
        cell1 = new PdfPCell(new Phrase("D Date:-"+ddate, SMALL_BOLD));
        cell1.setBorder(Rectangle.NO_BORDER);
        cell1.setHorizontalAlignment(2);
        table1.addCell(cell1);
        page = document.getPageSize();
        table1.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
        preDetails1.add(table1);
        document.add(preDetails1);
    }
    private static void createReportTable(Paragraph paragraph, List<Item> dataObjList, Document document,String pdfFilename,String name,String now,String mno,String ddate,double t,double adv,double sOff)
            throws DocumentException {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        if(null == dataObjList){
            paragraph.add(new Chunk("No data to display."));
            return;
        }
        addHeaderInTable(HEADER_ARRAY, table);
        int y = 20;
        for(Item dataObject : dataObjList){
            addToTable(table, String.valueOf(dataObject.getSrNo()));
            addToTable(table, dataObject.getDesc());
            addToTable(table, String.valueOf(dataObject.getQty()));
            addToTable(table, dataObject.getPo1());
            addToTable(table, String.valueOf(dataObject.getTp()));
            tSrno++;
            tQty+=dataObject.getQty();
            y--;
            if(y ==0){
                paragraph.add(table);
                document.add(paragraph);
                document.newPage();
                List<Item> dataObjList1 = new ArrayList<Item>();
                int s=dataObjList.indexOf(dataObject);
                if(dataObjList.size()>0)dataObjList1.addAll(dataObjList.subList(s+1,dataObjList.size()));
                PDFCreator.addContent(document, dataObjList1,pdfFilename,name,now,mno,ddate,t,adv,sOff);
                break;
            }
        }
        if(y!=0)
        {
            paragraph.add(table);
            document.add(paragraph);

            Paragraph preDetails1 = new Paragraph();
            addEmptyLine(preDetails1, 1);
            preDetails1.setFont(NORMAL_FONT);
            PdfPTable t1 = new PdfPTable(3);
            t1.setWidthPercentage(100);
            t1.setLockedWidth(true);
            t1.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            PdfPCell cell1 = new PdfPCell(new Phrase("Total: "+tSrno, SMALL_BOLD));
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(0);
            t1.addCell(cell1);
            cell1 = new PdfPCell(new Phrase("TQty: "+Math.round(tQty*100.0)/100.0, SMALL_BOLD));
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(0);
            t1.addCell(cell1);
             cell1 = new PdfPCell(new Phrase("TAmount:Rs."+t, SMALL_BOLD));
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(2);
            t1.addCell(cell1);
            if(sOff>0) {
                cell1 = new PdfPCell(new Phrase("", SMALL_BOLD));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setHorizontalAlignment(0);
                t1.addCell(cell1);
                cell1 = new PdfPCell(new Phrase("", SMALL_BOLD));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setHorizontalAlignment(0);
                t1.addCell(cell1);
                cell1 = new PdfPCell(new Phrase("- " + sOff, SMALL_BOLD));
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setHorizontalAlignment(2);
                t1.addCell(cell1);
            }
            cell1 = new PdfPCell(new Phrase("", SMALL_BOLD));
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(0);
            t1.addCell(cell1);
            cell1 = new PdfPCell(new Phrase("", SMALL_BOLD));
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(0);
            t1.addCell(cell1);
            cell1 = new PdfPCell(new Phrase("Final Amount:Rs." + (t - sOff), SMALL_BOLD));
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(2);
            t1.addCell(cell1);


            cell1 = new PdfPCell(new Phrase("", SMALL_BOLD));
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(0);
            t1.addCell(cell1);
            cell1 = new PdfPCell(new Phrase("", SMALL_BOLD));
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(0);
            t1.addCell(cell1);
            cell1 = new PdfPCell(new Phrase("Advance:Rs."+adv, SMALL_BOLD));
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(2);
            t1.addCell(cell1);

            cell1 = new PdfPCell(new Phrase("", SMALL_BOLD));
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(0);
            t1.addCell(cell1);
            cell1 = new PdfPCell(new Phrase("", SMALL_BOLD));
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(0);
            t1.addCell(cell1);
            cell1 = new PdfPCell(new Phrase("Balance:Rs."+(t-adv-sOff), SMALL_BOLD));
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(2);
            t1.addCell(cell1);

            Rectangle page = document.getPageSize();
            t1.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
            preDetails1.add(t1);
            document.add(preDetails1);
            tSrno=0;
            tQty=0;

        }


    }
    /** Helper methods start here **/
    public static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    public static void addHeaderInTable(String[] headerArray, PdfPTable table){
        PdfPCell c1 = null;
        for(String header : headerArray) {
            c1 = new PdfPCell(new Phrase(header, PDFCreator.SMALL_BOLD));
            c1.setBackgroundColor(BaseColor.WHITE);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
        }
        table.setHeaderRows(1);
    }
    public static void addToTable(PdfPTable table, String data){
        table.addCell(new Phrase(data, PDFCreator.NORMAL_FONT));
    }
    public static Paragraph getParagraph(){
        Paragraph paragraph = new Paragraph();
        paragraph.setFont(PDFCreator.NORMAL_FONT);
        addEmptyLine(paragraph, 1);
        return paragraph;
    }
}