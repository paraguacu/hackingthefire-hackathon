package bombeiros.mg.gov.br.thefirecall.utils;

import android.app.Application;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class SocketIO extends Application {

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://172.10.100.78:8000/chat");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
