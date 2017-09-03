package penkit

import org.eclipse.jetty.websocket.api.*
import org.eclipse.jetty.websocket.api.annotations.*
import penkit.db.DataAccessLayer
import java.util.concurrent.CopyOnWriteArrayList
import com.github.salomonbrys.kotson.*
import com.google.gson.GsonBuilder
import penkit.db.Entry

@WebSocket
class Socket(private val db: DataAccessLayer?) {

    private val sessions = CopyOnWriteArrayList<Session>()
    private val gson = GsonBuilder().create()

    @OnWebSocketConnect
    @Throws(Exception::class)
    fun onConnect(user: Session) {
        
        sessions.add(user)
    }

    @OnWebSocketClose
    fun onClose(user: Session, statusCode: Int, reason: String) {
        sessions.remove(user)
    }

    @OnWebSocketMessage
    fun onMessage(user: Session, message: String) {
        /*
        val entry = gson.fromJson<Entry>(message)
        println(entry)
        */
        user.remote.sendString(message)
    }
}