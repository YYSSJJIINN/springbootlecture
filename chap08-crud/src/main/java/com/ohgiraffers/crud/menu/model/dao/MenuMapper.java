package com.ohgiraffers.crud.menu.model.dao;

import com.ohgiraffers.crud.menu.model.dto.CategoryDTO;
import com.ohgiraffers.crud.menu.model.dto.MenuDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/* MyBatis의 Mapper 인터페이스임을 나타내는
 * org.apache.ibatis.annotation 소속의 어노테이션.
 * (Spring의 어노테이션이 아님)
 * 이 어노테이션이 정의된 인터페이스는 MyBatis와 연동되어 SQL Mapping을 처리해준다. */
@Mapper
public interface MenuMapper {

    List<MenuDTO> findAllMenu();

    MenuDTO findMenuByCode(int code);

    List<CategoryDTO> findAllCategory();

    void registMenu(MenuDTO newMenu);

    void updateMenu(MenuDTO menu);
}
