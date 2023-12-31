package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.OrderItemRepository;
import com.shop.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class OrderTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderItemRepository orderItemRepository;



    public Item createItem(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("상세설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        return item;
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest(){ // 영속성 전이 테스트. 영
        Order order = new Order();

        for(int i = 0; i < 3; i++){
            Item item = this.createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem); // 아직 영속성 컨텍스트에 저장되지 않은 orderItem엔티티를 order엔티티에 담아준다.
        }

        orderRepository.saveAndFlush(order); // order 엔티티를 저장하면서 강제로 flush를 호출하여 영속성 컨텍스트에 있는 객체들을 데이터베이스에 반영합니다.
        em.clear(); // 영속성 컨텍스트를 초기화.

        Order savedOrder = orderRepository.findById(order.getId())
                .orElseThrow(EntityNotFoundException::new); // 영속성 컨텍스트를 초기화했기 때문에 데이터베이스에서 주문 엔티티를 조회. select쿼리문이 실행되는 것을 콘솔창에서 확인할 수 있다.
        assertEquals(3, savedOrder.getOrderItems().size());
    }

    public Order createOrder(){ // 주문 데이터를 생성해서 저장하는 메소드를 만듭니다.
        Order order = new Order();

        for(int i = 0; i < 3; i++){
            Item item = createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }

        Member member = new Member();
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);
        return order;
    }

    @Test
    @DisplayName("고아 객체 제거 테스트")
    public void orphanRemovalTest(){
        Order order = this.createOrder();
        order.getOrderItems().remove(0); // order 엔티티에서 관리하고 있는  orderItem 리스트의 0번째 인덱스 요소를 제거.
        em.flush();
    }

    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest(){
        Order order = this.createOrder(); // 기존 주문 생성 매소드 이용해 주문 데이터 저장.
        Long orderItemId = order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(EntityNotFoundException::new); // 영속성 컨텍스트의 상태 초기화 후 order 엔티티에 저정했던 주문 상품 아이디를 이용하여 orderItem을 데이터베이스에서 다시 조회합니다. EntityNotFoundException::new는 EntityNotFoundException 클래스의 생성자를 참조하는 것입니다. 이것은 주로 함수형 인터페이스(하나의 추상 메서드만 가진 인터페이스)에서 예외를 생성하고 던지는 메서드를 대체하는데 사용됩니다. 자세한 내용을 알아보기 위해선, Java 8의 함수형 인터페이스와 메서드 레퍼런스에 대한 이해가 필요합니다. new는 기본형이고 비슷한거 여러 개 있음.
        System.out.println("Order class : " + orderItem.getOrder().getClass()); // 코드 실행 결과 Order 클래스 조회 결과가 HibernateProxy라고 출력되는 것을 볼 수 있다. 지연로딩 설정하면 실제 엔티티 대신 프록시 객체를 넣어둡니다.
        System.out.println("================================");
        orderItem.getOrder().getOrderDate();
        System.out.println("================================");

    }

}
