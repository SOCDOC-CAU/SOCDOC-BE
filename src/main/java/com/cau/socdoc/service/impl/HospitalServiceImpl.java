package com.cau.socdoc.service.impl;

import com.cau.socdoc.repository.HospitalRepository;
import com.cau.socdoc.repository.LikeRepository;
import com.cau.socdoc.service.HospitalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;
    private final LikeRepository likeRepository;

}
