package box.auto.service

import box.auto.Box
import box.auto.entity.BoxEntities3
import box.auto.entity.BoxRelation3
import box.auto.entity.Id
import box.auto.entity.Relation
import box.auto.repositories.BoxRelationRepository
import box.auto.repositories.BoxRepositories
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class BoxServices {

    @Autowired
    lateinit var boxRepositories: BoxRepositories

    @Autowired
    lateinit var boxRelationRepository: BoxRelationRepository

    fun poll(): Box {
        val entity = boxRepositories.findFirstByProcessedFalseOrderById()
        return Box(3, entity.props.line, entity.props.box, entity.props.player )
    }

    fun isNotEmpty():Boolean = boxRepositories.existsByProcessedFalse()

    fun isEmpty():Boolean = !isNotEmpty()

    fun having(box: Box):Boolean = boxRepositories.existsByProps(Id(box.line, box.box, box.player))

    @Transactional
    fun processing(box: Box)  {
        val entity = boxRepositories.findByProps(Id(box.line, box.box, box.player))
        entity.processed = true
        boxRepositories.save(entity)
    }

    @Transactional
    fun addChild(parent: Box, child: Box) {
        val p = boxRepositories.findByProps(Id(parent.line, parent.box, parent.player))
        val c = boxRepositories.findByProps(Id(child.line, child.box, child.player))

        val relation = Relation(p, c)

        boxRelationRepository.save(BoxRelation3(relation))
    }

    @Transactional
    fun save(box: Box) {
        boxRepositories.save(BoxEntities3(props = Id(box.line, box.box, box.player)))
    }

    fun processing():Long = boxRepositories.countByProcessedTrue()

    fun pending(): Long = boxRepositories.countByProcessedFalse()

    fun all():List<Box> = boxRepositories.findAll().map { Box(3, it.props.line, it.props.box, it.props.player ) }
}