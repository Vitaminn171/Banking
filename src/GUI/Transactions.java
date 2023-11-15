/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import BLL.Controller;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.event.ChangeEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Quoc An
 */
public class Transactions extends javax.swing.JFrame {

    JFrame frame = this;
    Controller controller = new Controller();
    DecimalFormat formatter = new DecimalFormat("###,###,###,###");
    private static boolean changingModel = false;

    public Transactions(JSONObject json) throws ParseException {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        UtilDateModel model = new UtilDateModel();
        UtilDateModel model1 = new UtilDateModel();
        model.setSelected(true);
        model1.setSelected(true);
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePanelImpl datePanel1 = new JDatePanelImpl(model1);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
        JDatePickerImpl datePicker1 = new JDatePickerImpl(datePanel1);

        jPanel2.putClientProperty(FlatClientProperties.STYLE, "arc: 80");
        jButton_search.putClientProperty(FlatClientProperties.STYLE, "arc: 100");
        datePicker.setBackground(Color.decode("#ED3036"));
        datePicker1.setBackground(Color.decode("#ED3036"));

        jPanel1.add(datePicker);
        jPanel1.add(datePicker1);
        datePicker.setBounds(20, 70, 140, 40);
        datePicker1.setBounds(173, 70, 140, 40);

        //DefaultTableModel modelTable = (DefaultTableModel) jTable1.getModel();
        getAllTransactions(json.getString("email"));

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        model.addChangeListener((ChangeEvent e) -> {
            if (!changingModel) {
                System.out.println("Model 1 changed: " + model.getValue());
                updateTextField(model, datePicker, dateFormatter);
            }
        });

        model1.addChangeListener((ChangeEvent event) -> {
            if (!changingModel) {
                System.out.println("Model 2 changed: " + model1.getValue());
                updateTextField(model1, datePicker1, dateFormatter);
            }
        });
//        model.addChangeListener((ChangeEvent e) -> {
//            if (model.getValue() != null) {
//                Date date = model.getValue();
//                String formattedDate = dateFormatter.format(date);
//                datePicker.getJFormattedTextField().setText(formattedDate);
//            }
//        });
//
//        model1.addChangeListener((ChangeEvent event) -> {
//            if (model1.getValue() != null) {
//                Date date = model1.getValue();
//                String formattedDate = dateFormatter.format(date);
//                datePicker1.getJFormattedTextField().setText(formattedDate);
//            }
//        });

        jButton_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Home(json).setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(addMoney.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        jButton_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String fromDate = datePicker.getJFormattedTextField().getText();
                    String toDate = datePicker1.getJFormattedTextField().getText();

                    SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = dateFormat1.parse(fromDate);
                    Date date1 = dateFormat1.parse(toDate);
                    if (date.equals(date1)) {
                        getTransactionsDate(json.getString("email"), dateFormat2.format(date));
                    } else if (date.before(date1)) {
                        getTransactionsBetween(json.getString("email"), dateFormat2.format(date), dateFormat2.format(date1));
                    } else {
                        JOptionPane.showMessageDialog(frame, "Wrong date selected!");
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(Transactions.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

//        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
//            @Override
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                int row = jTable1.rowAtPoint(evt.getPoint());
//                String s = jTable1.getModel().getValueAt(row, 0) + "";
//                int id = Integer.parseInt(s);
//                System.out.println(id);
//            }
//        });
    }

    private static void updateTextField(UtilDateModel model, JDatePickerImpl datePicker, SimpleDateFormat dateFormatter) {
        if (model.getValue() != null) {
            Date date = model.getValue();
            String formattedDate = dateFormatter.format(date);
            changingModel = true;  // Set to true to avoid triggering additional changes
            datePicker.getJFormattedTextField().setText(formattedDate);
            changingModel = false; // Reset to false after updating the text field
        }
    }

    private void setData(JSONArray jSONArray, String email, JPanel jPanel) throws ParseException {

        //int y = 30;
//        JSeparator line = new JSeparator();
//        line.setBounds(35, 190, 320, 5);
//        line.setForeground(Color.white);
//        jPanel.add(line);
//        for (int i = 0; i < jSONArray.length(); i++) {
//            JSONObject jOBJ = jSONArray.getJSONObject(i);
//            JLabel jlabel_icon = new JLabel();
//            JLabel jlabel_text = new JLabel();
//            JLabel jlabel_total = new JLabel();
//
//            if (jOBJ.getString("sender").equals(email)) {
//                jlabel_text.setText("Transfer to " + jOBJ.getString("receiver"));
//                jlabel_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Img/out.png")));
//                jlabel_total.setText(" - " + formatter.format(jOBJ.get("total")));
//            } else if (jOBJ.getString("receiver").equals(email)) {
//                jlabel_text.setText("Receive from " + jOBJ.getString("sender"));
//                jlabel_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Img/in.png")));
//                jlabel_total.setText(" + " + formatter.format(jOBJ.get("total")));
//                jlabel_text.setForeground(Color.decode("#1AC593"));
//                jlabel_total.setForeground(Color.decode("#1AC593"));
//            }
//
//            jlabel_text.addMouseListener(new MouseAdapter() {
//                public void mouseClicked(MouseEvent e) {
//
//                    try {
//                        new Transaction_Detail(jOBJ).setVisible(true);
//                    } catch (ParseException ex) {
//                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//                }
//            });
//
//            String dateString = String.valueOf(jOBJ.get("date"));
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
//            Date date = dateFormat.parse(dateString);
//
//            JLabel jlabel_date = new JLabel(dateFormat1.format(date));
//            //jlabel_total.setHorizontalAlignment(SwingConstants.TRAILING);
//
//            JPanel subJPanel = new JPanel();
//            subJPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//            JPanel subJPanel1 = new JPanel();
//            subJPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
//
//            JPanel subJPanel2 = new JPanel();
//            subJPanel2.setLayout(new FlowLayout(FlowLayout.TRAILING));
//
//            // Add the components to the subpanel
//            subJPanel.add(jlabel_icon);
//            subJPanel.add(jlabel_text);
//
//            subJPanel1.add(jlabel_date);
//            subJPanel1.add(jlabel_total);
//
//            subJPanel.setBorder(new EmptyBorder(0, 0, 0, 150));
//            subJPanel1.setBorder(new EmptyBorder(0, 0, 0, 0));
//
//            jlabel_icon.setBorder(new EmptyBorder(0, 20, 0, 0));
//            jlabel_text.setBorder(new EmptyBorder(0, 0, 0, 0));
//            jlabel_date.setBorder(new EmptyBorder(0, 50, 0, 0));
//            jlabel_total.setBorder(new EmptyBorder(0, 30, 0, 20));
//            jlabel_total.setHorizontalAlignment(SwingConstants.TRAILING);
//
//            jlabel_date.setForeground(Color.black);
//            jlabel_date.setFont(new Font("Segoe UI", Font.ITALIC, 13));
//
//            jlabel_text.setFont(new Font("Segoe UI", Font.BOLD, 15));
//
//            jlabel_total.setFont(new Font("Segoe UI", Font.BOLD, 15));
//
//            //y += 60;
//            // Add the subpanel to the main panel
//            jPanel.add(subJPanel);
//            jPanel.add(subJPanel1);
//            //jPanel.add(subJPanel2);
//
//            JSeparator line1 = new JSeparator();
//            line1.setAlignmentX(Component.CENTER_ALIGNMENT);
//            //line1.setBounds(35, y + 60, 320, 5);
//            jPanel.add(line1);
//
//        }
        //jScrollPane1.setViewportView(jPanel);
        int y = 5;
        if (jPanel.getComponentCount() != 0) {
            System.out.println("ok");
            jPanel.removeAll();
            jPanel.revalidate();
            jPanel.repaint();
        }

        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jOBJ = jSONArray.getJSONObject(i);

            JLabel jlabel_icon = new JLabel();
            JLabel jlabel_text = new JLabel();
            JLabel jlabel_total = new JLabel();

            if (jOBJ.getString("sender").equals(email)) {
                jlabel_text.setText("Transfer to " + jOBJ.getString("receiver"));
                jlabel_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Img/out.png")));
                jlabel_total.setText(" - " + formatter.format(jOBJ.get("total")));
            } else if (jOBJ.getString("receiver").equals(email)) {
                jlabel_text.setText("Receive from " + jOBJ.getString("sender"));
                jlabel_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Img/in.png")));
                jlabel_total.setText(" + " + formatter.format(jOBJ.get("total")));
                jlabel_text.setForeground(Color.decode("#1AC593"));
                jlabel_total.setForeground(Color.decode("#1AC593"));
            }

            jlabel_text.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {

                    try {
                        new Transaction_Detail(jOBJ).setVisible(true);
                    } catch (ParseException ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });

            String dateString = String.valueOf(jOBJ.get("date"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormat.parse(dateString);

            // JLabel jlabel_icon = new JLabel();
            //jlabel_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Img/in.png")));
            JLabel jlabel_date = new JLabel(dateFormat1.format(date));
            jlabel_total.setHorizontalAlignment(SwingConstants.TRAILING);
            jPanel.add(jlabel_text);
            jPanel.add(jlabel_icon);
            jPanel.add(jlabel_date);
            jPanel.add(jlabel_total);

            jlabel_icon.setBounds(30, y + 10, 24, 24);

            jlabel_total.setBounds(225, y + 20, 150, 20);

            jlabel_text.setBounds(55, y + 12, 250, 20);

            jlabel_date.setBounds(55, y + 33, 100, 15);

            jlabel_date.setForeground(Color.black);
            jlabel_date.setFont(new Font("Segoe UI", Font.ITALIC, 13));

            jlabel_text.setFont(new Font("Segoe UI", Font.BOLD, 15));

            jlabel_total.setFont(new Font("Segoe UI", Font.BOLD, 15));

            JSeparator line = new JSeparator();
            line.setBounds(45, y + 60, 320, 5);
            line.setForeground(Color.LIGHT_GRAY);
            jPanel.add(line);
            y += 60;

        }
    }

    private void getAllTransactions(String email) throws ParseException {
        JSONObject jsonSend = new JSONObject();

        jsonSend.put("email", email);
        jsonSend.put("func", "getALLTransactions");
        String dataReceive = "";
        try {
            dataReceive = controller.SendReceiveData(jsonSend.toString());
        } catch (Exception ex) {
            //Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONObject json = new JSONObject(dataReceive);

        JSONArray arrayTransaction = json.getJSONArray("transactionList");

        //jPanel3.setLayout(new BoxLayout(jPanel3, BoxLayout.Y_AXIS));
        setData(arrayTransaction, email, jPanel3);
    }

    private void getTransactionsDate(String email, String date) throws ParseException {
        JSONObject jsonSend = new JSONObject();

        jsonSend.put("email", email);
        jsonSend.put("date", date);
        jsonSend.put("func", "getTransactionsDate");
        String dataReceive = "";
        try {
            dataReceive = controller.SendReceiveData(jsonSend.toString());
        } catch (Exception ex) {
            //Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONObject json = new JSONObject(dataReceive);

        JSONArray arrayTransaction = json.getJSONArray("transactionList");

        setData(arrayTransaction, email, jPanel3);
        //tableClick(arrayTransaction);
        //setDataToTable(arrayTransaction, model, json.getString("email"));
    }

    private void getTransactionsBetween(String email, String fromDate, String toDate) throws ParseException {
        JSONObject jsonSend = new JSONObject();

        jsonSend.put("email", email);
        jsonSend.put("fromDate", fromDate);
        jsonSend.put("toDate", toDate);
        jsonSend.put("func", "getTransactionsBetween");
        String dataReceive = "";
        try {
            dataReceive = controller.SendReceiveData(jsonSend.toString());
        } catch (Exception ex) {
            //Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONObject json = new JSONObject(dataReceive);

        JSONArray arrayTransaction = json.getJSONArray("transactionList");
        setData(arrayTransaction, email, jPanel3);
        //tableClick(arrayTransaction);
        //setDataToTable(arrayTransaction, model, json.getString("email"));
    }

//    private void tableClick(JSONArray arrayTransaction) {
//        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
//            @Override
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                int row = jTable1.rowAtPoint(evt.getPoint());
//                String s = jTable1.getModel().getValueAt(row, 0) + "";
//                int id = Integer.parseInt(s);
//                for (int i = 0; i < arrayTransaction.length(); i++) {
//                    JSONObject jOBJ = arrayTransaction.getJSONObject(i);
//                    if (jOBJ.getInt("transaction_id") == id) {
//                        try {
//                            new Transaction_Detail(jOBJ).setVisible(true);
//                        } catch (ParseException ex) {
//                            Logger.getLogger(Transactions.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//
//                    }
//
//                }
//            }
//        });
//    }
    /**
     * Creates new form Transactions
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton_back = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton_search = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(237, 48, 54));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jButton_back.setBackground(new java.awt.Color(237, 48, 54));
        jButton_back.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Img/back.png"))); // NOI18N
        jButton_back.setBorderPainted(false);
        jButton_back.setFocusPainted(false);
        jButton_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_backActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("From day:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("To day:");

        jButton_search.setBackground(new java.awt.Color(255, 255, 255));
        jButton_search.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Img/search.png"))); // NOI18N
        jButton_search.setBorderPainted(false);
        jButton_search.setFocusPainted(false);
        jButton_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_searchActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 478, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 35, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton_back)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(85, 85, 85)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                .addComponent(jButton_search, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton_back)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2))
                    .addComponent(jButton_search, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_searchActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_jButton_searchActionPerformed

    private void jButton_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_backActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton_backActionPerformed

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
            java.util.logging.Logger.getLogger(Transactions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transactions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transactions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transactions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Transactions(json).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_back;
    private javax.swing.JButton jButton_search;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
