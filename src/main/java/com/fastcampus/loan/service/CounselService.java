package com.fastcampus.loan.service;

import com.fastcampus.loan.dto.CounselDto;

public interface CounselService {

    CounselDto.Response create(CounselDto.Request request);

}
