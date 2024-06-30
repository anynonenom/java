package Agent_Historique;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class MainHistorique extends JFrame {
    private Historique myAgent;
    private Box box;
    private JTextArea textArea;

    // DateTimeFormatter pour formater la date et l'heure
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public MainHistorique(Historique a) {
        super(a.getLocalName());
        myAgent = a;
        JFrame frame = new JFrame("Historique");
        box = Box.createHorizontalBox();
        textArea = new JTextArea();
        textArea.setEditable(false);

        box.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        box.add(Box.createHorizontalGlue());
        box.add(Box.createHorizontalStrut(5));
        box.add(Box.createHorizontalStrut(5));

        frame.add(new JScrollPane(textArea));
        frame.add(box, BorderLayout.SOUTH);
        frame.setPreferredSize(new Dimension(420, 340));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLocation(950,650);

    }

    public void EnterAction(String desc, boolean app) throws ParserConfigurationException, TransformerException {
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        String formattedNow = now.format(formatter);

        if (app) {
            textArea.append(String.format(" %s%n  \n %s%n\n", formattedNow, desc));
            myAgent.storeMessageInXML(String.format(" %s%n  --  %s%n", formattedNow, desc));
        }
    }
}
