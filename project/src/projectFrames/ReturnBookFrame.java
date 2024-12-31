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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
//import org.apache.commons.text.similarity.JaroWinklerSimilarity; //to be used for names
/**
 *
 * @author Chessman
 */
public class ReturnBookFrame extends javax.swing.JFrame {

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
    public ReturnBookFrame() {
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

    public ReturnBookFrame(String Name) {
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
    
    //to update stocks after return
    public void updateBookStocks(){
        int book_id = Integer.parseInt(bookIdTxtField.getText());
        try {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE book_inventory SET stocks = stocks + 1 WHERE bookID = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, book_id);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Book stocks updated!");
            } else {
                JOptionPane.showMessageDialog(this, "Error in updating book stocks.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    //to fetch the issue details in the database and display them to the panel
    public boolean getIssueBookDetails(){
        boolean detailsFound = false;
        int book_id = Integer.parseInt(bookIdTxtField.getText());
        String returner_name = returnerNameTxtField.getText().trim();

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT issueID, bookTitle, studentNo, contact, issueDate, dueDate, remarks FROM issue_book_table WHERE bookID = ? AND name = ? AND status = 'Pending'";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, book_id);
            pst.setString(2, returner_name);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                issueIdTxtField.setText(String.valueOf(rs.getInt("issueID")));
                bookTitleTxtField.setText(rs.getString("bookTitle"));
                studentNumberTxtField.setText(String.valueOf(rs.getInt("studentNo")));
                contactTxtField.setText(rs.getString("contact"));
                issueDateTxtField.setText(rs.getString("issueDate"));
                dueDateTxtField.setText(rs.getString("dueDate"));
                remarksTxtField.setText(rs.getString("remarks"));
                detailsFound = true;
            } else {
                clearFields();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return detailsFound;
    }
    
    //to return a book
    public void returnBook(){
        if (issueIdTxtField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Find the issue details first before returning the book.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return; 
        }

        int issue_id = Integer.parseInt(issueIdTxtField.getText());

        try {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE issue_book_table SET status = 'Returned' WHERE issueID = ? AND status = 'Pending'";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, issue_id);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Book returned successfully!");
                updateBookStocks();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Error in returning the book.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //to clear all fields in the form
    public void clearFields(){
        issueIdTxtField.setText("");
        bookTitleTxtField.setText("");
        studentNumberTxtField.setText("");
        contactTxtField.setText("");
        issueDateTxtField.setText("");
        dueDateTxtField.setText("");
        remarksTxtField.setText("");
        bookIdTxtField.setText("");
        returnerNameTxtField.setText("");
    }


    
    //Similarity Algorithm to be used for names
//    public double calculateSimilarity(String name1, String name2) {
//        JaroWinklerSimilarity similarity = new JaroWinklerSimilarity();
//        return similarity.apply(name1.toLowerCase().trim(), name2.toLowerCase().trim());
//    }
    
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
        returnBookRoundPanelContainer = new RoundedPanel.RoundPanel();
        issueBookDetailsHeader = new javax.swing.JLabel();
        issueBookDetailsHeaderLine = new javax.swing.JPanel();
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
        issuedbookDetailsRoundPanelContainer = new RoundedPanel.RoundPanel();
        returnBookDetailsHeader = new javax.swing.JLabel();
        returnBook = new rojerusan.RSMaterialButtonCircle();
        bookIdTxtField = new app.bolivia.swing.JCTextField();
        bookIdIcon = new javax.swing.JLabel();
        bookIdLabel = new javax.swing.JLabel();
        findDetails = new rojerusan.RSMaterialButtonCircle();
        returnerNameTxtField = new app.bolivia.swing.JCTextField();
        returnerNameLabel = new javax.swing.JLabel();
        returnerFieldIcon = new javax.swing.JLabel();
        issueIdLabel = new javax.swing.JLabel();
        issueIdTxtField = new app.bolivia.swing.JCTextField();
        issueIdIcon = new javax.swing.JLabel();
        bookTitleLabel = new javax.swing.JLabel();
        bookTitleTxtField = new app.bolivia.swing.JCTextField();
        bookTitleIcon = new javax.swing.JLabel();
        issueDateTxtField = new app.bolivia.swing.JCTextField();
        dueDateTxtField = new app.bolivia.swing.JCTextField();

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

        issueBookTab.setBackground(new java.awt.Color(153, 153, 0));
        issueBookTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                issueBookTabMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                issueBookTabMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                issueBookTabMouseExited(evt);
            }
        });

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

        returnBookTab.setBackground(new java.awt.Color(51, 102, 0));

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

        returnBookRoundPanelContainer.setBackground(new java.awt.Color(102, 0, 204));
        returnBookRoundPanelContainer.setRoundBottomLeft(15);
        returnBookRoundPanelContainer.setRoundBottomRight(15);
        returnBookRoundPanelContainer.setRoundTopLeft(15);
        returnBookRoundPanelContainer.setRoundTopRight(15);

        issueBookDetailsHeader.setFont(new java.awt.Font("Monotype Corsiva", 1, 60)); // NOI18N
        issueBookDetailsHeader.setForeground(new java.awt.Color(255, 255, 0));
        issueBookDetailsHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        issueBookDetailsHeader.setText("Issued Book  Details");

        issueBookDetailsHeaderLine.setBackground(new java.awt.Color(255, 255, 0));

        javax.swing.GroupLayout issueBookDetailsHeaderLineLayout = new javax.swing.GroupLayout(issueBookDetailsHeaderLine);
        issueBookDetailsHeaderLine.setLayout(issueBookDetailsHeaderLineLayout);
        issueBookDetailsHeaderLineLayout.setHorizontalGroup(
            issueBookDetailsHeaderLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 525, Short.MAX_VALUE)
        );
        issueBookDetailsHeaderLineLayout.setVerticalGroup(
            issueBookDetailsHeaderLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        sNoLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        sNoLabel.setForeground(new java.awt.Color(255, 255, 0));
        sNoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sNoLabel.setText("Student No.");

        studentNumberTxtField.setEditable(false);
        studentNumberTxtField.setBackground(new java.awt.Color(102, 0, 204));
        studentNumberTxtField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 0)));
        studentNumberTxtField.setForeground(new java.awt.Color(255, 255, 0));
        studentNumberTxtField.setToolTipText("");
        studentNumberTxtField.setFocusCycleRoot(true);
        studentNumberTxtField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        studentNumberTxtField.setPhColor(new java.awt.Color(255, 255, 255));
        studentNumberTxtField.setPlaceholder("Returner's student number");
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

        contactTxtField.setEditable(false);
        contactTxtField.setBackground(new java.awt.Color(102, 0, 204));
        contactTxtField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 0)));
        contactTxtField.setForeground(new java.awt.Color(255, 255, 0));
        contactTxtField.setFocusCycleRoot(true);
        contactTxtField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        contactTxtField.setPhColor(new java.awt.Color(255, 255, 255));
        contactTxtField.setPlaceholder("Returner's contact number/email");
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
        dueDateLabel.setText("Deadline");

        dueDateIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        dueDateIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dueDateIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/issueBookIcons/id-badge (1).png"))); // NOI18N

        remarksTxtField.setEditable(false);
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

        issuedbookDetailsRoundPanelContainer.setBackground(new java.awt.Color(255, 255, 0));
        issuedbookDetailsRoundPanelContainer.setRoundBottomLeft(20);
        issuedbookDetailsRoundPanelContainer.setRoundBottomRight(20);
        issuedbookDetailsRoundPanelContainer.setRoundTopLeft(20);
        issuedbookDetailsRoundPanelContainer.setRoundTopRight(20);

        returnBookDetailsHeader.setFont(new java.awt.Font("Monotype Corsiva", 1, 60)); // NOI18N
        returnBookDetailsHeader.setForeground(new java.awt.Color(102, 0, 204));
        returnBookDetailsHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        returnBookDetailsHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/returnBookicons/book-arrow-right.png"))); // NOI18N
        returnBookDetailsHeader.setText("   Return Book");

        returnBook.setBackground(new java.awt.Color(102, 0, 204));
        returnBook.setText("return book");
        returnBook.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        returnBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnBookActionPerformed(evt);
            }
        });

        bookIdTxtField.setBackground(new java.awt.Color(255, 255, 0));
        bookIdTxtField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(102, 0, 204)));
        bookIdTxtField.setForeground(new java.awt.Color(102, 0, 204));
        bookIdTxtField.setFocusCycleRoot(true);
        bookIdTxtField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        bookIdTxtField.setPlaceholder("Enter the book's ID");
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

        bookIdIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        bookIdIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bookIdIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/issueBookIcons/search-alt (3).png"))); // NOI18N

        bookIdLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        bookIdLabel.setForeground(new java.awt.Color(102, 0, 204));
        bookIdLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bookIdLabel.setText("Book ID");

        findDetails.setBackground(new java.awt.Color(51, 102, 0));
        findDetails.setText("find details");
        findDetails.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        findDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findDetailsActionPerformed(evt);
            }
        });

        returnerNameTxtField.setBackground(new java.awt.Color(255, 255, 0));
        returnerNameTxtField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(102, 0, 204)));
        returnerNameTxtField.setForeground(new java.awt.Color(102, 0, 204));
        returnerNameTxtField.setFocusCycleRoot(true);
        returnerNameTxtField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        returnerNameTxtField.setPlaceholder("Enter the name of the Returner");
        returnerNameTxtField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                returnerNameTxtFieldFocusLost(evt);
            }
        });
        returnerNameTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnerNameTxtFieldActionPerformed(evt);
            }
        });

        returnerNameLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        returnerNameLabel.setForeground(new java.awt.Color(102, 0, 204));
        returnerNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        returnerNameLabel.setText("Name of the Returner");

        returnerFieldIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        returnerFieldIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        returnerFieldIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/issueBookIcons/search-alt (3).png"))); // NOI18N

        javax.swing.GroupLayout issuedbookDetailsRoundPanelContainerLayout = new javax.swing.GroupLayout(issuedbookDetailsRoundPanelContainer);
        issuedbookDetailsRoundPanelContainer.setLayout(issuedbookDetailsRoundPanelContainerLayout);
        issuedbookDetailsRoundPanelContainerLayout.setHorizontalGroup(
            issuedbookDetailsRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, issuedbookDetailsRoundPanelContainerLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(findDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, issuedbookDetailsRoundPanelContainerLayout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(issuedbookDetailsRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, issuedbookDetailsRoundPanelContainerLayout.createSequentialGroup()
                        .addGroup(issuedbookDetailsRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(returnBook, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(returnBookDetailsHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, issuedbookDetailsRoundPanelContainerLayout.createSequentialGroup()
                        .addGroup(issuedbookDetailsRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(issuedbookDetailsRoundPanelContainerLayout.createSequentialGroup()
                                .addComponent(returnerFieldIcon)
                                .addGap(18, 18, 18)
                                .addGroup(issuedbookDetailsRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(returnerNameLabel)
                                    .addComponent(returnerNameTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(issuedbookDetailsRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, issuedbookDetailsRoundPanelContainerLayout.createSequentialGroup()
                                    .addComponent(bookIdIcon)
                                    .addGap(18, 18, 18)
                                    .addComponent(bookIdTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, issuedbookDetailsRoundPanelContainerLayout.createSequentialGroup()
                                    .addGap(50, 50, 50)
                                    .addComponent(bookIdLabel))))
                        .addGap(67, 67, 67))))
        );
        issuedbookDetailsRoundPanelContainerLayout.setVerticalGroup(
            issuedbookDetailsRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(issuedbookDetailsRoundPanelContainerLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(returnBookDetailsHeader)
                .addGap(62, 62, 62)
                .addComponent(bookIdLabel)
                .addGroup(issuedbookDetailsRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(issuedbookDetailsRoundPanelContainerLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(bookIdIcon))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, issuedbookDetailsRoundPanelContainerLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(bookIdTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(62, 62, 62)
                .addComponent(returnerNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(issuedbookDetailsRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(returnerNameTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(returnerFieldIcon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(issuedbookDetailsRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(findDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(returnBook, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
        );

        issueIdLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        issueIdLabel.setForeground(new java.awt.Color(255, 255, 0));
        issueIdLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        issueIdLabel.setText("Issue No.");

        issueIdTxtField.setEditable(false);
        issueIdTxtField.setBackground(new java.awt.Color(102, 0, 204));
        issueIdTxtField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 0)));
        issueIdTxtField.setForeground(new java.awt.Color(255, 255, 0));
        issueIdTxtField.setFocusCycleRoot(true);
        issueIdTxtField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        issueIdTxtField.setPhColor(new java.awt.Color(255, 255, 255));
        issueIdTxtField.setPlaceholder("Issue ID");
        issueIdTxtField.setRequestFocusEnabled(false);
        issueIdTxtField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                issueIdTxtFieldFocusLost(evt);
            }
        });
        issueIdTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                issueIdTxtFieldActionPerformed(evt);
            }
        });

        issueIdIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        issueIdIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        issueIdIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/issueBookIcons/key (1).png"))); // NOI18N

        bookTitleLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        bookTitleLabel.setForeground(new java.awt.Color(255, 255, 0));
        bookTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bookTitleLabel.setText("Book Title");

        bookTitleTxtField.setEditable(false);
        bookTitleTxtField.setBackground(new java.awt.Color(102, 0, 204));
        bookTitleTxtField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 0)));
        bookTitleTxtField.setForeground(new java.awt.Color(255, 255, 0));
        bookTitleTxtField.setToolTipText("");
        bookTitleTxtField.setFocusCycleRoot(true);
        bookTitleTxtField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        bookTitleTxtField.setPhColor(new java.awt.Color(255, 255, 255));
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

        bookTitleIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        bookTitleIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bookTitleIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/issueBookIcons/id-badge (1).png"))); // NOI18N

        issueDateTxtField.setEditable(false);
        issueDateTxtField.setBackground(new java.awt.Color(102, 0, 204));
        issueDateTxtField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 0)));
        issueDateTxtField.setForeground(new java.awt.Color(255, 255, 0));
        issueDateTxtField.setFocusCycleRoot(true);
        issueDateTxtField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        issueDateTxtField.setPhColor(new java.awt.Color(255, 255, 255));
        issueDateTxtField.setPlaceholder("Issue Date");
        issueDateTxtField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                issueDateTxtFieldFocusLost(evt);
            }
        });
        issueDateTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                issueDateTxtFieldActionPerformed(evt);
            }
        });

        dueDateTxtField.setEditable(false);
        dueDateTxtField.setBackground(new java.awt.Color(102, 0, 204));
        dueDateTxtField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 0)));
        dueDateTxtField.setForeground(new java.awt.Color(255, 255, 0));
        dueDateTxtField.setFocusCycleRoot(true);
        dueDateTxtField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        dueDateTxtField.setPhColor(new java.awt.Color(255, 255, 255));
        dueDateTxtField.setPlaceholder("Deadline");
        dueDateTxtField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                dueDateTxtFieldFocusLost(evt);
            }
        });
        dueDateTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dueDateTxtFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout returnBookRoundPanelContainerLayout = new javax.swing.GroupLayout(returnBookRoundPanelContainer);
        returnBookRoundPanelContainer.setLayout(returnBookRoundPanelContainerLayout);
        returnBookRoundPanelContainerLayout.setHorizontalGroup(
            returnBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(returnBookRoundPanelContainerLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(returnBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, returnBookRoundPanelContainerLayout.createSequentialGroup()
                        .addComponent(sNoicon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(returnBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sNoLabel)
                            .addComponent(studentNumberTxtField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, returnBookRoundPanelContainerLayout.createSequentialGroup()
                        .addComponent(issueDIcon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(returnBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(issueDLabel)
                            .addComponent(issueDateTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, returnBookRoundPanelContainerLayout.createSequentialGroup()
                        .addComponent(bookTitleIcon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(returnBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bookTitleLabel)
                            .addComponent(bookTitleTxtField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, returnBookRoundPanelContainerLayout.createSequentialGroup()
                        .addComponent(dueDateIcon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(returnBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dueDateLabel)
                            .addComponent(dueDateTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, returnBookRoundPanelContainerLayout.createSequentialGroup()
                        .addComponent(contactIcon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(returnBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(contactLabel)
                            .addComponent(contactTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, returnBookRoundPanelContainerLayout.createSequentialGroup()
                        .addComponent(issueIdIcon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(returnBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(issueIdLabel)
                            .addComponent(issueIdTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(returnBookRoundPanelContainerLayout.createSequentialGroup()
                        .addComponent(remarksIcon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(returnBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(remarksLabel)
                            .addComponent(remarksTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(issuedbookDetailsRoundPanelContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, returnBookRoundPanelContainerLayout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(returnBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(issueBookDetailsHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(issueBookDetailsHeaderLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(615, 615, 615))
        );
        returnBookRoundPanelContainerLayout.setVerticalGroup(
            returnBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(returnBookRoundPanelContainerLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(issueBookDetailsHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(issueBookDetailsHeaderLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(returnBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(returnBookRoundPanelContainerLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(issueIdLabel)
                        .addGap(8, 8, 8)
                        .addGroup(returnBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(issueIdTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(issueIdIcon))
                        .addGap(12, 12, 12)
                        .addComponent(bookTitleLabel)
                        .addGap(8, 8, 8)
                        .addGroup(returnBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bookTitleTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bookTitleIcon))
                        .addGap(12, 12, 12)
                        .addComponent(sNoLabel)
                        .addGap(8, 8, 8)
                        .addGroup(returnBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(studentNumberTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sNoicon))
                        .addGap(12, 12, 12)
                        .addComponent(contactLabel)
                        .addGap(8, 8, 8)
                        .addGroup(returnBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(contactIcon)
                            .addComponent(contactTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(issueDLabel)
                        .addGap(8, 8, 8)
                        .addGroup(returnBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(issueDIcon)
                            .addComponent(issueDateTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(dueDateLabel)
                        .addGap(8, 8, 8)
                        .addGroup(returnBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dueDateIcon)
                            .addComponent(dueDateTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(remarksLabel)
                        .addGap(8, 8, 8)
                        .addGroup(returnBookRoundPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(remarksIcon)
                            .addComponent(remarksTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(returnBookRoundPanelContainerLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(issuedbookDetailsRoundPanelContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(96, Short.MAX_VALUE))
        );

        homePanel.add(returnBookRoundPanelContainer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1180, 800));

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

    private void issueIdTxtFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_issueIdTxtFieldFocusLost
   
    }//GEN-LAST:event_issueIdTxtFieldFocusLost

    private void issueIdTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_issueIdTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_issueIdTxtFieldActionPerformed

    private void bookTitleTxtFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bookTitleTxtFieldFocusLost

    }//GEN-LAST:event_bookTitleTxtFieldFocusLost

    private void bookTitleTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookTitleTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookTitleTxtFieldActionPerformed

    private void returnerNameTxtFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_returnerNameTxtFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_returnerNameTxtFieldFocusLost

    private void returnerNameTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnerNameTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_returnerNameTxtFieldActionPerformed

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

    private void returnBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnBookActionPerformed
        if (bookIdTxtField.getText().isEmpty() || returnerNameTxtField.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill up all the fields", "Validation Error", JOptionPane.WARNING_MESSAGE);
        clearFields();
        return;
        }
        returnBook();
    }//GEN-LAST:event_returnBookActionPerformed

    private void issueBookTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_issueBookTabMouseClicked
        IssueBookFrame i = new IssueBookFrame(uName);
        i.setVisible(true);
        dispose();
    }//GEN-LAST:event_issueBookTabMouseClicked

    private void issueBookTabMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_issueBookTabMouseEntered
        issueBookTab.setBackground(mouseEnterColor);
    }//GEN-LAST:event_issueBookTabMouseEntered

    private void issueBookTabMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_issueBookTabMouseExited
        issueBookTab.setBackground(mouseExitColor);
    }//GEN-LAST:event_issueBookTabMouseExited

    private void bookIdTxtFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bookIdTxtFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_bookIdTxtFieldFocusLost

    private void bookIdTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookIdTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookIdTxtFieldActionPerformed

    private void findDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findDetailsActionPerformed
        if (bookIdTxtField.getText().isEmpty() || returnerNameTxtField.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill up all the fields", "Validation Error", JOptionPane.WARNING_MESSAGE);
        clearFields();
        return;
        } 
        boolean detailsFound = getIssueBookDetails();
        if (!detailsFound) {
            JOptionPane.showMessageDialog(this, "No matching issue details found.", "Not Found", JOptionPane.WARNING_MESSAGE);
            clearFields();
        }
    }//GEN-LAST:event_findDetailsActionPerformed

    private void issueDateTxtFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_issueDateTxtFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_issueDateTxtFieldFocusLost

    private void issueDateTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_issueDateTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_issueDateTxtFieldActionPerformed

    private void dueDateTxtFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dueDateTxtFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_dueDateTxtFieldFocusLost

    private void dueDateTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dueDateTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dueDateTxtFieldActionPerformed

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
            java.util.logging.Logger.getLogger(ReturnBookFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReturnBookFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReturnBookFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReturnBookFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new ReturnBookFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel HeaderPanel;
    private javax.swing.JPanel bgPanel;
    private javax.swing.JLabel bookIdIcon;
    private javax.swing.JLabel bookIdLabel;
    private app.bolivia.swing.JCTextField bookIdTxtField;
    private javax.swing.JLabel bookTitleIcon;
    private javax.swing.JLabel bookTitleLabel;
    private app.bolivia.swing.JCTextField bookTitleTxtField;
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
    private javax.swing.JLabel dueDateIcon;
    private javax.swing.JLabel dueDateLabel;
    private app.bolivia.swing.JCTextField dueDateTxtField;
    private javax.swing.JLabel featuresLabel;
    private rojerusan.RSMaterialButtonCircle findDetails;
    private javax.swing.JPanel homePanel;
    private RoundedPanel.RoundPanel homeTab;
    private javax.swing.JLabel homeTabLabel;
    private javax.swing.JLabel issueBookDetailsHeader;
    private javax.swing.JPanel issueBookDetailsHeaderLine;
    private RoundedPanel.RoundPanel issueBookTab;
    private javax.swing.JLabel issueBookTabLabel;
    private javax.swing.JLabel issueDIcon;
    private javax.swing.JLabel issueDLabel;
    private app.bolivia.swing.JCTextField issueDateTxtField;
    private javax.swing.JLabel issueIdIcon;
    private javax.swing.JLabel issueIdLabel;
    private app.bolivia.swing.JCTextField issueIdTxtField;
    private RoundedPanel.RoundPanel issuedbookDetailsRoundPanelContainer;
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
    private rojerusan.RSMaterialButtonCircle returnBook;
    private javax.swing.JLabel returnBookDetailsHeader;
    private RoundedPanel.RoundPanel returnBookRoundPanelContainer;
    private RoundedPanel.RoundPanel returnBookTab;
    private javax.swing.JLabel returnBookTabLabel;
    private javax.swing.JLabel returnerFieldIcon;
    private javax.swing.JLabel returnerNameLabel;
    private app.bolivia.swing.JCTextField returnerNameTxtField;
    private javax.swing.JLabel sNoLabel;
    private javax.swing.JLabel sNoicon;
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
