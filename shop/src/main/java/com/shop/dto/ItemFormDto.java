package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "상품 상세는 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>(); // 상품 저장 후 수정할 때 상품 이미지 정보를 저장하는 리스트

    private List<Long> itemImgIds = new ArrayList<>(); // 상품의 이미지 아이디를 저장하는 리스트입니다. 상품 등록 시에는 아직 상품의 이미지를 저장하지 않았기 때문에 아무 값도 들어가 있지 않고 수정 시에 이미지 아이디를 담아둘 용도로 사용.

    private static ModelMapper modelMapper = new ModelMapper(); // 모델매퍼는 자바에서 객체 간의 자동 매핑을 지원하는 라이브러리. 객체 간의 필드 이름이 서로 일치하면 자동으로 값을 복사해줌.

    public Item createItem(){
        return modelMapper.map(this, Item.class); // 1.
    }

    public static ItemFormDto of(Item item){
        return modelMapper.map(item, ItemFormDto.class); // 2. 1과 2는 modelMapper를 이용하여 엔티티 객체와 DTO 객체 간의 데이터를 복사하여 복사한 객체를 반환해주는 메소드입니다.
    }

//위의 코드에서 modelMapper.map() 메서드를 호출하면, itemFormDto 객체의 필드가 Item 엔터티의 필드로 자동으로 매핑되어 item 객체에 복사됩니다.
//
//이렇게 ModelMapper를 사용하면 일일이 필드를 수동으로 매핑하지 않아도 되므로 코드를 간결하게 유지하면서도 객체 간의 변환을 수행할 수 있습니다.


}
