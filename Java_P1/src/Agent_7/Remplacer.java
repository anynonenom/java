package Remplacer_Agent;

import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


import jade.core.Runtime;

import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class Remplacer extends Agent{
    private DFAgentDescription df  ;
    private ServiceDescription sd  ;
    private int [] percent = { 100 , 50} ;
    private Random rand = new Random() ;
    String Appareil[]={"Télé","PC" , "Lave-Ligne" ,"chauffe-eau" , "luminaire" , "Clima"};
    int watte[]={100 ,500 ,  780 , 800   , 2500 , 3000} ,heur[] ={4,2,5,12,1 ,2} ;
      private Remplacer_Appareil dnm ;
    private ArrayList rempl = new ArrayList<>() ;
    private String app ;
    @Override
  protected void setup() {
      System.out.println("\n INFO: ---------------------------------- ");
      System.out.println("\t --- Agent remplacer commencer ! ---\n");
      dnm= new Remplacer_Appareil(this);
       // DF plan jaune
       df = new DFAgentDescription() ;
      sd = new ServiceDescription() ;
      sd.setName("Remplacer");
      sd.setType("Solution");
      df.addLanguages("Fr");
      df.addServices(sd);
      // Enregistrer dans le df pour que le décsionel va le trouver 
        try {
            DFService.register(this, df);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
      addBehaviour(new TickerBehaviour(this , 5000) {
        @Override
          protected void onTick() {
            MessageTemplate templ = MessageTemplate.and(
                 MessageTemplate.MatchConversationId("Decision_Agent"),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
            ACLMessage reçu_msg = receive(templ) ;
            if (reçu_msg != null) {
              
              System.out.println("\n INFO: ---------------------------------- \n");
                System.out.println("\t --> remplacer: "+reçu_msg.getContent());
                String[] msg1= reçu_msg.getContent().split(":");

                for (String j : msg1) {
                    if (!j.equals(" : ")) {
                        // effectuer les element dans arraylist :
                        rempl.add(j);
                    }
                }
              // si nous avons
         
              for (int i = 0; i < Appareil.length; i++) {
                if (i == Integer.valueOf( rempl.get(1).toString())  ) {
                   try {
                    dnm.EnterAction("cette Appreill besoin de remplacer : "+Appareil[i]+" \n", true);
                    app = Appareil[i];
                  } catch (ParserConfigurationException | TransformerException e) {
                    e.printStackTrace();
                  }
                }
                else{
                  
                }
              }
              rempl.clear(); 
                // System.out.println("\t --- remplacer.setup() -- \n Sender : "+reçu_msg.getSender().getLocalName()+"\n Content : "
                //    +reçu_msg.getContent()+" \n Proprieties :  "+reçu_msg.getAllUserDefinedParameters());  
                   ACLMessage msg = reçu_msg.createReply() ;
                  // msg.addReceiver(new AID("Decision" , AID.ISLOCALNAME));
                  msg.addReplyTo(reçu_msg.getSender());
                  msg.setPerformative(ACLMessage.PROPOSE);
                  msg.setConversationId("ID_2");
                   msg.setContent(""+percent[rand.nextInt(percent.length)]);
                   msg.setOntology("Cette Appareil est besoin de remplacer : "+app);
                   send(msg);
                     // Confirmer le temps  au Memorisation : 
                     ACLMessage histoMessage = new ACLMessage(ACLMessage.CONFIRM) ;
                     histoMessage.addReceiver(new AID("Historique" , AID.ISLOCALNAME));
                     histoMessage.setConversationId("ID_Remplacer");
                     histoMessage.setContent("Interaction :  [ Remplacer->Decision ] ");
                     send(histoMessage);
                    
            }
            // else {
            //     System.out.println("\t [i] --- block ---");
            // }
        }
      });

  }
    // ------- terminer cette Agent -------
    @Override
    protected void takeDown() {
        // try {
          System.out.println("\n INFO: ---------------------------------- \n");
          // DFService.deregister(this, df);
          System.out.println("\t------- Terminer Cette Agent -------");

      // } catch (FIPAException e) {
      //     e.printStackTrace();
      // }
    }
//   public static void main(String[] args) {
//     try {
//         Runtime rt = Runtime.instance();
//         ProfileImpl  pr = new ProfileImpl(false);
//         pr.setParameter(ProfileImpl.MAIN_HOST, "localhost");
//         pr.setParameter(ProfileImpl.MTPS, "jade.mtp.http.MessageTransportProtocol");
//         pr.setParameter(ProfileImpl.CONTAINER_NAME, "Remplacant");
//         pr.setParameter(ProfileImpl.ACCEPT_FOREIGN_AGENTS, "true");
//         AgentContainer agcont =  rt.createAgentContainer(pr);
//         AgentController agcontr =  agcont.createNewAgent("Remplace" , Remplacer.class.getName() , new Object[] {});
//         agcontr.start();
//     } catch (Exception e) {
//         e.printStackTrace();
//     }
// }
}
