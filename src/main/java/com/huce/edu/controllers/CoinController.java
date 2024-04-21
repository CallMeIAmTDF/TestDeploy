package com.huce.edu.controllers;

import com.huce.edu.entities.HistoryEntity;
import com.huce.edu.entities.UserEntity;
import com.huce.edu.models.ApiResult;
import com.huce.edu.models.dto.HistoryDto;
import com.huce.edu.repositories.HistoryRepo;
import com.huce.edu.repositories.UserAccountRepo;
import com.huce.edu.repositories.WordRepo;
import com.huce.edu.services.HistoryService;
import com.huce.edu.shareds.Constants;
import com.huce.edu.utils.BearerTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/coin")
@RequiredArgsConstructor
public class CoinController {

    private final UserAccountRepo userAccountRepo;
    private final HistoryRepo historyRepo;
    private final WordRepo wordRepo;
    private final HistoryService historyService;

    @GetMapping("/getCoin")
    public ResponseEntity<ApiResult<?>> getCoin(HttpServletRequest request) {
        String email = BearerTokenUtil.getUserName(request);
        UserEntity user = userAccountRepo.findFirstByEmail(email);
        ApiResult<?> result =ApiResult.create(HttpStatus.OK, "Lấy thành công Coin.", user.getCoin());
        return ResponseEntity.ok(result);
    }


    /* Sau code sửa sau */
    @GetMapping("/post")
    public ResponseEntity<ApiResult<?>> sendAnswer(HttpServletRequest request, @RequestParam String answer, @RequestParam int wid) {
        ApiResult<?> result;

        String email = BearerTokenUtil.getUserName(request);
        UserEntity user = userAccountRepo.findFirstByEmail(email);
        ArrayList<HistoryEntity> historyEntityList = historyRepo.findByUid(user.getUid());
        if (!wordRepo.existsByWid(wid)) {
            result = ApiResult.create(HttpStatus.BAD_REQUEST, "Câu hỏi không tồn tại!", 0);
            return ResponseEntity.ok(result);
        }

        if (historyEntityList.stream().anyMatch(t -> t.getWid() == wid)) {
            result = ApiResult.create(HttpStatus.OK, "Câu này đã trả lời trước đây", 0);
            return ResponseEntity.ok(result);
        }

        if (wordRepo.findByWid(wid).getWord().equalsIgnoreCase(answer)) {
            user.setCoin(user.getCoin()+Constants.ANSWER_BONUS_AMOUNT);
            userAccountRepo.save(user);
            historyService.create(HistoryDto.create(user.getUid(), wid, 1));
            result = ApiResult.create(HttpStatus.OK, "Nhận được xu", Constants.ANSWER_BONUS_AMOUNT);
        } else {
            historyService.create(HistoryDto.create(user.getUid(), wid, 0));
            result = ApiResult.create(HttpStatus.OK, "Bạn đã trả lời sai", 0);
        }
        return ResponseEntity.ok(result);
    }
}
