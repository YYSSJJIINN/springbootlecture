package com.ohgiraffers.semi.product.controller;

import com.ohgiraffers.semi.common.paging.Pagenation;
import com.ohgiraffers.semi.product.model.dto.CategoryDTO;
import com.ohgiraffers.semi.product.model.dto.ProductDTO;
import com.ohgiraffers.semi.product.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 전체 상품 목록
    @GetMapping("/all")
    public String productList(@RequestParam(defaultValue = "1") int page,
                              Model model) {

        Pagenation pagenation = new Pagenation();
        pagenation.setPage(page);
        
        Map<String, Object> resultMap = productService.getAllProducts(pagenation);
        
        model.addAttribute("products", resultMap.get("products"));
        model.addAttribute("pagenation", resultMap.get("pagenation"));
        
        return "product/list";
    }

    private int getCategoryCode(String category) {
        return switch (category.toLowerCase()) {
            case "food" -> 1;
            case "dessert" -> 2;
            case "drink" -> 3;
            default -> throw new IllegalArgumentException("Invalid category: " + category);
        };
    }

    // 카테고리별 상품 목록
    @GetMapping("/category/{category}")
    public String getProductList(@PathVariable String category,
                               @RequestParam(defaultValue = "1") int page,
                               Model model) {
        try {
            Pagenation pagenation = new Pagenation();
            pagenation.setPage(page);
            
            int categoryCode = getCategoryCode(category);
            Map<String, Object> resultMap = productService.getProductList(categoryCode, pagenation);
            
            model.addAttribute("products", resultMap.get("products"));
            model.addAttribute("pagenation", resultMap.get("pagenation"));
            model.addAttribute("category", category);
            
            return "product/list";
        } catch (IllegalArgumentException e) {
            return "redirect:/product/all";
        }
    }

    // 상품 상세 조회
    @GetMapping("/detail/{productCode}")
    public ModelAndView productDetail(@PathVariable int productCode,
                                      ModelAndView mv) {

        mv.addObject("product", productService.findProductByCode(productCode));
        mv.setViewName("product/detail");

        return mv;
    }

    // 관리자용 상품 목록
    @GetMapping("/admin/list")
    public ModelAndView adminProductList(ModelAndView mv) {

        List<ProductDTO> products = productService.findAllProducts();

        mv.addObject("products", products);
        mv.setViewName("product/admin/list");

        return mv;
    }

    // 관리자용 상품 등록 페이지
    @GetMapping("/admin/create")
    public String adminProductCreatePage() {
        return "product/admin/create";
    }

    // 관리자용 상품 등록 처리
    @PostMapping("/admin/create")
    public String adminProductCreate(@ModelAttribute ProductDTO product,
                                   @RequestParam("productImage") MultipartFile productImage,
                                   RedirectAttributes rttr) {
        try {
            productService.createProduct(product, productImage);
            rttr.addFlashAttribute("message", "상품이 등록되었습니다.");
            return "redirect:/product/admin/list";
        } catch (IllegalArgumentException e) {
            rttr.addFlashAttribute("message", e.getMessage());
            return "redirect:/product/admin/create";
        } catch (Exception e) {
            rttr.addFlashAttribute("message", "상품 등록에 실패했습니다: " + e.getMessage());
            return "redirect:/product/admin/create";
        }
    }

    // 카테고리 목록 조회 API
    @GetMapping(value = "/api/categories", produces = "application/json")
    @ResponseBody
    public List<CategoryDTO> getCategories() {
        return productService.findAllCategories();
    }

    @GetMapping("/admin/edit/{productCode}")
    public ModelAndView adminProductEditPage(@PathVariable int productCode,
                                             ModelAndView mv) {

        ProductDTO product = productService.findProductByCode(productCode);

        mv.addObject("product", product);
        mv.setViewName("product/admin/edit");

        return mv;
    }

    @PostMapping("/admin/edit/{productCode}")
    public String adminProductEdit(@PathVariable int productCode,
                                 @ModelAttribute ProductDTO product,
                                 @RequestParam(required = false) MultipartFile productImage,
                                 RedirectAttributes rttr) {
        try {
            product.setProductCode(productCode);
            productService.updateProduct(product, productImage);
            rttr.addFlashAttribute("message", "상품이 수정되었습니다.");
            return "redirect:/product/admin/list";
        } catch (Exception e) {
            rttr.addFlashAttribute("message", "상품 수정에 실패했습니다: " + e.getMessage());
            return "redirect:/product/admin/edit/" + productCode;
        }
    }

    @PostMapping("/admin/delete/{productCode}")
    public String adminProductDelete(@PathVariable int productCode,
                                     RedirectAttributes rttr) {
        try {
            productService.deleteProduct(productCode);
            rttr.addFlashAttribute("message", "상품이 삭제되었습니다.");
            return "redirect:/product/admin/list";
        } catch (Exception e) {
            rttr.addFlashAttribute("message", "상품 삭제에 실패했습니다: " + e.getMessage());
            return "redirect:/product/admin/list";
        }
    }

    @GetMapping("/search")
    public ModelAndView searchProducts(@RequestParam String keyword,
                                       ModelAndView mv) {

        List<ProductDTO> products = productService.searchProducts(keyword);

        mv.addObject("products", products);
        mv.addObject("keyword", keyword);
        mv.setViewName("product/search-result");

        return mv;
    }
} 