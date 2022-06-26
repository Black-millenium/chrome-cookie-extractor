package me.desu.chromecookieextractor.mapper;

import me.desu.chromecookieextractor.dto.CookiesDTO;
import me.desu.chromecookieextractor.entity.CookiesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CookiesMapper {

    @Mapping(target = "value", ignore = true)
    CookiesDTO toDto(CookiesEntity cookiesEntity);
}
