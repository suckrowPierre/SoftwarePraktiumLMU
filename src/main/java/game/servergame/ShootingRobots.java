package game.servergame;

import communication.server.connectedclient.ServerConnectedPlayer;

/**
 * @author Pierre, Simon Hümmer
 */
record ShootingRobots(ServerConnectedPlayer shooter,
                      ServerConnectedPlayer target) {

    public ServerConnectedPlayer getTarget() {
        return target;
    }
}
