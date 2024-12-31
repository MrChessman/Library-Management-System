/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package projectFrames;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import projectFrames.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.table.TableModel;
import java.util.Date;

/**
 *
 * @author Chessman
 */
public class ManageFrame extends javax.swing.JFrame {

    /**
     * Creates new form ManageFrame
     */
    
    //local variables
    String book_Code,book_Title,Category,date_Added;
    int book_ID,Stocks;
    DefaultTableModel model;
    public String uName;
    public ManageFrame() {
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
    
    public ManageFrame(String Name) {
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
        setDetailsToTable();
    }
    
    
    //Set the data from the database into the table
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
    
    //To clear and reinitialize the table
    public void clearTable(){
        DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
        model.setRowCount(0);
    }
    
    //fetch the date from the database that is selected from the table and display it to the form
    private void fetchAndSetDateAdded(String bookID) {

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT dateAdded FROM book_inventory WHERE bookID = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, bookID);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                String dateValue = rs.getString("dateAdded");
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateValue);
                dateAdded.setDate(date);
            } else {
                JOptionPane.showMessageDialog(this, "Date not found for the selected book.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching dateAdded: " + e.getMessage());
        }
    }
    
    

    
    //Add book to the book_inventory
    public boolean addBook(){
        boolean isAdded = false;
        book_Code = bookCode.getText();
        book_Title = bookTitle.getText();
        Category = categoryCombo.getSelectedItem().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date_Added = null;
        
        // Check if fields are empty
        if (book_Code.isEmpty() || book_Title.isEmpty() || Category.isEmpty() || dateAdded.getDate() == null || stocks.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.");
            return false;
        }
            try {
            date_Added = dateFormat.format(dateAdded.getDate());
            } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please select a valid date.");
            return false; 
            }
        
            try {
            Stocks = Integer.parseInt(stocks.getText());
            } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stocks must be a valid number.");
            return false;
            }
            
        // Check if bookCode already exists in the database
        if (checkIfBookCodeExists(book_Code)) {
            JOptionPane.showMessageDialog(this, "The book code already exists.");
            return false;
        }
        
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO book_inventory (bookTitle, bookCode, category, dateAdded, stocks) VALUES(?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, book_Title);
            pst.setString(2, book_Code);
            pst.setString(3, Category);
            pst.setString(4, date_Added);
            pst.setInt(5, Stocks);
            
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
            isAdded = true;
            } else {
            isAdded = false;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return isAdded;
    }
    
    //To Update Books
    public boolean updateBook(){
        boolean isUpdated = false;
        book_ID = Integer.parseInt(bookID.getText());
        book_Code = bookCode.getText();
        book_Title = bookTitle.getText();
        Category = categoryCombo.getSelectedItem().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date_Added = null;
        
        // Check if fields are empty
        if (book_Code.isEmpty() || book_Title.isEmpty() || Category.isEmpty() || dateAdded.getDate() == null || stocks.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.");
            return false;
        }
            try {
            date_Added = dateFormat.format(dateAdded.getDate());
            } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please select a valid date.");
            return false; 
            }
        
            try {
            Stocks = Integer.parseInt(stocks.getText());
            } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stocks must be a valid number.");
            return false;
            }
            
            // Check if the new bookCode already exists (except for the current book)
        if (checkIfBookCodeExists(book_Code) && !isSameBookCode(book_Code)) {
            JOptionPane.showMessageDialog(this, "The book code already exists. Please use a different code.");
            return false;
        }
            
            try {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE book_inventory SET bookTitle = ?, bookCode = ?, category = ?, dateAdded = ?, stocks = ? WHERE bookID = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, book_Title);
            pst.setString(2, book_Code);
            pst.setString(3, Category);
            pst.setString(4, date_Added);
            pst.setInt(5, Stocks);
            pst.setInt(6, book_ID);
            
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
            isUpdated = true;
            } else {
            isUpdated = false;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return isUpdated;
    }
    
    //To Delete books
    public boolean deleteBook(){
        boolean isDeleted = false;
        book_ID = Integer.parseInt(bookID.getText());
        try {
            Connection con = DBConnection.getConnection();
            String sql = "DELETE FROM book_inventory WHERE bookID = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, book_ID);
            
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
            isDeleted = true;
            } else {
            isDeleted = false;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return isDeleted; 
    }
    
    //To reset the form and fields after action
    private void clearFields() {
    bookID.setText(""); 
    bookCode.setText("");
    bookTitle.setText("");
    categoryCombo.setSelectedIndex(0);
    dateAdded.setDate(null);
    stocks.setText("");
    }
    
    //Check Uniqueness of book
    // Check if bookCode already exists in the database
    private boolean checkIfBookCodeExists(String bookCode) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT COUNT(*) FROM book_inventory WHERE bookCode = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, bookCode);
            ResultSet rs = pst.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    return true; 
                }
            } catch (Exception e) {
                e.printStackTrace();
        }
        return false;
    }
    
    // Check if the new bookCode is the same as the current editing book code
    private boolean isSameBookCode(String newBookCode) {
        String currentBookCode = bookCode.getText(); 
        return newBookCode.equals(currentBookCode);
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
        roundPanel1 = new RoundedPanel.RoundPanel();
        usernameLabel = new javax.swing.JLabel();
        userIcon = new javax.swing.JLabel();
        bookTitle = new app.bolivia.swing.JCTextField();
        bookCode = new app.bolivia.swing.JCTextField();
        usernameLabel1 = new javax.swing.JLabel();
        usernameLabel2 = new javax.swing.JLabel();
        usernameLabel3 = new javax.swing.JLabel();
        stocks = new app.bolivia.swing.JCTextField();
        usernameLabel4 = new javax.swing.JLabel();
        categoryCombo = new javax.swing.JComboBox<>();
        dateAdded = new com.toedter.calendar.JDateChooser();
        titleIcon = new javax.swing.JLabel();
        codeIcon = new javax.swing.JLabel();
        categoryIcon = new javax.swing.JLabel();
        dateIcon = new javax.swing.JLabel();
        addBook = new rojerusan.RSMaterialButtonCircle();
        updateBook = new rojerusan.RSMaterialButtonCircle();
        deleteBook = new rojerusan.RSMaterialButtonCircle();
        back = new javax.swing.JLabel();
        manageHeader = new javax.swing.JLabel();
        manageHeaderLine = new javax.swing.JPanel();
        idIcon = new javax.swing.JLabel();
        bookIdLabel = new javax.swing.JLabel();
        bookID = new app.bolivia.swing.JCTextField();
        resetFields = new rojerusan.RSMaterialButtonCircle();
        close = new javax.swing.JLabel();
        manageTable = new javax.swing.JScrollPane();
        booksTable = new rojeru_san.complementos.RSTableMetro();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bgPanel.setBackground(new java.awt.Color(51, 102, 0));
        bgPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        roundPanel1.setBackground(new java.awt.Color(255, 255, 204));
        roundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        usernameLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        usernameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usernameLabel.setText("Book Title");
        roundPanel1.add(usernameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 320, -1, -1));

        userIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        userIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/book-medical.png"))); // NOI18N
        roundPanel1.add(userIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 640, -1, -1));

        bookTitle.setBackground(new java.awt.Color(255, 255, 204));
        bookTitle.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(51, 102, 0)));
        bookTitle.setFocusCycleRoot(true);
        bookTitle.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        bookTitle.setPlaceholder("Enter Book's Title");
        bookTitle.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                bookTitleFocusLost(evt);
            }
        });
        bookTitle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookTitleActionPerformed(evt);
            }
        });
        roundPanel1.add(bookTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 350, 480, 40));

        bookCode.setBackground(new java.awt.Color(255, 255, 204));
        bookCode.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(51, 102, 0)));
        bookCode.setFocusCycleRoot(true);
        bookCode.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        bookCode.setPlaceholder("Enter Book's Code");
        bookCode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                bookCodeFocusLost(evt);
            }
        });
        bookCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookCodeActionPerformed(evt);
            }
        });
        roundPanel1.add(bookCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 480, 40));

        usernameLabel1.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        usernameLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usernameLabel1.setText("Book Code");
        roundPanel1.add(usernameLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 230, -1, -1));

        usernameLabel2.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        usernameLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usernameLabel2.setText("Category");
        roundPanel1.add(usernameLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 410, -1, -1));

        usernameLabel3.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        usernameLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usernameLabel3.setText("Date Added");
        roundPanel1.add(usernameLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 510, -1, -1));

        stocks.setBackground(new java.awt.Color(255, 255, 204));
        stocks.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(51, 102, 0)));
        stocks.setFocusCycleRoot(true);
        stocks.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        stocks.setPlaceholder("Stocks Available");
        stocks.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                stocksFocusLost(evt);
            }
        });
        stocks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stocksActionPerformed(evt);
            }
        });
        roundPanel1.add(stocks, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 640, 480, 40));

        usernameLabel4.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        usernameLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usernameLabel4.setText("Stocks");
        roundPanel1.add(usernameLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 610, -1, -1));

        categoryCombo.setBackground(new java.awt.Color(255, 255, 204));
        categoryCombo.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        categoryCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Stay In", "Take Home", "Not Borrowable", "Thesis", "Encyclopedia", "Thesaurus", "Dictionary" }));
        roundPanel1.add(categoryCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 440, 480, 40));

        dateAdded.setBackground(new java.awt.Color(255, 255, 204));
        dateAdded.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(51, 102, 0)));
        dateAdded.setDateFormatString("yyyy-MM-dd");
        dateAdded.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        roundPanel1.add(dateAdded, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 542, 480, 40));

        titleIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        titleIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/book-medical.png"))); // NOI18N
        roundPanel1.add(titleIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 360, -1, -1));

        codeIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        codeIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        codeIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/book-medical.png"))); // NOI18N
        roundPanel1.add(codeIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, -1, -1));

        categoryIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        categoryIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        categoryIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/book-medical.png"))); // NOI18N
        roundPanel1.add(categoryIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 450, -1, -1));

        dateIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        dateIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dateIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/book-medical.png"))); // NOI18N
        roundPanel1.add(dateIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 550, -1, -1));

        addBook.setBackground(new java.awt.Color(51, 102, 0));
        addBook.setText("Add Book");
        addBook.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        addBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBookActionPerformed(evt);
            }
        });
        roundPanel1.add(addBook, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 730, 150, 90));

        updateBook.setBackground(new java.awt.Color(51, 102, 0));
        updateBook.setText("Update book");
        updateBook.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        updateBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBookActionPerformed(evt);
            }
        });
        roundPanel1.add(updateBook, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 730, 170, 90));

        deleteBook.setBackground(new java.awt.Color(51, 102, 0));
        deleteBook.setText("Delete book");
        deleteBook.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        deleteBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBookActionPerformed(evt);
            }
        });
        roundPanel1.add(deleteBook, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 730, 160, 90));

        back.setBackground(new java.awt.Color(255, 255, 204));
        back.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        back.setForeground(new java.awt.Color(51, 102, 0));
        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/angle-double-small-left (1).png"))); // NOI18N
        back.setText("Back");
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backMouseClicked(evt);
            }
        });
        roundPanel1.add(back, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 80, 40));

        manageHeader.setFont(new java.awt.Font("Monotype Corsiva", 1, 60)); // NOI18N
        manageHeader.setForeground(new java.awt.Color(51, 102, 0));
        manageHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        manageHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/book-circle-arrow-right (1).png"))); // NOI18N
        manageHeader.setText("  Manage Books");
        roundPanel1.add(manageHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 460, -1));

        manageHeaderLine.setBackground(new java.awt.Color(51, 102, 0));

        javax.swing.GroupLayout manageHeaderLineLayout = new javax.swing.GroupLayout(manageHeaderLine);
        manageHeaderLine.setLayout(manageHeaderLineLayout);
        manageHeaderLineLayout.setHorizontalGroup(
            manageHeaderLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 430, Short.MAX_VALUE)
        );
        manageHeaderLineLayout.setVerticalGroup(
            manageHeaderLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        roundPanel1.add(manageHeaderLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 430, 5));

        idIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        idIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        idIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/key.png"))); // NOI18N
        roundPanel1.add(idIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, -1, -1));

        bookIdLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        bookIdLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bookIdLabel.setText("Book ID");
        roundPanel1.add(bookIdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, -1, -1));

        bookID.setEditable(false);
        bookID.setBackground(new java.awt.Color(255, 255, 204));
        bookID.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(51, 102, 0)));
        bookID.setEnabled(false);
        bookID.setFocusCycleRoot(true);
        bookID.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        bookID.setPlaceholder("Book ID");
        bookID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                bookIDFocusLost(evt);
            }
        });
        bookID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookIDActionPerformed(evt);
            }
        });
        roundPanel1.add(bookID, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, 480, 40));

        resetFields.setBackground(new java.awt.Color(51, 102, 0));
        resetFields.setText("Reset");
        resetFields.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        resetFields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetFieldsActionPerformed(evt);
            }
        });
        roundPanel1.add(resetFields, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 730, 160, 90));

        bgPanel.add(roundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 714, 864));

        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/circle-xmark.png"))); // NOI18N
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });
        bgPanel.add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(1480, 20, -1, -1));

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

        bgPanel.add(manageTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 70, 740, 740));

        getContentPane().add(bgPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1540, 870));
    }// </editor-fold>//GEN-END:initComponents

    private void stocksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stocksActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stocksActionPerformed

    private void stocksFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_stocksFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_stocksFocusLost

    private void bookCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookCodeActionPerformed

    private void bookCodeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bookCodeFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_bookCodeFocusLost

    private void bookTitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookTitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookTitleActionPerformed

    private void bookTitleFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bookTitleFocusLost

    }//GEN-LAST:event_bookTitleFocusLost

    private void deleteBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBookActionPerformed
        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this book?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION && deleteBook()) {
            JOptionPane.showMessageDialog(this, "Book successfully deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearTable();
            setDetailsToTable();
            clearFields();
        }

    }//GEN-LAST:event_deleteBookActionPerformed

    private void addBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBookActionPerformed
        if(addBook() == true){
            JOptionPane.showMessageDialog(this, "Book added successfully!");
            clearTable();
            setDetailsToTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add the book.");
        }
    }//GEN-LAST:event_addBookActionPerformed

    private void updateBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBookActionPerformed
        if(updateBook() == true){
            JOptionPane.showMessageDialog(this, "Book updated successfully!");
            clearTable();
            setDetailsToTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update the book.");
        }
    }//GEN-LAST:event_updateBookActionPerformed

    private void backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backMouseClicked
        ManageBooksFrame m = new ManageBooksFrame(uName);
        m.setVisible(true);
        dispose();
    }//GEN-LAST:event_backMouseClicked

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeMouseClicked

    private void booksTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_booksTableMouseClicked
        int rowNo = booksTable.getSelectedRow();
        TableModel model = booksTable.getModel();
        bookID.setText(model.getValueAt(rowNo, 0).toString());
        bookCode.setText(model.getValueAt(rowNo, 1).toString());
        bookTitle.setText(model.getValueAt(rowNo, 2).toString());
        String categoryValue = model.getValueAt(rowNo, 3).toString();
        categoryCombo.setSelectedItem(categoryValue);
        stocks.setText(model.getValueAt(rowNo, 4).toString()); 
        String bookIdValue = model.getValueAt(rowNo, 0).toString(); 
        fetchAndSetDateAdded(bookIdValue);
    }//GEN-LAST:event_booksTableMouseClicked

    private void bookIDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bookIDFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_bookIDFocusLost

    private void bookIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookIDActionPerformed

    private void resetFieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetFieldsActionPerformed
        clearFields();
    }//GEN-LAST:event_resetFieldsActionPerformed

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
            java.util.logging.Logger.getLogger(ManageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ManageFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonCircle addBook;
    private javax.swing.JLabel back;
    private javax.swing.JPanel bgPanel;
    private app.bolivia.swing.JCTextField bookCode;
    private app.bolivia.swing.JCTextField bookID;
    private javax.swing.JLabel bookIdLabel;
    private app.bolivia.swing.JCTextField bookTitle;
    private rojeru_san.complementos.RSTableMetro booksTable;
    private javax.swing.JComboBox<String> categoryCombo;
    private javax.swing.JLabel categoryIcon;
    private javax.swing.JLabel close;
    private javax.swing.JLabel codeIcon;
    private com.toedter.calendar.JDateChooser dateAdded;
    private javax.swing.JLabel dateIcon;
    private rojerusan.RSMaterialButtonCircle deleteBook;
    private javax.swing.JLabel idIcon;
    private javax.swing.JLabel manageHeader;
    private javax.swing.JPanel manageHeaderLine;
    private javax.swing.JScrollPane manageTable;
    private rojerusan.RSMaterialButtonCircle resetFields;
    private RoundedPanel.RoundPanel roundPanel1;
    private app.bolivia.swing.JCTextField stocks;
    private javax.swing.JLabel titleIcon;
    private rojerusan.RSMaterialButtonCircle updateBook;
    private javax.swing.JLabel userIcon;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JLabel usernameLabel1;
    private javax.swing.JLabel usernameLabel2;
    private javax.swing.JLabel usernameLabel3;
    private javax.swing.JLabel usernameLabel4;
    // End of variables declaration//GEN-END:variables
}
