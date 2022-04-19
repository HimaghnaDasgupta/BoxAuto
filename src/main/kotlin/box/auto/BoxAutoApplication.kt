package box.auto

//import box.auto.repositories.Box2Repositories
import jdk.nashorn.internal.objects.Global
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BoxAutoApplication

fun main(args: Array<String>) {
	runApplication<BoxAutoApplication>(*args)
	var box = Box(5)
	box.print()
	println(box.player)
	box = box.setOccupiedLine(0)
	box.print()
	println(box.player)
	box = box.setOccupiedLine(5)
	box.print()
	println(box.player)
	box = box.setOccupiedLine(6)
	box.print()
	println(box.player)
	box = box.setOccupiedLine(11)
	box.print()
	println(box.player)


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
