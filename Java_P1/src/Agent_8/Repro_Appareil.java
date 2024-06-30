package Reprogrammer_Agent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

    
    
    /**
     * @author Giovanni Caire - TILAB
     */
    
public class Repro_Appareil extends JFrame  {
        private JTextField appareilField, consom_w, consom_heur, coûtField;
        private  DefaultTableModel model  ;

        public Repro_Appareil() {
           
            JFrame frame = new JFrame(" Reprogrammer ");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
            // Set up the table with a DefaultTableModel
            String[] columnNames = {"Id", "Appareil électrique", "Consommation par h"};
             model = new DefaultTableModel(null, columnNames);
            JTable table = new JTable(model);
            table.setEnabled(false);
            // Set up the text fields for input
            appareilField = new JTextField(5);
            consom_w = new JTextField(5);
            consom_heur = new JTextField(5);
            coûtField = new JTextField(5);
    
            // Set up the button to add the text field values to the table
            JButton addButton = new JButton("Ajouter");
            // System.out.println("\t\t Maison : \n ");
        
            // Layout setup Component
            JPanel inputPanel = new JPanel();
    
            // inputPanel.add(addButton);
            frame.setLayout(new BorderLayout());
            frame.add(new JScrollPane(table), BorderLayout.CENTER);
            frame.add(inputPanel, BorderLayout.SOUTH);
    
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setLocation(450,5);

    
        }
        public void ShowinTable(int id , String  app , int heur , boolean bool){
            if ( bool == true ) {
                model.addRow(new Object[] {String.valueOf(id) ,app  ,String.valueOf(heur)});
            }
        }
    
    
    
}
    

