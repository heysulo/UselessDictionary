import java.awt.*;
import java.awt.event.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Dictionary;
import javax.swing.*;
import javax.swing.border.Border;
import java.io.*;
import static java.util.Collections.list;
import java.util.Scanner;

public class UselessDictionary extends JFrame {
    
    //Delcaration of BinarySearchTree which is used to store data
    private static BinarySearchTree dict = new BinarySearchTree();
    
    //Delcarations of JFrame objects.
    private DefaultListModel listModel = new DefaultListModel();
    private javax.swing.JPanel tabDictionary;
    private javax.swing.JPanel tabAbout;
    private javax.swing.JTabbedPane tabPageContainer;
    private JLabel lblGuidText_td = new JLabel( "Enter the word :" );
    private JTextField txtSearchDic = new JTextField();
    private JButton btnAdd = new JButton("Add/Edit");
    private JButton btnDelete = new JButton("Delete");
    private JButton btnSearchDic = new JButton("Search");
    private JButton btnImport = new JButton("Import");
    private JButton btnSave = new JButton("Save Changes");
    private JTextArea lblOutput_td = new JTextArea( "Search Results goes here." );
    private JList lstSynonims = new JList( listModel);
    
    
    //Initializer
    public UselessDictionary(){
        initComponents();
                
    }
    

    private void initComponents(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //default exit action
        this.setSize(800,430);  //window size
        this.setVisible(true);  //set visible at startup
        this.setTitle("Useless Dictionary");    //set name of the dictionary to the window title
        JPanel topPanel = new JPanel(); //new Jpanel for objects
        topPanel.setLayout( new BorderLayout() ); //Jpanel layout
        getContentPane().add( topPanel ); //Add the main panel to contentpane
        
        createPage1();  //Create the 1st tab page of the container
        //createPage2();  //Create the 2nd tab page of the container
        createPage3();  //Create the 3rd tab page of the container
                
        tabPageContainer = new JTabbedPane(); //init tabpage container
        tabPageContainer.addTab( "Dictionary", tabDictionary ); //add tabpage 1 to container
        tabPageContainer.addTab( "Settings/Import", tabAbout);  //add tabpage 2 to container
        topPanel.add( tabPageContainer, BorderLayout.CENTER );  //add tabpage container to toppanel

    }
    
    /*
    THE MD5 HASHING USAGE
    SYNNONIMS ARE COLLECTED BY SEARCHING THE BINARY TREE WITH DEFEINITIONS.
    IF THE DEFINITION IS VERY BIG THIS COULD TAKE SOME TIME.
    THEREFORE A THE DEFINITION IS HASED INSIDE THE NODE FOR FASTER COMPARISIONS.
    MD5 (Message-Digest algorithm 5) is a well-known cryptographic hash function with a 128-bit resulting hash value.
    */
    public String getMD5(String txt){
        String md5output;
        md5output = txt;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(md5output.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            md5output = bigInt.toString(16);
        } catch (Exception e) {
            md5output = null;
        } finally{
            return md5output;
        }
        
    }
    
