package Remplacer_Agent;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import Agent_Historique.Historique;

public class Remplacer_Appareil extends JFrame {
      private JFrame frame ;
  private Box box ; 
  private  JTextArea textArea ;
  private Remplacer myAgent ;
  public Remplacer_Appareil(Remplacer a) {
    super(a.getLocalName());
    myAgent = a;
    frame = new JFrame("Rappelle de Remplacage ");
     box = Box.createHorizontalBox();
     textArea = new JTextArea();
    // TEST: textArea.getDocument().addDocumentListener(new FIFODocumentListener(textArea));
    textArea.setEditable(false);


    //JButton button = new JButton("Clear");
    // button.addActionListener(e -> textArea.setText(""));
     //JTextField textField = new JTextField("aaa");

    box.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    box.add(Box.createHorizontalGlue());
   // box.add(textField);
    box.add(Box.createHorizontalStrut(5));
   // box.add(new JButton("Temps"));
    box.add(Box.createHorizontalStrut(5));
   // box.add(button);

    frame.add(new JScrollPane(textArea));
    frame.add(box, BorderLayout.SOUTH);
    frame.setPreferredSize(new Dimension(420, 340));
    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.setLocation(950,5);


  }

  public void  EnterAction(String desc , boolean app) throws ParserConfigurationException, TransformerException {
      if (app == true) {
        textArea.append(String.format("%s%n\n", desc));
        //myAgent.storeMessageInXML(String.format(" %s%n  %s%n", now, desc));
      }
  }

}
