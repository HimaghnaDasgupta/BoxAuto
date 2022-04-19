//package box.auto.repositories
//
//import box.auto.entity.*
//import org.springframework.data.repository.CrudRepository
//import org.springframework.data.repository.NoRepositoryBean
//import org.springframework.stereotype.Repository
//
//@NoRepositoryBean
//interface BoxRepositories<out E:BoxEntities> : CrudRepository<@UnsafeVariance E, Id> {
//    fun findFirstByProcessedFalseOrderById() : E
//    fun existsByProcessedFalse(): Boolean
//    fun countByProcessedFalse(): Long
//    fun countByProcessedTrue(): Long
//    fun findByProps(props: Id): E
//    fun existsByProps(props:Id) : Boolean
//
//}
//
//@NoRepositoryBean
//interface BoxRelationRepository<out E: BoxRelation<B>, B: BoxEntities>: CrudRepository<@UnsafeVariance E, Relation<B>>
//
//
//@Repository
//interface Box2Repositories : BoxRepositories<BoxEntities2>
//
//@Repository
//interface Box2RelationRepository: BoxRelationRepository<BoxRelation2, BoxEntities2>
//
//
//@Repository
//interface Box3Repositories : BoxRepositories<BoxEntities3>
//
//@Repository
//interface Box3RelationRepository: BoxRelationRepository<BoxRelation3, BoxEntities3>
//
//
//@Repository
//interface Box4Repositories : BoxRepositories<BoxEntities4>
//
//@Repository
//interface Box4RelationRepository: BoxRelationRepository<BoxRelation4, BoxEntities4>
//
//
//@Repository
//interface Box5Repositories : BoxRepositories<BoxEntities5>
//
//@Repository
//interface Box5RelationRepository: BoxRelationRepository<BoxRelation5, BoxEntities5>
