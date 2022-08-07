package sample;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import static java.lang.Math.ceil;
import static sample.PDFCreator.*;

class HeaderFooter extends PdfPageEventHelper {

    /** The header/footer text. */
    String header;
    /** The template with the total number of pages. */
    PdfTemplate total;
    /**
     * Allows us to change the content of the header.
     * @param header The new header String
     */
    private double t;
    private double sOff;
    HeaderFooter(double t,double sOff)
    {
        this.t=t;
        this.sOff=sOff;
    }
    public void setHeader(String header) {
        this.header = header;
    }
    /**
     * Creates the PdfTemplate that will hold the total number of pages
     */
    public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(25, 16);
    }
    /**
     * Adds a header to every page
     */
    public void onEndPage(PdfWriter writer, Document document) {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setLockedWidth(true);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        PdfPCell cell1 = new PdfPCell(new Phrase("GSTIN:"+Main.gstin,PDFCreator.NORMAL_FONT));
        cell1.setHorizontalAlignment(0);
        cell1.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell1);

        cell1 = new PdfPCell(new Phrase("CASH/CREDIT MEMO",PDFCreator.NORMAL_FONT1));
        cell1.setBorder(Rectangle.NO_BORDER);
        cell1.setHorizontalAlignment(1);
        table.addCell(cell1);
        cell1=new PdfPCell(new Phrase("CONTACT:"+Main.contactNo1,PDFCreator.NORMAL_FONT));
        cell1.setBorder(Rectangle.NO_BORDER);
        cell1.setHorizontalAlignment(2);
        table.addCell(cell1);
        table.addCell(new Phrase("",PDFCreator.NORMAL_FONT));
        table.addCell(new Phrase("",PDFCreator.NORMAL_FONT));
        cell1=new PdfPCell(new Phrase(Main.contactNo2,PDFCreator.NORMAL_FONT));
        cell1.setBorder(Rectangle.NO_BORDER);
        cell1.setHorizontalAlignment(2);
        table.addCell(cell1);

        Rectangle page = document.getPageSize();
        table.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
        table.writeSelectedRows(0, -1, document.leftMargin(), page.getHeight() - document.topMargin()
                + table.getTotalHeight()-5, writer.getDirectContent());

        PdfPTable table3 = new PdfPTable(1);
        table3.setWidthPercentage(100);
        table3.setLockedWidth(true);
        table3.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        cell1 = new PdfPCell(new Phrase(Main.companyName+"\n", SMALL_BOLD2));
        cell1.setBorder(Rectangle.NO_BORDER);
        cell1.setHorizontalAlignment(1);
        table3.addCell(cell1);
        cell1 = new PdfPCell(new Phrase(Main.companyTagLine+"\n", NORMAL_FONT));
        cell1.setBorder(Rectangle.NO_BORDER);
        cell1.setHorizontalAlignment(1);
        table3.addCell(cell1);
        cell1=new PdfPCell(new Phrase(Main.address+"\n", NORMAL_FONT));
        cell1.setBorder(Rectangle.NO_BORDER);
        cell1.setHorizontalAlignment(1);
        table3.addCell(cell1);
        table3.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
        table3.writeSelectedRows(0, -1, document.leftMargin(), page.getHeight() - document.topMargin()
                + table3.getTotalHeight()-50, writer.getDirectContent());

        PdfPTable table1 = new PdfPTable(3);
        table1.setWidthPercentage(100);
        try {
            table1.setWidths(new int[]{100,200,200});
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        table1.setLockedWidth(true);
        table1.getDefaultCell().setBorder(Rectangle.BOX);
        table1.addCell(new Phrase(" \n\n\n\n\nReceiver's Sign.",PDFCreator.NORMAL_FONT));
        table1.addCell(new Phrase(" \n\n\n\n",PDFCreator.NORMAL_FONT));
        cell1=new PdfPCell(new Phrase(" \n\n\n\nFor, "+Main.companyName, SMALL_BOLD1));
        cell1.setHorizontalAlignment(2);
        table1.addCell(cell1);
        //table1.addCell(String.format("Page %d ", writer.getPageNumber()));
        table1.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
        table1.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin()+60, writer.getDirectContent());

        PdfPTable table4 = new PdfPTable(1);
        table4.setWidthPercentage(100);
        table4.setLockedWidth(true);
        table4.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        cell1=new PdfPCell(Phrase.getInstance(String.format("Page %d ", writer.getPageNumber())));
        cell1.setHorizontalAlignment(2);
        table4.addCell(cell1);
        table4.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
        table4.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(), writer.getDirectContent());

        PdfPTable table2 = new PdfPTable(2);
        table2.setWidthPercentage(100);
        try {
            table2.setWidths(new int[]{90,20});
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        table2.setLockedWidth(true);
        table2.getDefaultCell().setBorder(Rectangle.BOX);
        table2.addCell(new Phrase(" Total Amount",PDFCreator.NORMAL_FONT));
        table2.addCell(new Phrase("Rs."+String.valueOf(ceil(t-sOff)), SMALL_BOLD));
        table2.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
        table2.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin()+75, writer.getDirectContent());
    }
    /**
     * Fills out the total number of pages before the document is closed
     */
    public void onCloseDocument(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                new Phrase(String.valueOf(writer.getPageNumber() - 1)), 2, 2, 0);
    }
}
