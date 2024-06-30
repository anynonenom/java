package Agent_Decisionnel;

import java.awt.*;

import javax.swing.*;  

 public class Area_Decision extends JPanel {
  private JFrame frame ;
  private Box box ; 
  private  JTextArea textArea ;
  public Area_Decision() {
    frame = new JFrame(" Décision ");
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
    frame.setLocation(950,350);


  }

  public void  EnterAction(String desc , boolean app) {
      if (app == true) {
        textArea.append(String.format("\n\n %s%n  \n ", desc));
      }
  }

 
   //  public static void main(String args[])  
   //      {  
   //         new Area_Decision();  
   //      }
}
