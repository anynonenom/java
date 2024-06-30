package Agent_Recuperateur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Giovanni Caire - TILAB
 */

public class DataBase extends JFrame {
     private Recuperateur myAgent;// = new Détecteur();
    String Appareil[]={"Télé","PC" , "Lave-Ligne" ,"chauffe-eau" , "luminaire" , "Clima"};
    int watte[]={100 ,500 ,  780 , 800   , 2500 , 3000} ,heur[] ={4,2,5,12,1 ,2} ;
    JTextField appareilField, consom_w, consom_heur, coûtField;
    private int step=0 ;

    public DataBase( Recuperateur a  ) {
         super(a.getLocalName()); 
         myAgent = a ;
        // SwingUtilities.invokeLater(() -> {
        JFrame frame = new JFrame("Data Base Maison Intelegente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up the table with a DefaultTableModel
        String[] columnNames = {"id", "Appareil électrique", "Consommation par W", "Consommation par h"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames);
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
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                // System.out.println("\n INFO: ---------------------------------- \n");

                   System.out.print("\t| Appareil électrique : "+Appareil[step]+" | watte annuel: "
                    +watte[step]+" | Heur :" + heur[step] +"\n");
                    myAgent.Recuperer_donnees(step, Appareil[step] ,watte[step], heur[step]);
                    
                    model.addRow(new Object[] { step , Appareil[step], watte[step], heur[step] });
                    step++;
                    // Clear the text fields after adding to the table
                    appareilField.setText("");
                    consom_w.setText("");
                    consom_heur.setText("");
                    coûtField.setText("");
                    

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(DataBase.this,
                            "Pardon!  le données que vous entrez indisponible." + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        // Layout setup Component
        JPanel inputPanel = new JPanel();

        inputPanel.add(addButton);
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLocation(450,500);

    }




}
