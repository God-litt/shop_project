package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    void insert(List<OrderDetail> orderDetailList);

    List<OrderDetail> getByOrderId(Long orderId);
}
