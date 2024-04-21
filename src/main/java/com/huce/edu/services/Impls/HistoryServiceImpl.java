package com.huce.edu.services.Impls;

import com.huce.edu.entities.HistoryEntity;
import com.huce.edu.models.dto.HistoryDto;
import com.huce.edu.repositories.HistoryRepo;
import com.huce.edu.services.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
	private final HistoryRepo historyRepo;

	@Override
	public void create(HistoryDto historyDto) {
		HistoryEntity historyEntity = add(historyDto);
		historyRepo.save(historyEntity);
	}

	public HistoryEntity add(HistoryDto historyDto) {
		return HistoryEntity.create(0, historyDto.getUid(), historyDto.getWid(), historyDto.getIscorrect());
	}
}
