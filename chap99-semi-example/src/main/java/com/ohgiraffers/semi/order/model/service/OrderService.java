package com.ohgiraffers.semi.order.model.service;

import com.ohgiraffers.semi.order.model.dao.OrderMapper;
import com.ohgiraffers.semi.order.model.dto.OrderDTO;
import com.ohgiraffers.semi.product.model.dao.ProductMapper;
import com.ohgiraffers.semi.product.model.dto.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    
    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    public OrderService(OrderMapper orderMapper, ProductMapper productMapper) {
        this.orderMapper = orderMapper;
        this.productMapper = productMapper;
    }
    
    @Transactional
    public void createOrder(OrderDTO order) throws Exception {

        log.info("주문 시작: {}", order);
        
        // 1. 상품 재고 확인
        ProductDTO product = productMapper.findProductByCode(order.getProductCode());
        log.info("상품 조회 결과: {}", product);
        
        int orderAmount = Integer.parseInt(order.getOrderAmount());
        if (product.getProductStock() < orderAmount) {
            throw new Exception("상품의 재고가 부족합니다.");
        }
        
        // 2. 주문 등록
        orderMapper.insertOrder(order);
        log.info("주문 등록 완료");
        
        // 3. 상품 재고 감소
        product.setProductStock(product.getProductStock() - orderAmount);
        productMapper.updateProductStock(product);
        log.info("재고 업데이트 완료. 남은 재고: {}", product.getProductStock());
    }
    
    public List<OrderDTO> findOrdersByMemberCode(int memberCode) {
        return orderMapper.findOrdersByMemberCode(memberCode);
    }
    
    public List<OrderDTO> findAllOrders() {
        return orderMapper.findAllOrders();
    }
} 