package com.huce.edu.controllers;

import com.huce.edu.entities.*;
import com.huce.edu.models.ApiResult;
import com.huce.edu.models.dto.LevelDto;
import com.huce.edu.repositories.*;
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
@RequestMapping(path = "/api/level")
@RequiredArgsConstructor
public class LevelController {
    private final UserAccountRepo userAccountRepo;
    private final LevelService levelService;
    private final LevelRepo levelRepo;
    private final TopicRepo topicRepo;
    private final AdminsRepo adminsRepo;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResult<ArrayList<LevelDto>>> getAllLevels(HttpServletRequest request) {
        String email = BearerTokenUtil.getUserName(request);
        UserEntity user = userAccountRepo.findFirstByEmail(email);

        ApiResult<ArrayList<LevelDto>> result = ApiResult.create(HttpStatus.OK, "Lấy thành công level", levelService.getAll(user));
        return ResponseEntity.ok(result);
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResult<LevelEntity>> add(@RequestParam String name) {
        if (levelRepo.existsByLevel(name)) {
            ApiResult<LevelEntity> result = ApiResult.create(HttpStatus.BAD_REQUEST, "Tên Level đã tồn tại!!!", null);
            return ResponseEntity.ok(result);
        }

        ApiResult<LevelEntity> result = ApiResult.create(HttpStatus.OK, "Them thành công level", levelService.add(name));
        return ResponseEntity.ok(result);
    }

    @PutMapping("/edit")
    public ResponseEntity<ApiResult<LevelEntity>> edit(@RequestBody LevelEntity levelEntity) {
        LevelEntity level = levelRepo.findByLid(levelEntity.getLid());
        if (level == null) {
            ApiResult<LevelEntity> result = ApiResult.create(HttpStatus.BAD_REQUEST, "Không tồn tại Level!!", null);
            return ResponseEntity.ok(result);
        }
        if (levelRepo.existsByLevel(levelEntity.getLevel())) {
            LevelEntity levelByName = levelRepo.findFirstByLevel(levelEntity.getLevel());
            if (!Objects.equals(levelEntity.getLid(), levelByName.getLid())) {
                ApiResult<LevelEntity> result = ApiResult.create(HttpStatus.BAD_REQUEST, "Tên Level đã tồn tại!!!", null);
                return ResponseEntity.ok(result);
            }
        }

        ApiResult<LevelEntity> result = ApiResult.create(HttpStatus.OK, "Sua thành công level", levelService.edit(levelEntity));
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResult<LevelEntity>> delete(@RequestParam Integer id) {
        LevelEntity level = levelRepo.findByLid(id);
        if (level == null) {
            ApiResult<LevelEntity> result = ApiResult.create(HttpStatus.BAD_REQUEST, "Không tồn tại Level!!", null);
            return ResponseEntity.ok(result);
        }

        if (topicRepo.existsByLid(id)) {
            ApiResult<LevelEntity> result = ApiResult.create(HttpStatus.BAD_REQUEST, "Không thể xoá Level khi còn Topic thuộc Level đó!", null);
            return ResponseEntity.ok(result);
        }


        ApiResult<LevelEntity> result = ApiResult.create(HttpStatus.OK, "Xoa thành công level", levelService.delete(id));
        return ResponseEntity.ok(result);
    }


}
