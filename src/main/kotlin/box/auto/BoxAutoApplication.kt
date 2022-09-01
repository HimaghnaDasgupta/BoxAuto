package box.auto

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BoxAutoApplication

fun main(args: Array<String>) {
	runApplication<BoxAutoApplication>(*args)
	var box = Box(2)
	box.print()
	println(box.player)
	box = box.setOccupiedLine(0)
	box.print()
	println(box.player)
	box = box.setOccupiedLine(1)
	box.print()
	println(box.player)
	box = box.setOccupiedLine(4)
	box.print()
	println(box.player)
	box = box.setOccupiedLine(6)
	box.print()
	println(box.player)
	box = box.setOccupiedLine(3)
	box.print()
	println(box.player)
	box = box.setOccupiedLine(2)
	box.print()
	println(box.player)
	box = box.setOccupiedLine(5)
	box.print()
	println(box.player)
	box = box.setOccupiedLine(9)
	box.print()
	println(box.player)
	box = box.setOccupiedLine(11)
	box.print()
	println(box.player)
	box = box.setOccupiedLine(8)
	box.print()
	println(box.player)
	box = box.setOccupiedLine(7)
	box.print()
	println(box.player)
	box = box.setOccupiedLine(10)
	box.print()
	println(box.player)
	println(box.winner)
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
