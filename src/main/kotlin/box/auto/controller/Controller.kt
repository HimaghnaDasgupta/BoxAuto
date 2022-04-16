package box.auto.controller

import box.auto.Box
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletResponse

@Controller
class Controllers {
    @Autowired
    lateinit var box: Box
    var running = true

    @PostConstruct
    fun init() {
        Thread {
            box.calculate()
            running = false
        }.start()

    }

    @GetMapping(value = ["/download"])
    @ResponseBody
    fun download(response: HttpServletResponse) {
        if(running) {
            response.outputStream.write("Running (Processed: ${box.processed}, pending: ${box.pending})".toByteArray())
        } else {
            box.write(response.outputStream)
        }
    }
}