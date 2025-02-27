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
import java.util.ArrayList;
import java.util.List;
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
         *  2. 해당 경로 하위로 디렉토리를 생성: uploadFiles/img/single, uploadFiles/img/multi
         *     즉, 아래와 같이 디렉토리가 생성되어야 한다.
         *     - src/main/resources/static/uploadFiles/img/single
         *     - src/main/resources/static/uploadFiles/img/multi
         *  3. 내장 톰캣을 실행해 프로젝트를 빌드한다.
         *     이 때, /build 디렉토리 밑에 /src 디렉토리에서 만든 구조가 반영되는 것을 확인해야 한다.
         *     - build/resources/main/static/uploadFiles/img/single
         *     - build/resources/main/static/uploadFiles/img/multi
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
            System.out.println("[INFO] 파일을 저장할 경로가 존재하지 않아 폴더를 생성합니다...");
            String root = "src/main/resources/static/uploadFiles/img/single";

            File file = new File(root);
            file.mkdirs();

            filePath = file.getAbsolutePath();
        } else {
            // 경로가 이미 존재할 때:
            System.out.println("[INFO] 파일을 저장할 경로가 이미 존재합니다...");
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
            /* 실질적으로 파일이 저장되는 구문 */
            singleFile.transferTo(new File(filePath + "/" + savedName));

            /* 해당 영역이 실제로 DB를 다녀오는 비즈니스 로직을 호출하는 영역이다.
             * 즉, 이쯤에서 서비스 메서드를 호출하면 된다. (여기서는 스킵)
             * */

            /* 파일 업로드 로직(팡리 저장 + DB에 해당 정보 저장이 끝난 후,
             * 결과 화면을 보여주는 핸들러 메서드에게 리다이렉트한 후 사용할 데이터를
             * RedirectAttributes에 추가.
             * */
            rAttr.addFlashAttribute("message", "[Success] 단일 파일 업로드 성공!");
            rAttr.addFlashAttribute("img", "static/uploadFiles/img/single/" + savedName);
            rAttr.addFlashAttribute("singleFileDescription", singleFileDescription);

            System.out.println("[Success] 단일 파일 업로드 성공!");


        } catch(IOException e) {    // 파일 입출력(Io) 관련이므로 아이오익셉션

            /* try 구문 안, 즉 비즈니스 로직 처리 도중 예외가 발생했다면,
             * 분명 파일만 업로드(=저장) 될 것이다.
             * 그러면 파일 서버에 파일만 저장되고
             * 해당 정보가 DB에 기록된 적이 없는 것이기 때문에
             * 파일 서버는 수인 없는 파일만 1개 추가된 셈이다.
             * 이러한 경우가 수백 수천 번 반복되면 파일 서버의 용량은 쓰레기 파일들로 영향받을 것이다.
             * 이러한 현상을 막기 위해 비즈니스 로직이 실패하면
             * 파일이 성공적으로 저장되었다 하더라도 반드시 삭제해줘야한다.
             * */

            /* 실질적으로 파일이 삭제되는 구문*/
            new File(filePath + "/" + savedName).delete();

            /* 어떤 예외로 비즈니스 로직이 실패했는지 확인 */
            e.printStackTrace();

            rAttr.addFlashAttribute("message", "[Failed] 단일 파일 업로드 실패!!");
            System.out.println("[Failed] 단일 파일 업로드 실패!!");
        }
        /* 파일 업로드 작업이 성공하든 실패하든 클라이언트에게 보여줄 결과 메세지를
         * RedirectAttributes에 적절히 담았으므로 응답 화면을 그려내는 핸들러 메서드에게 리다이렉트.
         * */
        return "redirect:/result";
    }

    @PostMapping("multi-file")
    public String multiFileUpload(@RequestParam List<MultipartFile> multiFiles,     // 멀티파일은 여러개 이기 때문에 컬렉션(List)으로 받아야한다.
                                  @RequestParam String multiFileDescription,
                                  RedirectAttributes rAttr) throws IOException {

        /* 클라이언트로부터 넘어온 multipart form 데이터를 확인 */
        System.out.println("파일 폼데이터 : " + multiFiles);
        System.out.println("비파일 폼데이터 : " + multiFileDescription);

        Resource resource = resourceLoader.getResource("classpath:static/uploadFiles/img/multi");
        System.out.println("resource 경로 확인 : " + resource);

        String filePath = null;
        if(!resource.exists()) {
            String root = "src/main/resources/static/uploadFiles/img/multi";
            File file = new File(root);

            file.mkdirs();

            filePath = file.getAbsolutePath();
        } else {

            filePath = resourceLoader.getResource("classpath:static/uploadFiles/img/multi")
                                        .getFile()
                                        .getAbsolutePath();
        }
        System.out.println("파일이 저장될 경로 : " + filePath);

        /* 위에서 살펴본 단일 팡리 업로드와 다중 파일 업로드의 가장 큰 차이점은
         * 파일인 파라미터가 단수 타입으로 존재하냐, 컬렉션 타입으로 존재하냐의 차이다.
         * 따라서 전반적인 로직은 거의 대부분 동일하며,
         * 파일을 다울 때 반복문(컬렉션)을 사용한다는 차이만 존재한다.
         * */
        List<FileDTO> files = new ArrayList<>();                // 실제 저장될 파일을 묶는 ArrayList
        List<String> savedFilesPaths = new ArrayList<>();       // 실제 저장될 파일의 경로를 묶은 ArrayList

        try {

            /* 클라이언트로부터 전달된 파일의 개수만큼 저장 스퀀스를 반복. */
            for(int i = 0; i < multiFiles.size(); i++) {
                System.out.println("========== 전체 " + multiFiles.size() + "개 파일 중 " + (i + 1) + "번째 파일 ==========");

                /* Java의 UUID를 이용해 파일명 랜덤변경 작업 시작 */
                String originalFileName = multiFiles.get(i).getOriginalFilename();
                System.out.println("원본파일명 : " + originalFileName);

                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                System.out.println("파일 확장자 : " + extension);

                String savedName = UUID.randomUUID().toString().replace("-", "") + extension;
                System.out.println("저장될 파일명 : " + savedName);

                /* DTO에 담으 파일 정보를 추출한 후, 파일 List에 추가 */
                files.add(new FileDTO(originalFileName, savedName, filePath, multiFileDescription));

                /* 실질적으로 파일이 저장되는 구문 */
                multiFiles.get(i).transferTo(new File(filePath + "/" + savedName));

                /* 파일이 저장되었으니, 저장된 경로를 List에 추가 */
                savedFilesPaths.add("static/uploadFiles/img/multi" + "/" + savedName);

                System.out.println("=======================================================================================");
            }

            rAttr.addFlashAttribute("message", "[Success] 다중 파일 업로드 성공");
            rAttr.addFlashAttribute("imgs", savedFilesPaths);
            rAttr.addFlashAttribute("multiFileDescription", multiFileDescription);

            System.out.println("[Success] 다중 파일 업로드 성공");

        } catch (Exception e) {

            /* 예외 발생 시, 파일 삭제 작업 */
            for(FileDTO f : files) {
                new File(filePath + "/" + f.getSavedName()).delete();
            }

            /* 발생한 예외 확인 */
            e.printStackTrace();

            rAttr.addFlashAttribute("message", "[Failed] 다중 파일 업로드 실패!");

            System.out.println("[Failed] 다중 파일 업로드 실패!");

        }

        return "redirect:result";
    }

    @GetMapping("result")
    public void result() {
        // 반환형이 void 면 ("")안에 들어간 것이 바로 view(teimplates내부의 html파일)이름이 된다.
    }
}
