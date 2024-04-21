package com.huce.edu.controllers;

import com.huce.edu.entities.ProductEntity;
import com.huce.edu.entities.UserEntity;
import com.huce.edu.models.ApiResult;
import com.huce.edu.models.dto.LevelDto;
import com.huce.edu.models.dto.ProductDto;
import com.huce.edu.repositories.ProductRepo;
import com.huce.edu.repositories.UserAccountRepo;
import com.huce.edu.services.LevelService;
import com.huce.edu.utils.BearerTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepo productRepo;
    @GetMapping("/getAll")
    public ResponseEntity<ApiResult<List<ProductEntity>>> getAll() {
        ApiResult<List<ProductEntity>> result = ApiResult.create(HttpStatus.OK, "Lấy thành công sản phẩm.", productRepo.findAll());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResult<ProductEntity>> add(@RequestBody ProductDto productDto) {
        ApiResult<ProductEntity> result = null;

        if (productRepo.existsByName(productDto.getName())){
            result = ApiResult.create(HttpStatus.BAD_REQUEST, "Tên sản phẩm đã tồn tại.", null);
            return ResponseEntity.ok(result);
        }

        ProductEntity newProduct = ProductEntity.create(
                0,
                productDto.getName(),
                productDto.getPrice(),
                productDto.getImage(),
                productDto.getRemain(),
                false
        );
        productRepo.save(newProduct);

        result = ApiResult.create(HttpStatus.OK, "Thêm thành công sản phẩm.", productRepo.findFirstByName(newProduct.getName()));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/edit")
    public ResponseEntity<ApiResult<ProductEntity>> edit(@RequestBody ProductEntity productEntity) {
        ApiResult<ProductEntity> result;

        ProductEntity productById = productRepo.findFirstByPid(productEntity.getPid());

        if (productRepo.existsByName(productEntity.getName()) && !Objects.equals(productById.getName(), productEntity.getName())){
            result = ApiResult.create(HttpStatus.BAD_REQUEST, "Tên sản phẩm đã tồn tại.", null);
            return ResponseEntity.ok(result);
        }

        productById.setName(productEntity.getName());
        productById.setImage(productEntity.getImage());
        if (productEntity.getPrice() > 0)
            productById.setPrice(productEntity.getPrice());
        if (productEntity.getRemain() >= 0)
            productById.setRemain(productEntity.getRemain());

        productRepo.save(productById);

        result = ApiResult.create(HttpStatus.OK, "Cập nhật thành công sản phẩm.", productById);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResult<ProductEntity>> delete(@RequestParam int pid) {
        ApiResult<ProductEntity> result;

        ProductEntity productById = productRepo.findFirstByPid(pid);

        if (productById.getIsDeleted()){
            result = ApiResult.create(HttpStatus.BAD_REQUEST, "Sản phẩm đã được xoá rồi!!!", null);
            return ResponseEntity.ok(result);
        }

        productById.setIsDeleted(true);
        productRepo.save(productById);

        result = ApiResult.create(HttpStatus.OK, "Xoá thành công sản phẩm.", productById);
        return ResponseEntity.ok(result);
    }
}
