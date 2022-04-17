package box.auto.entity

import box.auto.Player
import javax.persistence.*

@Entity
@Table
class BoxEntities3(
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Embedded val props: Id,
    var processed: Boolean = false
) {
    constructor() : this(1, Id(),)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BoxEntities3

        if (props != other.props) return false

        return true
    }

    override fun hashCode(): Int {
        return props.hashCode()
    }

}

@Entity
@Table
class BoxRelation3(@EmbeddedId val id: Relation?) {
    constructor() : this(null)
}

@Embeddable
class Relation(
    @OneToOne
    @JoinColumn(name = "parent", referencedColumnName = "id")
    val parent: BoxEntities3?,
    @OneToOne
    @JoinColumn(name = "child", referencedColumnName = "id")
    val child: BoxEntities3?
) : java.io.Serializable {
    constructor() : this(null, null)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Relation

        if (parent != other.parent) return false
        if (child != other.child) return false

        return true
    }

    override fun hashCode(): Int {
        var result = parent?.hashCode() ?: 0
        result = 31 * result + (child?.hashCode() ?: 0)
        return result
    }


}

@Embeddable
class Id(
    val line:Long,
    var box: Long,
    val player: Player) {
    constructor() : this(0,0 ,Player.A)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Id

        if (line != other.line) return false
        if (box != other.box) return false
        if (player != other.player) return false

        return true
    }

    override fun hashCode(): Int {
        var result = line.hashCode()
        result = 31 * result + box.hashCode()
        result = 31 * result + player.hashCode()
        return result
    }

}