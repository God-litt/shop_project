package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {

    void insert(Orders order);

    @Select("select * from orders where status = #{pendingPayment} and order_time < #{now}")
    List<Orders> getByStatusAndOrderTime(Integer pendingPayment, LocalDateTime now);


    void update(Orders orders);

    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 跳过微信支付直接修改数据库订单信息
     * @param orders:
     * @return void
     */
    @Update("update orders set pay_status = #{payStatus}, status = #{status}, checkout_time = #{checkoutTime} " +
            "where number = #{number}")
    void updateByOrderNumber(Orders orders);

    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    Page<Orders> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);
    @Select("select count(id) from orders where status = #{toBeConfirmed}")
    Integer countStatus(Integer toBeConfirmed);
}
