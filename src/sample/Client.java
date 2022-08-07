package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;

public class Client {
    private static String path;
    public static final String PDF_EXTENSION = ".pdf";
    public static void main(String[] args) {
        String pdfFilename = "one";
        pdfFilename = args[0].trim();
        String name=args[1].trim();
        String now=args[2].trim();
        String mno=args[3].trim();
        String ddate=args[4].trim();
        Double t=Double.parseDouble(args[5].trim());
        Double adv=Double.parseDouble(args[6].trim());
        Double sOff=Double.parseDouble(args[7].trim());
        List<Item> dataObjList = getDataObjectList(pdfFilename);

        path = "BillingApplication/printout/"+pdfFilename;
        Document document = null;
        try {
            //Document is not auto-closable hence need to close it separately

            document = new Document(PageSize.A5);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(
                    new File(path + PDF_EXTENSION)));
            HeaderFooter event = new HeaderFooter(t,sOff);
            event.setHeader("");
            writer.setPageEvent(event);
            document.open();
            PDFCreator.addMetaData(document, pdfFilename);
            PDFCreator.addContent(document, dataObjList,pdfFilename,name,now,mno,ddate,t,adv,sOff);
        }catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }finally{
            if(null != document){
                document.close();
            }
        }
    }
    public static List<Item> getDataObjectList(String pdfFilename){
        List<Item> dataObjList = new ArrayList<Item>();
        File myReader=new File("BillingApplication\\invoice_detail\\"+pdfFilename+".txt");
        Scanner sc = null;
        try {
            sc = new Scanner(myReader);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,pdfFilename+".txt file is missing","Error", JOptionPane.ERROR_MESSAGE);
        }
        while (sc.hasNextLine()){
            String s=sc.nextLine();
            int firstIndex=s.indexOf(':');
            int secondIndex=s.indexOf(':',firstIndex+1);
            int thirdIndex=s.indexOf(':',secondIndex+1);
            int fourthIndex=s.indexOf(':',thirdIndex+1);
            int fifthIndex=s.indexOf(':',fourthIndex+1);
            int sixthIndex=s.indexOf(':',fifthIndex+1);
            int seventhIndex=s.indexOf(':',sixthIndex+1);
            String invoiceNumber=s.substring(0,firstIndex);
            int srNo=Integer.parseInt(s.substring(firstIndex+1,secondIndex));
            String s1=s.substring(secondIndex+1,thirdIndex);
            double a=Double.parseDouble(s.substring(thirdIndex+1,fourthIndex));
            String price=s.substring(fourthIndex+1,fifthIndex);
            double tPrice=Double.parseDouble(s.substring(fifthIndex+1,sixthIndex));
            double price1=Double.parseDouble(s.substring(sixthIndex+1,seventhIndex));
            double stitch=Double.parseDouble(s.substring(seventhIndex+1));
            Item d1=new Item(srNo,s1,a,price,tPrice,price1,stitch);
            dataObjList.add(d1);
        }
        return dataObjList;
    }
}
