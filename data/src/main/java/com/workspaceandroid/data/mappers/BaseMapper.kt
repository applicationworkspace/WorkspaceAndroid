package com.workspaceandroid.data.mappers

interface EntityMapper<InModel, OutModel>{

    fun mapFromEntity(entity: InModel): OutModel =
        throw IllegalStateException("Not supported!")

    fun mapToEntity(domainModel: OutModel): InModel =
        throw IllegalStateException("Not supported!")

    fun mapListTo(model: List<OutModel>): List<InModel> = model.map { mapToEntity(it) }
    fun mapListFrom(model: List<InModel>): List<OutModel> = model.map { mapFromEntity(it) }
}