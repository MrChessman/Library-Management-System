/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package projectFrames;

//import java.awt.Toolkit;

import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import projectFrames.*;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.sql.Date;
import java.sql.SQLException;
//import org.apache.commons.text.similarity.JaroWinklerSimilarity; //to be used for names
/**
 *
 * @author Chessman
 */
public class IssueBookFrame extends javax.swing.JFrame {

    /**
     * Creates new form SignUpFrame
     */
    //Local variables
    public String uName;
    private String bookCategory = "";
    Color mouseEnterColor = new Color(102,102,0);
    Color mouseExitColor = new Color(153,153,0);
    Color logoutMouseEnterColor = new Color(255,0,0);
    Color logoutMouseExitColor = new Color(255,255,204);
    DefaultTableModel model;
    public IssueBookFrame() {
        initComponents();
        
        setVisible(true);
        setAlwaysOnTop(true);
        Toolkit t = Toolkit.getDefaultToolkit();
        int w = (int)t.getScreenSize().getWidth();
        int h = (int)t.getScreenSize().getHeight();
        setSize(w, h);
        //setExtendedState(MAXIMIZED_BOTH);
        System.out.println(w);
        System.out.println(h);

    }   

    public IssueBookFrame(String Name) {
        initComponents();
        setVisible(true);
        setAlwaysOnTop(true);
        Toolkit t = Toolkit.getDefaultToolkit();
        int w = (int)t.getScreenSize().getWidth();
        int h = (int)t.getScreenSize().getHeight();
        setSize(w, h);
        //setExtendedState(MAXIMIZED_BOTH);
        System.out.println(w);
        System.out.println(h);
        uName = Name;
        userGreetHeader.setText("Welcome, " + uName);

    }
    
    //To fetch the book details from the database and display it to the book details panel
    public void getBookDetails(){
        int book_id = Integer.parseInt(bookIdTxtField.getText());
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT bookTitle, bookCode, category, stocks FROM book_inventory WHERE bookID = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, book_id);
            ResultSet rs = pst.executeQuery();
      
            if (rs.next()){
                bookTitleTxtField.setText(rs.getString("bookTitle"));
                bookCodeTxtField.setText(rs.getString("bookCode"));
                stockTxtField.setText(rs.getString("stocks"));
                categoryTxtField.setText(rs.getString("category"));
                bookCategory = rs.getString("category");
                
                if (isNotBorrowableCategory(bookCategory)) {
                JOptionPane.showMessageDialog(this, "This book is not borrowable.", "Not Borrowable", JOptionPane.WARNING_MESSAGE);
                }  
            } else {
                clearBookDetails();
                JOptionPane.showMessageDialog(this, "Book not found.", "Book Not Found", JOptionPane.WARNING_MESSAGE);
                }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    //to check if a category is not borrowable
    private boolean isNotBorrowableCategory(String category) {
    return category.equalsIgnoreCase("Not Borrowable") ||
           category.equalsIgnoreCase("Thesis") ||
           category.equalsIgnoreCase("Encyclopedia") ||
           category.equalsIgnoreCase("Thesaurus") ||
           category.equalsIgnoreCase("Dictionary");
}
    
    //Method to clear the book details panel
    private void clearBookDetails() {
        bookTitleTxtField.setText("");
        bookCodeTxtField.setText("");
        stockTxtField.setText("");
        categoryTxtField.setText("");
        bookIdTxtField.setText("");
    }
    
    //Insert Issue Book Details to the database
    public boolean issueBook(){
        boolean isIssued = false;
        int book_id = Integer.parseInt(bookIdTxtField.getText());
        String book_title = bookTitleTxtField.getText();
        String borrower_name = borrowerNameTxtField.getText();
        int borrower_student_num;
        String borrower_contact = contactTxtField.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String issue_date = null;
        String due_date = null;
        String issue_remarks = remarksTxtField.getText();
        
        //validate inputs
        if (bookIdTxtField.getText().isEmpty() || borrowerNameTxtField.getText().isEmpty() || studentNumberTxtField.getText().isEmpty() || contactTxtField.getText().isEmpty() || issueDateChooser.getDate() == null || dueDateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "All fields must be valid and filled out.");
            return false;
        }
        
        if (isAlreadyIssued()) {
        JOptionPane.showMessageDialog(this, "This book is already issued to the same user.", "Duplicate Issue", JOptionPane.WARNING_MESSAGE);
        return false;
        }
        
        if (isNotBorrowableCategory(bookCategory)) {
        JOptionPane.showMessageDialog(this, "This book cannot be issued because it is not borrowable.", "Not Borrowable", JOptionPane.ERROR_MESSAGE);
        return false;
        }
        
        if (!hasAvailableStock()) {
        JOptionPane.showMessageDialog(this, "Sorry, this book is out of stock and cannot be issued.", "Out of Stock", JOptionPane.ERROR_MESSAGE);
        return false;
        }
        
