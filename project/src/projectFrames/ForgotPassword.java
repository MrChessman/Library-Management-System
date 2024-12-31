/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package projectFrames;

//import java.awt.Toolkit;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import projectFrames.*;
/**
 *
 * @author Chessman
 */
public class ForgotPassword extends javax.swing.JFrame {

    /**
     * Creates new form SignUpFrame
     */
    public ForgotPassword() {
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgPanel = new javax.swing.JPanel();
        cvsuheader2 = new javax.swing.JLabel();
        cvsuLogoPlaceholder = new javax.swing.JLabel();
        cvsuheader1 = new javax.swing.JLabel();
        cvsuheader3 = new javax.swing.JLabel();
        closeIcon = new javax.swing.JLabel();
        fPanelContainer = new RoundedPanel.RoundPanel();
        fheader = new javax.swing.JLabel();
        username = new app.bolivia.swing.JCTextField();
        userIcon = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        proceed = new rojerusan.RSMaterialButtonCircle();
        fheadermain = new javax.swing.JLabel();
        back = new javax.swing.JLabel();
        recQuestion = new app.bolivia.swing.JCTextField();
        recQicon = new javax.swing.JLabel();
        recQLabel = new javax.swing.JLabel();
        recAnswer = new app.bolivia.swing.JCTextField();
        recAnswericon = new javax.swing.JLabel();
        recanswerlabel = new javax.swing.JLabel();
        search = new javax.swing.JButton();
        newIcon = new javax.swing.JLabel();
        newPassIcon = new javax.swing.JLabel();
        showIcon = new javax.swing.JLabel();
        newPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cavite State University Silang-Campus Library Management System Copyright 2023-2024");
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bgPanel.setBackground(new java.awt.Color(51, 102, 0));

        cvsuheader2.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 36)); // NOI18N
        cvsuheader2.setForeground(new java.awt.Color(255, 255, 255));
        cvsuheader2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cvsuheader2.setText("Library Management System");

        cvsuLogoPlaceholder.setBackground(new java.awt.Color(255, 255, 255));
        cvsuLogoPlaceholder.setForeground(new java.awt.Color(255, 255, 255));
        cvsuLogoPlaceholder.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cvsuLogoPlaceholder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/LogoCvsu_enhanced (1).png"))); // NOI18N

        cvsuheader1.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 36)); // NOI18N
        cvsuheader1.setForeground(new java.awt.Color(255, 255, 255));
        cvsuheader1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cvsuheader1.setText("Cavite State University");

        cvsuheader3.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 36)); // NOI18N
        cvsuheader3.setForeground(new java.awt.Color(255, 255, 255));
        cvsuheader3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cvsuheader3.setText("Silang Campus");

        closeIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        closeIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        closeIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/circle-xmark.png"))); // NOI18N
        closeIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeIconMouseClicked(evt);
            }
        });

        fPanelContainer.setBackground(new java.awt.Color(255, 255, 204));
        fPanelContainer.setRoundBottomLeft(20);
        fPanelContainer.setRoundBottomRight(20);
        fPanelContainer.setRoundTopLeft(20);
        fPanelContainer.setRoundTopRight(20);

        fheader.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        fheader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fheader.setText("Recover your Account");

        username.setBackground(new java.awt.Color(255, 255, 204));
        username.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(51, 102, 0)));
        username.setFocusCycleRoot(true);
        username.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        username.setPlaceholder("Enter your account's username");
        username.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                usernameFocusLost(evt);
            }
        });
        username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameActionPerformed(evt);
            }
        });

        userIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        userIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/id-card-clip-alt.png"))); // NOI18N

        usernameLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        usernameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usernameLabel.setText("Username");

        proceed.setBackground(new java.awt.Color(51, 102, 0));
        proceed.setText("Retrieve your account");
        proceed.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        proceed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proceedActionPerformed(evt);
            }
        });

        fheadermain.setFont(new java.awt.Font("Monotype Corsiva", 1, 60)); // NOI18N
        fheadermain.setForeground(new java.awt.Color(51, 102, 0));
        fheadermain.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fheadermain.setText("Forgot Password");

        back.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        back.setForeground(new java.awt.Color(51, 102, 0));
        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/angle-left (1).png"))); // NOI18N
        back.setText("Return to Login");
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backMouseClicked(evt);
            }
        });

        recQuestion.setEditable(false);
        recQuestion.setBackground(new java.awt.Color(255, 255, 204));
        recQuestion.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(51, 102, 0)));
        recQuestion.setEnabled(false);
        recQuestion.setFocusCycleRoot(true);
        recQuestion.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        recQuestion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                recQuestionFocusLost(evt);
            }
        });
        recQuestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recQuestionActionPerformed(evt);
            }
        });

        recQicon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        recQicon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        recQicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user-question.png"))); // NOI18N

        recQLabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        recQLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        recQLabel.setText("Recovery Question");

        recAnswer.setBackground(new java.awt.Color(255, 255, 204));
        recAnswer.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(51, 102, 0)));
        recAnswer.setFocusCycleRoot(true);
        recAnswer.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        recAnswer.setPlaceholder("Answer");
        recAnswer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                recAnswerFocusLost(evt);
            }
        });
        recAnswer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recAnswerActionPerformed(evt);
            }
        });

        recAnswericon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        recAnswericon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        recAnswericon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/answer-alt.png"))); // NOI18N

        recanswerlabel.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        recanswerlabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        recanswerlabel.setText("Recovery Answer");

        search.setBackground(new java.awt.Color(255, 255, 204));
        search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search.png"))); // NOI18N
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        newIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        newIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/password-lock.png"))); // NOI18N

        newPassIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        newPassIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        newPassIcon.setText("New Password");

        showIcon.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 18)); // NOI18N
        showIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        showIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/eye.png"))); // NOI18N
        showIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                showIconMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                showIconMouseReleased(evt);
            }
        });

        newPassword.setBackground(new java.awt.Color(255, 255, 204));
        newPassword.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 17)); // NOI18N
        newPassword.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(51, 102, 0)));
        newPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                newPasswordMouseReleased(evt);
            }
        });
        newPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newPasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout fPanelContainerLayout = new javax.swing.GroupLayout(fPanelContainer);
        fPanelContainer.setLayout(fPanelContainerLayout);
        fPanelContainerLayout.setHorizontalGroup(
            fPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fPanelContainerLayout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addGroup(fPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fPanelContainerLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(fPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fPanelContainerLayout.createSequentialGroup()
                                .addComponent(fheadermain, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(65, 114, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fPanelContainerLayout.createSequentialGroup()
                                .addComponent(fheader, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(fPanelContainerLayout.createSequentialGroup()
                        .addGroup(fPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(newIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(fPanelContainerLayout.createSequentialGroup()
                                .addComponent(userIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(fPanelContainerLayout.createSequentialGroup()
                                .addComponent(recAnswericon, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(recAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(fPanelContainerLayout.createSequentialGroup()
                                .addComponent(recQicon, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(recQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(fPanelContainerLayout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addGroup(fPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(fPanelContainerLayout.createSequentialGroup()
                                        .addGroup(fPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(newPassIcon)
                                            .addGroup(fPanelContainerLayout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addComponent(newPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(showIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(fPanelContainerLayout.createSequentialGroup()
                                        .addComponent(usernameLabel)
                                        .addGap(333, 333, 333)
                                        .addComponent(search))
                                    .addComponent(recanswerlabel)
                                    .addComponent(recQLabel))))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fPanelContainerLayout.createSequentialGroup()
                .addGroup(fPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(fPanelContainerLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(back)
                        .addGap(82, 82, 82))
                    .addGroup(fPanelContainerLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(proceed, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(182, 182, 182))
        );
        fPanelContainerLayout.setVerticalGroup(
            fPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fPanelContainerLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(fheadermain)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fheader)
                .addGap(60, 60, 60)
                .addGroup(fPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(fPanelContainerLayout.createSequentialGroup()
                        .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(fPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(userIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(fPanelContainerLayout.createSequentialGroup()
                        .addComponent(search)
                        .addGap(9, 9, 9)))
                .addGap(18, 18, 18)
                .addComponent(recQLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(recQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(recQicon, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(recanswerlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(recAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(recAnswericon, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(fPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(fPanelContainerLayout.createSequentialGroup()
                        .addComponent(newPassIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(fPanelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(newIcon, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                            .addComponent(newPassword)))
                    .addGroup(fPanelContainerLayout.createSequentialGroup()
                        .addComponent(showIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(proceed, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(back)
                .addGap(62, 62, 62))
        );

        javax.swing.GroupLayout bgPanelLayout = new javax.swing.GroupLayout(bgPanel);
        bgPanel.setLayout(bgPanelLayout);
        bgPanelLayout.setHorizontalGroup(
            bgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgPanelLayout.createSequentialGroup()
                .addContainerGap(107, Short.MAX_VALUE)
                .addGroup(bgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgPanelLayout.createSequentialGroup()
                        .addComponent(cvsuLogoPlaceholder, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgPanelLayout.createSequentialGroup()
                        .addComponent(cvsuheader3)
                        .addGap(124, 124, 124))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgPanelLayout.createSequentialGroup()
                        .addComponent(cvsuheader1)
                        .addGap(52, 52, 52))
                    .addComponent(cvsuheader2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(135, 135, 135)
                .addComponent(fPanelContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        bgPanelLayout.setVerticalGroup(
            bgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgPanelLayout.createSequentialGroup()
                .addGroup(bgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(closeIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bgPanelLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(fPanelContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bgPanelLayout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(cvsuLogoPlaceholder, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cvsuheader1)
                        .addGap(5, 5, 5)
                        .addComponent(cvsuheader3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cvsuheader2)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        getContentPane().add(bgPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1540, 870));
    }// </editor-fold>//GEN-END:initComponents
    
    //search the users username in the database
    private void proceedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proceedActionPerformed
        String name = username.getText();
        String question = recQuestion.getText();
        String newPass = newPassword.getText();
        String answer = recAnswer.getText();
        if (answer.equals("")){
            JOptionPane.showMessageDialog(this, "Please enter an answer");
        } else if (newPass.equals("")){
            JOptionPane.showMessageDialog(this, "Please enter a new password");
        } else {
            try {
                Connection con = DBConnection.getConnection();
                String sql = "SELECT * FROM users WHERE username = ? AND answer = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, name);
                pst.setString(2, answer);
                ResultSet rs = pst.executeQuery();
                if (rs.next()){
                    pst.executeUpdate("UPDATE users SET password = '" + newPass + "' WHERE username = '" + name + "' AND answer = '" + answer +"'");
                    JOptionPane.showMessageDialog(this, "Your Password is successfully updated!");
                    setVisible(true);
                    new LoginFrame().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Please write the correct answer!");
                    recAnswer.setText("");
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        
    }//GEN-LAST:event_proceedActionPerformed

    private void closeIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeIconMouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeIconMouseClicked

    private void usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameActionPerformed

    private void usernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usernameFocusLost

    }//GEN-LAST:event_usernameFocusLost

    private void backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backMouseClicked
        LoginFrame login = new LoginFrame();
        login.setVisible(true);
        dispose();
    }//GEN-LAST:event_backMouseClicked

    private void recQuestionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_recQuestionFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_recQuestionFocusLost

    private void recQuestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recQuestionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_recQuestionActionPerformed

    private void recAnswerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_recAnswerFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_recAnswerFocusLost

    private void recAnswerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recAnswerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_recAnswerActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
         String name = username.getText();
        if (name.equals("")){
            JOptionPane.showMessageDialog(this, "Please enter a username");
            username.setText("");
            recQuestion.setText("");
        } else {
                try {
                Connection con = DBConnection.getConnection();
                String sql = "SELECT question FROM users WHERE username = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, name);
                ResultSet rs = pst.executeQuery();
                if (rs.next()){
                    JOptionPane.showMessageDialog(this, "Username Found!");
                    recQuestion.setText(rs.getString(1));
                } else {
                    JOptionPane.showMessageDialog(this, "Username doesn't exist!");
                    username.setText("");
                    recQuestion.setText("");
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }    
    }//GEN-LAST:event_searchActionPerformed

    private void showIconMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showIconMousePressed
        newPassword.setEchoChar((char)0);
    }//GEN-LAST:event_showIconMousePressed

    private void newPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newPasswordActionPerformed

    private void newPasswordMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newPasswordMouseReleased
        
    }//GEN-LAST:event_newPasswordMouseReleased

    private void showIconMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showIconMouseReleased
        newPassword.setEchoChar('*');
    }//GEN-LAST:event_showIconMouseReleased

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
            java.util.logging.Logger.getLogger(ForgotPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ForgotPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ForgotPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ForgotPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ForgotPassword().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel back;
    private javax.swing.JPanel bgPanel;
    private javax.swing.JLabel closeIcon;
    private javax.swing.JLabel cvsuLogoPlaceholder;
    private javax.swing.JLabel cvsuheader1;
    private javax.swing.JLabel cvsuheader2;
    private javax.swing.JLabel cvsuheader3;
    private RoundedPanel.RoundPanel fPanelContainer;
    private javax.swing.JLabel fheader;
    private javax.swing.JLabel fheadermain;
    private javax.swing.JLabel newIcon;
    private javax.swing.JLabel newPassIcon;
    private javax.swing.JPasswordField newPassword;
    private rojerusan.RSMaterialButtonCircle proceed;
    private app.bolivia.swing.JCTextField recAnswer;
    private javax.swing.JLabel recAnswericon;
    private javax.swing.JLabel recQLabel;
    private javax.swing.JLabel recQicon;
    private app.bolivia.swing.JCTextField recQuestion;
    private javax.swing.JLabel recanswerlabel;
    private javax.swing.JButton search;
    private javax.swing.JLabel showIcon;
    private javax.swing.JLabel userIcon;
    private app.bolivia.swing.JCTextField username;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}
