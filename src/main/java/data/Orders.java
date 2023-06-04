package data;

import java.util.List;

public class Orders {
    public Orders(List<Order> orders) {
        this.orders = orders;
    }

    public Orders() {
    }

    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
