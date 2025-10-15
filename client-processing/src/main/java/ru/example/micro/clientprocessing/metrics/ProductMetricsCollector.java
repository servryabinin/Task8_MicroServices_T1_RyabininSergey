package ru.example.micro.clientprocessing.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;
import ru.example.micro.clientprocessing.model.ProductType;
import ru.example.micro.clientprocessing.service.ProductMetricsService;

@Component
public class ProductMetricsCollector {

    public ProductMetricsCollector(MeterRegistry meterRegistry, ProductMetricsService metricsService) {

        // Клиентские продукты
        Gauge.builder("open_client_products", metricsService, ProductMetricsService::countClientProducts)
                .description("Number of active client products")
                .register(meterRegistry);

        // Кредитные продукты
        Gauge.builder("open_credit_products", metricsService, ProductMetricsService::countCreditProducts)
                .description("Number of active credit products")
                .register(meterRegistry);

        // Опционально: по каждому типу продукта
        for (ProductType type : ProductType.values()) {
            Gauge.builder("open_products_by_type", metricsService,
                            svc -> svc.countByType(type))
                    .description("Number of active products of type " + type)
                    .tag("type", type.name())
                    .register(meterRegistry);
        }
    }
}
