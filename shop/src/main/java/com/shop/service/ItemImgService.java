package com.shop.service;


import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

    @Value("${itemImgLocation}") //@Value는 properties에서 값을 가져와 적용할 때 사용한다.
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception{
        String oriImgName = itemImgFile.getOriginalFilename(); // oriImgName은 MultipartFile 객체에서 얻어진 원래 파일 이름이다.

        String imgName = ""; //

        String imgUrl = ""; // 나중에 업로드한 이미지 파일의 이름('imgName')과 해당 이미지를 웹에서 불러올 수 있는 경로('imgUrl')를 초기화하는 것이다. 파일 업로드가 유저에 의해 이루어지지 않을 수 있기 때문.

        // 파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){ // 이미지가 선택되지 않은 경우 코드 블록이 실행되지 않고, 'imgName'과 'imgUrl'은 초기에 빈 문자열로 남게 된다.
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes()); // 사용자가 상품의 이미지를 등록했다면 저장할 경로와 파일의 이름, 파일을 파일의 바이트 배열을 파일 업로드 파라미터로 uploadFile 메소드를 호출합니다. 호출 결과 로컬에 저장된 파일의 이름을 imgName 변수에 저장.
            imgUrl = "/images/item/" + imgName; // 저장한 상품 이미지를 불러올 경로를 설정. 외부 리소스를 불러오는 urlPatterns로 WebMvcConfig클래스에서  "/images/**"를 설정. 또한 application.properties에서 설정한 uploadPath 프로퍼티 경로인 "C:/SHOP/" 아래 item 폴더에 이미지를 저장하므로 상품 이미지를 불러오는 경로로 "/images/item/"를 붙여줍니다.
        }

        // 상품 이미지 정보 저장
        itemImg.updateItemImg(oriImgName, imgName, imgUrl); // 1.
        itemImgRepository.save(itemImg); // 2. 1과 2는 입력받은 상품 이미지 정보를 저장합니다. imgName: 실제 로컬에 저장된 상품 이미지 파일의 이름. oriImgName: 업로드했던 상품 이미지 파일의 원래 이름. imgUrl: 업로드 결과 로컬에 저장된 상품 이미지 파일을 불러오는 경로.

    }

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {
        ItemImg savedItemImg;
        if (!itemImgFile.isEmpty()) { // 상품 이미지를 수정한 경우 상품 이미지를 업데이트
            savedItemImg = itemImgRepository.findById(itemImgId) // 상품 이미지 아이디를 이용하여 기존에 저장했던 상품 이미지 엔티티를 조회합니다.
                    .orElseThrow(EntityNotFoundException::new);

            if (!StringUtils.isEmpty(savedItemImg.getImgName())){ // 기존 등록된 상품 이미지 파일이 있을 경우 해당 파일을 삭제하는 코드.
                fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes()); // 업데이트한 상품 이미지 파일을 업로드.

            String imgUrl = "/images/item/" + imgName;
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl); // 변경된 상품 이미지 정보를 세팅해 줌. 여기서 중요한 점은 상품 등록 때처럼 itemImgRepository.save()로직을 호출하지 않는다는것. savedItemImg 엔티티는 현재 영속 상태이므로 데이터를 변경하는 것 만으로 변경 감지 기능이 동작하여 트랜잭션이 끝날 때 update쿼리가 실행됨. 여기서 중요한 것은 엔티티가 영속 상태여야 한다는 것.
        }

        // 기존 이미지 파일 삭제

    }

}
