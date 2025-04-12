package com.uniquindio.reporte.mapper;

import com.uniquindio.reporte.model.DTO.comment.CreateCommentDTO;
import com.uniquindio.reporte.model.entities.Comment;
import com.uniquindio.reporte.utils.ObjectIdMapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ObjectIdMapperUtil.class, LocationMapper.class})
public interface CommentMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "id", expression = "java(new org.bson.types.ObjectId())")
    @Mapping(target = "status", expression = "java(EnumStatusComment.ACTIVO)")
    Comment toDocumentCreate(CreateCommentDTO commentDTO);
}
