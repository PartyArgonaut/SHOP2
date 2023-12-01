package com.shop.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(value = {AuditingEntityListener.class}) // BaseEntity 클래스에 AuditingEntityListener를 등록하여 엔터티의 감사 정보 이벤트를 처리합니다.
@MappedSuperclass // 공통 매핑 정보가 필요할 때 사용하는 어노테이션으로 부모 클래스를 상속 받는 자식 클래스에 매핑 정보만 제공.
@Getter
public abstract class BaseEntity extends BaseTimeEntity{

    @CreatedBy // JPA Auditing에서 사용되며, 엔터티가 생성될 때 해당 필드에 자동으로 생성한 사용자의 정보를 기록합니다.
    @Column(updatable = false) // false로 해두면 생성자 정보는 업데이트 되지 않도록 설정됨. 엔티티가 수정될 때 해당 필드가 갱신되지 않음.
    private  String createdBy; // 생성자 필드

    @LastModifiedBy // 엔터티가 수정될 때 해당 필드에 자동으로 수정한 사용자의 정보를 기록합니다.
    private String modifiedBy; // 수정자 필드
}
