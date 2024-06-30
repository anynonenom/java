package Reduire_Agent;

import java.util.ArrayList;
import java.util.Random;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

// Container Agent :
import jade.core.ProfileImpl;
import jade.core.Runtime;

import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
public class Reduire extends Agent {
    private  DFAgentDescription df  ;
    private ServiceDescription sd  ;
        private int [] percent = {  100 , 50 } ;
    private Random rand = new Random() ;
    // pour stocker les éléments :
    String Appareil[]={"Télé","PC" , "Lave-Ligne" ,"chauffe-eau" , "luminaire" , "Clima"};
    int watte[]={100 ,500 ,  780 , 800   , 2500 , 3000} ,heur[] ={4,2,5,12,1 ,2} ;
     //
     private Redui_data dnm ;
    private ArrayList red = new ArrayList<>() ;
    // private int step=0 ;
  @Override
  protected void setup() {
      System.out.println("\t --- Agentt réduire commencer ! ---\n");

      // DF plan jaune
       df = new DFAgentDescription() ;
      sd = new ServiceDescription() ;
      sd.setName("Reduire");
      sd.setType("Solution");
      df.addLanguages("Fr");
      df.addServices(sd);
      // Enregistrer dans le df pour que le décsionel va le trouver 
        try {
            DFService.register(this, df);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        //
            dnm = new Redui_data();
        // va recevoir  le message pour Résoudre le problem :
      addBehaviour(new TickerBehaviour(this , 5000) {
        @Override
          protected void onTick() {
            MessageTemplate templ = MessageTemplate.and(
                 MessageTemplate.MatchConversationId("Decision_Agent"),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
            ACLMessage reçu_msg = receive(templ) ;
            if (reçu_msg != null) {
              System.out.println("\n INFO: ---------------------------------- \n");
              String[] msg1= reçu_msg.getContent().split(":");

                    for (String j : msg1) {
                        if (!j.equals(" : ")) {
                            // effectuer les element dans arraylist :
                            red.add(j);
                        }
                    }
                  // si nous avons
                int  watt =Integer.valueOf(red.get(2).toString()) ;
                  for (int i = 0; i < Appareil.length; i++) {
                    if (i ==Integer.valueOf( red.get(1).toString())  ) {
                      // dnm.ShowinTable(i, Appareil[i], watt, heur[i] , true);
                    }
                    else{
                      dnm.ShowinTable(i, Appareil[i], 0,  true);
                    }
                  }
                  red.clear(); 
                
                  System.out.println("\t --> réduire: "+watt);
                 System.out.println("\t --- réduire.setup() -- \n Sender : "+reçu_msg.getSender().getLocalName()+"\n Ontology : "
                   +reçu_msg.getOntology()+" \n Proprieties :  "+reçu_msg.getAllUserDefinedParameters()); 
                  // renvoyer le message 
                  ACLMessage msg = reçu_msg.createReply();
                  // msg.addReceiver(new AID("Decision" , AID.ISLOCALNAME));
                  msg.addReplyTo(reçu_msg.getSender());
                  msg.setPerformative(ACLMessage.PROPOSE);
                  msg.setConversationId("ID_1");
                  msg.setContent(""+percent[rand.nextInt(percent.length)]);
                  msg.setOntology("éteignant les autres paramètres.");
                  send(msg);

                     // Confirmer le temps  au Memorisation : 

                     ACLMessage histoMessage = new ACLMessage(ACLMessage.CONFIRM) ;
                     histoMessage.addReceiver(new AID("Historique" , AID.ISLOCALNAME));
                     histoMessage.setConversationId("ID_Réduire");
                     histoMessage.setContent("Interaction :  [ Réduire->Decisionnel ] ");
                  
                   send(histoMessage);
                   // pop les éléments qui sont déja stocké
                    
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
        System.out.println("\t------- Terminer cet Agent -------");
    // } catch (FIPAException e) {
    //     e.printStackTrace();
    // }
  }
 /* public static void main(String[] args) {
    try {
        Runtime rt = Runtime.instance();
        ProfileImpl  pr = new ProfileImpl(false);
        pr.setParameter(ProfileImpl.MAIN_HOST, "localhost");
        pr.setParameter(ProfileImpl.MTPS, "jade.mtp.http.MessageTransportProtocol");
        pr.setParameter(ProfileImpl.CONTAINER_NAME, "Reduire");
        pr.setParameter(ProfileImpl.ACCEPT_FOREIGN_AGENTS, "true");
        AgentContainer agcont =  rt.createAgentContainer(pr);
        AgentController agcontr =  agcont.createNewAgent("Reduire" , Reduire.class.getName() , null);
        agcontr.start();
    } catch (Exception e) {
        e.printStackTrace();
    }
}*/
}