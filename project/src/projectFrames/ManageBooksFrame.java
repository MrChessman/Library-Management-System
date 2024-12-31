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
import javax.swing.JOptionPane;
/**
 *
 * @author Chessman
 */
public class ManageBooksFrame extends javax.swing.JFrame {

    /**
     * Creates new form SignUpFrame
     */
    //Local variables
    public String uName;
    Color mouseEnterColor = new Color(102,102,0);
    Color mouseExitColor = new Color(153,153,0);
    Color logoutMouseEnterColor = new Color(255,0,0);
    Color logoutMouseExitColor = new Color(255,255,204);
    DefaultTableModel model;
    public ManageBooksFrame() {
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
        setDetailsToTable();
    }   

    public ManageBooksFrame(String Name) {
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
        setDetailsToTable();
    }
    
    //to initalize the table
    public void setDetailsToTable(){
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM book_inventory";
            Statement st = con.createStatement();           
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()){
                String bookID = rs.getString("bookID");
                String bookTitle = rs.getString("bookTitle");
                String bookCode = rs.getString("bookCode");
                String category = rs.getString("category");
                String addDate = rs.getString("dateAdded");
                int stocks = rs.getInt("stocks");
                Object[] obj = {bookID, bookCode, bookTitle, category, stocks};
                model = (DefaultTableModel)booksTable.getModel();
                model.addRow(obj);
            } 

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    //to filter table according to book title
    private void filterBooksTable(){
        try {
            String bookTitle = searchTxtField.getText().trim();

            StringBuilder sql = new StringBuilder("SELECT bookId, bookCode, bookTitle, category, stocks FROM book_inventory WHERE 1=1");

            if (!bookTitle.isEmpty()) {
                sql.append(" AND bookTitle LIKE ?");
            }

            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql.toString());

            int paramIndex = 1;
            if (!bookTitle.isEmpty()) {
                ps.setString(paramIndex++, "%" + bookTitle + "%");
            }

            ResultSet rs = ps.executeQuery();
            model = (DefaultTableModel) booksTable.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String bookId = rs.getString("bookId");
                String bookCode = rs.getString("bookCode");
                String bookTitleResult = rs.getString("bookTitle");
                String category = rs.getString("category");
                int stocks = rs.getInt("stocks");
                Object[] obj = {bookId, bookCode, bookTitleResult, category, stocks};
                model.addRow(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        manageBookButton = new rojerusan.RSMaterialButtonCircle();
        inventoryHeader = new javax.swing.JLabel();
        inventoryHeaderLine = new javax.swing.JPanel();
        manageTable = new javax.swing.JScrollPane();
        booksTable = new rojeru_san.complementos.RSTableMetro();
        searchTxtField = new app.bolivia.swing.JCTextField();
        searchfieldIcon = new javax.swing.JLabel();
        searchButton = new javax.swing.JButton();

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

        manageBooksTab.setBackground(new java.awt.Color(51, 102, 0));

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

        manageBookButton.setBackground(new java.awt.Color(153, 153, 0));
        manageBookButton.setBorder(null);
        manageBookButton.setText("Manage Books");
        manageBookButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageBookButtonMouseClicked(evt);
            }
        });
        manageBookButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageBookButtonActionPerformed(evt);
            }
        });
        homePanel.add(manageBookButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 700, 300, 80));

        inventoryHeader.setFont(new java.awt.Font("Monotype Corsiva", 1, 60)); // NOI18N
        inventoryHeader.setForeground(new java.awt.Color(255, 255, 255));
        inventoryHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inventoryHeader.setText("Book  Inventory");
        homePanel.add(inventoryHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 30, 390, -1));

        inventoryHeaderLine.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout inventoryHeaderLineLayout = new javax.swing.GroupLayout(inventoryHeaderLine);
        inventoryHeaderLine.setLayout(inventoryHeaderLineLayout);
        inventoryHeaderLineLayout.setHorizontalGroup(
            inventoryHeaderLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 373, Short.MAX_VALUE)
        );
        inventoryHeaderLineLayout.setVerticalGroup(
            inventoryHeaderLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        homePanel.add(inventoryHeaderLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, -1, 5));

        booksTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "BookID", "Book Code", "Book Title", "Category", "Stocks"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        booksTable.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        booksTable.setColorSelBackgound(new java.awt.Color(51, 102, 0));
        booksTable.setFont(new java.awt.Font("Yu Gothic Light", 0, 25)); // NOI18N
        booksTable.setFuenteFilas(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        booksTable.setFuenteFilasSelect(new java.awt.Font("Yu Gothic UI", 1, 20)); // NOI18N
        booksTable.setFuenteHead(new java.awt.Font("Yu Gothic UI Semibold", 1, 20)); // NOI18N
        booksTable.setRowHeight(30);
        booksTable.getTableHeader().setReorderingAllowed(false);
        booksTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                booksTableMouseClicked(evt);
            }
        });
        manageTable.setViewportView(booksTable);

        homePanel.add(manageTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 1100, 500));

        searchTxtField.setBackground(new java.awt.Color(51, 102, 0));
        searchTxtField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        searchTxtField.setForeground(new java.awt.Color(255, 255, 255));
        searchTxtField.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        searchTxtField.setFocusCycleRoot(true);
        searchTxtField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        searchTxtField.setPlaceholder("Search a Book by its Title");
        searchTxtField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchTxtFieldFocusLost(evt);
            }
        });
        searchTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTxtFieldActionPerformed(evt);
            }
        });
        homePanel.add(searchTxtField, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, 550, 40));

        searchfieldIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search-alt (1).png"))); // NOI18N
        homePanel.add(searchfieldIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, 30, 40));

        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/computerLogIcons/search (1).png"))); // NOI18N
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        homePanel.add(searchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 130, 50, 40));

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

    private void manageBookButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageBookButtonActionPerformed
        ManageFrame add = new ManageFrame(uName);
        add.setVisible(true);
        dispose();
    }//GEN-LAST:event_manageBookButtonActionPerformed

    private void manageBookButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageBookButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_manageBookButtonMouseClicked

    private void booksTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_booksTableMouseClicked

    }//GEN-LAST:event_booksTableMouseClicked

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

    private void searchTxtFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchTxtFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTxtFieldFocusLost

    private void searchTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTxtFieldActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        filterBooksTable();
    }//GEN-LAST:event_searchButtonActionPerformed

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
            java.util.logging.Logger.getLogger(ManageBooksFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageBooksFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageBooksFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageBooksFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageBooksFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel HeaderPanel;
    private javax.swing.JPanel bgPanel;
    private rojeru_san.complementos.RSTableMetro booksTable;
    private javax.swing.JLabel closeIcon;
    private RoundedPanel.RoundPanel computerTab;
    private javax.swing.JLabel computerTabLabel;
    private RoundedPanel.RoundPanel dashboardTab;
    private javax.swing.JLabel dashboardTabLabel;
    private RoundedPanel.RoundPanel defaulterListTab;
    private javax.swing.JLabel defaulterListTabLabel;
    private javax.swing.JLabel featuresLabel;
    private javax.swing.JPanel homePanel;
    private RoundedPanel.RoundPanel homeTab;
    private javax.swing.JLabel homeTabLabel;
    private javax.swing.JLabel inventoryHeader;
    private javax.swing.JPanel inventoryHeaderLine;
    private RoundedPanel.RoundPanel issueBookTab;
    private javax.swing.JLabel issueBookTabLabel;
    private RoundedPanel.RoundPanel liner;
    private javax.swing.JLabel logo;
    private RoundedPanel.RoundPanel logoutTab;
    private javax.swing.JLabel logoutTabLabel;
    private rojerusan.RSMaterialButtonCircle manageBookButton;
    private RoundedPanel.RoundPanel manageBooksTab;
    private javax.swing.JLabel manageBooksTabLabel;
    private javax.swing.JScrollPane manageTable;
    private javax.swing.JLabel menuIcon;
    private RoundedPanel.RoundPanel returnBookTab;
    private javax.swing.JLabel returnBookTabLabel;
    private javax.swing.JButton searchButton;
    private app.bolivia.swing.JCTextField searchTxtField;
    private javax.swing.JLabel searchfieldIcon;
    private javax.swing.JPanel tabBar;
    private javax.swing.JLabel titleHeader;
    private javax.swing.JLabel userGreetHeader;
    private RoundedPanel.RoundPanel viewIssuedBooksTab;
    private javax.swing.JLabel viewIssuedBooksTabLabel;
    private RoundedPanel.RoundPanel viewRecordsTab;
    private javax.swing.JLabel viewRecordsTabLabel;
    // End of variables declaration//GEN-END:variables
}
