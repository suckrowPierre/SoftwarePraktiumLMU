package communication.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import communication.protocol_v2v1.ProtocolSendObject;
import communication.protocol_v2v1.SendObjectJsonWrapper;
import org.tinylog.Logger;
import util.LoggingTags;
import util.PPLogger;

import java.io.BufferedWriter;

/**
 * Singleton to access writeOutStream and clientID of Client
 * @author Pierre-Louis Suckrow
 */
public class WritingClientSingelton {


    private static WritingClientSingelton instance;
    private  BufferedWriter outStream;
    private int clientID;


    public void setOutStream(BufferedWriter outStream){
    this.outStream = outStream;
    }



    //Threadsafe getInstance
    public static synchronized WritingClientSingelton getInstance() {
        if (instance == null) {
            instance = new WritingClientSingelton();
        }
        return instance;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public void writeOutStream(ProtocolSendObject send) {

        JsonObject jsontosend = SendObjectJsonWrapper.wrap(send);
        String tosend = new Gson().toJson(jsontosend);
        writeOutStreamString(tosend);
    }

    public void writeOutStreamString(String send) {
        try {
            Logger.tag(LoggingTags.communication.toString()).info(PPLogger.logCommunicationSending,send);
            outStream.write(send);
            outStream.newLine();
            outStream.flush();
        } catch (Exception e) {

        }
    }
}
