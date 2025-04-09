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

    public int login(String username,String password){
        int result = -1 ;
        for(MemberDTO d : DB){
            if( d.getUsername().equals(username) ){
                // 아이디가 일치하면
                result = 1;
                if( d.getPassword().equals(password) ){
                    // 아이디에 이어 비밀번호까지 일치하면
                    result = 0;
                }
                // 아이디와 비번 모두 일치하면 반복문을 빠져나간다.
                break;
            }
        }
        // 리턴값이 -1로 온다면 아예 처음부터 if문이 돌아가지 않은 것이므로 계정이 존재하지 않는 것이다.
        // 리턴값이 1로 온다면 아이디는 존재하지만 비밀번호는 일치하지 않아 로그인에 실패한 것이다.
        // 리턴값이 0으로 온다면 로그인에 성공한 것이다.
        return result;
    }
}
