package com.ohgiraffers.crud.menu.model.service;

import com.ohgiraffers.crud.menu.model.dao.MenuMapper;
import com.ohgiraffers.crud.menu.model.dto.MenuDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuMapper menuMapper;

    @Autowired
    public MenuService(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    public List<MenuDTO> findMenuList() {

        return menuMapper.findAllMenu();
    }

    public MenuDTO findMenuDetail(int code) {

        // DAO에게 DB에 가서 조회해오라고 해야함.
        // 이 프로젝트에서는 MenuMapper인터페이스임
        return menuMapper.findMenuByCode(code);
    }
}
