package com.task.exchange.dto;

import com.task.exchange.model.CommissionEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CommissionMapper {

    @Autowired
    private ModelMapper mapper;

    public CommissionEntity toEntity(Commission dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, CommissionEntity.class);
    }

    public Commission toDto(CommissionEntity entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, Commission.class);
    }
}
