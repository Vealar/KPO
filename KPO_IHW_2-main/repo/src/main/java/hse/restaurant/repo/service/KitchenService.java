package hse.restaurant.repo.service;
import hse.restaurant.repo.model.Order;
import hse.restaurant.repo.model.status.OrderStatus;
import hse.restaurant.repo.repository.OrderRepository;

public class KitchenService {
    private final OrderRepository orderRepository;

    public KitchenService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void cookOrder(Order order) {
        Thread thread = new Thread(new OrderCooker(order));
        thread.start();
    }

    private class OrderCooker implements Runnable {
        private final Order order;

        public OrderCooker(Order order) {
            this.order = order;
        }

        @Override
        public void run() {
            while (order.getStatus() != OrderStatus.READY && order.getStatus() != OrderStatus.CANCELED) {
                try {
                    Thread.sleep(3000); // Приостановка выполнения потока на 3 секунды
                    switch (order.getStatus()) {
                        case ACCEPTED:
                            order.setStatus(OrderStatus.PREPARING);
                            break;
                        case PREPARING:
                            order.setStatus(OrderStatus.READY);
                            break;
                    }
                    orderRepository.updateOrderStatus(order.getId(), order.getStatus());
                    Thread.sleep(5000); // Приостановка выполнения потока на 5 секунд
                } catch (InterruptedException e) {
                    // Обработка прерывания потока
                    e.printStackTrace();
                }
            }
        }
    }
}
