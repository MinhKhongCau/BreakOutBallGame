/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SubGame;

import com.Commons;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author minhn
 */
public class InfoPanel extends JPanel {
        private JLabel leftLabel;
        private JLabel rightLabel;

        public InfoPanel(String leftLabel, String rightLabel) {
            this.leftLabel = new JLabel(leftLabel);
            this.rightLabel = new JLabel(rightLabel);

            // set UI label
            this.leftLabel.setForeground(Commons.COMPONENT_COLOR);
            this.leftLabel.setFont(Commons.MEDIUM_FONT);
            this.rightLabel.setForeground(Commons.COMPONENT_COLOR);
            this.rightLabel.setFont(Commons.MEDIUM_FONT);

            // set UI panel
            setBackground(Commons.BACKGROUND_COLOR);
            setBorder(Commons.INFO_BORDER);
            setLayout(new BorderLayout());
            setOpaque(false);
            add(this.leftLabel, BorderLayout.WEST);
            add(this.rightLabel, BorderLayout.EAST);
        }

        public void setLeftLabel(String title) {
            this.leftLabel.setText(title);
        }

        public void setRightLabel(String title) {
            this.rightLabel.setText(title);
        }
    }