    public void createPage1(){
        tabDictionary = new JPanel();
        tabDictionary.setLayout( null );

        
        lblGuidText_td.setBounds( 10, 5, 150, 20 );
        tabDictionary.add( lblGuidText_td );
                

        
        txtSearchDic.setBounds( 10, 30, 760, 30 );
        txtSearchDic.setHorizontalAlignment(JTextField.CENTER);
        tabDictionary.add( txtSearchDic );
        
        btnSave.setBounds(630, 340, 140, 25);
        tabDictionary.add(btnSave);
        
        btnAdd.setBounds(560, 65, 100, 25);
        tabDictionary.add(btnAdd);
        
        
        btnDelete.setBounds(450, 65, 100, 25);
        tabDictionary.add(btnDelete);
        
        
        btnSearchDic.setBounds(670, 65, 100, 25);
        tabDictionary.add(btnSearchDic);

        JLabel label2 = new JLabel( "Search Result:" );
        label2.setBounds( 10, 75, 150, 20 );
        tabDictionary.add( label2 );
        
        
        Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
        lblOutput_td.setBounds( 10, 100, 760, 130 );
        lblOutput_td.setLineWrap(true);
        lblOutput_td.setWrapStyleWord(true);
        lblOutput_td.setBorder(border);
        lblOutput_td.setOpaque(true);
        tabDictionary.add( lblOutput_td );
        
        JLabel labelx = new JLabel( "Synonims Result:" );
        labelx.setBounds( 10, 235, 150, 20 );
        tabDictionary.add( labelx );
        
       
        lstSynonims.setBounds( 10, 260, 760, 70 );
        lstSynonims.setBorder(border);
        lstSynonims.setOpaque(true);
        
        lstSynonims.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstSynonims.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        lstSynonims.setVisibleRowCount(-1);
        lstSynonims.setFixedCellHeight(20);
        lstSynonims.setFixedCellWidth(150);
        tabDictionary.add( lstSynonims );
        
        
        event_buttonSearch ebd = new event_buttonSearch();
        btnSearchDic.addActionListener(ebd);
        
        event_btnAdd ans = new event_btnAdd();
        btnAdd.addActionListener(ans);
        
        event_btnDelete ssd = new event_btnDelete();
        btnDelete.addActionListener(ssd);
        
        event_btnSave fds = new event_btnSave();
        btnSave.addActionListener(fds);
        
        
        
    }
    public class event_btnDelete implements ActionListener {
        public void actionPerformed(ActionEvent e){
            dict.deletenode(txtSearchDic.getText().trim().toLowerCase());
            
        }
    }
    
    
    public class event_btnSave implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String export = dict.exportTree();
            if (export==null){
                JOptionPane.showMessageDialog(null, "Nothing to save.");
                return;
            }
            FileWriter output=null;
            try {
                output = new FileWriter("content.txt");
                output.write(export);
            } catch (IOException ex) {
                System.out.println(ex);
            } finally {
                try {
                    output.close();
                } catch (Exception es) {
                    System.out.println(es.getMessage());
                }
            }
            JOptionPane.showMessageDialog(null, "Changes Saved!");
            
        }
    }
    
    
    public class event_btnAdd implements ActionListener {
        public void actionPerformed(ActionEvent e){
            addToDict(txtSearchDic.getText().trim(), lblOutput_td.getText().trim());
            JOptionPane.showMessageDialog(null, "The word " + txtSearchDic.getText().trim() + " added to the dictionary.");
        }
    }
    
    public class event_buttonSearch implements ActionListener {
        public void actionPerformed(ActionEvent e){
            listModel.removeAllElements();
            Node xs = dict.find(txtSearchDic.getText().trim().toLowerCase());
            if (xs==null){
                lblOutput_td.setText("The searched word '"+ txtSearchDic.getText() + "' was not found inside the dictionary");
            }else{
                lblOutput_td.setText(xs.definition);
                
                listModel = dict.findSyn(xs.definition);
                listModel.removeElement(xs.word);
                lstSynonims.setModel(listModel);
                
            }
        }
    }
        
    public void createPage3(){
        tabAbout = new JPanel();
        tabAbout.setLayout( null );
        

        JLabel lblGuidText_syn = new JLabel( "<html><h1>Useless Dictionary</h1><p>Sulochana Kodituwakku<br>2014/CS/066<br>14000662</p></html>" );
        lblGuidText_syn.setHorizontalAlignment(JLabel.LEFT);
        lblGuidText_syn.setVerticalAlignment(JLabel.TOP);
        lblGuidText_syn.setBounds( 10, 200, 750, 380 );
        tabAbout.add( lblGuidText_syn );
        
        btnImport.setBounds(10,10,125,25);
        tabAbout.add(btnImport);
        
        event_buttonImport ebd = new event_buttonImport();
        btnImport.addActionListener(ebd);
    }
    
    public class event_buttonImport implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Boolean isfail = false;            
            JFileChooser c = new JFileChooser();
            int rVal = c.showOpenDialog(UselessDictionary.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {

                FileReader input = null;
                Scanner in = null;
                try {
                    input = new FileReader(c.getSelectedFile().getAbsolutePath());
                    in = new Scanner(input);
                    int sum = 0;
                    while (in.hasNext() ){
                        String dd[];
                        dd = in.nextLine().split(";");
                        System.out.println("Adding the word " + dd[0]);
                        addToDict(dd[0],dd[1]);
                        sum++;
                    }

                } catch (IOException xe) {
                    System.out.println(xe);
                }
                
                try {
                    input.close();
                } catch (Exception de) {
                    isfail = true;
                    System.out.println("File close failed.");
                }
                if (isfail == true) {
                    JOptionPane.showMessageDialog(null, "Error occured while impoting words from the external source.");
                    System.out.println("Error occured while impoting words from the external source.");
                } else {
                    JOptionPane.showMessageDialog(null, "Words successfully imported from the external source.");
                    System.out.println("Words successfully imported from the external source.");
                }

            }

        }
    }
    
    public void addToDict(String word,String def){
        dict.insert(word.trim().toLowerCase(), def.trim().toLowerCase());
        
    }


    public static void main(String[] args) {
        UselessDictionary frmMain = new UselessDictionary();
        frmMain.setVisible(true);
        FileReader input = null;
        Scanner in = null;
        try {
            input = new FileReader("content.txt");
            in = new Scanner(input);
            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(line);
                if (line.contains(";")){
                    String dd[];
                    dd = line.split(";");
                    System.out.println("Adding the word " + dd[0] + " to the tree.");
                    dict.insert(dd[0].trim().toLowerCase(), dd[1].trim().toLowerCase());
                }
                
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                input.close();
                System.out.println("Startup import finished.");
      
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}
