package com.huce.edu.services;

import com.huce.edu.entities.OrderEntity;
import com.huce.edu.entities.UserEntity;
import com.huce.edu.models.dto.OrderDto;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public interface OrderService {
    OrderEntity add(OrderDto orderDto, UserEntity user);
    ArrayList<Map<String, Object>> getAllOrderDetail();
}
