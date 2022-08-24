package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Scanner;

import static java.lang.Math.ceil;

public class Controller2 implements Initializable {
    @FXML
    public TextField descTf;
    @FXML
    public TextField descTf1;
    @FXML
    public TextField qtyTf;
    @FXML
    public TextField descTf11;
    @FXML
    public TextField descTf111;
    @FXML
    public Button add;
    @FXML
    public Button deleteButton;
    @FXML
    public TextField pTf;
    @FXML
    public TextField tf12;
    @FXML
    public Button strtEdit;
    @FXML
    public TextField tfAmount1;
    @FXML
    public TextField setOff;
    @FXML
    public Label dscLbl;
    @FXML
    public Label itemLbl;
    @FXML
    public Label qtyLbl;
    @FXML
    public Label typLbl;
    @FXML
    public Label prLbl;
    @FXML
    public Label stLbl;
    @FXML
    public DatePicker dp1;
    @FXML
    private TableView <Invoice_amount> tbv = new TableView<Invoice_amount>();
    @FXML
    TableView<Item> tbv1;
    @FXML
    TableColumn<Invoice_amount, String> tbc6;
    @FXML
    TableColumn<Invoice_amount, String> tbc7;
    @FXML
    TableColumn<Invoice_amount, Double> tbc8;
    @FXML
    TableColumn<Item, Integer> tbc9;
    @FXML
    TableColumn<Item, String> tbc10;
    @FXML
    TableColumn<Item, Double> tbc11;
    @FXML
    TableColumn<Item, String> tbc12;
    @FXML
    TableColumn<Item, Double> tbc13;
    @FXML
    TableColumn<Item, Double> tbc14;
    @FXML
    TableColumn<Item, Double> tbc15;
    @FXML
    TextField tfTsrNo;
    @FXML
    TextField date;
    @FXML
    TextField date1;
    @FXML
    TextField tfQty;
    @FXML
    TextField balTf;
    @FXML
    TextField advTf;
    @FXML
    TextField tfAmount;
    @FXML
    TextField nFilter;
    @FXML
    TextArea tA2;
    @FXML
    Button prntInvoice;
    @FXML
    Button cnfEdit;
    @FXML
    Button fetch;
    @FXML
    Button deleteAll;
    @FXML
    Button all;
    @FXML
    Label lb2;
    @FXML
    DatePicker dp;
    private double adv=0.0;
    double st=0;
    final KeyCombination keyComb1 = new KeyCodeCombination(KeyCode.P,
            KeyCombination.CONTROL_ANY);
    final KeyCombination keyComb2 = new KeyCodeCombination(KeyCode.A,
            KeyCombination.CONTROL_ANY);
    DateTimeFormatter dtf;
    private final static ObservableList<Invoice_amount> data1 =
            FXCollections.observableArrayList();
    private final static ObservableList<Invoice_amount> data2 =
            FXCollections.observableArrayList();
    private final static ObservableList<Item> data =
            FXCollections.observableArrayList();
    private final String[] array =getArray();
    int tSrNo=0,Qty=0;
    double t=0.0;
    double sOff=0;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchDetails();
        tbv1.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode.equals(KeyCode.DELETE)) {
                deleteFromTable1();
            }
        });
        dp1.setOnAction(event -> {
            LocalDate i = dp1.getValue();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String date_ ="";
            try
            {
                date_= i.format(dtf).toString();
                date.setText(date_);
            }
            catch(Exception ignored)
            {
            }
        });
        strtEdit.setOnMouseClicked(event -> {
            descTf.setVisible(true);
            descTf1.setVisible(true);
            descTf11.setVisible(true);
            descTf111.setVisible(true);
            pTf.setVisible(true);
            qtyTf.setVisible(true);
            tf12.setVisible(true);
            add.setVisible(true);
            deleteButton.setVisible(true);
            cnfEdit.setVisible(true);
            advTf.setEditable(true);
            setOff.setEditable(true);
            tA2.setEditable(true);
            tbv1.setEditable(true);
            date.setEditable(true);
            date1.setEditable(true);
            dscLbl.setVisible(true);
            itemLbl.setVisible(true);
            qtyLbl.setVisible(true);
            prLbl.setVisible(true);
            typLbl.setVisible(true);
            stLbl.setVisible(true);
            dp1.setDisable(false);
            date1.setEditable(true);
        });
        cnfEdit.setOnMouseClicked(event -> confirmEdit());
        setOff.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode.equals(KeyCode.ENTER) || keyCode.equals(KeyCode.TAB)) {
                try {
                    String str1=setOff.getText();
                    if(str1.isEmpty()||str1.equals(" "))sOff=0;
                    else {
                        sOff = Double.parseDouble(str1);
                        sOff = ceil(sOff);
                    }
                    if(sOff>t) throw new Exception("");
                    tfAmount1.setText(String.valueOf(ceil(t - sOff)));
                    balTf.setText(String.valueOf(ceil(t - sOff-adv)));
                    advTf.requestFocus();
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Wrong data in set Off" , "Error", JOptionPane.ERROR_MESSAGE);
                    setOff.setText("");
                    setOff.requestFocus();
                }
            }
        });
        advTf.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode.equals(KeyCode.ENTER) || keyCode.equals(KeyCode.TAB)) {
                try {
                    String str1=advTf.getText();
                    if(str1.isEmpty()||str1.equals(" "))adv=0;
                    else {
                        adv = Double.parseDouble(str1);
                        adv = ceil(adv);
                    }
                    if(adv>(t-sOff)) throw new Exception("");
                    balTf.setText(String.valueOf(ceil(t-adv-sOff)));
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Wrong data in set Off" , "Error", JOptionPane.ERROR_MESSAGE);
                    advTf.setText("");
                    advTf.requestFocus();
                }
            }
        });
        descTf1.setOnKeyPressed(keyEvent ->
        {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode.equals(KeyCode.ENTER) || keyCode.equals(KeyCode.TAB)) {
                try {
                    int i = Integer.parseInt(descTf1.getText());
                    if (i > 100) {
                        pTf.setText(String.valueOf(i - 100));
                    } else throw new Exception("");
                    qtyTf.requestFocus();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Wrong Input For Item No.");
                    descTf1.setText("");
                }
            }
        });
        pTf.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode.equals(KeyCode.ENTER) || keyCode.equals(KeyCode.TAB)) {
                try {
                    double d = Double.parseDouble(qtyTf.getText());
                    double price = Double.parseDouble(pTf.getText());
                    st = price * d;
                    tf12.setText(String.valueOf(st));
                    descTf11.requestFocus();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Alphabetic Inputs not allowed");
                    pTf.setText("");
                }
            }
        });
        qtyTf.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode.equals(KeyCode.ENTER) || keyCode.equals(KeyCode.TAB)) {
                try {
                    double d = Double.parseDouble(qtyTf.getText());
                    double price = Double.parseDouble(pTf.getText());
                    st = price * d;
                    tf12.setText(String.valueOf(st));
                    descTf11.requestFocus();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Alphabetic Inputs not allowed");
                    qtyTf.setText("");
                }
            }
        });
        descTf11.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode.equals(KeyCode.ENTER) || keyCode.equals(KeyCode.TAB)) {
                descTf111.requestFocus();
            }
        });
        descTf111.setOnKeyPressed(keyEvent ->
        {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode.equals(KeyCode.ENTER) || keyCode.equals(KeyCode.TAB)) {
                try {
                    Integer.parseInt(descTf111.getText());
                    addToTable();
                    descTf.requestFocus();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Wrong Input For Stitching Price.");
                    descTf111.setText("");
                }
            }
        });
        qtyTf.setOnMouseClicked(e -> {
            try {
                int i = Integer.parseInt(descTf1.getText());
                if (i > 100) {
                    pTf.setText(String.valueOf(i - 100));
                } else throw new Exception("");
                qtyTf.requestFocus();
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Wrong Input For Item No.");
                descTf1.setText("");
                descTf1.requestFocus();
            }
        });
        deleteButton.setOnAction(e -> deleteFromTable1());
        add.setOnAction(event -> addToTable());
        descTf.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode.equals(KeyCode.ENTER) || keyCode.equals(KeyCode.TAB)) {
                descTf.setText(checkValue());
                descTf1.requestFocus();
            }
        });
        tbv.setPlaceholder(new Label("No entries yet."));
        nFilter.setOnKeyReleased(e->
        {
            fetch();
        });
        dp.setOnKeyPressed(keyEvent ->
        {
            KeyCode keyCode=keyEvent.getCode();
            if(keyCode.equals(KeyCode.ENTER))
            {
                fetch();
            }
        });
        deleteAll.setOnMouseClicked(event -> {
            try {
                deleteFromTable();

                tbv.setItems(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fetch.setOnMouseClicked(e->fetch());
        prntInvoice.setOnMouseClicked(mouseEvent -> askPrint());
        TableView.TableViewSelectionModel<Invoice_amount> selectionModel = tbv.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
        all.setOnAction(e->fetchDetails());
        tbv.setOnKeyReleased(keyEvent ->{
            KeyCode keyCode=keyEvent.getCode();
            if (keyCode.equals(KeyCode.UP)||keyCode.equals(KeyCode.DOWN)) {
                if(checkNoOfSelection())fetchDetail();
                else tbv1.setItems(null);
            }
            else if (keyComb2.match(keyEvent)) {
                selectionModel.selectAll();
                tbv1.setItems(null);
            }
        });
        tbv.setOnMouseClicked(mouseEvent -> {
            if(checkNoOfSelection())fetchDetail();
            else tbv1.setItems(null);
        });
        tbv.setPlaceholder(new Label("No rows to display"));
        tbv.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode=keyEvent.getCode();
            if(keyCode.equals(KeyCode.ENTER))
            {
                fetchDetail();
            }
        });
    }
    private void confirmEdit() {
        try {
            Invoice_amount selectedItem = tbv.getSelectionModel().getSelectedItem();
            String invoiceNumber=selectedItem.getDesc();
            String name=selectedItem.getQty();
            String mno=date1.getText();
            String date2=date.getText();
            t=ceil(t);
            if(t>0)
            {
                FileWriter myWriter = new FileWriter("BillingApplication\\invoice_detail\\" + invoiceNumber + ".txt");
                for (Item i1 : data) {
                    myWriter.write(invoiceNumber + ":" + i1.getSrNo() + ":" + i1.getDesc() + ":" + i1.getQty() + ":" + i1.getPo1() + ":" + i1.getTp() +":" + i1.getPrice()+":" + i1.getStitch() +"\n");
                }
                myWriter.close();

                File myReader=new File("BillingApplication\\invoice_amount.txt");
                Scanner sc = null;
                try {
                    sc = new Scanner(myReader);
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(null,"invoice_amount.txt file is missing","Error", JOptionPane.ERROR_MESSAGE);
                }
                String str=null;
                while(true)
                {
                    assert sc != null;
                    if (!sc.hasNextLine()) break;
                    str=sc.nextLine();
                    if(str.substring(0,str.indexOf(':')).equals(selectedItem.getDesc()))
                    {
                        break;
                    }
                }



                PrintWriter pw = new PrintWriter("BillingApplication\\output.txt");

                // BufferedReader object for input.txt
                BufferedReader br1 = new BufferedReader(new FileReader("BillingApplication\\invoice_amount.txt"));

                String line1 = br1.readLine();

                // loop for each line of input.txt
                while(line1 != null)
                {
                    // if line is not present in delete.txt
                    // write it to output.txt
                    assert str != null;
                    if(!str.contains(line1))
                        pw.println(line1);

                    line1 = br1.readLine();
                }

                pw.flush();

                // closing resources
                br1.close();
                pw.close();


                PrintWriter pw1 = new PrintWriter("BillingApplication\\invoice_amount.txt");

                // BufferedReader object for input.txt
                BufferedReader br11 = new BufferedReader(new FileReader("BillingApplication\\output.txt"));

                String line11 = br11.readLine();
                // loop for each line of input.txt
                while(line11 != null)
                {
                    // if line is not present in delete.txt
                    // write it to output.txt
                    pw1.println(line11);

                    line11 = br11.readLine();
                }

                pw1.flush();

                // closing resources
                br11.close();
                pw1.close();


                File file = new File("BillingApplication\\invoice_amount.txt");
                FileWriter fr = new FileWriter(file, true);
                BufferedWriter br = new BufferedWriter(fr);
                br.write(invoiceNumber + ":" + name + ":"+ t +":"+mno+":"+date2+":"+adv+":"+sOff+ "\n");
                myWriter = new FileWriter("BillingApplication\\invoice_comment\\" + invoiceNumber + ".txt");
                myWriter.write(tA2.getText());
                String dateOfFile=invoiceNumber.substring(0,invoiceNumber.lastIndexOf('_'));
                dateOfFile=dateOfFile.replace('_','/');
                Client.main(new String[]{invoiceNumber,name,dateOfFile,mno,date2,String.valueOf(t),String.valueOf(adv),String.valueOf(sOff)});
                br.close();
                fr.close();
                myWriter.close();
                data.clear();
                tSrNo=0;
                t=0;
                adv=0;
                sOff=0;
                st=0;
            }
            else
                JOptionPane.showMessageDialog(null,"Empty Invoice Not Allowed","Error",JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"An error occurred. "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
        fetchDetails();
        descTf.setVisible(false);
        descTf1.setVisible(false);
        descTf11.setVisible(false);
        descTf111.setVisible(false);
        pTf.setVisible(false);
        qtyTf.setVisible(false);
        tf12.setVisible(false);
        add.setVisible(false);
        deleteButton.setVisible(false);
        cnfEdit.setVisible(false);
        advTf.setEditable(false);
        setOff.setEditable(false);
        tA2.setEditable(false);
        tbv1.setEditable(false);
        date.setEditable(false);
        date1.setEditable(false);
        dscLbl.setVisible(false);
        itemLbl.setVisible(false);
        qtyLbl.setVisible(false);
        prLbl.setVisible(false);
        typLbl.setVisible(false);
        stLbl.setVisible(false);
        dp1.setDisable(true);
        balTf.setText("0.0");
        tfQty.setText("0");
        tfAmount.setText("0");
        tfTsrNo.setText("0");
        date1.setText("");
        date.setText("");
        tA2.clear();
        tfAmount1.setText("0");
        advTf.setText("0");
        setOff.setText("0");

    }

    private String[] getArray() {
        File myReader=new File("BillingApplication\\others\\invoice_options.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(myReader);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,"invoice_options.txt file is missing","Error", JOptionPane.ERROR_MESSAGE);
        }
        int l=0,i=0;
        while (true){
            assert sc != null;
            if (!sc.hasNextLine()) break;
            sc.nextLine();
            ++l;
        }
        String[] ar =new String[l];
        try {
            sc = new Scanner(myReader);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,"invoice_options.txt file is missing","Error", JOptionPane.ERROR_MESSAGE);
        }
        while (sc.hasNextLine()) {
            String s=sc.nextLine();
            ar[i++]=s;
        }
        return ar;
    }
    private String checkValue() {
        String s=descTf.getText();
        try
        {
            int i=Integer.parseInt(s);
            return array[i-1];
        }
        catch (Exception e)
        {
            return s;
        }
    }

    void addToTable() {
        String s=descTf.getText();
        String mno;
        double a;
        String type=descTf11.getText();
        String itemNo=descTf1.getText();
        double price,stitchPrice=0.0;

        if(s.equals(""))
        {
            JOptionPane.showMessageDialog(null,"Item Description can't be Null","Error",JOptionPane.ERROR_MESSAGE);
            descTf.requestFocus();
            return;
        }
        if(itemNo.equals(""))
        {
            JOptionPane.showMessageDialog(null,"Item No can't be Null","Error",JOptionPane.ERROR_MESSAGE);
            descTf1.requestFocus();
            return;
        }
        if(type.equals(""))
        {
            JOptionPane.showMessageDialog(null,"Item Type can't be Null","Error",JOptionPane.ERROR_MESSAGE);
            descTf11.requestFocus();
            return;
        }
        try {
            a = Double.parseDouble(qtyTf.getText());
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null,"Quantity should be an Integer","Error",JOptionPane.ERROR_MESSAGE);
            qtyTf.setText("");
            qtyTf.requestFocus();
            return;
        }
        mno = date1.getText();
        try {
            price = Double.parseDouble(pTf.getText());
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null,"Price should be numeric","Error",JOptionPane.ERROR_MESSAGE);
            pTf.setText("");
            pTf.requestFocus();
            return;
        }
        String stitch=descTf111.getText();
        try
        {
            stitchPrice = Double.parseDouble(stitch);
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Stitch Price should be numeric", "Error", JOptionPane.ERROR_MESSAGE);
            descTf111.setText("0.0");
        }
        double sub=a*price;
        double tPrice = sub + stitchPrice;
        tSrNo++;
        Qty+=a;
        t+= tPrice;
        data.add(new Item(tSrNo,s+" - "+itemNo,a,type, tPrice,price,stitchPrice));
        tbc9.setCellValueFactory(
                new PropertyValueFactory<Item, Integer>("srNo"));
        tbc10.setCellValueFactory(
                new PropertyValueFactory<Item, String>("desc"));
        tbc11.setCellValueFactory(
                new PropertyValueFactory<Item, Double>("qty"));
        tbc12.setCellValueFactory(
                new PropertyValueFactory<Item, String >("po1"));
        tbc13.setCellValueFactory(
                new PropertyValueFactory<Item, Double>("tp"));
        tbc15.setCellValueFactory(
                new PropertyValueFactory<Item, Double>("price"));
        tbc14.setCellValueFactory(
                new PropertyValueFactory<Item, Double>("stitch"));
        tbv1.setItems(data);
        tfAmount.setText(String.valueOf(ceil(t)));
        tfAmount1.setText(String.valueOf(ceil(t-sOff)));
        balTf.setText((String.valueOf(ceil(t-adv-sOff))));
        tfTsrNo.setText(String.valueOf(tSrNo));
        tfQty.setText(String.valueOf(Math.round(Qty*100.0)/100.0));
        tf12.setText("");
        descTf.setText("");
        descTf1.setText("");
        descTf11.setText("");
        descTf111.setText("");
        qtyTf.setText("");
        pTf.setText("");
        descTf.requestFocus();
    }

    private void deleteFromTable1() {
        ObservableList<Item> selectedItem = tbv1.getSelectionModel().getSelectedItems();
        for (Item item : selectedItem) {
            tSrNo--;
            t -= item.getTp();
            Qty -= item.getQty();
        }
        tbv1.getItems().removeAll(selectedItem);

        int i=1;
        for(Item i1:data)
        {
            i1.setSrNo(i++);
        }
        tfAmount.setText(String.valueOf(t));
        tfAmount1.setText(String.valueOf(t-sOff));
        tfTsrNo.setText(String.valueOf(tSrNo));
        tfQty.setText(String.valueOf(Math.round(Qty*100.0)/100.0));
        balTf.setText(String.valueOf(t-adv-sOff));
    }

    private void deleteFromTable() throws IOException {
        int response=JOptionPane.showConfirmDialog(null,"Are you sure to delete all the entries permanently?");
        System.out.println(response);
        if(response==0){
            PrintWriter writer = new PrintWriter("BillingApplication\\invoice_amount.txt");
            writer.print("");
            writer.close();
            deleteDir(new File("BillingApplication\\invoice_detail"));
            File newFile=new File("BillingApplication\\invoice_detail");
            newFile.mkdir();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            assert children != null;
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));

                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
    private boolean checkNoOfSelection() {
        ObservableList<Invoice_amount> selectedItem = tbv.getSelectionModel().getSelectedItems();
        return selectedItem.size() <= 1;
    }
    private void fetchDetails() {
        data1.clear();
        File myReader=new File("BillingApplication\\invoice_amount.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(myReader);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,"invoice_amount.txt file is missing","Error", JOptionPane.ERROR_MESSAGE);
        }
        tbc6.setCellValueFactory(
                new PropertyValueFactory<Invoice_amount, String>("desc"));
        tbc7.setCellValueFactory(
                new PropertyValueFactory<Invoice_amount, String>("qty"));
        tbc8.setCellValueFactory(
                new PropertyValueFactory<Invoice_amount, Double>("po1"));
        while (true) {
            assert sc != null;
            if (!sc.hasNextLine()) break;
            String s=sc.nextLine();
            int firstIndex=s.indexOf(':');
            int secondIndex=(s.indexOf(':',firstIndex+1));
            int thirdIndex=(s.indexOf(':',secondIndex+1));
            int fourthIndex=(s.indexOf(':',thirdIndex+1));
            int fifthIndex=(s.indexOf(':',fourthIndex+1));
            int sixthIndex=(s.indexOf(':',fifthIndex+1));
            String billNo=s.substring(0,firstIndex);
            String Name=s.substring(firstIndex+1,secondIndex);
            double Amount=Double.parseDouble(s.substring(secondIndex+1,thirdIndex));
            String mno=s.substring(thirdIndex+1,fourthIndex);
            String ddate=s.substring(fourthIndex+1,fifthIndex);
            double adv=Double.parseDouble(s.substring(fifthIndex+1,sixthIndex));
            double sO=Double.parseDouble(s.substring(sixthIndex+1));
            data1.add(new Invoice_amount(billNo,Name,Amount,mno,ddate,adv,sO));
        }
        tbv.setItems(data1);
    }

    private void askPrint() {
        Invoice_amount selectedItem = tbv.getSelectionModel().getSelectedItem();
        String path = "BillingApplication/printout/" + selectedItem.getDesc()+".pdf";
        try {
            P.main(new String[]{path});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void fetch()
    {
        try {
            data2.clear();
            LocalDate i = dp.getValue();
            String name=nFilter.getText().trim();
            File myReader = new File("BillingApplication\\invoice_amount.txt");
            Scanner sc = null;
            int i1=0;
            try {
                sc = new Scanner(myReader);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "invoice_amount.txt file is missing", "Error", JOptionPane.ERROR_MESSAGE);
            }
            tbc6.setCellValueFactory(
                    new PropertyValueFactory<Invoice_amount, String>("desc"));
            tbc7.setCellValueFactory(
                    new PropertyValueFactory<Invoice_amount, String>("qty"));
            tbc8.setCellValueFactory(
                    new PropertyValueFactory<Invoice_amount, Double>("po1"));
            if(i==null&&name.isEmpty())
            {
                fetchDetails();
            }
            if((i==null)&&(!name.isEmpty()))
            {

                while (true) {
                    assert sc != null;
                    if (!sc.hasNextLine()) break;
                    String s=sc.nextLine();
                    int firstIndex=s.indexOf(':');
                    int secondIndex=(s.indexOf(':',firstIndex+1));
                    int thirdIndex=(s.indexOf(':',secondIndex+1));
                    int fourthIndex=(s.indexOf(':',thirdIndex+1));
                    int fifthIndex=(s.indexOf(':',fourthIndex+1));
                    int sixthIndex=(s.indexOf(':',fifthIndex+1));
                    String billNo=s.substring(0,firstIndex);
                    String Name=s.substring(firstIndex+1,secondIndex);
                    double Amount=Double.parseDouble(s.substring(secondIndex+1,thirdIndex));
                    String mno=s.substring(thirdIndex+1,fourthIndex);
                    String ddate=s.substring(fourthIndex+1,fifthIndex);
                    double adv=Double.parseDouble(s.substring(fifthIndex+1,sixthIndex));
                    double sO=Double.parseDouble(s.substring(sixthIndex+1));
                    Name=Name.toUpperCase();
                    name=name.toUpperCase();
                    if (Name.startsWith(name)) {
                        i1++;
                        data2.add(new Invoice_amount(billNo, Name, Amount,mno,ddate,adv,sO));
                    }
                }
                tbv.setItems(data2);
                return;
            }

            dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            assert i != null;
            String s1 = getInvoice(i.format(dtf).toString());
            if(!name.isEmpty())
            {
                while (true) {
                    assert sc != null;
                    if (!sc.hasNextLine()) break;
                    String s=sc.nextLine();
                    int firstIndex=s.indexOf(':');
                    int secondIndex=(s.indexOf(':',firstIndex+1));
                    int thirdIndex=(s.indexOf(':',secondIndex+1));
                    int fourthIndex=(s.indexOf(':',thirdIndex+1));
                    int fifthIndex=(s.indexOf(':',fourthIndex+1));
                    int sixthIndex=(s.indexOf(':',fifthIndex+1));
                    String billNo=s.substring(0,firstIndex);
                    String Name=s.substring(firstIndex+1,secondIndex);
                    double Amount=Double.parseDouble(s.substring(secondIndex+1,thirdIndex));
                    String mno=s.substring(thirdIndex+1,fourthIndex);
                    String ddate=s.substring(fourthIndex+1,fifthIndex);
                    double adv=Double.parseDouble(s.substring(fifthIndex+1,sixthIndex));
                    double sO=Double.parseDouble(s.substring(sixthIndex+1));
                    Name=Name.toUpperCase();
                    name=name.toUpperCase();
                    if (billNo.startsWith(s1)&&Name.startsWith(name)) {
                        i1++;
                        data2.add(new Invoice_amount(billNo, Name, Amount,mno,ddate,adv,sO));
                    }
                }
            }
            if(name.isEmpty())
            {
                while (true) {
                    assert sc != null;
                    if (!sc.hasNextLine()) break;
                    String s=sc.nextLine();
                    int firstIndex=s.indexOf(':');
                    int secondIndex=(s.indexOf(':',firstIndex+1));
                    int thirdIndex=(s.indexOf(':',secondIndex+1));
                    int fourthIndex=(s.indexOf(':',thirdIndex+1));
                    int fifthIndex=(s.indexOf(':',fourthIndex+1));
                    int sixthIndex=(s.indexOf(':',fifthIndex+1));
                    String billNo=s.substring(0,firstIndex);
                    String Name=s.substring(firstIndex+1,secondIndex);
                    double Amount=Double.parseDouble(s.substring(secondIndex+1,thirdIndex));
                    String mno=s.substring(thirdIndex+1,fourthIndex);
                    String ddate=s.substring(fourthIndex+1,fifthIndex);
                    double adv=Double.parseDouble(s.substring(fifthIndex+1,sixthIndex));
                    double sO=Double.parseDouble(s.substring(sixthIndex+1));
                    if (billNo.startsWith(s1)) {
                        i1++;
                        data2.add(new Invoice_amount(billNo, Name, Amount,mno,ddate,adv,sO));
                    }
                }

            }
            if(i1!=0)
            {
                tbv.setItems(data2);
                lb2.setText("");
            }
            else
            {
                lb2.setText("No Records Found");
            }
        }
        catch (Exception e)
        {
            dp.setValue(null);
            tbv.setItems(data1);
        }

    }

    private String getInvoice(String toString) {
        return toString.replace('/','_');
    }

    private void fetchDetail() {
        Invoice_amount selectedItem = tbv.getSelectionModel().getSelectedItem();
        data.clear();
        tSrNo=0;
        Qty=0;
        t=0.0;
        adv=0.0;
        sOff=selectedItem.getSO();
        date1.setText(selectedItem.getMno());
        date.setText(selectedItem.getDdate());
        tfQty.setText(String.valueOf(Qty));
        tfAmount.setText(String.valueOf(t));
        tfAmount1.setText(String.valueOf(t-sOff));
        adv=selectedItem.getAdv();
        advTf.setText(String.valueOf(adv));
        balTf.setText(String.valueOf(selectedItem.getPo1()-selectedItem.getAdv()-selectedItem.getSO()));
        tfTsrNo.setText(String.valueOf(tSrNo));
        File myReader=new File("BillingApplication\\invoice_detail\\"+selectedItem.getDesc()+".txt");
        Scanner sc = null;
        try {
            sc = new Scanner(myReader);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,selectedItem.getDesc()+".txt file is missing","Error", JOptionPane.ERROR_MESSAGE);
        }
        tbc9.setCellValueFactory(
                new PropertyValueFactory<Item, Integer>("srNo"));
        tbc10.setCellValueFactory(
                new PropertyValueFactory<Item, String>("desc"));
        tbc11.setCellValueFactory(
                new PropertyValueFactory<Item, Double>("qty"));
        tbc12.setCellValueFactory(
                new PropertyValueFactory<Item, String >("po1"));
        tbc13.setCellValueFactory(
                new PropertyValueFactory<Item, Double>("tp"));
        tbc15.setCellValueFactory(
                new PropertyValueFactory<Item, Double>("price"));
        tbc14.setCellValueFactory(
                new PropertyValueFactory<Item, Double>("stitch"));
        assert sc != null;
        sc.useDelimiter(":\n");
        while (sc.hasNext()) {
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
            double stitchPrice=Double.parseDouble(s.substring(seventhIndex+1));

            tSrNo++;
            Qty+=a;
            t+=tPrice;
            data.add(new Item(srNo,s1,a,price,tPrice,price1,stitchPrice));
        }
        myReader=new File("BillingApplication\\invoice_comment\\"+selectedItem.getDesc()+".txt");
        tA2.setText("");
        try {
            sc = new Scanner(myReader);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,selectedItem.getDesc()+".txt file is missing","Error", JOptionPane.ERROR_MESSAGE);
        }
        while (sc.hasNextLine()) {
            tA2.appendText(sc.nextLine()+"\n");
        }
        tfQty.setText(String.valueOf(Math.round(Qty*100.0)/100.0));
        tfAmount.setText(String.valueOf(ceil(t)));
        tfAmount1.setText(String.valueOf(ceil(t-sOff)));
        tfTsrNo.setText(String.valueOf(tSrNo));
        setOff.setText(String.valueOf(sOff));
        tbv1.setItems(data);
        descTf.setVisible(false);
        descTf1.setVisible(false);
        descTf11.setVisible(false);
        descTf111.setVisible(false);
        pTf.setVisible(false);
        qtyTf.setVisible(false);
        tf12.setVisible(false);
        add.setVisible(false);
        deleteButton.setVisible(false);
        cnfEdit.setVisible(false);
        advTf.setEditable(false);
        tA2.setEditable(false);
        tbv1.setEditable(false);
        date.setEditable(false);
        date1.setEditable(false);
        dscLbl.setVisible(false);
        itemLbl.setVisible(false);
        qtyLbl.setVisible(false);
        prLbl.setVisible(false);
        typLbl.setVisible(false);
        stLbl.setVisible(false);
        dp1.setDisable(true);
    }
}
