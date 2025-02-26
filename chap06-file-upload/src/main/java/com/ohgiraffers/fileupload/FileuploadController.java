package com.ohgiraffers.fileupload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class FileuploadController {

    /* ResourceLoader 의존성 주입
     * /build 경로 하위에 작성한 파일 업로드 경로를 가져오기 위해 ResourceLoader 의존성을 주입한다.
     * */
    private final ResourceLoader resourceLoader;

    @Autowired
    public FileuploadController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostMapping("single-file")
    public String singleFileUpload(@RequestParam MultipartFile singleFile,
                                   @RequestParam String singleFileDescription,
                                   RedirectAttributes rAttr) throws IOException {

        /* 클라이언트로부터 넘어온 요청에서 multipart form 데이터를 확인. */
        System.out.println("파일 폼데이터 : " + singleFile);
        System.out.println("비파일 폼데이터 : " + singleFileDescription);

        /* 설명. build 경로의 static에 업로드한 파일이 저장될 경로를 설정.
         *  1. /src/main/resources/static/ 경로에 아무것도 없는 것을 확인.
         *  2. 해당 경로 하위로 디렉토리를 생성: uploadedFiles/img/single, uploadedFiles/img/multi
         *     즉, 아래와 같이 디렉토리가 생성되어야 한다.
         *     - src/main/resources/static/uploadedFiles/img/single
         *     - src/main/resources/static/uploadedFiles/img/multi
         *  3. 내장 톰캣을 실행해 프로젝트를 빌드한다.
         *     이 때, /build 디렉토리 밑에 /src 디렉토리에서 만든 구조가 반영되는 것을 확인해야 한다.
         *     - build/resources/main/static/uploadedFiles/img/single
         *     - build/resources/main/static/uploadedFiles/img/multi
         * */

        /* 파일을 저장할 경로 설정: /build 디렉토리 하위를 지정.
         * 참고로, 여기서 classpath는 'build/resources/main'이 된다.
         * */
        Resource resource = resourceLoader.getResource("classpath:static/uploadFiles/img/single");
        System.out.println("파일 저장할 resource 경로 확인 : " + resource);

        String filePath = null;

        /* 파일을 저장할 경로(위에서 준비한 resource)가 존재하지 않을 경우:
         * 디렉토리를 직접 생성해서 해당 경로를 잡아줄 수도 있다.
         * 하지만, 우리는 내장 Tomcat을 실행해 build 디렉토리 하위에 저장 경로가 생성되는 것을 위에서 한 번 확인했기 때문에
         * 아래 작성한 if문은 동작하지 않을 것이라 확신할 수 있으며 else문만 동작할 것이다.
         * 즉, Spring이 resource를 읽어와야 하는데, 어떠한 이유로 경로가 생성되지 않았을 때를 대비하여
         * 아래와 같이 if-else 문을 작성하면 혹시 모를 runtime 에러를 방지할 수 있다.
         * */
        // 만약 리소스 디렉토리가 존재하지 않는다면 이 위치에 만들어줄게mkdirs~
        if(!resource.exists()) {
            // 경로가 존재하지 않을 때:
            String root = "src/main/resources/static/uploadFiles/img/single";

            File file = new File(root);
            file.mkdirs();

            filePath = file.getAbsolutePath();
        } else {
            // 경로가 이미 존재할 때:
            filePath = resourceLoader.getResource("classpath:static/uploadFiles/img/single").getFile().getAbsolutePath();
        }
        System.out.println("빌드된 isngle 디렉토리의 절대 경로(=파일저장위치) : " + filePath);

        /* 파일명 변경 처리(Java의 UUID 클래스를 활용) */
        String originalFileName = singleFile.getOriginalFilename();
        System.out.println("원본 파일명 : " + originalFileName);

        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        System.out.println("파일 확장자 : " + extension);

//        String savedName = UUID.randomUUID().toString();    // 랜덤화된 유니크한 아이디를 문자열로 반환해줘
        String savedName = UUID.randomUUID().toString().replace("-", "") + extension;
        System.out.println("저장될 파일명 : " + savedName);

        /* 파일 저장 시도:
         * 해당 메서드에 이미 IOException 예외 처리를 해놓았지만, 파일을 업로드하는 과정에서 에러가 발생하면
         * 이미 저장된 파일을 삭제한 후 실패 페이지로 포워딩해야 하기 때문에 의도적으로 try-catch문으로 감싼다.
         * */
        try {

        } catch(IOException e) {    // 파일 입출력 관련이므로 아이오익셉션
            e.printStackTrace();
        }

        return "redirect:/result";
    }

    @GetMapping("result")
    public void result() {
        // 반환형이 void 면 ("")안에 들어간 것이 바로 view(teimplates내부의 html파일)이름이 된다.
    }
}
