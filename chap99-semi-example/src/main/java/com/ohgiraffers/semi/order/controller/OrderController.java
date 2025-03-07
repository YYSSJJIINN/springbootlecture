package com.ohgiraffers.semi.order.controller;

import com.ohgiraffers.semi.member.model.dto.MemberDTO;
import com.ohgiraffers.semi.order.model.dto.OrderDTO;
import com.ohgiraffers.semi.order.model.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @PostMapping("/create")
    public String createOrder(@ModelAttribute OrderDTO order,
                            @AuthenticationPrincipal MemberDTO member,
                            RedirectAttributes rttr) {
        try {
            // 로그인한 회원 정보 설정
            order.setOrderMember(member.getMemberCode());
            
            orderService.createOrder(order);
            rttr.addFlashAttribute("successMessage", "주문이 완료되었습니다.");

            return "redirect:/order/list";  // 주문 목록 페이지로 리다이렉트
            
        } catch (Exception e) {
            rttr.addFlashAttribute("errorMessage", "주문 실패: " + e.getMessage());

            return "redirect:/product/detail/" + order.getProductCode();
        }
    }
    
    @GetMapping("/list")
    public String orderList(@AuthenticationPrincipal MemberDTO member,
                           Model model) {

        List<OrderDTO> orders = null;
        
        // 관리자인 경우 모든 주문을 조회
        if (member.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            orders = orderService.findAllOrders();
        } else {
            // 일반 사용자는 자신의 주문만 조회
            orders = orderService.findOrdersByMemberCode(member.getMemberCode());
        }
        
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", "order");

        return "order/list";
    }
} 