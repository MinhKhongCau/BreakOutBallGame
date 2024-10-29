package com;


import DatabaseConfig.ConnectionConfig;
import SubGame.PrepareGame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.table.JTableHeader;

public class RankingTable extends JPanel{
        JLabel close;
        private JTable table;
        private RankingTableEvent rankTableEvt;
        private PrepareGame pre;

	public RankingTable() {
            super();
            this.setBackground(Commons.BACKGROUND_COLOR);
//            this.setSize(Commons.SCREEN_WIDTH, Commons.SCREEN_HEIGHT);
            this.setLayout(new GridBagLayout());
            this.setSize(500, 500);
            initComponent();
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
                    pre = new PrepareGame();
                    rankTableEvt.addPrepareGame();
                    setVisible(false);
                }                
            });
            
            // set table
            String[] title = {"Top","Name","Score"};
            String[][] data = null;
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.setDataVector(data, title);
            tableModel.setRowCount(0);
            // Add content in table
            ResultSet rel = getData();
            Vector row;
            try {
                System.out.println("Data in Raking table is initizalation...");
                Integer count = 1;
                while(rel.next()) {
                    row = new Vector<String>();
                    String top = String.format("#%d", count);
                    String nickName = rel.getString("nick_name");
                    String score = rel.getString("score");
                    row.add(top);
                    row.add(nickName);
                    row.add(score);
                    count++;
                    System.out.println("\tName: "+nickName+" - Score: "+score);
                    
                    tableModel.addRow(row);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RankingTable.class.getName()).log(Level.SEVERE, null, ex);
            }
            table = new JTable(tableModel);
            // Set table color and border
            table.setBackground(Commons.BACKGROUND_COLOR); // Set background color
            table.setForeground(Commons.COMPONENT_COLOR);      // Set text color
            table.setGridColor(Commons.COMPONENT_COLOR);   // Set grid line color
            table.setRowHeight(25);                // Set row height
            table.setShowGrid(true);               // Display grid lines
            table.setIntercellSpacing(new Dimension(1, 1)); // Set space between cells
            
            // Customize header color
            JTableHeader header = table.getTableHeader();
            header.setBackground(Commons.BACKGROUND_COLOR);           // Set background color of header
            header.setForeground(Color.WHITE);           // Set text color of header
            header.setFont(Commons.MEDIUM_FONT); // Set font for header
            // Optional: Add border to the table
            table.setBorder(null);
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
            tableSP.setPreferredSize(new Dimension(300,280));
            tableSP.setForeground(Commons.BACKGROUND_COLOR);
            tableSP.setBorder(null);
            this.add(tableSP,gbc);
            
            this.revalidate();
            this.repaint();
        }
        
        private ResultSet getData() {
            ResultSet rel = null;
            try {
            // 1. get connection to database
            Connection con = ConnectionConfig.getConnection();
            // 2. create query insert data to database
            String query = "SELECT TOP 10 * FROM Player\n" +
                            "ORDER BY score DESC, life DESC";
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
        
        public void addEventTable(RankingTableEvent evt) {
            rankTableEvt = evt;
        };
        
        public interface RankingTableEvent {
            public void rakingRemove(RankingTable rank);
            public void addPrepareGame();
        }   
}
