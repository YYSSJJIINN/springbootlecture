package com.example.db_test.service;

import com.example.db_test.domain.MemberEntity;
import com.example.db_test.dto.MemberDTO;
import com.example.db_test.repo.MemberRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    // DAO나 Mapper 사용할 때도,
    // 원래 Controller -> Service -> Mapper였음
    private final MemberRepo repo;

    public int insert(MemberDTO dto) {
        int result = 0;
        // insert의 기능
        try {
            MemberEntity entity = repo.save( new MemberEntity(dto));
            log.info("service entity : {}", entity);
        } catch (Exception e) {
            result = 1;
            throw new RuntimeException();
//            e.printStackTrace();
        }
        return result;
    }

    public List<MemberDTO> getList() {

        // list한테 데이터 값을 하나씩 넣어주고, 이 list 값을 return으로 돌려준다.
        List<MemberDTO> list = null;
        // 여러개의 데이터를 받아오는데
        // Entity 값으로 처리한다.
        List<MemberEntity> listE = repo.findAll();

        // 만약 사이즈가 0과 같지 않다면(데이터가 들어 있다면) 처리해라
        if( listE.size() != 0 ) {
            list = listE.stream()
            .map( m -> new MemberDTO(m) )
            .toList();
        }

//        // 반복문 사용해서 값 넣기
//        List<MemberDTO> list = new ArrayList<>();
//        for(MemberEntity entity : listE) {
//            // 그 DTO를 list에 추가해라
//            list.add(new MemberDTO(e));
//        }

        log.info("list entity : {}", listE);
        // list entity : [MemberEntity(number=1, userId=aaa, userName=홍길동, age=20)]
        return list;
    }

    public MemberDTO getData(long number) {

        // Optional로 받은 값을 우리가 사용하는 <MemberEntity>로 꺼내야 한다.
        Optional<MemberEntity> opM = repo.findById(number);

        // 만약 저장되어 있는 값이 없다면 entity로 꺼내고,
        //
        MemberEntity entity = opM.orElse(null);

        // 만약  entity가 null 값아 아니라면 DTO에 저장해준다.
        if( entity != null )
            return new MemberDTO(entity);
        return null;
    }

    public int deleteData(long num) {

        int result = 0;

        MemberDTO dto = getData(num);

        if( dto != null ) {
            repo.deleteById(num);
            result = 1;
        }
        return result;
    }

    // 데이터 수정
    // dto에는 내가 수정할 값이 저장되어 있고
    public int updateData(String userId, MemberDTO dto) {

        // entity는 모든 값이 저장되어 있음
        MemberEntity entity = repo.findByUserId(userId);
        log.info("service update : {}", entity);

        if( entity != null ) {
            entity.setUserName(dto.getUserName());
            entity.setAge(dto.getAge());
            repo.save(entity);
            return 1;   // 성공
        }
        // userId가 없는 경우
        return 0;
    }

    // 한 페이지를 구성할 리스트처리
    public List<MemberDTO> getListPage(int start, int page) {
//        int page = 3;
        // 내림차순 요청
        Pageable pageable = PageRequest.of(start, page,
                Sort.by(Sort.Order.desc("number")));
        // page자료형을 우리가 사용할 수 있는 DTO형식으로 꺼내온다.
        Page<MemberEntity> pageEntity = repo.findAll(pageable);
        List<MemberEntity> listE = pageEntity.getContent();
        List<MemberDTO> list = listE.stream().map(m -> new MemberDTO(m)).toList();
        return list;
    }

    public MemberDTO getContent(long number) {

        // Optional로 받은 값을 우리가 사용하는 <MemberEntity>로 꺼내야 한다.
        // 리턴으로 돌려줄 값은 MemberEntity 타입의 entity
        MemberEntity entity = repo.findByContent(number);
        log.info("entity : {}", entity);
        // 만약  entity가 null 값아 아니라면 DTO에 저장해준다.
        if( entity != null )
            return new MemberDTO(entity);
        return null;
    }

    public int insertContent(MemberDTO dto) {
        int result = 0;     // 초기값
        // insert의 기능
        try {
            // DB 저장이 됐다면 result는 1이 될것이다.
            result = repo.insertContent(dto.getUserId(), dto.getUserName(), dto.getAge());
            log.info("service result : {}", result);
        } catch (Exception e) {
            throw new RuntimeException();
//            e.printStackTrace();
        }
        return result;
    }
}
