package com;


import DatabaseConfig.ConnectionConfig;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class RankingTable extends JPanel{
        JLabel close;
        private JTable table;

	public RankingTable() {
            super();
            this.setBackground(Commons.BACKGROUND_COLOR);
            this.setSize(500, 600);
            this.setLayout(new GridBagLayout());
            initComponent();
//            setVisible(true);
            System.out.println("Ranking table was initizalation...");
	}
        
        private void initComponent() {
            // set close table
            close = new JLabel();
            close.setBorder(new EmptyBorder(10,14,10,14));
            close.setText("X");
            close.setBackground(Commons.BACKGROUND_COLOR);
            close.setForeground(Color.GRAY);
            close.setOpaque(true);
            close.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                    close.setBackground(Color.red);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                    close.setBackground(Commons.BACKGROUND_COLOR);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                    setVisible(false);
                }                
            });
            
            // set table
            String[] title = {"Name","Score"};
            String[][] data = null;
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.setDataVector(data, title);
            // Add content in table
            ResultSet rel = getData();
            Vector row;
            try {
                System.out.println("Data in Raking table is initizalation...");
                while(rel.next()) {
                    row = new Vector<String>();
                    String nickName = rel.getString("nick_name");
                    String score = rel.getString("score");
                    row.add(nickName);
                    row.add(score);
                    System.out.println("\tName: "+nickName+" - Score: "+score);
                    
                    tableModel.addRow(row);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RankingTable.class.getName()).log(Level.SEVERE, null, ex);
            }
            table = new JTable(tableModel);
            
            // add component
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.LAST_LINE_END;
            this.add(close,gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.CENTER;
            
            JScrollPane tableSP = new JScrollPane(table);
            tableSP.setPreferredSize(new Dimension(500,400));
            this.add(new JScrollPane(table),gbc);
            
            this.revalidate();
            this.repaint();
        }
        
        private ResultSet getData() {
            ResultSet rel = null;
            try {
            // 1. get connection to database
            Connection con = ConnectionConfig.getConnection();
            // 2. create query insert data to database
            String query = "SELECT TOP 10 nick_name, score FROM Player AS P\n" +
                            "JOIN DetailPlayer AS D ON p.id = D.id_player\n" +
                            "ORDER BY score DESC";
            System.out.println("Query to call data in database:");
            System.out.println("\t"+query);
            // 3. create statement for execute query
            Statement st = con.createStatement();
            // 4. execute query
            rel = st.executeQuery(query);
            } catch (SQLException ex) {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            }
            return rel;
        }
}
