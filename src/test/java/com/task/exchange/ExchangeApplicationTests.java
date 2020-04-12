package com.task.exchange;

import com.task.exchange.model.*;
import com.task.exchange.repository.CommissionRepository;
import com.task.exchange.repository.ExchangeRateRepository;
import com.task.exchange.service.ExchangeServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ExchangeApplicationTests {

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @Mock
    private CommissionRepository commissionRepository;

    @InjectMocks
    private ExchangeServiceImpl exchangeService;

    final static private BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    final static private BigDecimal COMMISSION_PERCENT = BigDecimal.valueOf(15);
    final static private BigDecimal DIRECT_RATE = BigDecimal.valueOf(0.5);
    final static private BigDecimal BACK_RATE = BigDecimal.valueOf(2);

    @BeforeEach
    void init() {
        CommissionEntity commissionEntity = new CommissionEntity(CurrencyEnum.USD, CurrencyEnum.EUR, COMMISSION_PERCENT);
        given(commissionRepository.findByFromCurrencyAndToCurrency(CurrencyEnum.USD, CurrencyEnum.EUR)).willReturn(Optional.of(commissionEntity));
    }

    @Test
    void shouldReturnValidDirectExchangeValue() {
        // given
        ExchangeRequest request = new ExchangeRequest(ONE_HUNDRED, ONE_HUNDRED, CurrencyEnum.USD, CurrencyEnum.EUR, OperationTypeEnum.GIVE);
        ExchangeRateEntity directRateEntity = new ExchangeRateEntity(CurrencyEnum.USD, CurrencyEnum.EUR, DIRECT_RATE);
        given(exchangeRateRepository.findByFromCurrencyAndToCurrency(CurrencyEnum.USD, CurrencyEnum.EUR)).willReturn(Optional.of(directRateEntity));

        // when
        ExchangeRequest response = exchangeService.exchange(request);

        // then
        assertEquals(BigDecimal.valueOf(35).stripTrailingZeros(), response.getAmountTo().stripTrailingZeros());
    }

    @Test
    void shouldReturnValidBackExchangeValue() {
        // given
        ExchangeRequest request = new ExchangeRequest(ONE_HUNDRED, ONE_HUNDRED, CurrencyEnum.USD, CurrencyEnum.EUR, OperationTypeEnum.GET);
        ExchangeRateEntity backRateEntity = new ExchangeRateEntity(CurrencyEnum.EUR, CurrencyEnum.USD, BACK_RATE);
        given(exchangeRateRepository.findByFromCurrencyAndToCurrency(CurrencyEnum.EUR, CurrencyEnum.USD)).willReturn(Optional.of(backRateEntity));

        // when
        ExchangeRequest response = exchangeService.exchange(request);

        // then
        assertEquals(BigDecimal.valueOf(230).stripTrailingZeros(), response.getAmountFrom().stripTrailingZeros());
    }
}
