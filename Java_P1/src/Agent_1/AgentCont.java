package Menu;

import Agent_Decisionnel.Decision;

import Agent_Detection.Detecteur;
import Agent_Historique.Historique;
import Agent_Recuperateur.Recuperateur;
import Reduire_Agent.Reduire;
import Remplacer_Agent.Remplacer;
import Reprogrammer_Agent.Reprogrammer;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class AgentCont {
	 public static void main(String[] args) {
	        try {
				Runtime rt = Runtime.instance();
				ProfileImpl  pr = new ProfileImpl(false);
				pr.setParameter(ProfileImpl.MAIN_HOST, "localhost");
				pr.setParameter(ProfileImpl.MTPS, "jade.mtp.http.MessageTransportProtocol");
	            pr.setParameter(ProfileImpl.CONTAINER_NAME, "Agents");
				pr.setParameter(ProfileImpl.ACCEPT_FOREIGN_AGENTS, "true");
				AgentContainer agcont =  rt.createAgentContainer(pr);
				AgentController ag = agcont.createNewAgent("Historique", Historique.class.getName(), null);
		        ag.start();
			    AgentController red =  agcont.createNewAgent("Reduire" , Reduire.class.getName() , null);
			    red.start();
		        AgentController rep =  agcont.createNewAgent("Reprogrammer" , Reprogrammer.class.getName() , null);
		        rep.start();
			    AgentController rem=  agcont.createNewAgent("Remplacer" , Remplacer.class.getName() , null);
		        rem.start();
				Thread.sleep(1000);
				AgentController agcontr =  agcont.createNewAgent("Recuperateur" , Recuperateur.class.getName() , null);
				agcontr.start();
				Thread.sleep(1000);
		        AgentController det =  agcont.createNewAgent("Detecteur" , Detecteur.class.getName() , null);
				det.start();
				AgentController dec =  agcont.createNewAgent("Decision" , Decision.class.getName() , null);
				dec.start();
			

		      

			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	
}
