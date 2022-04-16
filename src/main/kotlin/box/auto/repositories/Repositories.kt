package box.auto.repositories

import box.auto.Box
import box.auto.entity.BoxEntities
import box.auto.entity.BoxRelation
import box.auto.entity.Id
import box.auto.entity.Relation
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BoxRepositories : CrudRepository<BoxEntities, Id> {
    fun findFirstByProcessedFalseOrderById() : BoxEntities
    fun existsByProcessedFalse(): Boolean
    fun countByProcessedFalse(): Long
    fun countByProcessedTrue(): Long
    fun findByProps(props: Id): BoxEntities
    fun existsByProps(props:Id) : Boolean

}

@Repository
interface BoxRelationRepository: CrudRepository<BoxRelation, Relation>