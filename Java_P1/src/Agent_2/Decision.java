package Agent_Decisionnel;

import java.util.Random;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.ArrayList;
// Container Agent :
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
// DFService 
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Decision extends Agent{
    private AID[]  Solution_Agent ;
    private Random rand = new Random();
    private int[] tab = {100 , 50 , 20};
    private ArrayList array = new ArrayList();
    private String [] obj  ;
    private Area_Decision gui ;
   

    @Override
    protected void setup() {
        System.out.println("\n INFO: ---------------------------------- \n");
        System.out.println("\t\t -- Start Decision ! --\n");
        // Montrer le panel :
       
        gui = new Area_Decision();
        // Comportement : 
        addBehaviour(new TickerBehaviour(this, 6000) {
            @Override
            public void onTick() {

                // recieve from Detetcteur
                MessageTemplate temp =MessageTemplate.and(
                     MessageTemplate.MatchPerformative(ACLMessage.INFORM) ,
                      MessageTemplate.MatchConversationId("Detecter")) ; 
                ACLMessage reçu = receive(temp) ;
                if (reçu != null ) {// reply not null           
                    // aprés que été reçu le contenut 
                   String donnees =  reçu.getContent() ;
                   // ------------------------------------ -------------------- -------------- 
                    if (!donnees.equals(null))
                    {
                        // ------  --  ------ ------
                        obj =  donnees.split(" | ") ;
                        //   String anomail_ [] = reçu.getContent().split(" | ")  ;
                                    for (String  j : obj ) 
                                    {
                                        if (!j.equals("|")) 
                                            {
                                                array.add(j);
                                            }
                                    }
                    // obtenir Heur ,  Id , Watt , Heur

                    int id =Integer.valueOf (array.get(1).toString()) ;
                    int watt =Integer.valueOf (array.get(3).toString()) ;
                    int Heur =Integer.valueOf (array.get(4).toString()) ;

                    // Printer les donnes de cette appareil :
                    System.out.println("\n INFO: ---------------------------------- \n");

                    System.out.print(" id: "+id+" watt : "+watt+" Heur : "+Heur) ;
                    
                   // System.out.println("\t --> [i] INFO :  "+reçu.getContent()+" from "+reçu.getSender().getName()+" <--\n");

                    // Confirmer le temps  au Memorisation :
                    ACLMessage msg = new ACLMessage(ACLMessage.REQUEST) ;
                    DFAgentDescription fd = new DFAgentDescription();
                    ServiceDescription sd = new ServiceDescription() ;
                    sd.setType("Solution");
                    fd.addServices(sd);
                  
                  try {
                      DFAgentDescription[] resultat = DFService.search(myAgent, fd);
                      Solution_Agent = new AID[resultat.length];
                      for (int i = 0; i < resultat.length; i++) {
                          // Stocker les DFAgent ( Fournisseur ) qui apporter ce type "Agent-Book-En-ligne" dans Objet "chercher"
                          Solution_Agent[i] = resultat[i].getName();
                          System.out.println("\n INFO: ---------------------------------- \n");
                          System.out.println("\t --->"+Solution_Agent[i].getName()+"<---");
                      }
                      } catch (FIPAException e) {
                          e.printStackTrace();
                      }
                      // Lister les Agent de DF :
                      System.out.println("\n INFO: ---------------------------------- \n");
                       System.out.println("\n\t ---- List des Agent dans Le DF : -----\n");
                      for (AID aid : Solution_Agent) {
                          msg.addReceiver(new AID(aid.getLocalName() , AID.ISLOCALNAME));
                          msg.setContent(":"+id+":"+watt+":"+Heur);
                          msg.addUserDefinedParameter("id", ""+id);
                          msg.addUserDefinedParameter("watt", ""+watt);
                          msg.addUserDefinedParameter("Heur", ""+Heur);
                          msg.setOntology("Solution de problem !");
                        //   msg.setAllUserDefinedParameters(prop);
                          msg.setConversationId("Decision_Agent");
                          
                          
                        } 
                        myAgent.send(msg);
                      array.clear();
                      ACLMessage histoMessage = new ACLMessage(ACLMessage.CONFIRM) ;
                      histoMessage.addReceiver(new AID("Historique" , AID.ISLOCALNAME));
                      histoMessage.setConversationId("ID_Decisione");
                      histoMessage.setContent("Interaction :  [ Decision -> {Reduire , Reprogrammer , Remplacer} ] ");
                      send(histoMessage);
                    System.out.println("\n INFO: ---------------------------------- \n");
                    System.out.println("\t\t --- > [i] Sent ");
                }// end of data not null
                else {
                    System.out.println("Fatal Error !");
                }
            }// end repl not null
        }
    });
    System.out.println("Afficher les donneés :\n");
    addBehaviour(new Afficher_Decision(this , 5000));
    
    }
private class Afficher_Decision extends TickerBehaviour {

   public  Afficher_Decision(Agent a , long l ){
        super(a,l);
    }
    @Override
        protected void onTick() {
            // int choix  = tab[rand.nextInt(tab.length)] ;
            MessageTemplate templ = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE) ;
            ACLMessage recoi = receive(templ) ;

            if (recoi != null ) {
                int j = Integer.valueOf(recoi.getContent()) ;
               
                if (j == 100 ) {
                     System.out.println("\n INFO: ---------------------------------- \n");
                     System.out.println("[!] Decision =>\n"+recoi.getOntology());
                     gui.EnterAction( "INFO: ---------------------------------- ",true);
                     gui.EnterAction("[!] Decision => \n"+recoi.getOntology() , true);
                     
                    }
                    else System.out.println("vide");
                }
        }
    }
        
    
    // public static void main(String[] args) {
    //     try {
	// 		Runtime rt = Runtime.instance();
	// 		ProfileImpl  pr = new ProfileImpl(false);
	// 		pr.setParameter(ProfileImpl.MAIN_HOST, "localhost");
	// 		pr.setParameter(ProfileImpl.MTPS, "jade.mtp.http.MessageTransportProtocol");
    //         pr.setParameter(ProfileImpl.CONTAINER_NAME, "Decision");
	// 		pr.setParameter(ProfileImpl.ACCEPT_FOREIGN_AGENTS, "true");
	// 		AgentContainer agcont =  rt.createAgentContainer(pr);
	// 		AgentController agcontr =  agcont.createNewAgent("Decision" , Decision.class.getName() , null);
	// 		agcontr.start();
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 	}
// }
}