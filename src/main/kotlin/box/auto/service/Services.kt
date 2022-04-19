//package box.auto.service
//
//import box.auto.Box
//import box.auto.entity.*
//import box.auto.repositories.*
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.context.ApplicationContext
//import org.springframework.context.ApplicationContextAware
//import org.springframework.context.annotation.DependsOn
//import org.springframework.stereotype.Service
//import javax.annotation.PostConstruct
//import javax.transaction.Transactional
//
//
//abstract class BoxServices< T: BoxRepositories<E>, R: BoxRelationRepository<F, @UnsafeVariance E>, out E: BoxEntities, out F: BoxRelation<E>> {
//
//
//     abstract fun getBoxRepo(): T
//     abstract fun getBoxRelRepo(): R
//     abstract fun getN(): Int
//
//    fun poll(): Box<*> {
//        val entity = getBoxRepo().findFirstByProcessedFalseOrderById()
//        return Box<BoxServices<T, R, E, F>>(getN(), entity.props.line, entity.props.box, entity.props.player )
//    }
//
//    fun isNotEmpty():Boolean = getBoxRepo().existsByProcessedFalse()
//
//    fun isEmpty():Boolean = !isNotEmpty()
//
//    fun having(box: Box<BoxServices<*, *, *, *>>):Boolean = getBoxRepo().existsByProps(Id(box.line, box.box, box.player))
//
//    @Transactional
//    fun processing(box: Box<BoxServices<*, *, *, *>>)  {
//        val entity = getBoxRepo().findByProps(Id(box.line, box.box, box.player))
//        entity.processed = true
//        getBoxRepo().save(entity)
//    }
//
//    @Transactional
//    fun addChild(parent: Box<BoxServices<*, *, *, *>>, child: Box<BoxServices<*, *, *, *>>) {
//        val p = getBoxRepo().findByProps(Id(parent.line, parent.box, parent.player))
//        val c = getBoxRepo().findByProps(Id(child.line, child.box, child.player))
//
//        val relation = Relation(p, c)
//
//        getBoxRelRepo().save(getRelation(relation)!!)
//    }
//
//    @Transactional
//    fun save(box: Box<BoxServices<*, *, *, *>>) {
//        getBoxRepo().save(getEntity(Id(box.line, box.box, box.player)))
//    }
//
//    abstract fun getRelation(id: Relation<@UnsafeVariance E>): F
//    abstract fun getEntity(id:Id): E
//
//    fun processing():Long = getBoxRepo().countByProcessedTrue()
//
//    fun pending(): Long = getBoxRepo().countByProcessedFalse()
//
//    fun all():List<Box<BoxServices<T, R, E, F>>> = getBoxRepo().findAll().map { Box(getN(), it.props.line, it.props.box, it.props.player ) }
//}
//
//@Service
//@DependsOn(value = ["box2Repositories", "box2RelationRepository"])
//class Box2Services: BoxServices<Box2Repositories, Box2RelationRepository, BoxEntities2, BoxRelation2>() {
//    @Autowired
//    lateinit var box2Repositories: Box2Repositories
//    @Autowired
//    lateinit var box2RelationRepository: Box2RelationRepository
//
//    override fun getN(): Int {
//        return 2
//    }
//
//    override fun getBoxRepo(): Box2Repositories {
//        return box2Repositories
//    }
//
//    override fun getBoxRelRepo(): Box2RelationRepository {
//        return box2RelationRepository
//    }
//    override fun getRelation(id: Relation<BoxEntities2>): BoxRelation2 = BoxRelation2(id)
//
//    override fun getEntity(id: Id): BoxEntities2 = BoxEntities2(id)
//
//}
//
//@Service
//@DependsOn(value = ["box3Repositories", "box3RelationRepository"])
//class Box3Services: BoxServices<Box3Repositories, Box3RelationRepository, BoxEntities3, BoxRelation3>() {
//    @Autowired
//    lateinit var box3Repositories: Box3Repositories
//    @Autowired
//    lateinit var box3RelationRepository: Box3RelationRepository
//
//    override fun getN(): Int {
//        return 3
//    }
//
//    override fun getBoxRepo(): Box3Repositories {
//        return box3Repositories
//    }
//
//    override fun getBoxRelRepo(): Box3RelationRepository {
//        return box3RelationRepository
//    }
//
//    override fun getRelation(id: Relation<BoxEntities3>): BoxRelation3 = BoxRelation3(id)
//
//    override fun getEntity(id: Id): BoxEntities3 = BoxEntities3(id)
//
//}
//
//@Service
//@DependsOn(value = ["box4Repositories", "box4RelationRepository"])
//class Box4Services: BoxServices<Box4Repositories, Box4RelationRepository, BoxEntities4, BoxRelation4>() {
//    @Autowired
//    lateinit var box4Repositories: Box4Repositories
//    @Autowired
//    lateinit var box4RelationRepository: Box4RelationRepository
//
//    override fun getN(): Int {
//        return 4
//    }
//
//    override fun getBoxRepo(): Box4Repositories {
//        return box4Repositories
//    }
//
//    override fun getBoxRelRepo(): Box4RelationRepository {
//        return box4RelationRepository
//    }
//    override fun getRelation(id: Relation<BoxEntities4>): BoxRelation4 = BoxRelation4(id)
//
//    override fun getEntity(id: Id): BoxEntities4 = BoxEntities4(id)
//
//}
//
//@Service
//@DependsOn(value = ["box5Repositories", "box5RelationRepository"])
//class Box5Services: BoxServices<Box5Repositories, Box5RelationRepository, BoxEntities5, BoxRelation5>() {
//    @Autowired
//    lateinit var box5Repositories: Box5Repositories
//    @Autowired
//    lateinit var box5RelationRepository: Box5RelationRepository
//
//    override fun getN(): Int {
//        return 5
//    }
//
//    override fun getBoxRepo(): Box5Repositories {
//        return box5Repositories
//    }
//
//    override fun getBoxRelRepo(): Box5RelationRepository {
//        return box5RelationRepository
//    }
//    override fun getRelation(id: Relation<BoxEntities5>): BoxRelation5 = BoxRelation5(id)
//
//    override fun getEntity(id: Id): BoxEntities5 = BoxEntities5(id)
//
//}
