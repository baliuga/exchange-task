package com.task.exchange.dto;

import com.task.exchange.model.ExchangeRateEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ExchangeRateMapper {

    @Autowired
    private ModelMapper mapper;

    public ExchangeRateEntity toEntity(ExchangeRate dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, ExchangeRateEntity.class);
    }

    public ExchangeRate toDto(ExchangeRateEntity entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, ExchangeRate.class);
    }
}
