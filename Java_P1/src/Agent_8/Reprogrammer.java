package Reprogrammer_Agent;

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

// pour le DF et FIPA
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Reprogrammer extends Agent {
  private DFAgentDescription df  ;
   private  ServiceDescription sd  ;
   private Repro_Appareil gui ;
   String tab = null;
      // pour stocker les éléments :
      String Appareil[]={"Télé","PC" , "Lave-Ligne" ,"chauffe-eau" , "luminaire" , "Clima"};
      int watte[]={100 ,500 ,  780 , 800   , 2500 , 3000} ,heur[] ={4,2,5,12,1 ,2} ;
       // update : 
    private int [] percent = { 100 , 50 } ;
    private Random rand = new Random() ;
    // --- 
    private int [] decre = {1,2,3} ; 
    //    private DonneesMaison 
    private ArrayList prog = new ArrayList()  ; // ajouter pour 
    @Override
  protected void setup() {
      System.out.println("\n INFO: ---------------------------------- \n");
      System.out.println("\t --- Agentt reprogrammer commencer ! ---\n");
        // Montrer le mis a jour dde cette appareil : 
        gui = new Repro_Appareil();
       // DF plan jaune
       df = new DFAgentDescription() ;
      sd = new ServiceDescription() ;
      sd.setName("Reprogrammer");
      sd.setType("Solution");
      df.addLanguages("Fr");
      df.addServices(sd);
      // Enregistrer dans le df pour que le décsionel va le trouver 
        try {
            DFService.register(Reprogrammer.this, df);
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
                //
                String[] msg1= reçu_msg.getContent().split(":");

                    for (String j : msg1) {
                        if (!j.equals(":")) {
                            // effectuer les element dans arraylist :
                            prog.add(j);
                        }
                    }
               int x =Integer.valueOf(prog.get(3).toString()) ;
                System.out.print(" id: "+prog.get(1).toString()+" watt : "+prog.get(2).toString()+" Heur : "+prog.get(3).toString()) ;
                for (int i = 0; i < Appareil.length; i++) {
                    int Ameliorer = x  - decre[rand.nextInt(decre.length)] ;
                    if (i ==Integer.valueOf( prog.get(1).toString()) ) {
                     if ( Ameliorer  < 0 ) {
                        //   dnm.ShowinTable(i, Appareil[i], watt, heur[i, true);
                        System.out.println(i+""+ Appareil[i]+""+ prog.get(2).toString() +""+ Ameliorer);
                        gui.ShowinTable(i, Appareil[i], 1 , true  ); 
                       
                        //msg.addReceiver(new AID("Decision" , AID.ISLOCALNAME));
                         tab= Appareil[i];
                       
                        break;
                        }
                    else if (Ameliorer > 0) {
                      
                      System.out.println(i+""+ Appareil[i]+""+ prog.get(2).toString() +""+ Ameliorer);
                      gui.ShowinTable(i, Appareil[i], Ameliorer , true  ); 
                      tab= Appareil[i];break;

                      
                    }
                        
                    }
                    // else{
                    // //   dnm.ShowinTable(i, Appareil[i], 0, heur[i] , true);
                    // System.out.println(i+""+ Appareil[i]+""+ prog.get(2).toString() +""+ heur[i]);

                    // }
                  }
                  prog.clear();
                System.out.println("\t --> reprogrammer: "+reçu_msg.getContent());
                
                System.out.println("\t --- Reprogrammer.setup() -- \n Sender : "+reçu_msg.getSender().getLocalName()+"\nContent : "+reçu_msg.getContent());  
                   ACLMessage msg = reçu_msg.createReply() ;
                   msg.addReplyTo(reçu_msg.getSender());
                   msg.setPerformative(ACLMessage.PROPOSE);
                   msg.setConversationId("ID_3");
               
                   msg.setContent(""+percent[rand.nextInt(percent.length)]);
                   msg.setOntology("L'appareil été reprogrammés: "+tab);
                   send(msg);
                     // Confirmer le temps  au Memorisation : 

                     ACLMessage histoMessage = new ACLMessage(ACLMessage.CONFIRM) ;
                     histoMessage.addReceiver(new AID("Historique" , AID.ISLOCALNAME));
                     histoMessage.setConversationId("ID_Reprogrammer");
                     histoMessage.setContent("Interaction :  [ reprogrammer->Decisionnel ] ");
                send(histoMessage);
                    
            }
            // else {
            //     System.out.println("\t [i] --- block ---");
            // }
        }
        
      });
      

  }
  
  // ---------- Terminer Cette Agent ----------
  @Override
  protected void takeDown() {
    //   try {
          System.out.println("\n INFO: ---------------------------------- \n");
        //   DFService.deregister(this, df);
          System.out.println("\t------- Terminer cette Agent -------");
        //doDelete();
        
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
        pr.setParameter(ProfileImpl.CONTAINER_NAME, "Reprogrammant");
        pr.setParameter(ProfileImpl.ACCEPT_FOREIGN_AGENTS, "true");
        AgentContainer agcont =  rt.createAgentContainer(pr);
        AgentController agcontr =  agcont.createNewAgent("Reprogrammer" , Reprogrammer.class.getName() , null);
        agcontr.start();
    } catch (Exception e) {
        e.printStackTrace();
    }
}*/
}
