package com.fastcampus.loan.service;

import com.fastcampus.loan.dto.JudgementDTO;

public interface JudgementService {

    JudgementDTO.Response create(JudgementDTO.Request request);
}
