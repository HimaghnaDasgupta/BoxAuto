package box.auto.controller

//import box.auto.Box
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import kotlin.properties.Delegates

@Controller
class Controllers {

    @Value(value = "\${box.auto.https}")
    private var https: Boolean = true
//    @Autowired
//    lateinit var boxes: List<Box<*>>
//    lateinit var running: MutableMap<Int, Boolean>
//
//    @PostConstruct
//    fun init() {
//        running = boxes.map { mutableMapOf(Pair(it.n, true)) }.reduce { acc, pair -> run {
//            acc.putAll(pair)
//            acc
//        } }
//        boxes.forEach {
//            Thread {
////                it.calculate()
//                running[it.n] = false
//            }.start()
//        }
//
//    }

    @GetMapping(value = ["/"])
    fun home(): String {
        return "home"
    }

    @PostMapping(value = ["/board-{n}"])
    fun index(@PathVariable n:Int, map: ModelMap): String {
        map["n"] = n
        map["https"] = https
        return "index"
    }

    @GetMapping(value = ["/download-{id}"])
    @ResponseBody
    fun download(@PathVariable id: Int,response: HttpServletResponse) {
//        if(running[id]!!) {
//            boxes.filter { it.n == id }.first().let {
//                response.outputStream.write("Running (Processed: ${it.processed}, pending: ${it.pending})".toByteArray())
//            }
//
//        } else {
//            boxes.filter { it.n == id }.first().let {
//                it.write(response.outputStream)
//            }
//        }
    }


}


@Controller
class WebSocketController {
    @get:RequestMapping("/websocket")
    val webSocket: String
        get() = "ws-broadcast"
}