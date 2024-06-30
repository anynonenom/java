package Agent_Historique;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Historique extends Agent {

    private MainHistorique gui;
    private MessageTemplate tmp; // Pour filtrer les messages
    LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());

    // DateTimeFormatter pour formater la date et l'heure
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void setup() {
        gui = new MainHistorique(this);

        String formattedNow = now.format(formatter);
        System.out.println(" \t ---- " + this.getName() + " A été Enregistré à ---- " + formattedNow);
        try {
            gui.EnterAction(" ---- Agent : " + this.getName() + " ---- \n", true);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }

        addBehaviour(new SelectTimeForAgent(this, 5000));
    }

    // Classe interne pour le comportement cyclique
    private class SelectTimeForAgent extends TickerBehaviour {

        public SelectTimeForAgent(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onTick() {
            ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));

            if (msg != null) {
                String conversationId = msg.getConversationId();
                String senderName = msg.getSender().getName();
                String content = msg.getContent() + " Agent Sender : " + senderName + " / Conversation ID : " + conversationId;
                LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());

                String formattedNow = now.format(formatter);
                System.out.println(" ---- Agent : " + senderName + " ---- \n LocalDateTime.now() : " + formattedNow);

                // Appeler la méthode pour stocker le contenu dans un fichier XML
                try {
                    gui.EnterAction("\t------------\n" + content + " at " + formattedNow, true);
                    // storeMessageInXML(conversationId, content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void takeDown() {
        // Printout a dismissal message
        String formattedNow = LocalDateTime.now().format(formatter);
        System.out.println("\n INFO: ---------------------------------- \n");
        System.out.println("\t\t [i] Agent Historique Terminé ! " + formattedNow);
    }

    public void storeMessageInXML(String content) throws ParserConfigurationException, TransformerException {
        File file = new File("messages.xml");

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document;

        if (file.exists()) {
            try {
                document = documentBuilder.parse(file);
                document.getDocumentElement().normalize();
            } catch (Exception e) {
                document = documentBuilder.newDocument();
                Element root = document.createElement("messages");
                document.appendChild(root);
            }
        } else {
            document = documentBuilder.newDocument();
            Element root = document.createElement("messages");
            document.appendChild(root);
        }

        Element root = document.getDocumentElement();

        // Ajout d'un élément message
        Element messageElement = document.createElement("message");

        Element idElement = document.createElement("content");
        idElement.appendChild(document.createTextNode(content));
        messageElement.appendChild(idElement);

        root.appendChild(messageElement);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(file);

        transformer.transform(domSource, streamResult);

        // Imprimer le chemin absolu du fichier pour confirmation
        System.out.println("Message stocké dans le fichier XML avec succès : " + file.getAbsolutePath());
    }

    /* public static void main(String[] args) {
        try {
            Runtime rt = Runtime.instance();
            ProfileImpl pr = new ProfileImpl(false);
            pr.setParameter(ProfileImpl.MAIN_HOST, "localhost");
            pr.setParameter(ProfileImpl.MTPS, "jade.mtp.http.MessageTransportProtocol");
            pr.setParameter(ProfileImpl.CONTAINER_NAME, "Historique");
            pr.setParameter(ProfileImpl.ACCEPT_FOREIGN_AGENTS, "true");
            AgentContainer agcont = rt.createAgentContainer(pr);
            AgentController agcontr = agcont.createNewAgent("Historique", Historique.class.getName(), null);
            agcontr.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
