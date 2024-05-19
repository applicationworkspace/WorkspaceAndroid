package com.workspaceandroid.data.mappers

interface EntityMapper<Entity, DomainModel>{

    fun mapFromEntity(entity: Entity): DomainModel = throw IllegalStateException("Not supported!")

    fun mapToEntity(domainModel: DomainModel): Entity = throw IllegalStateException("Not supported!")
}