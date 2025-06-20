package com.sky.mapper;


import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {



    void insertBreath(List<DishFlavor> flavors);


    void deleteByDishId(Long id);

    List<DishFlavor> getByDishId(Long id);
}
