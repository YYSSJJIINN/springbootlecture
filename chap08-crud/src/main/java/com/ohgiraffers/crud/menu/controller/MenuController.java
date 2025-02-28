package com.ohgiraffers.crud.menu.controller;

import com.ohgiraffers.crud.menu.model.dto.CategoryDTO;
import com.ohgiraffers.crud.menu.model.dto.MenuDTO;
import com.ohgiraffers.crud.menu.model.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {

    // MenuService클래스의 내용을 호출하기 위한 선언문
    private MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /* 전체 메뉴 조회 */
    @GetMapping("/list")
    public String findMenuList(Model model) {

        /* 1. 사용자로부터 요청 접수 */
        // 전체 조회이기 때문에 별다른 파라미터가 전송되지 않음.
        // 즉, 전체조회이므로 필터링 할 것이 없기 때문에 사용자로부터 전달받을 파라미터가 없다.

        /* 2. 비즈니스 로직 처리를 위한 서비스 호출 */
        // 서비스의 비즈니스 로직 호출
        List<MenuDTO> menuList  = menuService.findMenuList();

        // 서비스의 작업 결과를 바탕으로 요청에 대한 성공/실패 여부 판단

        /* 3. 성공/실패 여부에 따라서 반환할 뷰 결정(필요한 데이터는 모델에 추가)*/
        model.addAttribute("menuList", menuList);

        return "menu/list";
    }

    @GetMapping("/detail/{code}")
    public String findMenuDetail(@PathVariable("code") int code,
                                 Model model) {
        System.out.println("사용자가 요구하는 세부 메뉴의 PK번호 : " + code);

        // 위에서 소환된 code의 반환값은 MenuDTO다.
        /* 2. 비즈니스 로직 처리를 위한 서비스 호출 */
        // 서비스의 비즈니스 로직 호출
        MenuDTO menu = menuService.findMenuDetail(code);

        // 서비스의 작업 결과를 바탕으로 요청에 대한 성공/실패 여부 판단

        /* 3. 성공/실패 여부에 따라서 반환할 뷰 결정(필요한 데이터는 모델에 추가)*/
        model.addAttribute("menu", menu);

        return "menu/detail";
    }

    // insert
    @GetMapping("/regist")
    public void registMenu() {
    }

    @GetMapping("/category")
    public List<CategoryDTO> findCategoryList() {

    }
}
