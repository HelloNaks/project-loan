package com.fastcampus.loan.service;

import com.fastcampus.loan.domain.Application;
import com.fastcampus.loan.domain.Terms;
import com.fastcampus.loan.dto.ApplicationDTO;
import com.fastcampus.loan.exception.BaseException;
import com.fastcampus.loan.exception.ResultType;
import com.fastcampus.loan.repository.AcceptTermsRepository;
import com.fastcampus.loan.repository.ApplicationRepository;
import com.fastcampus.loan.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService
{

    private final ApplicationRepository applicationRepository;

    private final TermsRepository termsRepository;

    private final AcceptTermsRepository acceptTermsRepository;

    private final ModelMapper modelMapper;

    @Override
    public ApplicationDTO.Response create(ApplicationDTO.Request request) {
        Application application = modelMapper.map(request, Application.class);
        application.setAppliedAt(LocalDateTime.now());

        Application applied = applicationRepository.save(application);
        return modelMapper.map(applied, ApplicationDTO.Response.class);
    }

    @Override
    public ApplicationDTO.Response get(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(()->{
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        return modelMapper.map(application, ApplicationDTO.Response.class);
    }

    @Override
    public ApplicationDTO.Response update(Long applicationId, ApplicationDTO.Request request) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        application.setName(request.getName());
        application.setCellPhone(request.getCellPhone());
        application.setEmail(request.getEmail());
        application.setHopeAmount(request.getHopeAmount());

        applicationRepository.save(application);

        return modelMapper.map(application, ApplicationDTO.Response.class);
    }

    @Override
    public void delete(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        application.setIsDeleted(true);
        applicationRepository.save(application);
    }

    @Override
    public Boolean acceptTerms(Long applicationId, ApplicationDTO.AcceptTerms request) {
        applicationRepository.findById(applicationId).orElseThrow(()->{
           throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        List<Terms> termsList = termsRepository.findAll(Sort.by(Sort.Direction.ASC, "termsId"));
        if(termsList.isEmpty()){
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        List<Long> acceptTermsIds = request.getAcceptTermsIds();
        if(termsList.size() != acceptTermsIds.size()){
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        List<Long> termsIds = termsList.stream().map(Terms::getTermsId).collect(Collectors.toList());
        Collections.sort(acceptTermsIds);

        if(!termsIds.containsAll(acceptTermsIds)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
        System.out.println("aaaaaa");
        for(Long termsId : acceptTermsIds){
            com.fastcampus.loan.domain.AcceptTerms accepted = com.fastcampus.loan.domain.AcceptTerms.builder()
                    .termsId(termsId)
                    .applicationId(applicationId)
                    .build();

            acceptTermsRepository.save(accepted);
        }

        System.out.println("bbbbbb");


        return true;
    }


}
