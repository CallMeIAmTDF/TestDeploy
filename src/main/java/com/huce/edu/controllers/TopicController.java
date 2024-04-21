package com.huce.edu.controllers;


import com.huce.edu.entities.LevelEntity;
import com.huce.edu.entities.TopicEntity;
import com.huce.edu.entities.UserEntity;
import com.huce.edu.models.ApiResult;
import com.huce.edu.models.dto.ListTopicByLevel;
import com.huce.edu.repositories.*;
import com.huce.edu.services.TopicService;
import com.huce.edu.utils.BearerTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;


@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/topic")
@RequiredArgsConstructor
public class TopicController {

    private final WordRepo wordRepo;
    private final TopicRepo topicRepo;
    private final UserAccountRepo userAccountRepo;
    private final TopicService topicService;
    private final LevelRepo levelRepo;

    @GetMapping("/getTopicByLid")
    public ResponseEntity<ApiResult<ListTopicByLevel>> getTopicByLid(HttpServletRequest request, @RequestParam(defaultValue = "1") int lid) {
        ApiResult<ListTopicByLevel> result;
        LevelEntity level = levelRepo.findByLid(lid);
        if (level == null) {
            result = ApiResult.create(HttpStatus.BAD_REQUEST, "Không tìm thấy level " + lid, null);
            return ResponseEntity.ok(result);
        }

        String email = BearerTokenUtil.getUserName(request);
        UserEntity user = userAccountRepo.findFirstByEmail(email);
        result = ApiResult.create(HttpStatus.OK, "Lấy danh sách chủ đề thành công", topicService.getTopicByLevel(lid, user));
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getAllTopic")
    public ResponseEntity<ApiResult<ArrayList<Map<String, String>>>> getAllTopic(){
        ApiResult<ArrayList<Map<String, String>>> result = ApiResult.create(HttpStatus.OK, "Lấy danh sách chủ đề thành công", topicService.getAllTopic());
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getTopicByTid")
    public ResponseEntity<ApiResult<Map<String, String>>> getTopicByTid(@RequestParam int tid){
        ApiResult<Map<String, String>> result = ApiResult.create(HttpStatus.OK, "Lấy danh sách chủ đề thành công", topicService.getTopicByTid(tid));
        return ResponseEntity.ok(result);
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResult<TopicEntity>> add(@RequestParam Integer lid, @RequestParam String name) {
        ApiResult<TopicEntity> result;
        if (!levelRepo.existsById(lid)) {
            result = ApiResult.create(HttpStatus.BAD_REQUEST, "Không tìm thấy level " + lid, null);
            return ResponseEntity.ok(result);
        }
        if (topicRepo.existsByTopicAndLid(name, lid)) {
            result = ApiResult.create(HttpStatus.BAD_REQUEST, "Tên Topic đã tồn tại!!!", null);
            return ResponseEntity.ok(result);
        }

        result = ApiResult.create(HttpStatus.OK, "Them thành công Topic", topicService.add(lid, name));
        return ResponseEntity.ok(result);
    }

    @PutMapping("/edit")
    public ResponseEntity<ApiResult<TopicEntity>> edit(@RequestBody TopicEntity topicEntity) {
        ApiResult<TopicEntity> result;
        if (!topicRepo.existsByTid(topicEntity.getTid())) {
            result = ApiResult.create(HttpStatus.BAD_REQUEST, "Không tồn tại Topic!!", null);
            return ResponseEntity.ok(result);
        }
        if (!levelRepo.existsByLid(topicEntity.getLid())) {
            result = ApiResult.create(HttpStatus.BAD_REQUEST, "Không tồn tại Level!!", null);
            return ResponseEntity.ok(result);
        }
        TopicEntity topic = topicRepo.findByTopicAndLid(topicEntity.getTopic(), topicEntity.getLid());
        if (topic != null && !Objects.equals(topic.getTid(), topicEntity.getTid())) {
            result = ApiResult.create(HttpStatus.BAD_REQUEST, "Trong Level này tên Topic đã tồn tại!! ", null);
            return ResponseEntity.ok(result);
        }

        result = ApiResult.create(HttpStatus.OK, "Sua thành công Topic", topicService.edit(topicEntity));
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResult<TopicEntity>> delete(@RequestParam Integer id) {
        TopicEntity topic = topicRepo.findFirstByTid(id);
        if (topic == null) {
            ApiResult<TopicEntity> result = ApiResult.create(HttpStatus.BAD_REQUEST, "Không tồn tại Level!!", null);
            return ResponseEntity.ok(result);
        }

        if (wordRepo.existsByTid(id)) {
            ApiResult<TopicEntity> result = ApiResult.create(HttpStatus.BAD_REQUEST, "Không thể xoá Topic khi còn Word thuộc Topic đó!", null);
            return ResponseEntity.ok(result);
        }

        ApiResult<TopicEntity> result = ApiResult.create(HttpStatus.OK, "Xoa thành công level", topicService.delete(id));
        return ResponseEntity.ok(result);
    }
}
