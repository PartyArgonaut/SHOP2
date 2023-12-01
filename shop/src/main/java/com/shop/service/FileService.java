package com.shop.service;


import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileDate) throws Exception {
        UUID uuid = UUID.randomUUID(); //UUID(Universally Unique Identifier)란 서로 다른 개체들을 구별하기 위해서 이름을 부여할 때 사용. 실제 사용 시 중복될 가능성이 거의 없기 때문에 파일의 이름으로 사용하면 파일명 중복 문제를 해결할 수 있음. 구조 : [Low Time	4 / 8 (8자리)], [Mid time	2 / 4 (4자리)], [Mid time + version	2 / 4 (4자리)], [Clock sequence and variant	2 / 4 (4자리)], [Node	6 / 12 (12자리)]

        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //originalFileName.lastIndexOf(".") : lastIndexOf(".") 메서드는 문자열에서 마지막으로 점(.)이 나타나는 위치(인덱스)를 반환합니다. 따라서 이 부분은 파일 이름에서 마지막 점의 위치를 찾아냅니다.

        String savedFileName = uuid.toString() + extension; // UUID로 받은 값과 원래 파일의 이름의 확장자를 조합해서 저장될 파일 이름을 만듭니다.

        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl); // FileOutputStream 클래스는 바이트 단위의 출력을 내보내는 클래스입니다. FileOutputStream은 파일에 데이터를 출력하기 위한 스트림을 제공하는 클래스. 생성자로 파일이 저장될 위치와 파일의 이름을 넘겨 파일에 쓸 파일 출력 스트림을 만듭니다.

        fos.write(fileDate); // fileData를 파일 출력 스트림에 입력합니다. fileDate 바이트 배열을 생성된 출력 스트림에 기록합니다. 즉, 파일 데이터를 해당 경로에 저장합니다.
        fos.close(); //파일 출력 스트림을 닫습니다. 파일을 쓴 후에는 항상 스트림을 닫아야 합니다.
        return savedFileName; // 업로드된 파일의 이름을 반환합니다.
    }

    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath); // 파일이 저장된 경로를 이용하여 파일 객체를 생성합니다.

        if(deleteFile.exists()) { // 해당 파일이 존재하면 파일을 삭제합니다.
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
