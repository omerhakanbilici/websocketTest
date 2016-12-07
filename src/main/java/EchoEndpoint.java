import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hbilici on 2016-12-01.
 */
@ServerEndpoint("/echo")
public class EchoEndpoint {

    private static Set<Session> staticSessionSet = new HashSet<Session>();


    @OnOpen
    public void onOpen(Session session) throws IOException {
        session.getBasicRemote().sendText("Session id: " + session.getId() + " - onOpen is invoked.");
        staticSessionSet.add(session);
    }

    @OnMessage
    public String echo(String message, Session session) {
        return "Session id: " + session.getId() + " message: " + message + " (from server)";
    }

    @OnError
    public void onError(Throwable t, Session session) {
        staticSessionSet.remove(session);
        t.printStackTrace();
    }

    @OnClose
    public void onClose(Session session) {
        staticSessionSet.remove(session);
        System.out.println("Close connection for client: " + session.getId());
    }

}