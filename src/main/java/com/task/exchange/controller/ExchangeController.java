package com.task.exchange.controller;

import com.task.exchange.dto.Commission;
import com.task.exchange.dto.CommissionMapper;
import com.task.exchange.dto.ExchangeRate;
import com.task.exchange.dto.ExchangeRateMapper;
import com.task.exchange.model.CommissionEntity;
import com.task.exchange.model.Error;
import com.task.exchange.model.ExchangeRateEntity;
import com.task.exchange.model.ExchangeRequest;
import com.task.exchange.service.ExchangeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;
    @Autowired
    private CommissionMapper commissionMapper;
    @Autowired
    private ExchangeRateMapper exchangeRateMapper;

    @ApiOperation(value = "Получить список установленных комиссий.", response = Commission.class, responseContainer = "List", authorizations = {
            @Authorization(value = "basicAuth")}, tags = {"commissions",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Commission.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Unauthorized")})
    @RequestMapping(value = "/commissions",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<List<Commission>> getCommissions() {
        List<Commission> commissions = exchangeService.retrieveCommissions().stream()
                .map(commission -> commissionMapper.toDto(commission))
                .collect(Collectors.toList());
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @ApiOperation(value = "Установить значение комиссии для валютной пары.", response = Commission.class, authorizations = {
            @Authorization(value = "basicAuth")}, tags = {"commissions",})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Commission.class),
            @ApiResponse(code = 400, message = "Error", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized")})
    @RequestMapping(value = "/commissions",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<Commission> saveCommission(@ApiParam(value = "commission", required = true) @Valid @RequestBody Commission commission) {
        CommissionEntity newCommission = exchangeService.saveCommission(commissionMapper.toEntity(commission));
        return new ResponseEntity<>(commissionMapper.toDto(newCommission), HttpStatus.OK);
    }

    @ApiOperation(value = "Получить все курсы обмена валют.", response = ExchangeRate.class, responseContainer = "List", authorizations = {
            @Authorization(value = "basicAuth")}, tags = {"exchange-rates",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ExchangeRate.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Unauthorized")})
    @RequestMapping(value = "/exchange-rates",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<List<ExchangeRate>> getExchangeRates() {
        List<ExchangeRate> exchangeRates = exchangeService.retrieveExchangeRates().stream()
                .map(rate -> exchangeRateMapper.toDto(rate))
                .collect(Collectors.toList());
        return new ResponseEntity<>(exchangeRates, HttpStatus.OK);
    }

    @ApiOperation(value = "Установить курс обмена валют по валютной паре. Курс обртаной пары должен быть установлен автоматически.", response = ExchangeRate.class, authorizations = {
            @Authorization(value = "basicAuth")}, tags = {"exchange-rates",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ExchangeRate.class),
            @ApiResponse(code = 400, message = "Error", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized")})
    @RequestMapping(value = "/exchange-rates",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<ExchangeRate> saveExchangeRates(@ApiParam(value = "exchangeRate", required = true) @Valid @RequestBody ExchangeRate exchangeRate) {
        ExchangeRateEntity newExchangeRate = exchangeService.saveExchangeRate(exchangeRateMapper.toEntity(exchangeRate));
        return new ResponseEntity<>(exchangeRateMapper.toDto(newExchangeRate), HttpStatus.OK);
    }

    @ApiOperation(value = "Запрос обмена валют.", response = ExchangeRequest.class, authorizations = {
            @Authorization(value = "basicAuth")}, tags = {"exchange",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ExchangeRequest.class),
            @ApiResponse(code = 400, message = "Error", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized")})
    @RequestMapping(value = "/exchange",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<ExchangeRequest> exchange(@ApiParam(value = "exchangeRequest", required = true) @Valid @RequestBody ExchangeRequest exchangeRequest) {
        return new ResponseEntity<>(exchangeService.exchange(exchangeRequest), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Error> customExceptionHandler(Exception ex) {
        return new ResponseEntity<>(new Error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
