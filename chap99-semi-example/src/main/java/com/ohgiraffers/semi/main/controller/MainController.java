package com.ohgiraffers.semi.main.controller;

import com.ohgiraffers.semi.common.paging.Pagenation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

import com.ohgiraffers.semi.product.model.dto.ProductDTO;
import com.ohgiraffers.semi.product.model.service.ProductService;

@Controller
public class MainController {

    private final ProductService productService;

    @Autowired
    public MainController(ProductService productService) {
        this.productService = productService;
    }

    /*
     * 메인 페이지 로딩 시, 화면에 메뉴 목록을 출력할 것이다.
     * 이 때, 페이지네이션이 적용된 메뉴 조회 로직을 수행한 후 이를 뷰에서 그려낸다.
     * */
    @GetMapping(value = {"/", "/main"})
    public ModelAndView main(@RequestParam(defaultValue = "1") int page,
                       ModelAndView mv) {

        // 처음 보여줄 페이지 번호는 defaultValue로 1로 고정
        Pagenation pagenation = new Pagenation();
        pagenation.setPage(page);
        
        Map<String, Object> resultMap = productService.getAllProducts(pagenation);
        
        mv.addObject("menuList", resultMap.get("products"));
        mv.addObject("pagenation", resultMap.get("pagenation"));

        mv.setViewName("main/main");
        
        return mv;
    }

    @GetMapping("/menu/{category}")
    public ModelAndView menuByCategory(@PathVariable String category,
                                       ModelAndView mv) {

        List<ProductDTO> productList = productService.findProductsByCategory(category);

        mv.addObject("menuList", productList);

        mv.setViewName("main/main");

        return mv;
    }
}
