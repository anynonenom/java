package Menu;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;

public class Main_Agent {
	
    public static void main(String[] args) {
        Runtime rt = Runtime.instance();
		ProfileImpl pr = new ProfileImpl();
		pr.setParameter(ProfileImpl.MAIN_HOST,"localhost");
		pr.setParameter(ProfileImpl.ACCEPT_FOREIGN_AGENTS,"true");
		pr.setParameter(ProfileImpl.CONTAINER_NAME, "Menu Principal");
		pr.setParameter(ProfileImpl.GUI, "true");
		
		AgentContainer maincont = rt.createMainContainer(pr); 
		try {
			maincont.start();    
		} catch (ControllerException e) {
			e.printStackTrace();
		}
    }
}
  