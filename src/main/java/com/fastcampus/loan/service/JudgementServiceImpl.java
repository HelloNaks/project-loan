package com.fastcampus.loan.service;

import com.fastcampus.loan.domain.Judgement;
import com.fastcampus.loan.dto.JudgementDTO;
import com.fastcampus.loan.exception.BaseException;
import com.fastcampus.loan.exception.ResultType;
import com.fastcampus.loan.repository.ApplicationRepository;
import com.fastcampus.loan.repository.JudgementRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class JudgementServiceImpl implements JudgementService {

    private final JudgementRepository judgementRepository;

    private final ApplicationRepository applicationRepository;

    private final ModelMapper modelMapper;

    @Override
    public JudgementDTO.Response create(JudgementDTO.Request request) {
        Long applicationId = request.getApplicationId();
        if(!isPresentApplication(applicationId)){
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        Judgement judgement = modelMapper.map(request, Judgement.class);

        Judgement saved = judgementRepository.save(judgement);

        return modelMapper.map(saved, JudgementDTO.Response.class);
    }

    private boolean isPresentApplication(Long applicationId){
        return applicationRepository.findById(applicationId).isPresent();
    }
}
