package com.ohgiraffers.handlermethod;

/* @ModelAttribute 어노테이션으로 DTO를 파라미터로 사용할 때,
 * form 데이터의 각 name 어트리뷰트 이름과 DTO필드 변수의 이름을 맞춰주어야
 * reflection 기술을 이용해 자동으로 매핑할 수 있다.
 * */
public class MenuDTO {

    private String name;
    private int price;
    private int categoryCode;
    private String orderableStatus;

    /* 커맨드 객체는 기본생성자를 호출해 인스턴스를 생성하기 때문에 반드시 기본생성자를 정의해줘야 한다. */
    public MenuDTO() {
    }

    public MenuDTO(String name, int price, int categoryCode, String orderableStatus) {
        this.name = name;
        this.price = price;
        this.categoryCode = categoryCode;
        this.orderableStatus = orderableStatus;
    }

    /* 요청 파라미터의 name 어트리뷰트 이름과 일치하는 필드의 setter 메서드를 사용하기 때문에
     * 네이밍 룰에 맞게 setter메서드를 생성해줘야 한다.
     * */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getOrderableStatus() {
        return orderableStatus;
    }

    public void setOrderableStatus(String orderableStatus) {
        this.orderableStatus = orderableStatus;
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", categoryCode=" + categoryCode +
                ", categoryName='" + orderableStatus + '\'' +
                '}';
    }
}
