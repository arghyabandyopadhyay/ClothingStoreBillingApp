package sample;

//import com.sun.deploy.uitoolkit.SynthesizedEventListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Scanner;

import static java.lang.Math.ceil;
import static java.lang.Math.round;

public class Controller implements Initializable {
    @FXML
    public TextField setOff;
    @FXML
    public TextField tf14;
    @FXML
    public TextField dtt;
    private Controller Controller2;
    @FXML
    public Label  lbl1;
    @FXML
    TextField tf1;
    @FXML
    TextField tf11;
    @FXML
    TextField tf111;
    @FXML
    TextField tf2;
    @FXML
    TextField tf3;
    @FXML
    TextField tf4;
    @FXML
    TextField tf12;
    @FXML
    TextField descTf;
    @FXML
    TextField descTf1;
    @FXML
    TextField descTf11;
    @FXML
    TextField descTf111;
    @FXML
    TextField qtyTf;
    @FXML
    TextField pTf;
    @FXML
    TextField dt;
    @FXML
    TextField tb4;
    @FXML
    TextField tf31;
    @FXML
    TextField pTf1;
    @FXML
    TextField tf13;
    @FXML
    TableColumn tbc1;
    @FXML
    TableColumn tbc2;
    @FXML
    TableColumn tbc3;
    @FXML
    TableColumn tbc4;
    @FXML
    TableColumn tbc5;
    @FXML
    Button clrAll;
    @FXML
    TextArea tA1;
    @FXML
    private TableView <Item> tbv = new TableView<Item>();
    @FXML
    Button add;
    @FXML
    Button inc;
    @FXML
    Button btn1;
    @FXML
    DatePicker dt1;
    @FXML
    Button deleteButton;
    @FXML
    Button showPrev;
    @FXML
    Pane pane1;
    final KeyCombination keyComb1 = new KeyCodeCombination(KeyCode.P,
            KeyCombination.CONTROL_ANY);
    final KeyCombination keyComb2 = new KeyCodeCombination(KeyCode.A,
            KeyCombination.CONTROL_ANY);
    static int ivnNum=0,pr=0;
    int srNo=0;
    double t=0;
    double st=0;
    protected final static ObservableList<Item> data =
            FXCollections.observableArrayList();
    private double qty;
    private double tPrice;
    private double adv=0.0;
    private String invoiceNumber;
    private String array[]=getArray();
    private double sOff=0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dt1.setOnAction(event -> {
            LocalDate i = dt1.getValue();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String date ="";
            try
            {
                date= i.format(dtf).toString();
                dtt.setText(date);
            }
            catch(Exception e)
            {
            }
        });
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
                    tf14.setText(String.valueOf(ceil(t - sOff)));
                    tf13.setText(String.valueOf(ceil(t-adv-sOff)));
                    pTf1.requestFocus();
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Wrong data in set Off" , "Error", JOptionPane.ERROR_MESSAGE);
                    setOff.setText("");
                    setOff.requestFocus();
                }
            }
        });
        setOff.setOnMouseClicked(event -> {
            setOff.setText("");
        });
        generateInvoiceNumber(getDate());
        dt.setText(getDate());
        clrAll.setOnMouseClicked(mouseEvent -> {
            tf31.setText("");
            descTf.setText("");
            descTf1.setText("");
            descTf11.setText("");
            pTf.setText("");
            descTf111.setText("");
            tf12.setText("0.0");
        });
        showPrev.setOnMouseClicked(mouseEvent -> {
            try {
                nextPage();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Input Output Exception" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        pTf1.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode.equals(KeyCode.ENTER) || keyCode.equals(KeyCode.TAB)) {
                try {
                    String str1=pTf1.getText();
                    if(str1.isEmpty()||str1.equals(" "))adv=0;
                    else {
                        adv = Double.parseDouble(str1);
                        adv = ceil(adv);
                    }
                    if(adv>(t-sOff)) throw new Exception("");
                    tf13.setText(String.valueOf(ceil(t-adv-sOff)));
                    tA1.requestFocus();
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Wrong data in set Off" , "Error", JOptionPane.ERROR_MESSAGE);
                    pTf1.setText("");
                    pTf1.requestFocus();
                }
            }
        });
        TableViewSelectionModel selectionModel = tbv.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
        tbv.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode.equals(KeyCode.DELETE)) {
                deleteFromTable();
            }
        });
        tbv.setPlaceholder(new Label("No entries yet."));
        deleteButton.setOnAction(e -> deleteFromTable());
        tf3.setOnMouseClicked(event -> clear());
        tf3.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode.equals(KeyCode.ENTER) || keyCode.equals(KeyCode.TAB)) {
                tf31.requestFocus();
            }
        });
        tf31.setOnKeyPressed(keyEvent ->
        {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode.equals(KeyCode.ENTER) || keyCode.equals(KeyCode.TAB)) {
                    descTf.requestFocus();

            }
        });
        add.setOnAction(event -> addToTable());
        inc.setOnAction(mouseEvent -> increase());
        btn1.setOnAction(mouseEvent -> {
            printInvoice();
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
        descTf.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode.equals(KeyCode.ENTER) || keyCode.equals(KeyCode.TAB)) {
                descTf.setText(checkValue());
                descTf1.requestFocus();
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
        pane1.setOnKeyPressed(keyEvent ->
        {
            if (keyComb1.match(keyEvent)) {
                printInvoice();
            }
        });
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

    private String getDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dtf.format(now);
    }

    private void nextPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("interface2.fxml"));
        Controller2 =loader.getController();
        Pane rootNode = loader.load();
        Scene scene = new Scene(rootNode);
        Stage primaryStage=new Stage();
        primaryStage.setTitle("Invoice Details");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void deleteFromTable() {
        ObservableList<Item> selectedItem = tbv.getSelectionModel().getSelectedItems();
        for(int i1=0;i1<selectedItem.size();i1++)
        {
            srNo--;
            t-=selectedItem.get(i1).getTp();
            qty-=selectedItem.get(i1).getQty();
        }
        tbv.getItems().removeAll(selectedItem);

        int i=1;
        for(Item i1:data)
        {
            i1.setSrNo(i++);
        }
        tf1.setText(String.valueOf(t));
        tf14.setText(String.valueOf(ceil(t-sOff)));
        tf111.setText(String.valueOf(srNo));
        tf11.setText(String.valueOf(Math.round(qty*100.0)/100.0));
    }
    private void generateInvoiceNumber(String now) {
        File myReader = new File("BillingApplication\\lastInvoiceNumber.txt");

        Scanner sc = null;
        try {
            sc = new Scanner(myReader);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,"invoice_amount.txt file is missing","Error", JOptionPane.ERROR_MESSAGE);
        }
        String lastInvoice="";
        if(sc.hasNextLine()) lastInvoice=sc.nextLine();
        else
        {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter("BillingApplication\\lastInvoiceNumber.txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            now=now.replace('/','_');
            writer.print(now+"_0");
            lastInvoice=sc.nextLine();
            writer.close();
        }
        int index=lastInvoice.lastIndexOf("_");
        String lastInvoiceDate=lastInvoice.substring(0,index);

        lastInvoiceDate=lastInvoiceDate.replace('_','/');
        int lastNo=Integer.parseInt(lastInvoice.substring(index+1));
        if(lastInvoiceDate.equals(getDate())) ivnNum=lastNo+1;
        else ivnNum++;
        now=now.replace('/','_');
        invoiceNumber=now+"_"+ivnNum;
        tb4.setText(invoiceNumber);
    }
    private void clear() {
        tf3.setText("");
    }
    void increase()
    {
        String s=qtyTf.getText();
        if(s.equals(""))qtyTf.setText("1");
        else qtyTf.setText(String.valueOf(Integer.parseInt(s)+1));
    }
    void printInvoice(){
        try {
            String name=tf3.getText();
            String mno=tf31.getText();
            LocalDate i = dt1.getValue();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String date ="";
            try
            {
                date= i.format(dtf).toString();
            }
            catch(Exception e)
            {
                if(pr==0) {
                    JOptionPane.showMessageDialog(null, "Delivery Date can't be Null", "Error", JOptionPane.ERROR_MESSAGE);
                    dt1.setValue(null);
                    dt1.requestFocus();
                    pr++;
                    return;
                }
                else
                {
                    pr--;
                    date=" ";
                }
            }

            if (!name.isEmpty());
            else throw new Exception("No Name entered");
            t=ceil(t);
            if(t>0)
            {
                FileWriter myWriter = new FileWriter("BillingApplication\\invoice_detail\\" + invoiceNumber + ".txt");
                for (Item i1 : data) {
                    myWriter.write(invoiceNumber + ":" + i1.getSrNo() + ":" + i1.getDesc() + ":" + i1.getQty() + ":" + i1.getPo1() + ":" + i1.getTp() +":" + i1.getPrice()+":" + i1.getStitch() +"\n");
                }
                myWriter.close();
                File file = new File("BillingApplication\\invoice_amount.txt");
                FileWriter fr = new FileWriter(file, true);
                BufferedWriter br = new BufferedWriter(fr);
                br.write(invoiceNumber + ":" + name + ":"+ t +":"+mno+":"+date+":"+adv+":"+sOff+ "\n");
                myWriter = new FileWriter("BillingApplication\\invoice_comment\\" + invoiceNumber + ".txt");
                myWriter.write(tA1.getText());
                Client.main(new String[]{invoiceNumber,name,getDate(),mno,date,String.valueOf(t),String.valueOf(adv),String.valueOf(sOff)});
                br.close();
                fr.close();
                myWriter.close();
                data.clear();
                srNo=0;
                t=0;
                adv=0;
                qty=0;
                st=0;
                tf3.setText("Walk In");
                PrintWriter writer = new PrintWriter("BillingApplication\\lastInvoiceNumber.txt");
                writer.print(invoiceNumber);
                writer.close();
                tf31.setText("");
                tA1.clear();
                dt1.setValue(null);
                tf3.requestFocus();
                pTf1.setText("");
                tf13.setText("");
                setOff.setText("0");
                tf1.setText("");
                tf14.setText("");
                setOff.setText("");
                tf11.setText("");
                tf111.setText("");
                P.main(new String[]{"BillingApplication/printout/"+invoiceNumber+".pdf"});
                P.main(new String[]{"BillingApplication/printout/"+invoiceNumber+".pdf"});
                generateInvoiceNumber(getDate());
            }
            else
                JOptionPane.showMessageDialog(null,"Empty Invoice Not Allowed","Error",JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,"An error occurred. "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }

    }
    void addToTable() {
        String nm=tf3.getText();
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
        mno = tf31.getText();
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
        tPrice=sub+stitchPrice;
        srNo++;
        qty+=a;
        t+=tPrice;
        data.add(new Item(srNo,s+" - "+itemNo,a,type,tPrice,price,stitchPrice));
        tbc1.setCellValueFactory(
                new PropertyValueFactory<Item, String>("srNo"));
        tbc2.setCellValueFactory(
                new PropertyValueFactory<Item, String>("desc"));
        tbc3.setCellValueFactory(
                new PropertyValueFactory<Item, Double>("qty"));
        tbc4.setCellValueFactory(
                new PropertyValueFactory<Item, String>("po1"));
        tbc5.setCellValueFactory(
                new PropertyValueFactory<Item, Double>("tp"));
        tbv.setItems(data);
        tf1.setText(String.valueOf(ceil(t)));
        tf14.setText(String.valueOf(ceil(t-sOff)));
        tf13.setText(String.valueOf(ceil(t-adv-sOff)));
        tf111.setText(String.valueOf(srNo));
        tf11.setText(String.valueOf(Math.round(qty*100.0)/100.0));
        tf12.setText("");
        descTf.setText("");
        descTf1.setText("");
        descTf11.setText("");
        descTf111.setText("");
        qtyTf.setText("");
        pTf.setText("");
        descTf.requestFocus();
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
        while (sc.hasNextLine()){
            sc.nextLine();
            ++l;
        }
        String ar[]=new String[l];
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
}
