
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JFrame;



/**
 *
 * @author Suyash Misra
 */

public class BioInit {

    public static void main(String[] args) {
        
        try { 

            for(UIManager.LookAndFeelInfo info:UIManager.getInstalledLookAndFeels()){

                if("Nimbus".equals(info.getName())){
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

        } 
        catch (Exception e) { 
            System.out.println("Look and Feel not set"); 
        } 
        
        new Bio();
    }
}

class Bio extends JFrame implements ActionListener, KeyListener {

    //DECLARING ALL COMPONENTS
    JLabel fn, ln, dob, addressLabel, gender, phno, background, title, id, user, passwd,studentClass, studentClassSection;
    JLabel comboLabel;
    JButton save, delete, clsWindow, close, newacc, create, login, SEARCH, ADD, LOG, cancel;
    JPanel mainPanel = null, entry;
    JPanel Addop, Newacc;
    JRadioButton ml, fml;
    ButtonGroup bg;
    JTextArea address;
    JTextField un;
    JTextField f, l, ph, ID, username, stdClassField, stdSectionField;
    JPasswordField pw, password;
    JDateChooser dc;
    
    //SETTING DATABASE CONNECTION PARAMETERS
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    boolean inSearch = false;
    
    Bio() {
        //CONNECTING TO DATABASE
        conn = databaseConnection.connection();
        setTitle("Student Registration System");
        if(conn==null)
            System.exit(0); //application stops if database connection fails!
        try{
            conn.setAutoCommit(true);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        //Creating all components
        //Setting background
        ImageIcon imageIcon = null;
        imageIcon = new ImageIcon( getClass().getResource("student.jpg") ); //load the background

        comboLabel = new JLabel(imageIcon);
        comboLabel.setLayout(null);
        comboLabel.setBounds(0, 0, 1366 - 100, 768 - 100);
        add(comboLabel);

        entry = new JPanel();
        entry.setBounds(0, 0, 1366 - 100, 768 - 100);
        entry.setOpaque(false);
        entry.setLayout(null);

        user = new JLabel("Username: ");
        user.setBounds(400, 200, 200, 20);
        user.setFont(new Font("Arial", Font.PLAIN, 20));
        username = new JTextField(15);
        username.setBounds(550, 195, 210, 30);

        passwd = new JLabel("Password: ");
        passwd.setBounds(400, 270, 200, 20);
        passwd.setFont(new Font("Arial", Font.PLAIN, 20));
        password = new JPasswordField(15);
        password.setBounds(550, 265, 210, 30);

        //button for login
        login = new JButton("Login");
        login.addActionListener(this);
        login.setBounds(350, 400, 150, 40);

        //button for create new account
        newacc = new JButton("Create New Account");
        newacc.addActionListener(this);
        newacc.setBounds(550, 400, 150, 40);

        //BUTTON TO CLOSE WINDOW
        clsWindow = new JButton("Close");
        clsWindow.addActionListener(this);
        clsWindow.setActionCommand("CLOSE");
        clsWindow.setBounds(750, 400, 150, 40);

        entry.add(user);
        entry.add(username);
        entry.add(passwd);
        entry.add(password);
        entry.add(login);
        entry.add(newacc);
        entry.add(clsWindow);
        comboLabel.add(entry);

        //COMPONENTS FOR 'CREATE NEW ACCOUNT' PANEL
        Newacc = new JPanel();
        Newacc.setLayout(null);
        JLabel userName = new JLabel("Enter Username: ");
        userName.setBounds(400, 200, 200, 20);
        userName.setFont(new Font("Arial", Font.PLAIN, 18));
        
        un = new JTextField(15);
        un.setBounds(550, 195, 210, 30);
        un.addKeyListener(this);
        
        JLabel passWord = new JLabel("Set Password: ");
        passWord.setBounds(400, 270, 200, 20);
        passWord.setFont(new Font("Arial", Font.PLAIN, 18));
        
        pw = new JPasswordField(15);
        pw.setBounds(550, 265, 210, 30);
        
        create = new JButton("Create");
        create.setBounds(450, 400, 150, 40);
        create.addActionListener(this);
        
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        cancel.setBounds(650, 400, 150, 40);

        //ADD COMPONENTS TO NEW ACCOUNT PANEL
        Newacc.add(userName);
        Newacc.add(un);
        Newacc.add(passWord);
        Newacc.add(pw);
        Newacc.add(create);
        Newacc.add(cancel);
        Newacc.setBounds(0, 0, 1366 - 100, 768 - 100);
        Newacc.setOpaque(false);
        Newacc.setVisible(false);
        comboLabel.add(Newacc);

        //CREATING MAIN PANEL FOR OPTIONS
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 350, 768 - 100);
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setVisible(false);
        comboLabel.add(mainPanel);

        //ADDING OPTION PANEL
        JLabel optionsPanel = new JLabel("OPTION PANEL");
        optionsPanel.setBounds(100, 40, 200, 50);
        optionsPanel.setForeground(Color.WHITE);
        optionsPanel.setFont(new Font("Arial", Font.PLAIN, 18));
        mainPanel.add(optionsPanel);

        //CREATING BUTTONS FOR OPTIONS
        ADD = new JButton("Add");
        ADD.setFont(new Font("Serif", Font.PLAIN, 19));
        ADD.addActionListener(this);
        ADD.setBounds(50, 200, 250, 40);
        mainPanel.add(ADD);

        SEARCH = new JButton("Search");
        SEARCH.setFont(new Font("Serif", Font.PLAIN, 19));
        SEARCH.addActionListener(this);
        SEARCH.setBounds(50, 260, 250, 40);
        mainPanel.add(SEARCH);

        LOG = new JButton("Logout");
        LOG.addActionListener(this);
        LOG.setFont(new Font("Serif", Font.PLAIN, 19));
        LOG.setBounds(50, 460, 250, 40);
        mainPanel.add(LOG);

        //CREATING LABELS, TEXTFIELDS, TEXTAREA, RADIOBUTTON AND BUTTONS FOR ADD STUDENT PANEL
        fn = new JLabel("First name: ");
        fn.setFont(new Font("Arial", Font.PLAIN, 15));
        
        ln = new JLabel("Last Name: ");
        ln.setFont(new Font("Arial", Font.PLAIN, 15));
        
        dob = new JLabel("Date of birth (dd/mm/yyyy): ");
        dob.setFont(new Font("Arial", Font.PLAIN, 15));
        
        dc = new JDateChooser();
        dc.setName("date of birth");
        dc.setDateFormatString("dd/MM/yyyy");
        dc.setMaxSelectableDate(new Date());

        gender = new JLabel("Gender: ");
        gender.setFont(new Font("Arial", Font.PLAIN, 15));
        
        f = new JTextField(15);   //text field for firstname
        f.setName("first name field");
        f.addKeyListener(this);

        l = new JTextField(15);   //text field for lastname
        l.setName("last name field");
        l.addKeyListener(this);
        
        addressLabel = new JLabel("Address: ");
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        
        address = new JTextArea();
        address.setLineWrap(true);
        address.setWrapStyleWord(true);
        address.setName("address field");


        phno = new JLabel("Contact No.: ");
        phno.setFont(new Font("Arial", Font.PLAIN, 15));
        
        ph = new JTextField(11);  //text field for phone number
        ph.setName("phone number field");
        ph.addKeyListener(this);

        ml = new JRadioButton("Male");
        ml.setName("male button");
        fml = new JRadioButton("Female");
        fml.setName("female button");
        bg = new ButtonGroup();
        ml.setOpaque(false);
        fml.setOpaque(false);
        bg.add(ml);
        bg.add(fml);

        id = new JLabel("Assign ID: ");
        id.setFont(new Font("Arial", Font.PLAIN, 15));
        ID = new JTextField(11);
        ID.setName("ID field");
        
        studentClass = new JLabel("Class: ");
        studentClass.setFont(new Font("Arial", Font.PLAIN, 15));
        stdClassField = new JTextField(2);
        stdClassField.setName("class field");
        
        studentClassSection = new JLabel("Section: ");
        studentClassSection.setFont(new Font("Arial", Font.PLAIN, 15));
        stdSectionField = new JTextField(2);
        stdSectionField.setName("section field");
        
        //CREATE ADD STUDENT PANEL
        Addop = new JPanel();
        Addop.setLayout(null);
        Addop.setBounds(0, 0, 1366 - 100, 768 - 100);

        title = new JLabel("Add Student");
        title.setBounds(680, 20, 280, 34);
        title.setFont(new Font("Georgia", Font.PLAIN, 34));
        Addop.add(title);

        //ADDING EACH COMPONENT TO ADD STUDENT PANEL
        Addop.add(fn);
        Addop.add(ln);
        Addop.add(dob);
        Addop.add(gender);
        Addop.add(f);
        Addop.add(l);
        Addop.add(dc);
        Addop.add(addressLabel);
        Addop.add(address);
        Addop.add(phno);
        Addop.add(ph);
        Addop.add(ml);
        Addop.add(fml);
        Addop.add(id);
        Addop.add(ID);
        Addop.add(studentClass);
        Addop.add(stdClassField);
        Addop.add(studentClassSection);
        Addop.add(stdSectionField);

        Addop.setOpaque(false);
        Addop.setVisible(false);
        comboLabel.add(Addop);

        int xpos = 400;
        int ypos = 80;

        //LOCATION OF EACH COMPONENT
        fn.setBounds(xpos, ypos, 150, 40);
        f.setBounds(xpos + 250, ypos, 260, 40);

        ypos += 45;

        ln.setBounds(xpos, ypos, 150, 40);
        l.setBounds(xpos + 250, ypos, 260, 40);

        ypos += 45;

        dob.setBounds(xpos, ypos, 180, 40);
        dc.setBounds(xpos + 250, ypos, 260, 40);

        ypos += 45;
        
        addressLabel.setBounds(xpos, ypos, 150, 40);
        address.setBounds(xpos + 250, ypos, 260, 100);

        ypos += 105;

        phno.setBounds(xpos, ypos, 150, 40);
        ph.setBounds(xpos + 250, ypos, 260, 40);

        ypos += 45;

        gender.setBounds(xpos, ypos, 150, 40);
        ml.setBounds(xpos + 250, ypos + 10, 70, 20);
        fml.setBounds(xpos + 350, ypos + 10, 70, 20);

        ypos += 45;

        id.setBounds(xpos, ypos, 150, 40);
        ID.setBounds(xpos + 250, ypos, 260, 40);

        ypos += 45;
        
        studentClass.setBounds(xpos, ypos, 150, 40);
        stdClassField.setBounds(xpos+250, ypos, 260, 40);
        
        ypos += 45;
        
        studentClassSection.setBounds(xpos, ypos, 150,40);
        stdSectionField.setBounds(xpos+250, ypos, 260, 40);
        
        //BUTTONS FOR ADD STUDENT PANEL
        //BUTTON TO CLOSE STUDENT PANEL
        close = new JButton("CLOSE");
        close.setBounds(xpos + 380, ypos + 90, 150, 40);
        close.addActionListener(this);
        Addop.add(close);

        //BUTTON FOR SAVING STUDENT RECORD
        save = new JButton("SAVE");
        save.addActionListener(this);
        save.setBounds(xpos + 180, ypos + 90, 150, 40);
        Addop.add(save);

        //BUTTON FOR DELETING STUDENT RECORD
        delete = new JButton("DELETE");
        delete.addActionListener(this);
        delete.setBounds(xpos + 580, ypos + 90, 150, 40);
        Addop.add(delete);

        //SETTING FRAME PROPERTIES
        setResizable(false);
        setBackground(new Color(162, 217, 206));
        setBounds(50, 50, 1366 - 100, 768 - 100);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == close) {
            inSearch = false;
            Addop.setVisible(false);
        }
        else if(ae.getSource() == clsWindow){
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            System.exit(0);
        }
        
        //ACTION FOR DELETING STUDENT RECORD
        else if (ae.getSource() == delete) {
            try {
                int op = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this student record?");

                if (op == 0) {    //user selected Yes
                    //delete record
                    
                    String sql = "DELETE FROM `student_table` WHERE id = '"+ID.getText()+"'";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null, "Student record deleted!");
                    Addop.setVisible(false);
                    inSearch = false;
                }
            } catch (HeadlessException | SQLException e) {
                JOptionPane optionPane = new JOptionPane(e, JOptionPane.ERROR_MESSAGE);    
                JDialog dialog = optionPane.createDialog("Error");
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
            }
        } 

        //ACTION FOR SEARCHING STUDENTS
        else if (ae.getSource() == SEARCH) {

            try {

                Addop.setVisible(false);
                String ID = JOptionPane.showInputDialog(this, "Enter Student ID: ");
                if(ID!=null){
                    stmt = conn.createStatement();
                    String sql = "SELECT * FROM student_table WHERE id='"+ID+"'";
                    rs = stmt.executeQuery(sql);
                    if(rs.next()){
                        Addop.setVisible(true); //show the details panel
                        delete.setVisible(true);
                        title.setText("Edit Student");
                        inSearch = true;
                        for (Component component : Addop.getComponents()) {

                            if(component.getName()!=null){
                                if (component.getName().equalsIgnoreCase("first name field")) {

                                    JTextField textf = (JTextField) component;
                                    textf.setText(rs.getString(2));

                                } else if (component.getName().equalsIgnoreCase("last name field")) {

                                    JTextField textf = (JTextField) component;
                                    textf.setText(rs.getString(3));

                                } else if (component.getName().equalsIgnoreCase("address field")) {

                                    JTextArea textf = (JTextArea) component;
                                    textf.setText(rs.getString(7));

                                }else if (component.getName().equalsIgnoreCase("phone number field")) {

                                    JTextField textf = (JTextField) component;
                                    textf.setText(rs.getString(6));

                                } else if (component.getName().equalsIgnoreCase("ID field")) {

                                    JTextField textf = (JTextField) component;
                                    textf.setText(rs.getString(1));
                                    this.ID.setEditable(false);

                                } else if (component.getName().equalsIgnoreCase("male button")) {

                                    if (rs.getString(4).equals("Male")) {

                                        bg.clearSelection();
                                        ml.setSelected(true);

                                    }

                                } else if (component.getName().equalsIgnoreCase("female button")) {

                                    if (rs.getString(4).equals("Female")) {
                                        bg.clearSelection();
                                        fml.setSelected(true);

                                    }

                                } else if (component.getName().equalsIgnoreCase("date of birth")) {

                                    JDateChooser date = (JDateChooser) component;

                                    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    String inputDateStr = rs.getString(5);
                                    Date dateinp = inputFormat.parse(inputDateStr);
                                    String outputDateStr = outputFormat.format(dateinp);

                                    java.util.Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(outputDateStr);
                                    date.setDate(date2);


                                } else if (component.getName().equalsIgnoreCase("class field")){

                                    JTextField textf = (JTextField) component;
                                    textf.setText(rs.getString(8));

                                } else if(component.getName().equalsIgnoreCase("section field")){

                                    JTextField textf = (JTextField) component;
                                    textf.setText(rs.getString(9));

                                }
                            }
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Invalid ID!");
                    }
                    stmt.close();
                }
            }
            catch (HeadlessException | SQLException | ParseException e) {
                JOptionPane optionPane = new JOptionPane(e, JOptionPane.ERROR_MESSAGE);    
                JDialog dialog = optionPane.createDialog("Error");
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
            }
        }
        
        //ACTION TO ADD STUDENTS
        else if (ae.getSource() == ADD) {
            inSearch = false;
            ID.setEditable(true);
            title.setText("Add Student");
            Addop.setVisible(true);
            delete.setVisible(false);   //hide delete button when adding new student
            for (Component component : Addop.getComponents()) {
                if (component instanceof JTextField) {

                    ((JTextField) component).setText("");

                } else if (component instanceof JRadioButton) {

                    bg.clearSelection();

                } else if (component instanceof JDateChooser) {

                    ((JDateChooser) component).setDate(null);

                } else if(component instanceof JTextArea) {
                    
                    ((JTextArea) component).setText("");
                    
                }
            }

        } 

        //ACTION FOR CREATE NEW ACCOUNT
        else if (ae.getSource() == newacc) {
            try{
                
                String AdminID = JOptionPane.showInputDialog(this, "Enter School key to create new account: ");
                if (AdminID.equalsIgnoreCase("123456")) {   //SCHOOL KEY TO ADD NEW ADMIN ACCOUNT
                    entry.setVisible(false);
                    Newacc.setVisible(true);
                    un.setText("");
                    pw.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Key!");
                }
            }
            catch(Exception e){}
        } 

        //ACTION TO ADD ADMIN
        else if (ae.getSource() == create) {
            try {
                if(un.getText().length() == 0){
                    JOptionPane.showMessageDialog(null, "Enter username!");
                }
                else if(pw.getPassword().length < 8){
                    JOptionPane.showMessageDialog(null, "Password must be atleast 8 characters long!");
                }
                else{
                    
                    String adminUser = un.getText();
                    String adminPass = String.valueOf(pw.getPassword());
                    
                    String sql = "INSERT INTO `admin_table` VALUES (?,?)";
                    PreparedStatement st = conn.prepareStatement(sql);
                    st.setString(1, adminUser);
                    st.setString(2, adminPass);                    
                    st.executeUpdate();
                    st.close();
                    JOptionPane.showMessageDialog(null, "Account created!");
                    Newacc.setVisible(false);
                    entry.setVisible(true);
                    username.setText("");
                    password.setText("");
                    
                }
           
            } catch (HeadlessException | SQLException e) {
                JOptionPane optionPane = new JOptionPane(e, JOptionPane.ERROR_MESSAGE);    
                JDialog dialog = optionPane.createDialog("Error");
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
            }
        } 
        else if (ae.getSource() == cancel) {
            Newacc.setVisible(false);
            entry.setVisible(true);
        }
        
        //ACTION FOR LOGOUT
        else if (ae.getSource() == LOG) {
            inSearch = false;
            Addop.setVisible(false);
            username.setText("");
            password.setText("");
            mainPanel.setVisible(false);
            entry.setVisible(true);
        }
        
        //ACTION FOR LOGIN
        else if (ae.getSource() == login) {
            
            try {
                stmt = conn.createStatement();
                String userName = username.getText();
                String pass = String.valueOf(password.getPassword());
                
                String sql = "SELECT * FROM admin_table WHERE username='"+userName+"' && password='"+pass+"'";
                rs = stmt.executeQuery(sql);
                if(rs.next()){
                    
                    entry.setVisible(false);
                    mainPanel.setVisible(true);
                    JOptionPane.showMessageDialog(null, "Welcome to Student Registration System.");

                }
                else{
                    JOptionPane.showMessageDialog(comboLabel, "Invalid username/password!");
                }
                
            } catch (HeadlessException | SQLException e) {
                JOptionPane optionPane = new JOptionPane(e, JOptionPane.ERROR_MESSAGE);    
                JDialog dialog = optionPane.createDialog("Error");
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
            }

        } 
        
        //ACTION FOR SAVING STUDENT DETAILS
        else if (ae.getSource() == save) {
            
            try {
                
                String firstName = f.getText();
                String lastName = l.getText();
                String id = ID.getText();
                String add = address.getText();
                String gender = new String();
                if(ml.isSelected())
                    gender = "Male";
                else if (fml.isSelected()) {
                    gender = "Female";
                }
                String phone = ph.getText();
                String clss = stdClassField.getText();
                String section = stdSectionField.getText();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = dateFormat.format(dc.getDate());

                //Throw exception if any field is empty
                if(firstName.equals("")|| lastName.equals("") || id.equals("") || add.equals("") || gender.equals("") || phone.equals("") || clss.equals("") || section.equals("") || date.equals("")){
                    throw new NullPointerException();
                }

                String sql = "";
                if(!inSearch){
                    /*
                    stmt = conn.createStatement();
                    sql = "INSERT INTO `student_table` VALUES ('"+id+"', '"+firstName+"', '"+lastName+"', '"+gender+"', '"+date+"', '"+phone+"', '"+add+"', '"+clss+"', '"+section+"')";
                    int rowAffected = stmt.executeUpdate(sql);
                    System.out.println("Inserted data!"); 
                    stmt.close();
                    */
                    
                    
                    sql = "INSERT INTO `student_table` VALUES (?,?,?,?,?,?,?,?,?)";
                    PreparedStatement st = conn.prepareStatement(sql);
                    st.setString(1, id);
                    st.setString(2, firstName);
                    st.setString(3, lastName);
                    st.setString(4, gender);
                    st.setString(5, date);
                    st.setString(6, phone);
                    st.setString(7, add);
                    st.setString(8, clss);
                    st.setString(9, section);
                    int rowAffected = st.executeUpdate();
//                    System.out.println("No of rows affected: "+rowAffected);
                    st.close();
                }
                else{

                    sql = "UPDATE student_table SET firstName = '"+firstName+"', lastName = '"+lastName+"', gender = '"+gender+"', dob = '"+date+"', phone = '"+phone+"', address = '"+add+"', class = '"+clss+"', section = '"+section+"' WHERE id = '"+id+"';";
                    stmt = conn.createStatement();
                    int rowAffected = stmt.executeUpdate(sql);
//                    System.out.println("No of rows affected in updated: "+rowAffected);
                    inSearch = false;
                    stmt.close();
                }
                
                JOptionPane.showMessageDialog(comboLabel, "Student registered successfully!", "Success", 2);
                Addop.setVisible(false);
                      
                
            } catch (Exception e) {
                JOptionPane optionPane = new JOptionPane(e, JOptionPane.ERROR_MESSAGE);    
                JDialog dialog = optionPane.createDialog("Error");
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
            }
        }
    }
    
    
    //KEYLISTENERS FOR NAME AND CONTACT FIELDS
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
       
        //PHONE TEXTFIELD TAKES ONLY NUMBERS WITH LENGTH 10
        if (e.getSource() == ph) {
            String phno = ph.getText();
            char c = e.getKeyChar();
            if (c >= '0' && c <= '9') {
                if (phno.length() < 10) {
                    ph.setEditable(true);
                } else {
                    ph.setEditable(false);
                }
            } else {
                if (e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
                    ph.setEditable(true);
                } else {
                    ph.setEditable(false);
                }
            }
        } else if (e.getSource() == f || e.getSource() == l || e.getSource() == un) {
            char c = e.getKeyChar();
            if (Character.isLetter(c) || Character.isSpaceChar(c) || e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
                if (e.getSource() == f) {
                    f.setEditable(true);
                } else if(e.getSource() == l){
                    l.setEditable(true);
                } else if(e.getSource() == un){
                    un.setEditable(true);
                }
            } else {
                if (e.getSource() == f) {
                    f.setEditable(false);
                } else if(e.getSource() == l){
                    l.setEditable(false);
                }
                else if(e.getSource() == un){
                    un.setEditable(false);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
