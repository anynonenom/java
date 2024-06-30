package Agent_Detection;


import java.util.ArrayList;
import java.util.Iterator;

import jade.core.AID;
import jade.core.Agent;
// Container Agent :
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

/**
 * Détecteur
 */
public class Detecteur extends Agent {
   
    private DataAnomail gui ;
    ArrayList anomail = new ArrayList();
    // end constructor 
    
    @Override
    protected void setup() { // Start Setup 
        // Analyser :
        System.out.println("\n INFO: ---------------------------------- \n");

                System.out.println("\n\n\t Start This Agent  : \n") ;
                   gui = new DataAnomail(this);  
                   addBehaviour(new Detecter_consommation(this, 5000));
                //    takeDown();
    }   //end setup
    /**
     * Envoyer_consommation 
     */
    public class Detecter_consommation  extends TickerBehaviour{ // start inner class 1
        public Detecter_consommation(Agent a, long period) {
            super(a, period);
        }
        @Override
        protected void onTick() {
            
             //System.out.println("\t ------ Start le Comportement ! -----\n");
             MessageTemplate temp = MessageTemplate.and(MessageTemplate.MatchConversationId("id-watt"), 
             MessageTemplate.MatchPerformative(ACLMessage.INFORM))  ;
             ACLMessage reçu = receive(temp) ;
    
             
             if (reçu != null ) { // start 
                // Transfere le contenu au Array list si lui est null super 
                        if (reçu.getContent() == null  ) {
                            System.out.println("\n INFO: ---------------------------------- \n");
                            System.out.println("\t ------  Tout Super ! ----- \n");
                        }
                        // sinon 
                        else {
                            String anomail_ [] = reçu.getContent().split(" | ")  ;
                               for (String  j : anomail_ ) {
                                 if (!j.equals("|")) {
                                        anomail.add(j);
                                     }
                              }
                              System.out.println("\n INFO: ---------------------------------- \n");
                                   Iterator iterator = anomail.iterator();
                                   while(iterator.hasNext()){
                                             System.out.println(iterator.next());
                                        }
                                       int  id = Integer.valueOf(anomail.get(1).toString())  ;
                                        // System.out.println("id : "+id) ;
                                        gui.ShowinTable(
                                            // id
                                          id
                                        // appareill
                                        ,anomail.get(2).toString() , 
                                        // watt
                                        anomail.get(3).toString()
                                        // heur
                                        , anomail.get(4).toString(), 
                                        // bool
                                        true);  
                                        
                                        anomail.clear(); 


                        }
                     
                   // envoyer à l'Agent Décision 
                   ACLMessage msg = new ACLMessage(ACLMessage.INFORM) ;
                   msg.addReceiver(new AID("Decision" , AID.ISLOCALNAME));
                   msg.setContent(reçu.getContent());
                   msg.setConversationId("Detecter");
                   send(msg);
                    // Confirmer le temps  au Memorisation : 
                    ACLMessage histoMessage = new ACLMessage(ACLMessage.CONFIRM) ;
                    histoMessage.addReceiver(new AID("Historique" , AID.ISLOCALNAME));
                    histoMessage.setConversationId("ID_Détecteur");
                    histoMessage.setContent("Interaction :  Deteceteur => Decisionnel ]");                    
                    myAgent.send(histoMessage);
                    System.out.println("\n INFO: ---------------------------------- \n");

                    System.out.println("\t\t ---  [i] All have Sent ----- ");
             }// end que recu not null
        }// end of Ontick 
        
    }// end InnerDétecteur
        @Override
        protected void takeDown() { 
            System.out.println("\n INFO: ---------------------------------- \n");
            System.out.println("Agent detecteur a terminé");
        }
    // Conteneur d'Agent 
 /*   public static void main(String[] args) {
        try {
			Runtime rt = Runtime.instance();
			ProfileImpl  pr = new ProfileImpl(false);
			pr.setParameter(ProfileImpl.MAIN_HOST, "localhost");
			pr.setParameter(ProfileImpl.MTPS, "jade.mtp.http.MessageTransportProtocol");
            pr.setParameter(ProfileImpl.CONTAINER_NAME, "Detecter");
			pr.setParameter(ProfileImpl.ACCEPT_FOREIGN_AGENTS, "true");
			AgentContainer agcont =  rt.createAgentContainer(pr);
			AgentController agcontr =  agcont.createNewAgent("Detecteur" , Detecteur.class.getName() , null);
			agcontr.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }*/
}