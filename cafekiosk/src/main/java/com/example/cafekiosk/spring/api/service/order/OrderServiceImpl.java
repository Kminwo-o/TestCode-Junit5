package com.example.cafekiosk.spring.api.service.order;

import com.example.cafekiosk.spring.api.service.order.request.OrderCreateRequest;
import com.example.cafekiosk.spring.api.service.order.response.OrderResponse;
import com.example.cafekiosk.spring.domain.order.Order;
import com.example.cafekiosk.spring.domain.order.OrderRepository;
import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductRepository;
import com.example.cafekiosk.spring.domain.product.ProductType;
import com.example.cafekiosk.spring.domain.stock.Stock;
import com.example.cafekiosk.spring.domain.stock.StockRepository;
import com.example.cafekiosk.spring.exception.OrderServiceException;
import com.example.cafekiosk.spring.exception.errorcode.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
* 재고 감소 -> 동시성에 대한 고민
* optimistic lock / pessimistic lock / ...
* */
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService{

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<Product> duplicateProducts = findProductsBy(request.getProductNumbers());

        deductStockQuantities(duplicateProducts);

        Order order = Order.create(duplicateProducts, registeredDateTime);
        Order savedOrder = orderRepository.save(order);
        
        // Order
        return OrderResponse.of(savedOrder);
    }

    private void deductStockQuantities(List<Product> duplicateProducts) {
        // 재고 차감 체크가 필요한 상품들 Filter
        List<String> stockProductNumbers = extractStockProductNumbers(duplicateProducts);

        // 재고 엔티티 조회, 상품별 카운트하여 재고 차감
        Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);
        Map<String, Long> productCountingMap = createCountingMapBy(stockProductNumbers);

        for (String stockProductNumber : new HashSet<>(stockProductNumbers)) {
            Stock stock = stockMap.get(stockProductNumber);
            int quantity = productCountingMap.get(stockProductNumber).intValue();

            if (stock.isQuantityLessThan(quantity)) {
                throw new OrderServiceException(ErrorCode.DONT_HAVE_QUANTITY);
            }

            stock.deductQuantity(quantity);
        }
    }

    private static Map<String, Long> createCountingMapBy(List<String> stockProductNumbers) {
        return stockProductNumbers.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
    }

    private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
        return stocks.stream()
                .collect(Collectors.toMap(Stock::getProductNumber, s -> s));
    }

    private static List<String> extractStockProductNumbers(List<Product> duplicateProducts) {
        return duplicateProducts.stream()
                .filter(product -> ProductType.containsStockTypeCheck(product.getProductType()))
                .map(product -> product.getProductInfo().getProductNumber())
                .toList();
    }

    /*
     *  아메리카노 1개, 아메리카노 1개와 같은 중복 주문을 위해 총 가격을 List 화.
     * */
    private List<Product> findProductsBy(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductInfo_ProductNumberIn(productNumbers);
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(p -> p.getProductInfo().getProductNumber(), p -> p));

        return productNumbers.stream()
                .map(productMap::get)
                .toList();
    }
}
