package com.huce.edu.controllers;


import com.huce.edu.entities.OrderEntity;
import com.huce.edu.entities.ProductEntity;
import com.huce.edu.entities.UserEntity;
import com.huce.edu.models.ApiResult;
import com.huce.edu.models.dto.OrderDto;
import com.huce.edu.repositories.*;
import com.huce.edu.services.OrderService;
import com.huce.edu.utils.BearerTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final UserAccountRepo userAccountRepo;
    private final ProductRepo productRepo;
    private final OrderService orderService;
    private final OrderRepo orderRepo;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResult<List<OrderEntity>>> getAll(HttpServletRequest request) {
        String email = BearerTokenUtil.getUserName(request);
        UserEntity user = userAccountRepo.findFirstByEmail(email);

        ApiResult<List<OrderEntity>> result = ApiResult.create(HttpStatus.OK, "Lấy thành công lịch sử order.", orderRepo.findByUid(user.getUid()));
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getAllOrder")
    public ResponseEntity<ApiResult<ArrayList<Map<String, Object>>>> getAllOrder() {
        ApiResult<ArrayList<Map<String, Object>>> result = ApiResult.create(HttpStatus.OK, "Lấy thành công lịch sử order.", orderService.getAllOrderDetail());
        return ResponseEntity.ok(result);
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResult<?>> add(HttpServletRequest request, @RequestBody OrderDto orderDto) {
        ApiResult<?> result;
        String email = BearerTokenUtil.getUserName(request);
        UserEntity user = userAccountRepo.findFirstByEmail(email);

        ProductEntity product = productRepo.findFirstByPid(orderDto.getPid());
        if (product == null) {
            result = ApiResult.create(HttpStatus.BAD_REQUEST, "Sản phẩm không tồn tại!!!", null);
            return ResponseEntity.ok(result);
        }
        if (orderDto.getQuantity() * product.getPrice() > user.getCoin()) {
            result = ApiResult.create(HttpStatus.BAD_REQUEST, "Số dư tài khoản không đủ để mua sản phẩm!!!", null);
            return ResponseEntity.ok(result);
        }
        if (orderDto.getQuantity() > 0 && orderDto.getQuantity() > product.getRemain()) {
            result = ApiResult.create(HttpStatus.BAD_REQUEST, "Số lượng sản phẩm không hợp lệ!!!", null);
            return ResponseEntity.ok(result);
        }
        result = ApiResult.create(HttpStatus.OK, "Đổi sản phẩm thành công.", orderService.add(orderDto, user));
        return ResponseEntity.ok(result);
    }
}
