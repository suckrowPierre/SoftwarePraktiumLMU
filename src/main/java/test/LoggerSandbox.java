package test;


import org.tinylog.Logger;
import util.LoggingTags;
import util.PPLogger;

public class LoggerSandbox {

    public static void main(String[] args) {
        Logger.info("Wasssssup");
        Logger.tag(LoggingTags.communication.toString()).info(PPLogger.logCommunicationReceiving, "hey");
        Logger.tag(LoggingTags.communication.toString()).info(PPLogger.logCommunicationSending, "was geht ab?");
        Logger.tag(LoggingTags.game.toString()).info("gaming stuff");
        Logger.tag(LoggingTags.gui.toString()).info("gui stuff");
    }
}
