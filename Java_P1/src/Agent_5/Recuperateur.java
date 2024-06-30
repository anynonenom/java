package Agent_Recuperateur;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

import jade.core.AID;
import jade.core.Agent;

// Container Agent :
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

/**
 * Récuperateur
 */
public class Recuperateur extends Agent  {
  // --------------**---------------

    private Random rand = new Random() ;
    // les donnes de Maison
    private DataBase db  ;
    // Stocker les donneés dans chaque tableau avec leur id 
    private Hashtable<Integer, Integer> data_consom = new Hashtable<Integer , Integer>() ;
    private Hashtable<Integer, String> data_appareil = new Hashtable<Integer , String>() ;
    private Hashtable<Integer, Integer> data_heur = new Hashtable<Integer , Integer>() ;
    // Quelque Nouvelle Consommation  
    // par exempl des nouvelle aleatoire consommation : 
    private int r[] = { 781, 501  , 801 ,  3002 , 102 , 99 , 1500} ;
    // pour stocker l'elemnt qui consom plus
    private int rand_new_watt;
    // iteration 
    Enumeration<Integer>d ; // Analyser les calcules
    @Override
    protected void setup() {// start setup

        // ddémmarge cette Agent 
        System.out.println("\n INFO: ---------------------------------- ");
        System.out.println("Démmarer L'Agent Récuperateur");

        // Commencer de récuperer les donneés dans ce database
        db = new DataBase(this) ;

    // attende quelque second 
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    // ajouter le comportement pour que le message va envoyer une seul fois 
        addBehaviour(new TickerBehaviour(this , 5000) {
            @Override
            public void onTick() {// start Action 
        //     addBehaviour(new OneShotBehaviour() {
                
     
                
        // public void action() {
          try {
            // Envoyer le message au Detecteur 
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM) ;
            msg.setConversationId("id-watt");
            msg.addReceiver(new AID("Detecteur" , AID.ISLOCALNAME));
            // Analyser  des appareil plus consommer
            System.out.println("\n INFO: ---------------------------------- ");
                    d=data_consom.keys();
                    while (d.hasMoreElements()) {// start loop
                       int j = d.nextElement();
                        int old_consom = Integer.valueOf( data_consom.get(j).toString()) ;// conom watt
                        // la Nouvelle  Consommation de quelque Appareill
                        rand_new_watt =  r[rand.nextInt(r.length)]  ;
                       if (rand_new_watt > old_consom ) {
                        System.out.println("id watt : " + j + "\t\t Appareill : " + data_appareil.get(j)+" watt :"+rand_new_watt);
                        msg.setContent(" | "+j+" | "+data_appareil.get(j)+" | "+rand_new_watt+" | "+data_heur.get(j));    
                            break;
                         }
                    }// end of iterator 
                    send(msg);
                
                
               // -------------------------------------------------------------------------
               System.out.println("\n INFO: ---------------------------------- \n");
                System.out.println("\t\t  [!] Été Envoyé au Agent Detecteur");
                // Confirmer le temps  au Memorisation : 
                     ACLMessage histoMessage = new ACLMessage(ACLMessage.CONFIRM) ;
                     histoMessage.addReceiver(new AID("Historique" , AID.ISLOCALNAME));
                     histoMessage.setConversationId("ID_Recuperateur");
                     histoMessage.setContent("Interaction :  [ Recuperateur->Detection ] ");
                     send(histoMessage);
                 System.out.println("\n INFO: ---------------------------------- \n");
                 System.out.println("\t\t  [!] Été Envoyé Agent Historique ");
        // -------------------------------------------------------------------------
            } catch (Exception e) {
                    e.printStackTrace();
            }   

        } // End of action 
           
    }); // End of OneshotBehaviour
    
    
}// End of Setup
// Récuperer les donnes des Appareil avec leur id , watt , heur par jour 
public void Recuperer_donnees(int id ,  String app , int w ,  int h ){
    // id consommation watt
          data_consom.put(id, w) ;
    // id consommation heur 
          data_heur.put(id , h) ;
    // id  d'appareil
          data_appareil.put(id, app) ;

} 
// Annoncer que cette Agent a terminé soon travaill
    @Override
    protected void takeDown() {
        System.out.println("\n INFO: ---------------------------------- \n");
        System.out.println("Agent Recuperateur a terminé");
        doDelete();
    }
// Menu Sous-Princip : 

    // public static void main(String[] args) {
    //     try {
	// 		Runtime rt = Runtime.instance();
	// 		ProfileImpl  pr = new ProfileImpl(false);
	// 		pr.setParameter(ProfileImpl.MAIN_HOST, "localhost");
	// 		pr.setParameter(ProfileImpl.MTPS, "jade.mtp.http.MessageTransportProtocol");
    //         pr.setParameter(ProfileImpl.CONTAINER_NAME, "Recuperateur");
	// 		pr.setParameter(ProfileImpl.ACCEPT_FOREIGN_AGENTS, "true");
	// 		AgentContainer agcont =  rt.createAgentContainer(pr);
	// 		AgentController agcontr =  agcont.createNewAgent("Recuperateur" , Recuperateur.class.getName() , null);
	// 		agcontr.start();
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 	}
    // }
}