        try {
            issue_date = dateFormat.format(issueDateChooser.getDate());
            } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please select a valid date.");
            return false; 
            }
        
        try {
            due_date = dateFormat.format(dueDateChooser.getDate());
            } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please select a valid date.");
            return false; 
            }
        
        try {
            borrower_student_num = Integer.parseInt(studentNumberTxtField.getText());
            } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stocks must be a valid number.");
            return false;
            }
        
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO issue_book_table (bookID, bookTitle, name, studentNo, contact, issueDate, dueDate, remarks, status) VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, book_id);
            pst.setString(2, book_title);
            pst.setString(3, borrower_name);
            pst.setInt(4, borrower_student_num);
            pst.setString(5, borrower_contact);
            pst.setString(6, issue_date);
            pst.setString(7, due_date);
            pst.setString(8, issue_remarks);
            pst.setString(9, "Pending");
            
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
            isIssued = true;
            } else {
            isIssued = false;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return isIssued;
    }
    
    //To reset the form and fields after action
    private void clearFields() {
    bookIdTxtField.setText(""); 
    borrowerNameTxtField.setText("");
    studentNumberTxtField.setText("");
    contactTxtField.setText("");
    issueDateChooser.setDate(null);
    dueDateChooser.setDate(null);
    remarksTxtField.setText(""); 
    }
    
    //to update stocks after issue
    public void updateBookStocks(){
        int book_id = Integer.parseInt(bookIdTxtField.getText());
        try {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE book_inventory SET stocks = stocks - 1 WHERE bookID = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, book_id);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Book stocks updated!");
            } else {
                JOptionPane.showMessageDialog(this, "Error in updating book stocks.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //Similarity Algorithm to be used for names
//    public double calculateSimilarity(String name1, String name2) {
//        JaroWinklerSimilarity similarity = new JaroWinklerSimilarity();
//        return similarity.apply(name1.toLowerCase().trim(), name2.toLowerCase().trim());
//    }
    
    //Checking duplicated allocations
    public boolean isAlreadyIssued() {
        boolean isAlreadyIssued = false;
        int book_id = Integer.parseInt(bookIdTxtField.getText());
        int borrower_student_num = Integer.parseInt(studentNumberTxtField.getText());

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM issue_book_table WHERE bookID = ? AND studentNo = ? AND status = 'Pending'";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, book_id);
            pst.setInt(2, borrower_student_num);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                isAlreadyIssued = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isAlreadyIssued;
    }
    
    // Checking if the book has available stock
    public boolean hasAvailableStock() {
        boolean hasStock = false;
        int book_id = Integer.parseInt(bookIdTxtField.getText());

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT stocks FROM book_inventory WHERE bookID = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, book_id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int stock = rs.getInt("stocks");
                if (stock > 0) {
                    hasStock = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasStock;
    }
 
    //method to check if the student has overdue books to prevent him from borrowing
    public boolean hasOverdueBooks(int studentNo) {
        boolean hasOverdue = false;
        long timeToday = System.currentTimeMillis();
        Date dateToday = new Date(timeToday);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayDateStr = sdf.format(dateToday);

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM issue_book_table WHERE studentNo = ? AND dueDate < ? AND status = 'Pending'";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, studentNo);
            pst.setString(2, todayDateStr);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                hasOverdue = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasOverdue;
    }
    
    //logout
    public void logout() {
        Connection con = DBConnection.getConnection();
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
            this.dispose();
        }
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgPanel = new javax.swing.JPanel();
        HeaderPanel = new javax.swing.JPanel();
        menuIcon = new javax.swing.JLabel();
        liner = new RoundedPanel.RoundPanel();
        titleHeader = new javax.swing.JLabel();
        closeIcon = new javax.swing.JLabel();
        logo = new javax.swing.JLabel();
        userGreetHeader = new javax.swing.JLabel();
        tabBar = new javax.swing.JPanel();
        homeTab = new RoundedPanel.RoundPanel();
        homeTabLabel = new javax.swing.JLabel();
        dashboardTab = new RoundedPanel.RoundPanel();
        dashboardTabLabel = new javax.swing.JLabel();
        featuresLabel = new javax.swing.JLabel();
        manageBooksTab = new RoundedPanel.RoundPanel();
        manageBooksTabLabel = new javax.swing.JLabel();
        computerTab = new RoundedPanel.RoundPanel();
        computerTabLabel = new javax.swing.JLabel();
        issueBookTab = new RoundedPanel.RoundPanel();
        issueBookTabLabel = new javax.swing.JLabel();
        returnBookTab = new RoundedPanel.RoundPanel();
        returnBookTabLabel = new javax.swing.JLabel();
        viewRecordsTab = new RoundedPanel.RoundPanel();
        viewRecordsTabLabel = new javax.swing.JLabel();
        viewIssuedBooksTab = new RoundedPanel.RoundPanel();
        viewIssuedBooksTabLabel = new javax.swing.JLabel();
        defaulterListTab = new RoundedPanel.RoundPanel();
        defaulterListTabLabel = new javax.swing.JLabel();
        logoutTab = new RoundedPanel.RoundPanel();
        logoutTabLabel = new javax.swing.JLabel();
        homePanel = new javax.swing.JPanel();
        issueBookRoundPanelContainer = new RoundedPanel.RoundPanel();
        issueBookHeader = new javax.swing.JLabel();
        issueBookHeaderLine = new javax.swing.JPanel();
        bookIdLabel = new javax.swing.JLabel();
        bookIdIcon = new javax.swing.JLabel();
        borrowerNameIcon = new javax.swing.JLabel();
        bookIdTxtField = new app.bolivia.swing.JCTextField();
        borrowerNameLabel = new javax.swing.JLabel();
        borrowerNameTxtField = new app.bolivia.swing.JCTextField();
        sNoLabel = new javax.swing.JLabel();
        studentNumberTxtField = new app.bolivia.swing.JCTextField();
        sNoicon = new javax.swing.JLabel();
        contactIcon = new javax.swing.JLabel();
        contactTxtField = new app.bolivia.swing.JCTextField();
        contactLabel = new javax.swing.JLabel();
        issueDIcon = new javax.swing.JLabel();
        issueDLabel = new javax.swing.JLabel();
        dueDateLabel = new javax.swing.JLabel();
        dueDateIcon = new javax.swing.JLabel();
        remarksTxtField = new app.bolivia.swing.JCTextField();
        remarksLabel = new javax.swing.JLabel();
        remarksIcon = new javax.swing.JLabel();
        bookDetailsRoundPanelContainer = new RoundedPanel.RoundPanel();
        bookDetailsHeader = new javax.swing.JLabel();
        bookTitleLabel = new javax.swing.JLabel();
        bookTitleIcon = new javax.swing.JLabel();
        bookTitleTxtField = new app.bolivia.swing.JCTextField();
        bookCodeTxtField = new app.bolivia.swing.JCTextField();
        bookCodeIcon = new javax.swing.JLabel();
        bookCodeLabel = new javax.swing.JLabel();
        stockTxtField = new app.bolivia.swing.JCTextField();
        stockIcon = new javax.swing.JLabel();
        stockLabel = new javax.swing.JLabel();
        categoryTxtField = new app.bolivia.swing.JCTextField();
        categoryIcon = new javax.swing.JLabel();
        categoryLabel = new javax.swing.JLabel();
        issueBook = new rojerusan.RSMaterialButtonCircle();
        issueDateChooser = new com.toedter.calendar.JDateChooser();
        dueDateChooser = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cavite State University Silang-Campus Library Management System Copyright 2023-2024");
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bgPanel.setBackground(new java.awt.Color(51, 102, 0));
        bgPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        HeaderPanel.setBackground(new java.awt.Color(255, 255, 153));
        HeaderPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/menu-burger.png"))); // NOI18N
        HeaderPanel.add(menuIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 16, -1, -1));

        liner.setBackground(new java.awt.Color(153, 153, 0));
        liner.setRoundBottomLeft(8);
        liner.setRoundBottomRight(8);
        liner.setRoundTopLeft(8);
        liner.setRoundTopRight(8);

        javax.swing.GroupLayout linerLayout = new javax.swing.GroupLayout(liner);
        liner.setLayout(linerLayout);
        linerLayout.setHorizontalGroup(
            linerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        linerLayout.setVerticalGroup(
            linerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        HeaderPanel.add(liner, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, 48));

        titleHeader.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 36)); // NOI18N
        titleHeader.setForeground(new java.awt.Color(51, 102, 0));
        titleHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleHeader.setText("Library Management System");
        HeaderPanel.add(titleHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, -1, -1));

        closeIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        closeIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/circle-xmark.png"))); // NOI18N
        closeIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeIconMouseClicked(evt);
            }
        });
        HeaderPanel.add(closeIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(1477, 13, 46, 41));

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/LogoCvsu_enhanced small (1).png"))); // NOI18N
        logo.setText("jLabel1");
        HeaderPanel.add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 80, 70));

        userGreetHeader.setFont(new java.awt.Font("Monotype Corsiva", 1, 40)); // NOI18N
        userGreetHeader.setForeground(new java.awt.Color(51, 102, 0));
        userGreetHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userGreetHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/circle-user (1).png"))); // NOI18N
        userGreetHeader.setText("Welcome, Admin Admin Admin");
        HeaderPanel.add(userGreetHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 10, 530, -1));

        bgPanel.add(HeaderPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1540, 70));

        tabBar.setBackground(new java.awt.Color(153, 153, 0));

        homeTab.setBackground(new java.awt.Color(153, 153, 0));
        homeTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeTabMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homeTabMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                homeTabMouseExited(evt);
            }
        });

        homeTabLabel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 15)); // NOI18N
        homeTabLabel.setForeground(new java.awt.Color(255, 255, 255));
        homeTabLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/icons8_Home_26px_2.png"))); // NOI18N
        homeTabLabel.setText("    Home Page");

        javax.swing.GroupLayout homeTabLayout = new javax.swing.GroupLayout(homeTab);
        homeTab.setLayout(homeTabLayout);
        homeTabLayout.setHorizontalGroup(
            homeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homeTabLayout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(homeTabLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        homeTabLayout.setVerticalGroup(
            homeTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, homeTabLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(homeTabLabel)
                .addGap(15, 15, 15))
        );

        dashboardTab.setBackground(new java.awt.Color(153, 153, 0));

        dashboardTabLabel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 15)); // NOI18N
        dashboardTabLabel.setForeground(new java.awt.Color(255, 255, 255));
        dashboardTabLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/icons8_Library_32px.png"))); // NOI18N
        dashboardTabLabel.setText("    Dashboard");

        javax.swing.GroupLayout dashboardTabLayout = new javax.swing.GroupLayout(dashboardTab);
        dashboardTab.setLayout(dashboardTabLayout);
        dashboardTabLayout.setHorizontalGroup(
            dashboardTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardTabLayout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(dashboardTabLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dashboardTabLayout.setVerticalGroup(
            dashboardTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashboardTabLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(dashboardTabLabel)
                .addGap(15, 15, 15))
        );

        featuresLabel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        featuresLabel.setForeground(new java.awt.Color(255, 255, 255));
        featuresLabel.setText("Features");

        manageBooksTab.setBackground(new java.awt.Color(153, 153, 0));
        manageBooksTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageBooksTabMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                manageBooksTabMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                manageBooksTabMouseExited(evt);
            }
        });

        manageBooksTabLabel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 15)); // NOI18N
        manageBooksTabLabel.setForeground(new java.awt.Color(255, 255, 255));
        manageBooksTabLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/books (1).png"))); // NOI18N
        manageBooksTabLabel.setText("    Manage Books");

        javax.swing.GroupLayout manageBooksTabLayout = new javax.swing.GroupLayout(manageBooksTab);
        manageBooksTab.setLayout(manageBooksTabLayout);
        manageBooksTabLayout.setHorizontalGroup(
            manageBooksTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageBooksTabLayout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(manageBooksTabLabel)
                .addContainerGap(116, Short.MAX_VALUE))
        );
        manageBooksTabLayout.setVerticalGroup(
            manageBooksTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageBooksTabLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(manageBooksTabLabel)
                .addGap(15, 15, 15))
        );

        computerTab.setBackground(new java.awt.Color(153, 153, 0));
        computerTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                computerTabMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                computerTabMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                computerTabMouseExited(evt);
            }
        });

        computerTabLabel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 15)); // NOI18N
        computerTabLabel.setForeground(new java.awt.Color(255, 255, 255));
        computerTabLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/dashboard-monitor.png"))); // NOI18N
        computerTabLabel.setText("    Computer Logs");

        javax.swing.GroupLayout computerTabLayout = new javax.swing.GroupLayout(computerTab);
        computerTab.setLayout(computerTabLayout);
        computerTabLayout.setHorizontalGroup(
            computerTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(computerTabLayout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(computerTabLabel)
                .addContainerGap(113, Short.MAX_VALUE))
        );
        computerTabLayout.setVerticalGroup(
            computerTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, computerTabLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(computerTabLabel)
                .addGap(15, 15, 15))
        );

        issueBookTab.setBackground(new java.awt.Color(51, 102, 0));

        issueBookTabLabel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 15)); // NOI18N
        issueBookTabLabel.setForeground(new java.awt.Color(255, 255, 255));
        issueBookTabLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/book-bookmark.png"))); // NOI18N
        issueBookTabLabel.setText("    Issue Book");

        javax.swing.GroupLayout issueBookTabLayout = new javax.swing.GroupLayout(issueBookTab);
        issueBookTab.setLayout(issueBookTabLayout);
        issueBookTabLayout.setHorizontalGroup(
            issueBookTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(issueBookTabLayout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(issueBookTabLabel)
                .addContainerGap(144, Short.MAX_VALUE))
        );
        issueBookTabLayout.setVerticalGroup(
            issueBookTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, issueBookTabLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(issueBookTabLabel)
                .addGap(15, 15, 15))
        );

        returnBookTab.setBackground(new java.awt.Color(153, 153, 0));
        returnBookTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                returnBookTabMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                returnBookTabMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                returnBookTabMouseExited(evt);
            }
        });

        returnBookTabLabel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 15)); // NOI18N
        returnBookTabLabel.setForeground(new java.awt.Color(255, 255, 255));
        returnBookTabLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/undo.png"))); // NOI18N
        returnBookTabLabel.setText("    Return Book");

        javax.swing.GroupLayout returnBookTabLayout = new javax.swing.GroupLayout(returnBookTab);
        returnBookTab.setLayout(returnBookTabLayout);
        returnBookTabLayout.setHorizontalGroup(
            returnBookTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(returnBookTabLayout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(returnBookTabLabel)
                .addContainerGap(132, Short.MAX_VALUE))
        );
        returnBookTabLayout.setVerticalGroup(
            returnBookTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, returnBookTabLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(returnBookTabLabel)
                .addGap(15, 15, 15))
        );

        viewRecordsTab.setBackground(new java.awt.Color(153, 153, 0));
        viewRecordsTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewRecordsTabMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                viewRecordsTabMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                viewRecordsTabMouseExited(evt);
            }
        });

        viewRecordsTabLabel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 15)); // NOI18N
        viewRecordsTabLabel.setForeground(new java.awt.Color(255, 255, 255));
        viewRecordsTabLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/attention-detail.png"))); // NOI18N
        viewRecordsTabLabel.setText("    View Records");

        javax.swing.GroupLayout viewRecordsTabLayout = new javax.swing.GroupLayout(viewRecordsTab);
        viewRecordsTab.setLayout(viewRecordsTabLayout);
        viewRecordsTabLayout.setHorizontalGroup(
            viewRecordsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewRecordsTabLayout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(viewRecordsTabLabel)
                .addContainerGap(126, Short.MAX_VALUE))
        );
        viewRecordsTabLayout.setVerticalGroup(
            viewRecordsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewRecordsTabLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(viewRecordsTabLabel)
                .addGap(15, 15, 15))
        );

        viewIssuedBooksTab.setBackground(new java.awt.Color(153, 153, 0));
        viewIssuedBooksTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewIssuedBooksTabMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                viewIssuedBooksTabMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                viewIssuedBooksTabMouseExited(evt);
            }
        });

        viewIssuedBooksTabLabel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 15)); // NOI18N
        viewIssuedBooksTabLabel.setForeground(new java.awt.Color(255, 255, 255));
        viewIssuedBooksTabLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/book-bookmark (1).png"))); // NOI18N
        viewIssuedBooksTabLabel.setText("    View Issued Books");

        javax.swing.GroupLayout viewIssuedBooksTabLayout = new javax.swing.GroupLayout(viewIssuedBooksTab);
        viewIssuedBooksTab.setLayout(viewIssuedBooksTabLayout);
        viewIssuedBooksTabLayout.setHorizontalGroup(
            viewIssuedBooksTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewIssuedBooksTabLayout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(viewIssuedBooksTabLabel)
                .addContainerGap(92, Short.MAX_VALUE))
        );
        viewIssuedBooksTabLayout.setVerticalGroup(
            viewIssuedBooksTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewIssuedBooksTabLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(viewIssuedBooksTabLabel)
                .addGap(15, 15, 15))
        );

        defaulterListTab.setBackground(new java.awt.Color(153, 153, 0));
        defaulterListTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                defaulterListTabMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                defaulterListTabMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                defaulterListTabMouseExited(evt);
            }
        });

        defaulterListTabLabel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 15)); // NOI18N
        defaulterListTabLabel.setForeground(new java.awt.Color(255, 255, 255));
        defaulterListTabLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/users-alt.png"))); // NOI18N
        defaulterListTabLabel.setText("    Defaulter List");

        javax.swing.GroupLayout defaulterListTabLayout = new javax.swing.GroupLayout(defaulterListTab);
        defaulterListTab.setLayout(defaulterListTabLayout);
        defaulterListTabLayout.setHorizontalGroup(
            defaulterListTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(defaulterListTabLayout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(defaulterListTabLabel)
                .addContainerGap(125, Short.MAX_VALUE))
        );
        defaulterListTabLayout.setVerticalGroup(
            defaulterListTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, defaulterListTabLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(defaulterListTabLabel)
                .addGap(15, 15, 15))
        );

        logoutTab.setBackground(new java.awt.Color(255, 255, 204));
        logoutTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutTabMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutTabMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutTabMouseExited(evt);
            }
        });

        logoutTabLabel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 15)); // NOI18N
        logoutTabLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/sign-out-alt.png"))); // NOI18N
        logoutTabLabel.setText("    Logout");

        javax.swing.GroupLayout logoutTabLayout = new javax.swing.GroupLayout(logoutTab);
        logoutTab.setLayout(logoutTabLayout);
        logoutTabLayout.setHorizontalGroup(
            logoutTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoutTabLayout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(logoutTabLabel)
                .addContainerGap(169, Short.MAX_VALUE))
        );
        logoutTabLayout.setVerticalGroup(
            logoutTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logoutTabLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(logoutTabLabel)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout tabBarLayout = new javax.swing.GroupLayout(tabBar);
        tabBar.setLayout(tabBarLayout);
        tabBarLayout.setHorizontalGroup(
            tabBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(homeTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(dashboardTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(manageBooksTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(computerTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(issueBookTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(returnBookTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(viewRecordsTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(tabBarLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(featuresLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(viewIssuedBooksTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(defaulterListTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(logoutTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tabBarLayout.setVerticalGroup(
            tabBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabBarLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(homeTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dashboardTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(featuresLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(manageBooksTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(computerTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(issueBookTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(returnBookTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewRecordsTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewIssuedBooksTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(defaulterListTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logoutTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        bgPanel.add(tabBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 360, 800));

        homePanel.setBackground(new java.awt.Color(51, 102, 0));
        homePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        issueBookRoundPanelContainer.setBackground(new java.awt.Color(102, 0, 204));
        issueBookRoundPanelContainer.setRoundBottomLeft(15);
        issueBookRoundPanelContainer.setRoundBottomRight(15);
        issueBookRoundPanelContainer.setRoundTopLeft(15);
        issueBookRoundPanelContainer.setRoundTopRight(15);

        issueBookHeader.setFont(new java.awt.Font("Monotype Corsiva", 1, 60)); // NOI18N
        issueBookHeader.setForeground(new java.awt.Color(255, 255, 0));
        issueBookHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        issueBookHeader.setText("Issue Book");

        issueBookHeaderLine.setBackground(new java.awt.Color(255, 255, 0));

        javax.swing.GroupLayout issueBookHeaderLineLayout = new javax.swing.GroupLayout(issueBookHeaderLine);
        issueBookHeaderLine.setLayout(issueBookHeaderLineLayout);
        issueBookHeaderLineLayout.setHorizontalGroup(
            issueBookHeaderLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 373, Short.MAX_VALUE)
        );
        issueBookHeaderLineLayout.setVerticalGroup(
            issueBookHeaderLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        bookIdLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        bookIdLabel.setForeground(new java.awt.Color(255, 255, 0));
        bookIdLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bookIdLabel.setText("Book ID");

        bookIdIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        bookIdIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bookIdIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/issueBookIcons/key (1).png"))); // NOI18N

        borrowerNameIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        borrowerNameIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        borrowerNameIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/issueBookIcons/id-badge (1).png"))); // NOI18N

        bookIdTxtField.setBackground(new java.awt.Color(102, 0, 204));
        bookIdTxtField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 0)));
        bookIdTxtField.setForeground(new java.awt.Color(255, 255, 0));
        bookIdTxtField.setFocusCycleRoot(true);
        bookIdTxtField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        bookIdTxtField.setPhColor(new java.awt.Color(255, 255, 255));
        bookIdTxtField.setPlaceholder("Book ID");
        bookIdTxtField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                bookIdTxtFieldFocusLost(evt);
            }
        });
        bookIdTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookIdTxtFieldActionPerformed(evt);
            }
        });

        borrowerNameLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        borrowerNameLabel.setForeground(new java.awt.Color(255, 255, 0));
        borrowerNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        borrowerNameLabel.setText("Name of the Borrower");

        borrowerNameTxtField.setBackground(new java.awt.Color(102, 0, 204));
        borrowerNameTxtField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 0)));
        borrowerNameTxtField.setForeground(new java.awt.Color(255, 255, 0));
        borrowerNameTxtField.setFocusCycleRoot(true);
        borrowerNameTxtField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        borrowerNameTxtField.setPhColor(new java.awt.Color(255, 255, 255));
        borrowerNameTxtField.setPlaceholder("Name of the Borrower");
        borrowerNameTxtField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                borrowerNameTxtFieldFocusLost(evt);
            }
        });
        borrowerNameTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrowerNameTxtFieldActionPerformed(evt);
            }
        });

        sNoLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        sNoLabel.setForeground(new java.awt.Color(255, 255, 0));
        sNoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sNoLabel.setText("Student No.");

        studentNumberTxtField.setBackground(new java.awt.Color(102, 0, 204));
        studentNumberTxtField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 0)));
        studentNumberTxtField.setForeground(new java.awt.Color(255, 255, 0));
        studentNumberTxtField.setFocusCycleRoot(true);
        studentNumberTxtField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        studentNumberTxtField.setPhColor(new java.awt.Color(255, 255, 255));
        studentNumberTxtField.setPlaceholder("Borrower's student number");
        studentNumberTxtField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                studentNumberTxtFieldFocusLost(evt);
            }
        });
        studentNumberTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentNumberTxtFieldActionPerformed(evt);
            }
        });

        sNoicon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        sNoicon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sNoicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/issueBookIcons/id-badge (1).png"))); // NOI18N

        contactIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        contactIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        contactIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/issueBookIcons/id-badge (1).png"))); // NOI18N

        contactTxtField.setBackground(new java.awt.Color(102, 0, 204));
        contactTxtField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 0)));
        contactTxtField.setForeground(new java.awt.Color(255, 255, 0));
        contactTxtField.setFocusCycleRoot(true);
        contactTxtField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        contactTxtField.setPhColor(new java.awt.Color(255, 255, 255));
        contactTxtField.setPlaceholder("Borrower's contact number/email");
        contactTxtField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                contactTxtFieldFocusLost(evt);
            }
        });
        contactTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactTxtFieldActionPerformed(evt);
            }
        });

        contactLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        contactLabel.setForeground(new java.awt.Color(255, 255, 0));
        contactLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        contactLabel.setText("Contact");

        issueDIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        issueDIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        issueDIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/issueBookIcons/id-badge (1).png"))); // NOI18N

        issueDLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        issueDLabel.setForeground(new java.awt.Color(255, 255, 0));
        issueDLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        issueDLabel.setText("Issue Date");

        dueDateLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        dueDateLabel.setForeground(new java.awt.Color(255, 255, 0));
        dueDateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dueDateLabel.setText("Due Date");

        dueDateIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        dueDateIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dueDateIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/issueBookIcons/id-badge (1).png"))); // NOI18N

        remarksTxtField.setBackground(new java.awt.Color(102, 0, 204));
        remarksTxtField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 0)));
        remarksTxtField.setForeground(new java.awt.Color(255, 255, 0));
        remarksTxtField.setFocusCycleRoot(true);
        remarksTxtField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        remarksTxtField.setPhColor(new java.awt.Color(255, 255, 255));
        remarksTxtField.setPlaceholder("Remarks");
        remarksTxtField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                remarksTxtFieldFocusLost(evt);
            }
        });
        remarksTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remarksTxtFieldActionPerformed(evt);
            }
        });

        remarksLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        remarksLabel.setForeground(new java.awt.Color(255, 255, 0));
        remarksLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        remarksLabel.setText("Remarks");

        remarksIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        remarksIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        remarksIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/issueBookIcons/id-badge (1).png"))); // NOI18N

        bookDetailsRoundPanelContainer.setBackground(new java.awt.Color(255, 255, 0));
        bookDetailsRoundPanelContainer.setRoundBottomLeft(20);
        bookDetailsRoundPanelContainer.setRoundBottomRight(20);
        bookDetailsRoundPanelContainer.setRoundTopLeft(20);
        bookDetailsRoundPanelContainer.setRoundTopRight(20);

        bookDetailsHeader.setFont(new java.awt.Font("Monotype Corsiva", 1, 60)); // NOI18N
        bookDetailsHeader.setForeground(new java.awt.Color(102, 0, 204));
        bookDetailsHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bookDetailsHeader.setText("Book  Details");

        bookTitleLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        bookTitleLabel.setForeground(new java.awt.Color(102, 0, 204));
        bookTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bookTitleLabel.setText("Book Title");

        bookTitleIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        bookTitleIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bookTitleIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/issueBookIcons/search-alt (3).png"))); // NOI18N

        bookTitleTxtField.setEditable(false);
        bookTitleTxtField.setBackground(new java.awt.Color(255, 255, 0));
        bookTitleTxtField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(102, 0, 204)));
        bookTitleTxtField.setForeground(new java.awt.Color(255, 255, 0));
        bookTitleTxtField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        bookTitleTxtField.setEnabled(false);
        bookTitleTxtField.setFocusCycleRoot(true);
        bookTitleTxtField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        bookTitleTxtField.setPlaceholder("Book's Title");
        bookTitleTxtField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                bookTitleTxtFieldFocusLost(evt);
            }
        });
        bookTitleTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookTitleTxtFieldActionPerformed(evt);
            }
        });

        bookCodeTxtField.setEditable(false);
        bookCodeTxtField.setBackground(new java.awt.Color(255, 255, 0));
        bookCodeTxtField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(102, 0, 204)));
        bookCodeTxtField.setForeground(new java.awt.Color(255, 255, 0));
        bookCodeTxtField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        bookCodeTxtField.setEnabled(false);
        bookCodeTxtField.setFocusCycleRoot(true);
        bookCodeTxtField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        bookCodeTxtField.setPlaceholder("Book's Code");
        bookCodeTxtField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                bookCodeTxtFieldFocusLost(evt);
            }
        });
        bookCodeTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookCodeTxtFieldActionPerformed(evt);
            }
        });

        bookCodeIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        bookCodeIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bookCodeIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/issueBookIcons/search-alt (3).png"))); // NOI18N

        bookCodeLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        bookCodeLabel.setForeground(new java.awt.Color(102, 0, 204));
        bookCodeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bookCodeLabel.setText("Book Code");

        stockTxtField.setEditable(false);
        stockTxtField.setBackground(new java.awt.Color(255, 255, 0));
        stockTxtField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(102, 0, 204)));
        stockTxtField.setForeground(new java.awt.Color(255, 255, 0));
        stockTxtField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        stockTxtField.setToolTipText("");
        stockTxtField.setEnabled(false);
        stockTxtField.setFocusCycleRoot(true);
        stockTxtField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        stockTxtField.setPlaceholder("Book's Stock");
        stockTxtField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                stockTxtFieldFocusLost(evt);
            }
        });
        stockTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockTxtFieldActionPerformed(evt);
            }
        });

        stockIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        stockIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stockIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/issueBookIcons/search-alt (3).png"))); // NOI18N

        stockLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        stockLabel.setForeground(new java.awt.Color(102, 0, 204));
        stockLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stockLabel.setText("Stock");

        categoryTxtField.setEditable(false);
        categoryTxtField.setBackground(new java.awt.Color(255, 255, 0));
        categoryTxtField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(102, 0, 204)));
        categoryTxtField.setForeground(new java.awt.Color(255, 255, 0));
        categoryTxtField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        categoryTxtField.setEnabled(false);
        categoryTxtField.setFocusCycleRoot(true);
        categoryTxtField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        categoryTxtField.setPlaceholder("Book's Category");
        categoryTxtField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                categoryTxtFieldFocusLost(evt);
            }
        });
        categoryTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryTxtFieldActionPerformed(evt);
            }
        });

        categoryIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        categoryIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        categoryIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/issueBookIcons/search-alt (3).png"))); // NOI18N

        categoryLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        categoryLabel.setForeground(new java.awt.Color(102, 0, 204));
        categoryLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        categoryLabel.setText("Category");

        issueBook.setBackground(new java.awt.Color(102, 0, 204));
        issueBook.setText("issue book");
        issueBook.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        issueBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                issueBookActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bookDetailsRoundPanelContainerLayout = new javax.swing.GroupLayout(bookDetailsRoundPanelContainer);
        bookDetailsRoundPanelContainer.setLayout(bookDetailsRoundPanelContainerLayout);
        bookDetailsRoundPanelContainerLayout.setHorizontalGroup(
            bookDetailsRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bookDetailsRoundPanelContainerLayout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addGroup(bookDetailsRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(bookDetailsRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, bookDetailsRoundPanelContainerLayout.createSequentialGroup()
                            .addComponent(bookTitleIcon)
                            .addGap(18, 18, 18)
                            .addComponent(bookTitleTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, bookDetailsRoundPanelContainerLayout.createSequentialGroup()
                            .addComponent(bookCodeIcon)
                            .addGap(18, 18, 18)
                            .addComponent(bookCodeTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, bookDetailsRoundPanelContainerLayout.createSequentialGroup()
                            .addComponent(stockIcon)
                            .addGap(18, 18, 18)
                            .addComponent(stockTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, bookDetailsRoundPanelContainerLayout.createSequentialGroup()
                            .addComponent(categoryIcon)
                            .addGap(18, 18, 18)
                            .addComponent(categoryTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, bookDetailsRoundPanelContainerLayout.createSequentialGroup()
                            .addGap(50, 50, 50)
                            .addGroup(bookDetailsRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(bookTitleLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(bookCodeLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(stockLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(categoryLabel, javax.swing.GroupLayout.Alignment.LEADING))))
                    .addComponent(bookDetailsHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69))
            .addGroup(bookDetailsRoundPanelContainerLayout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addComponent(issueBook, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bookDetailsRoundPanelContainerLayout.setVerticalGroup(
            bookDetailsRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookDetailsRoundPanelContainerLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(bookDetailsHeader)
                .addGap(30, 30, 30)
                .addComponent(bookTitleLabel)
                .addGroup(bookDetailsRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bookDetailsRoundPanelContainerLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(bookTitleIcon))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bookDetailsRoundPanelContainerLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(bookTitleTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addComponent(bookCodeLabel)
                .addGroup(bookDetailsRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bookDetailsRoundPanelContainerLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(bookCodeIcon))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bookDetailsRoundPanelContainerLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(bookCodeTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addComponent(stockLabel)
                .addGroup(bookDetailsRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bookDetailsRoundPanelContainerLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(stockIcon))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bookDetailsRoundPanelContainerLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(stockTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addComponent(categoryLabel)
                .addGroup(bookDetailsRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bookDetailsRoundPanelContainerLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(categoryIcon))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bookDetailsRoundPanelContainerLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(categoryTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(issueBook, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );

        issueDateChooser.setBackground(new java.awt.Color(102, 0, 204));
        issueDateChooser.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 0)));
        issueDateChooser.setDateFormatString("yyyy-MM-dd");
        issueDateChooser.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N

        dueDateChooser.setBackground(new java.awt.Color(102, 0, 204));
        dueDateChooser.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 0)));
        dueDateChooser.setDateFormatString("yyyy-MM-dd");
        dueDateChooser.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N

        javax.swing.GroupLayout issueBookRoundPanelContainerLayout = new javax.swing.GroupLayout(issueBookRoundPanelContainer);
        issueBookRoundPanelContainer.setLayout(issueBookRoundPanelContainerLayout);
        issueBookRoundPanelContainerLayout.setHorizontalGroup(
            issueBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(issueBookRoundPanelContainerLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(issueBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(issueBookRoundPanelContainerLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(dueDateIcon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(issueBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dueDateLabel)
                            .addComponent(dueDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(issueBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(issueBookRoundPanelContainerLayout.createSequentialGroup()
                            .addComponent(sNoicon)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(issueBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(sNoLabel)
                                .addComponent(studentNumberTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(issueBookRoundPanelContainerLayout.createSequentialGroup()
                            .addComponent(borrowerNameIcon)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(issueBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(borrowerNameLabel)
                                .addComponent(borrowerNameTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(issueBookRoundPanelContainerLayout.createSequentialGroup()
                            .addComponent(contactIcon)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(issueBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(contactLabel)
                                .addComponent(contactTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(issueBookRoundPanelContainerLayout.createSequentialGroup()
                            .addComponent(issueDIcon)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(issueBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(issueBookRoundPanelContainerLayout.createSequentialGroup()
                                    .addComponent(issueDLabel)
                                    .addGap(267, 267, 267))
                                .addComponent(issueDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(issueBookRoundPanelContainerLayout.createSequentialGroup()
                            .addComponent(remarksIcon)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(issueBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(remarksLabel)
                                .addComponent(remarksTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(issueBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(issueBookRoundPanelContainerLayout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(bookIdLabel))
                            .addGroup(issueBookRoundPanelContainerLayout.createSequentialGroup()
                                .addComponent(bookIdIcon)
                                .addGap(18, 18, 18)
                                .addComponent(bookIdTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(issueBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(issueBookHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(issueBookRoundPanelContainerLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(issueBookHeaderLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(bookDetailsRoundPanelContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        issueBookRoundPanelContainerLayout.setVerticalGroup(
            issueBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(issueBookRoundPanelContainerLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(issueBookHeader)
                .addGap(2, 2, 2)
                .addComponent(issueBookHeaderLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookIdLabel)
                .addGap(8, 8, 8)
                .addGroup(issueBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bookIdIcon)
                    .addComponent(bookIdTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(borrowerNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(issueBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(borrowerNameIcon)
                    .addComponent(borrowerNameTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(sNoLabel)
                .addGap(6, 6, 6)
                .addGroup(issueBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sNoicon)
                    .addComponent(studentNumberTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(contactLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(issueBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contactIcon)
                    .addComponent(contactTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(issueDLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(issueBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(issueDIcon)
                    .addComponent(issueDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(dueDateLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(issueBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dueDateIcon)
                    .addComponent(dueDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(remarksLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(issueBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(remarksIcon)
                    .addComponent(remarksTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, issueBookRoundPanelContainerLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(bookDetailsRoundPanelContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        homePanel.add(issueBookRoundPanelContainer, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 1100, 740));

        bgPanel.add(homePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 70, 1180, 800));

        getContentPane().add(bgPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1540, 870));
    }// </editor-fold>//GEN-END:initComponents
    
    private void closeIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeIconMouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeIconMouseClicked

    private void homeTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeTabMouseClicked
        HomePage h = new HomePage(uName);
        h.setVisible(true);
        dispose();
    }//GEN-LAST:event_homeTabMouseClicked

    private void homeTabMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeTabMouseEntered
        homeTab.setBackground(mouseEnterColor);
    }//GEN-LAST:event_homeTabMouseEntered

    private void homeTabMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeTabMouseExited
        homeTab.setBackground(mouseExitColor);
    }//GEN-LAST:event_homeTabMouseExited

    private void computerTabMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_computerTabMouseEntered
        computerTab.setBackground(mouseEnterColor);
    }//GEN-LAST:event_computerTabMouseEntered

    private void computerTabMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_computerTabMouseExited
        computerTab.setBackground(mouseExitColor);
    }//GEN-LAST:event_computerTabMouseExited

    private void computerTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_computerTabMouseClicked
        ComputerLogFrame com = new ComputerLogFrame(uName);
        com.setVisible(true);
        dispose();
    }//GEN-LAST:event_computerTabMouseClicked

    private void manageBooksTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageBooksTabMouseClicked
        ManageBooksFrame m = new ManageBooksFrame(uName);
        m.setVisible(true);
        dispose();
    }//GEN-LAST:event_manageBooksTabMouseClicked

    private void manageBooksTabMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageBooksTabMouseEntered
        manageBooksTab.setBackground(mouseEnterColor);
    }//GEN-LAST:event_manageBooksTabMouseEntered

    private void manageBooksTabMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageBooksTabMouseExited
        manageBooksTab.setBackground(mouseExitColor);
    }//GEN-LAST:event_manageBooksTabMouseExited

    private void bookIdTxtFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bookIdTxtFieldFocusLost
        String input = bookIdTxtField.getText().trim();
    
        if (!input.isEmpty()) {
            try {
                int bookId = Integer.parseInt(input);
                getBookDetails();
            } catch (NumberFormatException e) {
                clearBookDetails();
                JOptionPane.showMessageDialog(this, "Please enter a valid integer!");
            }
        } else {
            clearBookDetails();
        }    
    }//GEN-LAST:event_bookIdTxtFieldFocusLost

    private void bookIdTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookIdTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookIdTxtFieldActionPerformed

    private void bookTitleTxtFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bookTitleTxtFieldFocusLost

    }//GEN-LAST:event_bookTitleTxtFieldFocusLost

    private void bookTitleTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookTitleTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookTitleTxtFieldActionPerformed

    private void borrowerNameTxtFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_borrowerNameTxtFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_borrowerNameTxtFieldFocusLost

    private void borrowerNameTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrowerNameTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_borrowerNameTxtFieldActionPerformed

    private void studentNumberTxtFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_studentNumberTxtFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_studentNumberTxtFieldFocusLost

    private void studentNumberTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentNumberTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_studentNumberTxtFieldActionPerformed

    private void contactTxtFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_contactTxtFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_contactTxtFieldFocusLost

    private void contactTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contactTxtFieldActionPerformed

    private void remarksTxtFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_remarksTxtFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_remarksTxtFieldFocusLost

    private void remarksTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remarksTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_remarksTxtFieldActionPerformed

    private void bookCodeTxtFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bookCodeTxtFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_bookCodeTxtFieldFocusLost

    private void bookCodeTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookCodeTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookCodeTxtFieldActionPerformed

    private void stockTxtFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_stockTxtFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_stockTxtFieldFocusLost

    private void stockTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stockTxtFieldActionPerformed

    private void categoryTxtFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_categoryTxtFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_categoryTxtFieldFocusLost

    private void categoryTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_categoryTxtFieldActionPerformed

    private void issueBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_issueBookActionPerformed
        try {
            int borrowerStudentNo = Integer.parseInt(studentNumberTxtField.getText());

            //check first if the student has overdue books
            if (hasOverdueBooks(borrowerStudentNo)) {
                JOptionPane.showMessageDialog(this, "This student has overdue books and cannot borrow more until they are returned.", "Overdue Books Detected", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!isAlreadyIssued()) {
                if (issueBook()) {
                    JOptionPane.showMessageDialog(this, "Book issued successfully!", "Book Issued", JOptionPane.INFORMATION_MESSAGE);
                    updateBookStocks();
                    clearFields();
                    clearBookDetails(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to issue the book.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "This book is already issued to the student. Duplicate issues are not allowed.", "Duplicate Issue Detected", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid student number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_issueBookActionPerformed

    private void returnBookTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_returnBookTabMouseClicked
        ReturnBookFrame r = new ReturnBookFrame(uName);
        r.setVisible(true);
        dispose();
    }//GEN-LAST:event_returnBookTabMouseClicked

    private void returnBookTabMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_returnBookTabMouseEntered
        returnBookTab.setBackground(mouseEnterColor);
    }//GEN-LAST:event_returnBookTabMouseEntered

    private void returnBookTabMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_returnBookTabMouseExited
        returnBookTab.setBackground(mouseExitColor);
    }//GEN-LAST:event_returnBookTabMouseExited

    private void viewRecordsTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewRecordsTabMouseClicked
        ViewRecordsFrame v = new ViewRecordsFrame(uName);
        v.setVisible(true);
        dispose();
    }//GEN-LAST:event_viewRecordsTabMouseClicked

    private void viewRecordsTabMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewRecordsTabMouseEntered
        viewRecordsTab.setBackground(mouseEnterColor);
    }//GEN-LAST:event_viewRecordsTabMouseEntered

    private void viewRecordsTabMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewRecordsTabMouseExited
        viewRecordsTab.setBackground(mouseExitColor);
    }//GEN-LAST:event_viewRecordsTabMouseExited

    private void viewIssuedBooksTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewIssuedBooksTabMouseClicked
        ViewIssuedBooksFrame v = new ViewIssuedBooksFrame(uName);
        v.setVisible(true);
        dispose();
    }//GEN-LAST:event_viewIssuedBooksTabMouseClicked

    private void viewIssuedBooksTabMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewIssuedBooksTabMouseEntered
        viewIssuedBooksTab.setBackground(mouseEnterColor);
    }//GEN-LAST:event_viewIssuedBooksTabMouseEntered

    private void viewIssuedBooksTabMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewIssuedBooksTabMouseExited
        viewIssuedBooksTab.setBackground(mouseExitColor);
    }//GEN-LAST:event_viewIssuedBooksTabMouseExited

    private void defaulterListTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_defaulterListTabMouseClicked
        DefaulterFrame d = new DefaulterFrame(uName);
        d.setVisible(true);
        dispose();
    }//GEN-LAST:event_defaulterListTabMouseClicked

    private void defaulterListTabMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_defaulterListTabMouseEntered
        defaulterListTab.setBackground(mouseEnterColor);
    }//GEN-LAST:event_defaulterListTabMouseEntered

    private void defaulterListTabMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_defaulterListTabMouseExited
         defaulterListTab.setBackground(mouseExitColor);
    }//GEN-LAST:event_defaulterListTabMouseExited

    private void logoutTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutTabMouseClicked
        logout();
    }//GEN-LAST:event_logoutTabMouseClicked

    private void logoutTabMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutTabMouseEntered
        logoutTab.setBackground(logoutMouseEnterColor);
    }//GEN-LAST:event_logoutTabMouseEntered

    private void logoutTabMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutTabMouseExited
        logoutTab.setBackground(logoutMouseExitColor);
    }//GEN-LAST:event_logoutTabMouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IssueBookFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IssueBookFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IssueBookFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IssueBookFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IssueBookFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel HeaderPanel;
    private javax.swing.JPanel bgPanel;
    private javax.swing.JLabel bookCodeIcon;
    private javax.swing.JLabel bookCodeLabel;
    private app.bolivia.swing.JCTextField bookCodeTxtField;
    private javax.swing.JLabel bookDetailsHeader;
    private RoundedPanel.RoundPanel bookDetailsRoundPanelContainer;
    private javax.swing.JLabel bookIdIcon;
    private javax.swing.JLabel bookIdLabel;
    private app.bolivia.swing.JCTextField bookIdTxtField;
    private javax.swing.JLabel bookTitleIcon;
    private javax.swing.JLabel bookTitleLabel;
    private app.bolivia.swing.JCTextField bookTitleTxtField;
    private javax.swing.JLabel borrowerNameIcon;
    private javax.swing.JLabel borrowerNameLabel;
    private app.bolivia.swing.JCTextField borrowerNameTxtField;
    private javax.swing.JLabel categoryIcon;
    private javax.swing.JLabel categoryLabel;
    private app.bolivia.swing.JCTextField categoryTxtField;
    private javax.swing.JLabel closeIcon;
    private RoundedPanel.RoundPanel computerTab;
    private javax.swing.JLabel computerTabLabel;
    private javax.swing.JLabel contactIcon;
    private javax.swing.JLabel contactLabel;
    private app.bolivia.swing.JCTextField contactTxtField;
    private RoundedPanel.RoundPanel dashboardTab;
    private javax.swing.JLabel dashboardTabLabel;
    private RoundedPanel.RoundPanel defaulterListTab;
    private javax.swing.JLabel defaulterListTabLabel;
    private com.toedter.calendar.JDateChooser dueDateChooser;
    private javax.swing.JLabel dueDateIcon;
    private javax.swing.JLabel dueDateLabel;
    private javax.swing.JLabel featuresLabel;
    private javax.swing.JPanel homePanel;
    private RoundedPanel.RoundPanel homeTab;
    private javax.swing.JLabel homeTabLabel;
    private rojerusan.RSMaterialButtonCircle issueBook;
    private javax.swing.JLabel issueBookHeader;
    private javax.swing.JPanel issueBookHeaderLine;
    private RoundedPanel.RoundPanel issueBookRoundPanelContainer;
    private RoundedPanel.RoundPanel issueBookTab;
    private javax.swing.JLabel issueBookTabLabel;
    private javax.swing.JLabel issueDIcon;
    private javax.swing.JLabel issueDLabel;
    private com.toedter.calendar.JDateChooser issueDateChooser;
    private RoundedPanel.RoundPanel liner;
    private javax.swing.JLabel logo;
    private RoundedPanel.RoundPanel logoutTab;
    private javax.swing.JLabel logoutTabLabel;
    private RoundedPanel.RoundPanel manageBooksTab;
    private javax.swing.JLabel manageBooksTabLabel;
    private javax.swing.JLabel menuIcon;
    private javax.swing.JLabel remarksIcon;
    private javax.swing.JLabel remarksLabel;
    private app.bolivia.swing.JCTextField remarksTxtField;
    private RoundedPanel.RoundPanel returnBookTab;
    private javax.swing.JLabel returnBookTabLabel;
    private javax.swing.JLabel sNoLabel;
    private javax.swing.JLabel sNoicon;
    private javax.swing.JLabel stockIcon;
    private javax.swing.JLabel stockLabel;
    private app.bolivia.swing.JCTextField stockTxtField;
    private app.bolivia.swing.JCTextField studentNumberTxtField;
    private javax.swing.JPanel tabBar;
    private javax.swing.JLabel titleHeader;
    private javax.swing.JLabel userGreetHeader;
    private RoundedPanel.RoundPanel viewIssuedBooksTab;
    private javax.swing.JLabel viewIssuedBooksTabLabel;
    private RoundedPanel.RoundPanel viewRecordsTab;
    private javax.swing.JLabel viewRecordsTabLabel;
    // End of variables declaration//GEN-END:variables
}
