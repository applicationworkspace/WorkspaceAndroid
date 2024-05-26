package com.workspaceandroid.data.mappers

interface EntityMapper<Entity, DomainModel>{

    fun mapFromEntity(entity: Entity): DomainModel =
        throw IllegalStateException("Not supported!")

    fun mapToEntity(domainModel: DomainModel): Entity =
        throw IllegalStateException("Not supported!")

    fun mapListTo(model: List<DomainModel>): List<Entity> = model.map { mapToEntity(it) }
    fun mapListFrom(model: List<Entity>): List<DomainModel> = model.map { mapFromEntity(it) }
}