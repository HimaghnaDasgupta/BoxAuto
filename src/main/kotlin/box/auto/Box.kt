package box.auto

import box.auto.service.BoxServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.io.OutputStream
import java.util.Properties
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

enum class Player(val char: Char) {
    None('?'),
    A('A'),
    B('B');
    fun opponent() = when(this) {
        None -> None
        A ->  B
        B -> A
    }
}

enum class LineType {
    Horizontal,
    Vertical
}

@Service
class Box (val n: Int = 3, val line:Long=0, var box: Long=0, val player: Player = Player.A, val children: MutableSet<Box> = mutableSetOf<Box>()) {
    constructor(): this(3)
    var winner:Player? = null

    init {
        (0 until n*n).forEach {
            if (boundaryLinesOfBox(it).all { l -> isOccupiedLine(l) } && getBoxOccupiedBy(it)==Player.None) {
                setBoxOccupiedBy(it, player)
            }
        }
        val map = mutableMapOf(Pair(Player.None, 0), Pair(Player.A, 0), Pair(Player.B, 0))
        (0 until n*n).map { getBoxOccupiedBy(it) }.forEach { map[it] = map[it]!! + 1 }
        val max = map.keys.maxOf { map[it]!! }
        if(max > n*n/2) {
            winner = map.keys.first { map[it] == max }
        }
    }

    fun avaiableLines(): List<Int> {
        return (0 until 2*n*(n+1)).filter { !isOccupiedLine(it) }
    }

    fun lineType(int: Int): LineType = if((int % (2*n+1)) < n) LineType.Horizontal else LineType.Vertical

    fun isBoundaryLine(int: Int): Boolean = int<n+1 || int>= (n*(2*n+1)) || int% (2* n +1)== 2*n

    fun adjucentBoxes(int: Int): List<Int> {
        var box1:Int
        var box2: Int
        return when(lineType(int)) {
            LineType.Horizontal -> {
                box1 = n * (int % (2 * n + 1)) + int / (2 * n + 1)
                box2 = box1 + n
                if (box1 >= 0 && box2 >= 0) {
                    listOf(box1, box2)
                } else if (box1 >= 0) {
                    listOf(box1)
                } else {
                    listOf(box2)
                }
            }
            else-> {
                box1 = n * (int % (2 * n + 1) - n) + int / (2 * n + 1)
                box2 = box1 -1
                if (box1 >= 0 && box2 >= 0) {
                    listOf(box1, box2)
                } else if (box1 >= 0) {
                    listOf(box1)
                } else {
                    listOf(box2)
                }
            }
        }
    }

    fun isOccupiedLine(int: Int): Boolean = (line and  (1L shl int)) > 0

    fun setOccupiedLine(int: Int): Box = Box(n, line or  (1L shl int), box, player.opponent())

    fun boundaryLinesOfBox(int: Int): List<Int> {
        val top = (2*n+1)* (int/n)+ int%n
        return listOf(
            top,
            top + n,
            top+n+1,
            top+ (2*n+1)
        )
    }

    fun getBoxOccupiedBy(int: Int): Player {
        val p = ((box and (3L shl (int * 2))) shr (int * 2)).toInt()
        if(p>2) {
            println()
        }
        return Player.values()[p]
    }

    fun setBoxOccupiedBy(int: Int, player: Player): Unit {
        box = (box and (3L shl (int * 2)).inv()) or (player.ordinal.toLong() shl (int * 2))
    }

    override fun toString(): String {
        return "B${line}_${box}_${player.ordinal}"
    }

    @Synchronized fun print() {
        (0 until 2*n*(n+1)).forEach {

            if(lineType(it)==LineType.Horizontal)
                if(isOccupiedLine(it))
                    print(" ---")
                else
                    print("    ")
            if(lineType(it)==LineType.Vertical) {
                if(it%(2*n+1)==n) println()
                else {
                    print(" ${getBoxOccupiedBy(it/(2*n+1)+it%(2*n+1)-n -1).char} ")
                }
                if(isOccupiedLine(it))
                    print("|")
                else
                    print(" ")
                if(it%(2*n+1)==2*n) println()
            }
        }
        println()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Box

        if (n != other.n) return false
        if (line != other.line) return false
        if (box != other.box) return false
        if (player != other.player) return false

        return true
    }

    override fun hashCode(): Int {
        var result = n
        result = 31 * result + line.hashCode()
        result = 31 * result + box.hashCode()
        result = 31 * result + player.hashCode()
        return result
    }
    @Autowired
    lateinit var boxServices: BoxServices

    val processed: Long
        get() = boxServices.processing()

    val pending : Long
        get() = boxServices.pending()

    val services: ExecutorService = Executors.newFixedThreadPool(1000)

    fun write(outputStream: OutputStream) {
        val p = Properties()
        boxServices.all().forEach {
            p["$it"] = it.children.joinToString(",") { c->"$c" }
        }

        p.save(outputStream, "Box")

    }

    fun calculate() {
        boxServices.save(Box(3))

        while (boxServices.isNotEmpty()) {
            val box = boxServices.poll()

            if(box.winner==null ||box.winner==Player.None ) {
                //box.print()
                services.run {
                    for (line in box.avaiableLines()) {
                        val newBox = box.setOccupiedLine(line)
                        //newBox.print()
                        if (!boxServices.having(newBox)) {
                            boxServices.save(newBox)
                        }
                        boxServices.addChild(box, newBox)
                    }

                }
            }
            boxServices.processing(box)

        }
    }

}