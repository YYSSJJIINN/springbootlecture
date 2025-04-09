package com.example.ex01.service;

import com.example.ex01.dto.MemberDTO;
import com.example.ex01.repo.MemberDataSet;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    MemberDataSet ds;

    private final HttpSession session;

    public int insert(MemberDTO dto) {
        int result = 0;
        result = ds.insert(dto);
        return result;
    }

    public ArrayList<MemberDTO> getList() {
        return ds.getList();
    }

    public int update(MemberDTO dto, String id) {
        if(dto.getUsername() == null || dto.getPassword() == null || dto.getRole() == null)
            return -1;
        return ds.update(dto, id);
    }

    public int mDelete(String id) {
//        int result = 0;
//        result = ds.delete(id);
//        return ds.getList().remove(id) ? 1 : 0;
        return ds.mDelete(id);
    }

    public int login( String username, String password ){
        int result = 0;
        result = ds.login(username, password);
        if( result == 0 ){
            session.setAttribute("username", username);
        }
        return result;
    }
}
