package com.ohgiraffers.semi.order.model.dao;

import com.ohgiraffers.semi.order.model.dto.OrderDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {

    void insertOrder(OrderDTO order);

    List<OrderDTO> findOrdersByMemberCode(int memberCode);

	/*
	 * MyBatis는 Mapper XML을 사용하는 방법도 존재하지만, 어노테이션을 이용하는 방법도 존재한다.
	 * 해당 어노테이션은 보여주기 용도고 특별한 경우가 아니라면 Mapper XML로 통일하자.
	 */
    @Select("""
        SELECT 
            A.ORDER_CODE, 
            A.PRODUCT_CODE,
            A.ORDER_DATE,
            A.ORDER_AMOUNT,
            A.ORDER_MEMBER,
            B.MEMBER_NAME as orderMemberName,
            C.PRODUCT_NAME,
            C.PRODUCT_PRICE
        FROM TBL_ORDER A
        JOIN TBL_MEMBER B ON A.ORDER_MEMBER = B.MEMBER_CODE
        JOIN TBL_PRODUCT C ON A.PRODUCT_CODE = C.PRODUCT_CODE
        ORDER BY A.ORDER_DATE DESC
        """)
    List<OrderDTO> findAllOrders();
} 