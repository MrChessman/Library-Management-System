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

/**
 *
 * @author Chessman
 */
public class ComEditFrame extends javax.swing.JFrame {

    /**
     * Creates new form ManageFrame
     */
    
    //local variables
    String userName, userDepartment, userlogDate;
    int userId;
    DefaultTableModel model;
    public String uName;
    public ComEditFrame() {
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
        setUserDetailsToTable();
    }
    
    public ComEditFrame(String Name) {
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
        setUserDetailsToTable();
    }
    
    //Set the data from the database into the table
    public void setUserDetailsToTable(){
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM computer_users";
            Statement st = con.createStatement();           
            ResultSet rs = st.executeQuery(sql);
      
            while (rs.next()){
                String UserId = rs.getString("userID");
                String Name = rs.getString("name");
                String Department = rs.getString("department");
                String LogDate = rs.getString("logDate");
                Object[] obj = {UserId, Name, Department, LogDate};
                model = (DefaultTableModel)usersTable.getModel();
                model.addRow(obj);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    //fetch the date from the database that is selected from the table and display it to the form
    private void fetchAndSetLogDate(String userID) {

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT logDate FROM computer_users WHERE userID = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, userID);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String dateValue = rs.getString("logDate");
                java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateValue);
                logDateChooser.setDate(date);
            } else {
                JOptionPane.showMessageDialog(this, "Date not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching logDate" + e.getMessage());
        }
    }
    
    //To clear and reinitialize the table
    public void clearTable(){
        DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
        model.setRowCount(0);
    }
    
    //To reset the form and fields
    private void clearFields() {
    userIDField.setText(""); 
    nameField.setText("");
    departmentField.setText("");
    logDateChooser.setDate(null);
    }
    
    //To Add a Record 
    public boolean addRecord(){
        boolean isAdded = false;
        userName = nameField.getText();
        userDepartment = departmentField.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        userlogDate = null;
        
        // Check if fields are empty
        if (userName.isEmpty() || userDepartment.isEmpty() || logDateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.");
            return false;
        }
            try {
            userlogDate = dateFormat.format(logDateChooser.getDate());
            } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please select a valid date.");
            return false; 
            }
        
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO computer_users (name, department, logDate) VALUES(?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, userName);
            pst.setString(2, userDepartment);
            pst.setString(3, userlogDate);
            
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
    
    //To Update a Record
    public boolean updateRecord(){
        boolean isUpdated = false;
        userId = Integer.parseInt(userIDField.getText());
        userName = nameField.getText();
        userDepartment = departmentField.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        userlogDate = null;
        
        // Check if fields are empty
        if (userName.isEmpty() || userDepartment.isEmpty() || logDateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.");
            return false;
        }
            try {
            userlogDate = dateFormat.format(logDateChooser.getDate());
            } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please select a valid date.");
            return false; 
            }
            
            try {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE computer_users SET name = ?, department = ?, logDate = ? WHERE userID = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, userName);
            pst.setString(2, userDepartment);
            pst.setString(3, userlogDate);
            pst.setInt(4, userId);
            
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
    
    //To Delete a Record
    public boolean deleteRecord(){
        boolean isDeleted = false;
        userId = Integer.parseInt(userIDField.getText());
        try {
            Connection con = DBConnection.getConnection();
            String sql = "DELETE FROM computer_users WHERE userID = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, userId);
            
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
        departmentField = new app.bolivia.swing.JCTextField();
        nameField = new app.bolivia.swing.JCTextField();
        usernameLabel1 = new javax.swing.JLabel();
        departmentLabel = new javax.swing.JLabel();
        logDateLabel = new javax.swing.JLabel();
        logDateChooser = new com.toedter.calendar.JDateChooser();
        nameIcon = new javax.swing.JLabel();
        departmentIcon = new javax.swing.JLabel();
        logDateIcon = new javax.swing.JLabel();
        addRecord = new rojerusan.RSMaterialButtonCircle();
        updateRecord = new rojerusan.RSMaterialButtonCircle();
        deleteRecord = new rojerusan.RSMaterialButtonCircle();
        back = new javax.swing.JLabel();
        recordHeader = new javax.swing.JLabel();
        recordHeaderLine = new javax.swing.JPanel();
        userIDIcon = new javax.swing.JLabel();
        userIDLabel = new javax.swing.JLabel();
        userIDField = new app.bolivia.swing.JCTextField();
        resetFields = new rojerusan.RSMaterialButtonCircle();
        close = new javax.swing.JLabel();
        manageTable = new javax.swing.JScrollPane();
        usersTable = new rojeru_san.complementos.RSTableMetro();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bgPanel.setBackground(new java.awt.Color(51, 102, 0));
        bgPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        roundPanel1.setBackground(new java.awt.Color(255, 255, 204));
        roundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        departmentField.setBackground(new java.awt.Color(255, 255, 204));
        departmentField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(51, 102, 0)));
        departmentField.setFocusCycleRoot(true);
        departmentField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        departmentField.setPlaceholder("Enter user's department");
        departmentField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                departmentFieldFocusLost(evt);
            }
        });
        departmentField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                departmentFieldActionPerformed(evt);
            }
        });
        roundPanel1.add(departmentField, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 490, 480, 40));

        nameField.setBackground(new java.awt.Color(255, 255, 204));
        nameField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(51, 102, 0)));
        nameField.setFocusCycleRoot(true);
        nameField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        nameField.setPlaceholder("Enter the user's name");
        nameField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nameFieldFocusLost(evt);
            }
        });
        nameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameFieldActionPerformed(evt);
            }
        });
        roundPanel1.add(nameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, 480, 40));

        usernameLabel1.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        usernameLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usernameLabel1.setText("Name");
        roundPanel1.add(usernameLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 330, -1, -1));

        departmentLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        departmentLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        departmentLabel.setText("Department");
        roundPanel1.add(departmentLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 460, -1, -1));

        logDateLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        logDateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logDateLabel.setText("Log Date");
        roundPanel1.add(logDateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 590, -1, -1));

        logDateChooser.setBackground(new java.awt.Color(255, 255, 204));
        logDateChooser.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(51, 102, 0)));
        logDateChooser.setDateFormatString("yyyy-MM-dd");
        logDateChooser.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        roundPanel1.add(logDateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 620, 480, 40));

        nameIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        nameIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nameIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/computerLogIcons/id-badge.png"))); // NOI18N
        roundPanel1.add(nameIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, -1, -1));

        departmentIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        departmentIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        departmentIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/computerLogIcons/department-structure.png"))); // NOI18N
        roundPanel1.add(departmentIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 490, -1, -1));

        logDateIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        logDateIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logDateIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/computerLogIcons/calendar.png"))); // NOI18N
        roundPanel1.add(logDateIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 630, -1, -1));

        addRecord.setBackground(new java.awt.Color(51, 102, 0));
        addRecord.setText("Add record");
        addRecord.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        addRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRecordActionPerformed(evt);
            }
        });
        roundPanel1.add(addRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 730, 150, 90));

        updateRecord.setBackground(new java.awt.Color(51, 102, 0));
        updateRecord.setText("update record");
        updateRecord.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        updateRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateRecordActionPerformed(evt);
            }
        });
        roundPanel1.add(updateRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 730, 180, 90));

        deleteRecord.setBackground(new java.awt.Color(51, 102, 0));
        deleteRecord.setText("delete record");
        deleteRecord.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        deleteRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteRecordActionPerformed(evt);
            }
        });
        roundPanel1.add(deleteRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 730, 180, 90));

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

        recordHeader.setFont(new java.awt.Font("Monotype Corsiva", 1, 60)); // NOI18N
        recordHeader.setForeground(new java.awt.Color(51, 102, 0));
        recordHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        recordHeader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/computerLogIcons/devices.png"))); // NOI18N
        recordHeader.setText("   Modify Records");
        roundPanel1.add(recordHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 490, -1));

        recordHeaderLine.setBackground(new java.awt.Color(51, 102, 0));

        javax.swing.GroupLayout recordHeaderLineLayout = new javax.swing.GroupLayout(recordHeaderLine);
        recordHeaderLine.setLayout(recordHeaderLineLayout);
        recordHeaderLineLayout.setHorizontalGroup(
            recordHeaderLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 470, Short.MAX_VALUE)
        );
        recordHeaderLineLayout.setVerticalGroup(
            recordHeaderLineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        roundPanel1.add(recordHeaderLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 125, 470, 5));

        userIDIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        userIDIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userIDIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/key.png"))); // NOI18N
        roundPanel1.add(userIDIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, -1, -1));

        userIDLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        userIDLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userIDLabel.setText("UserID");
        roundPanel1.add(userIDLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, -1, -1));

        userIDField.setEditable(false);
        userIDField.setBackground(new java.awt.Color(255, 255, 204));
        userIDField.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(51, 102, 0)));
        userIDField.setEnabled(false);
        userIDField.setFocusCycleRoot(true);
        userIDField.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        userIDField.setPlaceholder("User ID");
        userIDField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                userIDFieldFocusLost(evt);
            }
        });
        userIDField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userIDFieldActionPerformed(evt);
            }
        });
        roundPanel1.add(userIDField, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 230, 480, 40));

        resetFields.setBackground(new java.awt.Color(51, 102, 0));
        resetFields.setText("Reset");
        resetFields.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        resetFields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetFieldsActionPerformed(evt);
            }
        });
        roundPanel1.add(resetFields, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 730, 140, 90));

        bgPanel.add(roundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 714, 864));

        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/circle-xmark.png"))); // NOI18N
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });
        bgPanel.add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(1480, 20, -1, -1));

        usersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "UserID", "Name", "Department", "Log Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        usersTable.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        usersTable.setColorSelBackgound(new java.awt.Color(51, 102, 0));
        usersTable.setFont(new java.awt.Font("Yu Gothic Light", 0, 25)); // NOI18N
        usersTable.setFuenteFilas(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        usersTable.setFuenteFilasSelect(new java.awt.Font("Yu Gothic UI", 1, 20)); // NOI18N
        usersTable.setFuenteHead(new java.awt.Font("Yu Gothic UI Semibold", 1, 20)); // NOI18N
        usersTable.setRowHeight(30);
        usersTable.getTableHeader().setReorderingAllowed(false);
        usersTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usersTableMouseClicked(evt);
            }
        });
        manageTable.setViewportView(usersTable);

        bgPanel.add(manageTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 70, 740, 740));

        getContentPane().add(bgPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1540, 870));
    }// </editor-fold>//GEN-END:initComponents

    private void nameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameFieldActionPerformed

    private void nameFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_nameFieldFocusLost

    private void departmentFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_departmentFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_departmentFieldActionPerformed

    private void departmentFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_departmentFieldFocusLost

    }//GEN-LAST:event_departmentFieldFocusLost

    private void deleteRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteRecordActionPerformed
        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this record?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION && deleteRecord()) {
            JOptionPane.showMessageDialog(this, "Record successfully deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearTable();
            setUserDetailsToTable();
            clearFields();
        }

    }//GEN-LAST:event_deleteRecordActionPerformed

    private void addRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRecordActionPerformed
        if(addRecord() == true){
            JOptionPane.showMessageDialog(this, "Record added successfully!");
            clearTable();
            setUserDetailsToTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add the record.");
        }
    }//GEN-LAST:event_addRecordActionPerformed

    private void updateRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateRecordActionPerformed
        if(updateRecord() == true){
            JOptionPane.showMessageDialog(this, "Record updated successfully!");
            clearTable();
            setUserDetailsToTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update the record.");
        }
    }//GEN-LAST:event_updateRecordActionPerformed

    private void backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backMouseClicked
        ComputerLogFrame m = new ComputerLogFrame(uName);
        m.setVisible(true);
        dispose();
    }//GEN-LAST:event_backMouseClicked

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeMouseClicked

    private void usersTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usersTableMouseClicked
        int rowNo = usersTable.getSelectedRow();
        TableModel model = usersTable.getModel();
        userIDField.setText(model.getValueAt(rowNo, 0).toString());
        nameField.setText(model.getValueAt(rowNo, 1).toString());
        departmentField.setText(model.getValueAt(rowNo, 2).toString());
        String user_ID = model.getValueAt(rowNo, 0).toString(); 
        fetchAndSetLogDate(user_ID);
    }//GEN-LAST:event_usersTableMouseClicked

    private void userIDFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_userIDFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_userIDFieldFocusLost

    private void userIDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userIDFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userIDFieldActionPerformed

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
            java.util.logging.Logger.getLogger(ComEditFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ComEditFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ComEditFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ComEditFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
            @Override
            public void run() {
                new ComEditFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonCircle addRecord;
    private javax.swing.JLabel back;
    private javax.swing.JPanel bgPanel;
    private javax.swing.JLabel close;
    private rojerusan.RSMaterialButtonCircle deleteRecord;
    private app.bolivia.swing.JCTextField departmentField;
    private javax.swing.JLabel departmentIcon;
    private javax.swing.JLabel departmentLabel;
    private com.toedter.calendar.JDateChooser logDateChooser;
    private javax.swing.JLabel logDateIcon;
    private javax.swing.JLabel logDateLabel;
    private javax.swing.JScrollPane manageTable;
    private app.bolivia.swing.JCTextField nameField;
    private javax.swing.JLabel nameIcon;
    private javax.swing.JLabel recordHeader;
    private javax.swing.JPanel recordHeaderLine;
    private rojerusan.RSMaterialButtonCircle resetFields;
    private RoundedPanel.RoundPanel roundPanel1;
    private rojerusan.RSMaterialButtonCircle updateRecord;
    private app.bolivia.swing.JCTextField userIDField;
    private javax.swing.JLabel userIDIcon;
    private javax.swing.JLabel userIDLabel;
    private javax.swing.JLabel usernameLabel1;
    private rojeru_san.complementos.RSTableMetro usersTable;
    // End of variables declaration//GEN-END:variables
}
