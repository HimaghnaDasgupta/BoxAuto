package box.auto.repositories

import box.auto.entity.BoxEntities3
import box.auto.entity.BoxRelation3
import box.auto.entity.Id
import box.auto.entity.Relation
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BoxRepositories : CrudRepository<BoxEntities3, Id> {
    fun findFirstByProcessedFalseOrderById() : BoxEntities3
    fun existsByProcessedFalse(): Boolean
    fun countByProcessedFalse(): Long
    fun countByProcessedTrue(): Long
    fun findByProps(props: Id): BoxEntities3
    fun existsByProps(props:Id) : Boolean

}

@Repository
interface BoxRelationRepository: CrudRepository<BoxRelation3, Relation>