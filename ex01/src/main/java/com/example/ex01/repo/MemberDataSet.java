package com.example.ex01.repo;

import com.example.ex01.dto.MemberDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class MemberDataSet {

    private ArrayList<MemberDTO> DB;

    public MemberDataSet() {
        DB = new ArrayList<>();
        DB.add(new MemberDTO("aaa", "aaa", "ROLE_api"));
        DB.add(new MemberDTO("bbb", "bbb", "ROLE_api"));
        DB.add(new MemberDTO("ccc", "ccc", "ROLE_api"));
    }

    public int insert(MemberDTO dto) {
        for(MemberDTO d : DB) {
            if(d.getUsername().equals(dto.getUsername())) {
                return 0;
            }
        }
        DB.add(dto);
        return 1;
    }

    public ArrayList<MemberDTO> getList() {
        return DB;
    }

    public int update(MemberDTO dto, String id) {
        for(int i=0; i<DB.size(); i++) {
            if(DB.get(i).getUsername().equals(id)) {
                // 기존에 있던 데이터 삭제하고 새로 추가하겠다는 코드
                DB.remove(i);
                DB.add(dto);
                return 1;
            }
        }
        return 0;
    }

    public int mDelete(String id) {
        for(int i=0; i<DB.size(); i++) {
            if(DB.get(i).getUsername().equals(id)) {
                DB.remove(i);
                // 성공이면
                return 1;
            }
        }
        // 아니면
        return 0;
    }
}
