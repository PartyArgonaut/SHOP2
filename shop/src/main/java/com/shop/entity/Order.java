package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // 매핑 테이블 이름을 orders로 지정
@Getter @Setter
public class Order extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 한 명의 회원은 여러 번 주문을 할 수 있으므로 주문 엔티티 기준에서 다대일 단방향 매핑을 합니다.



    private LocalDateTime orderDate; //주문일

    @Enumerated(EnumType.STRING) //
    private OrderStatus orderStatus; // 주문상태

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) //주문상품 엔티티와 일(Order)대다(OrderItem) 매핑을 한다. 왜래키인 order_id가 order_item 테이블에 있으므로 연관관계 주인은 OrderItem엔티티이다. 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이하는 CascadeType.ALL 옵션을 설정.
    private List<OrderItem> orderItems = new ArrayList<>(); // 하나의 주문이 여러 개의 주문 상품을 가지기 때문에 List자료형을 사용해서 매핑합니다.





}
