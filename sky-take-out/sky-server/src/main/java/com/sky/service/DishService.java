package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

     void saveWithFlavor(DishDTO dishDTO);

     PageResult pagequery(DishPageQueryDTO dishPageQueryDTO);

     void deleteById(List<Long> ids);

     DishVO getByIdWithFlavor(Long id);

     void update(DishDTO dishDTO);

     void updateStatus(Integer status, Long id);

    List<Dish> list(Long categoryId);
}
