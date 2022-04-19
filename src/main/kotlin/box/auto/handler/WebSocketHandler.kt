package box.auto.handler

import box.auto.Box
import box.auto.BoxPlayer
import com.fasterxml.jackson.databind.ObjectMapper
import lombok.SneakyThrows
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.io.IOException
import java.util.concurrent.CopyOnWriteArrayList
import java.util.function.Consumer

@Component
class MyTextWebSocketHandler : TextWebSocketHandler() {
    private val sessions: MutableList<WebSocketSession?> = CopyOnWriteArrayList()
    private val users = mutableListOf<String?>()
    private val ns = mutableListOf<Int?>()
    private val groups = mutableListOf<List<Int>>()
    private val boxes = mutableMapOf<Int, Box>()
    private val playerIndex = mutableMapOf<Int, BoxPlayer>()
    private val playerTurn = mutableMapOf<Int, BoxPlayer>()

    @SneakyThrows(Exception::class)
    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions.add(session)
        users.add(null)
        ns.add(null)
        super.afterConnectionEstablished(session)
    }

    @SneakyThrows(Exception::class)
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val uid = sessions.indexOf(session)
        sessions[uid] = null
        users[uid] = null
        val mapper = ObjectMapper()

        val otherIdleUsers = users.indices.filter { !groups.any { g->g.any { i->i==it } } && it!=uid && users[it]!=null}
        otherIdleUsers.map {
            sessions[it]
        }.forEach { webSocketSession:WebSocketSession? ->
            try {
                webSocketSession!!.sendMessage(TextMessage(mapper.writeValueAsString(Message(MessageType.removeIdlePlayer, Player(uid, users[uid]!!)))))
            } catch (e: IOException) {
                log.error("Error occurred.", e)
            }
        }

        val otherBusyUsers = groups.filter { g->g.any { i->i==uid } }.flatten().filter { it!=uid }
        otherBusyUsers.map {
            sessions[it]
        }.forEach { webSocketSession:WebSocketSession? ->
            try {
                webSocketSession!!.sendMessage(TextMessage(mapper.writeValueAsString(Message(MessageType.leftGame, Player(uid, users[uid]!!)))))
            } catch (e: IOException) {
                log.error("Error occurred.", e)
            }
        }

        groups.remove(groups.first { it.contains(uid) })

        users[uid] = null
        super.afterConnectionClosed(session, status)
    }

    @SneakyThrows(Exception::class)
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        super.handleTextMessage(session, message)
        val uid = sessions.indexOf(session)
        val msg =  message.payload
        val mapper = ObjectMapper()
        val m = mapper.readValue(msg, Message::class.java)
        println(m.message)
        when(m.message) {

            MessageType.setN -> {
                ns[uid] = m.value as Int
            }


            MessageType.register -> {
                users[uid]= m.value.toString()
                val otherIdleUsers = users.indices.filter { !groups.any { g->g.any { i->i==it } } && it!=uid && users[it]!=null && ns[it]==ns[uid]}
                println("$uid -> $otherIdleUsers")
                try {
                    session.sendMessage(TextMessage(mapper.writeValueAsString(Message(MessageType.playerId, uid))))
                } catch (e: IOException) {
                    log.error("Error occurred.", e)
                }
                try {
                    session.sendMessage(TextMessage(mapper.writeValueAsString(Message(MessageType.sendIdlePlayers, otherIdleUsers.map { Player(it, users[it]!!) }))))
                } catch (e: IOException) {
                    log.error("Error occurred.", e)
                }
                otherIdleUsers.map {
                    sessions[it]
                }.forEach { webSocketSession:WebSocketSession? ->
                    try {
                        webSocketSession!!.sendMessage(TextMessage(mapper.writeValueAsString(Message(MessageType.sendIdlePlayers, arrayOf(Player(uid, users[uid]!!))))))
                    } catch (e: IOException) {
                        log.error("Error occurred.", e)
                    }
                }
            }


            MessageType.selectPlayer -> {
                try {
                    sessions[m.value as Int]!!.sendMessage(TextMessage(mapper.writeValueAsString(Message(MessageType.confirmAccept, Player(uid, users[uid]!!)))))
                } catch (e: IOException) {
                    log.error("Error occurred.", e)
                }
            }


            MessageType.createGroup -> {
                val group = m.value as List<Int>
                val n = group.last()

                val box = Box(n)

                group.indices.filter { it<group.size-1 }.forEach {
                    try {
                        boxes[group[it]] = box
                        playerIndex[group[it]] = BoxPlayer.values()[it+1]
                        playerTurn[group[it]] = BoxPlayer.A
                    } catch (e: IOException) {
                        log.error("Error occurred.", e)
                    }
                }
                groups.add(listOf(group[0], group[1]))
                val playerNames = arrayOf("", users[group[0]], users[group[1]])
                try {
                    sessions[group[0]]!!.sendMessage(TextMessage(mapper.writeValueAsString(Message(MessageType.turn, BoxStatus(box.lines, box.boxes.map { playerNames[it.ordinal]!!})))))
                } catch (e: IOException) {
                    log.error("Error occurred.", e)
                }

            }


            MessageType.selectLine -> {
                val newBox = boxes[uid]!!.setOccupiedLine(m.value as Int)
                val group = groups.first { it.contains(uid) }
                val playerNames = arrayOf("", users[group[0]], users[group[1]])
                playerTurn[uid] = newBox.player
                group.forEach { boxes[it] = newBox }
                if(newBox.winner==BoxPlayer.None || newBox.winner == null) {
                    if(playerIndex[uid]==newBox.player) {
                        sessions[uid]!!.sendMessage(TextMessage(mapper.writeValueAsString(Message(MessageType.turn, BoxStatus(newBox.lines, newBox.boxes.map { playerNames[it.ordinal]!!})))))
                        sessions[group.first { it!=uid }]!!.sendMessage(TextMessage(mapper.writeValueAsString(Message(MessageType.update, newBox.boxes.map { playerNames[it.ordinal]!!}))))
                    } else {
                        sessions[group.first { it!=uid }]!!.sendMessage(TextMessage(mapper.writeValueAsString(Message(MessageType.turn, BoxStatus(newBox.lines, newBox.boxes.map { playerNames[it.ordinal]!!})))))
                        sessions[uid]!!.sendMessage(TextMessage(mapper.writeValueAsString(Message(MessageType.update, newBox.boxes.map { playerNames[it.ordinal]!!}))))
                    }
                } else {
                    group.forEach {
                        sessions[it]!!.sendMessage(TextMessage(mapper.writeValueAsString(Message(MessageType.turn, BoxStatus(newBox.lines, newBox.boxes.map { player->playerNames[player.ordinal]!!})))))
                        sessions[it]!!.sendMessage(TextMessage(mapper.writeValueAsString(Message(MessageType.winner, users[group.first { id->playerIndex[id]==newBox.winner }]))))
                    }

                }
            }
        }

    }
    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }
}

enum class MessageType {
    setN,
    register,
    playerId,
    sendIdlePlayers,
    removeIdlePlayer,
    selectPlayer,
    confirmAccept,
    createGroup,
    selectLine,
    leftGame,
    turn,
    update,
    winner
}

class BoxStatus(val lines: List<Boolean>, val boxes: List<String>)

class Player(val index:Int, val name: String)

class Message(var message: MessageType?, var value: Any?) {
    constructor(): this(null, null)
}