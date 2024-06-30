package Agent_Detection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import java.awt.*;


/**
 * @author Giovanni Caire - TILAB
 */

public class DataAnomail extends JFrame {
    //  private Detecteur myAgent ;// = new Détecteur();
     DefaultTableModel model ;
     JTextField appareilField, consom_w, consom_heur, coûtField;
     JTable table ; 

    public DataAnomail(Detecteur  a ) {
        //  super(a.getLocalName());
        //  myAgent = a ;
       
        JFrame frame = new JFrame("DataBase Anomalies");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up the table with a DefaultTableModel
        String[] columnNames = {"Id","Appareil électrique","Consommation par W","Consommation par h"};
        model = new DefaultTableModel(null, columnNames);
        table = new JTable(model);
        table.setEnabled(false);
        // Set up the text fields for input
        appareilField = new JTextField(5);
        consom_w = new JTextField(5);
        consom_heur = new JTextField(5);
        coûtField = new JTextField(5);
        // Layout setup Component
        JPanel inputPanel = new JPanel();
        // inputPanel.add(addButton);
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLocation(5,500);


    }
    
    public void ShowinTable(int id , String  app , String w , String heur  , boolean bool){
        if ( bool == true ) {
            
            model.addRow(new Object[] {String.valueOf(id) ,app ,String.valueOf(w)  ,String.valueOf(heur) });
        }
    }

    

}


