package com.sky.task;


import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 处理支付超时订单
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void processTimeoutOrder() {
        log.info("处理支付超时订单");
        LocalDateTime now = LocalDateTime.now().minusMinutes(-15);//15分钟内未支付
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTime(Orders.PENDING_PAYMENT,now);
        if(ordersList != null){
            for (Orders orders : ordersList) {
               orders.setStatus(Orders.CANCELLED);
               orders.setCancelReason("订单超时，自动取消");
               orders.setCancelTime(LocalDateTime.now());
               orderMapper.update(orders);
            }
        }

    }
    /**
     * 处理一直处用于派送的订单
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void processDeliveryOrder() {
        log.info("处理一直处于派送状态的订单");
        LocalDateTime now = LocalDateTime.now().minusMinutes(-60);//一小时内未支付
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTime(Orders.DELIVERY_IN_PROGRESS,now);
        if(ordersList != null){
            for (Orders orders : ordersList) {
                orders.setStatus(Orders.COMPLETED);
                orderMapper.update(orders);
            }
        }
    }

}
