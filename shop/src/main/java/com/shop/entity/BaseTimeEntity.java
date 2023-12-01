package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class}) // 어노테이션은 엔터티의 생명주기 이벤트에 대한 리스너를 지정하는데 사용됩니다.여기서는 AuditingEntityListener 클래스를 통해 엔터티의 감사 정보를 처리하는 리스너를 등록했습니다.
@MappedSuperclass //어노테이션은 이 클래스가 테이블과 매핑되지 않는다는 것을 나타냅니다. 대신, 이 클래스의 필드들이 하위 엔터티 클래스들에서 공통으로 사용되는 매핑 정보를 정의하는데 사용됩니다. 공통 매핑 정보가 필요할 때 사용하는 어노테이션으로 부모 클래스를 상속 받는 자식 클래스에 매핑 정보만 제공합니다.
@Getter @Setter
public abstract class BaseTimeEntity {

    @CreatedDate // 엔티티가 생성되어 저장될 때 시간을 자동으로 저장합니다.
    @Column(updatable = false)
    private LocalDateTime regTime;

    @LastModifiedDate // 엔티티 값을 변경할 때 시간을 자동으로 저장합니다.
    private LocalDateTime updateTime;
}
