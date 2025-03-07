package com.ohgiraffers.semi.common.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * 이미지 파일을 저장하고 삭제하는 메서드를 정적 메서드로 추상화해 공용으로 사용하기 위한 클래스다.
 * 이미지 파일 저장 기능이 필요한 단위 기능들이 많으면 각자 구현해도 되지만,
 * 아래와 같은 방식으로 공용으로 구현해놓고 참조해서 사용해도 된다.
 * */
public class FileUploadUtils {


    public static String saveFile(String fileDir, String fileName, MultipartFile file)
			throws IOException {

        System.out.println("--- 이미지 저장 시작 ---");
        System.out.println("fileDir = " + fileDir);
        System.out.println("fileName = " + fileName);
        System.out.println("file = " + file);

		/* 프로젝트 루트 디렉토리의 절대 경로를 가져옴 */
        String projectPath = System.getProperty("user.dir");
        System.out.println("projectPath = " + projectPath);
        
		/* build 폴더 경로 생성 */
        String buildPath = projectPath + "/build/resources/main/static/image/product";
        File dir = new File(buildPath);
        
		/* 디렉토리가 존재하지 않으면 디렉토리 생성 */
        if (!dir.exists()) {
            dir.mkdirs();
        }

		/* 원본 파일 이름 가져오기 */
        String originFileName = file.getOriginalFilename();

		/* 확장자 추출 */
        String ext = originFileName.substring(originFileName.lastIndexOf("."));

		/* 저장할 파일 이름 생성 */
        String savedFileName = fileName + ext;

		/* 저장할 파일 경로 생성 */
        File target = new File(dir, savedFileName);

		/* 파일 저장 */
        file.transferTo(target);

		/* 저장된 파일 이름 반환 */
        return savedFileName;
    }

    /* 이미지 삭제 기능은 SpringBoot 파일 업로드 기능을 복습하며 직접 구현해보세요~ */
    public static boolean deleteFile() {

        System.out.println("--- 이미지 삭제는 직접 구현해보세요~ ---");

        return false;       // return null과 같은 상황이므로 꼭 수정해야함. 부울리언의 거짓이면 아무것도 안나옴..
    }
} 