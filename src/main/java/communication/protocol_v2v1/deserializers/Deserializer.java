package communication.protocol_v2v1.deserializers;

/**
 * Json Deserializer for package structure of ProtocolSendObjects
 * @author Pierre-Louis Suckrow
 */
public abstract class Deserializer {

    private static final String COMMUNICATION_PROTOCOL_V_1_V_0_SENDOBJECTS = "communication.protocol_v2v1.sendobjects.";


    //Is there a way to get the name of the pacakges automaticlly ?
    private static final String[] packagenames = {"actions_events_effects.", "cards.", "chat.", "communication_setup.", "game_move.", "game_move.activation_phase.", "game_move.programm_phase.", "game_move.setup_phase.", "game_move.upgrade_phase.","lobby.", "special."};

    Class getClassWithSubStructure(String name) throws ClassNotFoundException{
        Class<?> act = null;
        for (String packagename:packagenames){
            try{
                act = Class.forName(COMMUNICATION_PROTOCOL_V_1_V_0_SENDOBJECTS + packagename + name);
            }catch (ClassNotFoundException e){
                //DONOT Refactor this. Must be ignored to check if class is in other packages
            }
        }
        if(act == null){
            //if class is not in any of the packages throw exception
            throw new ClassNotFoundException("class: " + name + "does not exist");
        }
        return act;
    }


}
