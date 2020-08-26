package com.lvshen.demo.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-21 16:17
 * @since JDK 1.8
 */
@Mapper
public interface StudentConverter {
    StudentConverter INSTANCE = Mappers.getMapper(StudentConverter.class);

    @Mappings(@Mapping(source = "name",target = "userName"))
    StudentDto vo2dto(StudentVo vo);

    List<StudentDto> listVo2dto(List<StudentVo> vos);
}
