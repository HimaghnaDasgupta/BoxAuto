package box.auto.controller

//import box.auto.Box
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletResponse

@Controller
class Controllers {
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

    @GetMapping(value = ["/board-{n}"])
    fun index(@PathVariable n:Int, map: ModelMap): String {
        map["n"] = n
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