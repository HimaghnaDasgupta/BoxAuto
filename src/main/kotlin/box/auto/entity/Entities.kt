//package box.auto.entity
//
//import box.auto.Player
//import javax.persistence.*
//
//
//@MappedSuperclass
//open class BoxEntities(
//    @javax.persistence.Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Long? = null,
//    @Embedded val props: Id,
//    var processed: Boolean = false
//) {
//    constructor() : this(1, Id(),)
//
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//
//        other as BoxEntities3
//
//        if (props != other.props) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        return props.hashCode()
//    }
//
//}
//
//@MappedSuperclass
//open class BoxRelation<out E: BoxEntities>(@EmbeddedId val id: Relation<E>?) {
//    constructor() : this(null)
//
//    companion object {
//        inline fun <reified R: BoxRelation<E>, E: BoxEntities> new(id:Relation<E>): R? =
//            when(R::class.java) {
//                BoxRelation2::class.java -> BoxRelation2(id as Relation<BoxEntities2>) as R
//                BoxRelation3::class.java -> BoxRelation3(id as Relation<BoxEntities3>) as R
//                BoxRelation4::class.java -> BoxRelation4(id as Relation<BoxEntities4>) as R
//                BoxRelation5::class.java -> BoxRelation5(id as Relation<BoxEntities5>) as R
//                else -> null
//            }
//
//
//    }
//}
//
//@Entity
//@Table(uniqueConstraints = [UniqueConstraint(name = "unique", columnNames = ["line", "box", "player"])])
//class BoxEntities2(
//    props: Id,
//): BoxEntities(props = props) {
//    constructor() : this(Id())
//}
//
//@Entity
//@Table
//class BoxRelation2( id: Relation<BoxEntities2>?): BoxRelation<BoxEntities2>(id) {
//    constructor() : this(null)
//}
//
//@Entity
//@Table(uniqueConstraints = [UniqueConstraint(name = "unique", columnNames = ["line", "box", "player"])])
//class BoxEntities3(
//    props: Id
//): BoxEntities(props = props) {
//    constructor() : this(Id())
//}
//
//@Entity
//@Table
//class BoxRelation3(id: Relation<BoxEntities3>?): BoxRelation<BoxEntities3>(id) {
//    constructor() : this(null)
//}
//
//@Entity
//@Table(uniqueConstraints = [UniqueConstraint(name = "unique", columnNames = ["line", "box", "player"])])
//class BoxEntities4(
//    props: Id
//): BoxEntities(props = props) {
//    constructor() : this( Id(),)
//
//}
//
//@Entity
//@Table
//class BoxRelation4(id: Relation<BoxEntities4>?): BoxRelation<BoxEntities4>(id) {
//    constructor() : this(null)
//}
//
//@Entity
//@Table(uniqueConstraints = [UniqueConstraint(name = "unique", columnNames = ["line", "box", "player"])])
//class BoxEntities5(
//    props: Id
//): BoxEntities(props = props) {
//    constructor() : this( Id(),)
//
//}
//
//@Entity
//@Table
//class BoxRelation5(id: Relation<BoxEntities5>?): BoxRelation<BoxEntities5>(id) {
//    constructor() : this(null)
//}
//
//@Embeddable
//class Relation<out E: BoxEntities>(
//    @OneToOne
//    @JoinColumn(name = "parent", referencedColumnName = "id")
//    val parent: E?,
//    @OneToOne
//    @JoinColumn(name = "child", referencedColumnName = "id")
//    val child: E?
//) : java.io.Serializable {
//    constructor() : this(null, null)
//
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//
//        other as Relation<E>
//
//        if (parent != other.parent) return false
//        if (child != other.child) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        var result = parent?.hashCode() ?: 0
//        result = 31 * result + (child?.hashCode() ?: 0)
//        return result
//    }
//
//
//}
//
//@Embeddable
//class Id(
//    val line:Long,
//    var box: Long,
//    val player: Player) {
//    constructor() : this(0,0 ,Player.A)
//
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//
//        other as Id
//
//        if (line != other.line) return false
//        if (box != other.box) return false
//        if (player != other.player) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        var result = line.hashCode()
//        result = 31 * result + box.hashCode()
//        result = 31 * result + player.hashCode()
//        return result
//    }
//
//}