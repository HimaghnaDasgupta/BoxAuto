package box.auto

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BoxAutoApplication

fun main(args: Array<String>) {
	runApplication<BoxAutoApplication>(*args)

//	val box = Box(3)
//	val lines = box.boundaryLinesOfBox(0)
//	var b =box
//	lines.forEach {
//		b = b.setOccupiedLine(it)
//	}
//
//	b.print()
	//println(lines)

//	Box.calculate()
}
