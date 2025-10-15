package ru.example.micro.clientprocessing.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.springframework.stereotype.Service;
import ru.example.micro.clientprocessing.model.ClientProduct;
import ru.example.micro.clientprocessing.model.ProductType;
import ru.example.micro.clientprocessing.model.Status;
import ru.example.micro.clientprocessing.repository.ClientProductRepository;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.EnumMap;

@Service
public class ProductMetricsService {

    private final ClientProductRepository clientProductRepository;
    private final MeterRegistry meterRegistry;

    private static final EnumSet<ProductType> CLIENT_PRODUCTS =
            EnumSet.of(ProductType.DC, ProductType.CC, ProductType.NS, ProductType.PENS);

    private static final EnumSet<ProductType> CREDIT_PRODUCTS =
            EnumSet.of(ProductType.IPO, ProductType.PC, ProductType.AC);

    public ProductMetricsService(ClientProductRepository clientProductRepository,
                                 MeterRegistry meterRegistry) {
        this.clientProductRepository = clientProductRepository;
        this.meterRegistry = meterRegistry;
        updateMetrics(); // Инициализация при старте
    }

    /**
     * Обновляет все метрики открытых продуктов.
     */
    public void updateMetrics() {
        List<ClientProduct> products = clientProductRepository.findAll();
        Map<ProductType, Long> counts = new EnumMap<>(ProductType.class);

        for (ProductType type : ProductType.values()) {
            long count = products.stream()
                    .filter(p -> p.getStatus() == Status.ACTIVE)
                    .filter(p -> p.getProductType() == type)
                    .count();
            counts.put(type, count);
        }

        counts.forEach((type, count) ->
                meterRegistry.gauge("open_products_total", Tags.of("type", type.name()), count));
    }

    /**
     * Количество активных клиентских продуктов.
     */
    public long countClientProducts() {
        return clientProductRepository.findAll().stream()
                .filter(p -> p.getStatus() == Status.ACTIVE)
                .filter(p -> CLIENT_PRODUCTS.contains(p.getProductType()))
                .count();
    }

    /**
     * Количество активных кредитных продуктов.
     */
    public long countCreditProducts() {
        return clientProductRepository.findAll().stream()
                .filter(p -> p.getStatus() == Status.ACTIVE)
                .filter(p -> CREDIT_PRODUCTS.contains(p.getProductType()))
                .count();
    }

    /**
     * Количество активных продуктов по конкретному типу.
     */
    public long countByType(ProductType type) {
        return clientProductRepository.findAll().stream()
                .filter(p -> p.getStatus() == Status.ACTIVE)
                .filter(p -> p.getProductType() == type)
                .count();
    }
}
