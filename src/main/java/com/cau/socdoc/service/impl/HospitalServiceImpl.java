package com.cau.socdoc.service.impl;

import com.cau.socdoc.component.KakaoPharInfo;
import com.cau.socdoc.domain.Hospital;
import com.cau.socdoc.dto.response.*;
import com.cau.socdoc.dto.response.kakao.Document;
import com.cau.socdoc.dto.response.kakao.ResponseKakaoDto;
import com.cau.socdoc.repository.HospitalRepository;
import com.cau.socdoc.repository.LikeRepository;
import com.cau.socdoc.repository.ReviewRepository;
import com.cau.socdoc.service.HospitalService;
import com.cau.socdoc.util.MessageUtil;
import com.cau.socdoc.util.api.ResponseCode;
import com.cau.socdoc.util.exception.HospitalException;
import com.cau.socdoc.util.exception.LikeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.cau.socdoc.util.MessageUtil.days;

@Slf4j
@RequiredArgsConstructor
@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;
    private final ReviewRepository reviewRepository;
    private final LikeRepository likeRepository;
    private final KakaoPharInfo kakaoPharInfo;

    @Value("${REST_API_KEY}")
    private String REST_API_KEY;

    // 병원 상세정보 조회
    @Override
    @Transactional(readOnly = true)
    public ResponseDetailHospitalDto findHospitalDetailById(String hospitalId, String userId) throws ExecutionException, InterruptedException {
        Hospital hospital = hospitalRepository.findHospitalDetail(hospitalId);
        log.info("병원 상세정보 조회 완료: " + hospitalId);
        return ResponseDetailHospitalDto.builder()
                .hpid(hospital.getHpid())
                .name(hospital.getDutyName())
                .phone(hospital.getDutyTel1())
                .address(hospital.getDutyAddr())
                .description(hospital.getDutyMapimg())
                .likeCount(likeRepository.findLikeCountByHospitalId(hospitalId))
                .time(findTime(hospital))
                .userLiked(likeRepository.existsLikeByUserIdAndHospitalId(userId, hospitalId))
                .rating(reviewRepository.getReviewAverage(hospitalId))
                .build();
    }

    // 특정 지역의 특정 분과 병원 조회
    @Override
    @Transactional(readOnly = true)
    public List<ResponseSimpleHospitalDto> findHospitalByTypeAndAddress(String type, String address1, String address2, int pageNum, int sortType) throws ExecutionException, InterruptedException {
        String hospitalType = MessageUtil.codeToHospitalType(type);
        List<Hospital> hospitals = hospitalRepository.findHospitalByTypeAndAddress(hospitalType, address1, address2, pageNum, sortType);
        log.info("특정 지역 특정 분과 병원 조회 완료: " + hospitalType + "- " + address1 + " " + address2);
        List<ResponseSimpleHospitalDto> dtos = new ArrayList<>();
        for (Hospital hospital : hospitals) {
            dtos.add(hospitalToSimpleHospitalDto(hospital));
        }
        return dtos;
    }

    // 특정 지역의 병원 조회
    @Override
    @Transactional(readOnly = true)
    public List<ResponseSimpleHospitalDto> findHospitalByAddress(String address1, String address2, int pageNum, int sortType) throws ExecutionException, InterruptedException {
        List<Hospital> hospitals = hospitalRepository.findHospitalByAddress(address1, address2, pageNum, sortType);
        log.info("특정 지역 병원 조회 완료: " + address1 + " " + address2);
        List<ResponseSimpleHospitalDto> dtos = new ArrayList<>();
        for (Hospital hospital : hospitals) {
            dtos.add(hospitalToSimpleHospitalDto(hospital));
        }
        return dtos;
    }

    // 유저가 좋아요 한 병원 조회
    @Override
    @Transactional(readOnly = true)
    public List<ResponseSimpleHospitalDto> findHospitalByLike(String userId) throws ExecutionException, InterruptedException {
        List<Hospital> hospitals = hospitalRepository.findHospitalByLike(userId);
        log.info("유저가 좋아요 누른 병원 조회 완료: " + userId);
        return hospitals.stream().map(hospital -> {
            try {
                return hospitalToSimpleHospitalDto(hospital);
            } catch (ExecutionException | InterruptedException e) {
                throw new HospitalException(ResponseCode.HOSPITAL_NOT_FOUND);
            }
        }).collect(Collectors.toList());
    }

    // 유저가 특정 병원에 좋아요
    @Override
    @Transactional
    public void saveLike(String userId, String hospitalId) throws ExecutionException, InterruptedException {
        if (likeRepository.existsLikeByUserIdAndHospitalId(userId, hospitalId)) {
            throw new LikeException(ResponseCode.LIKE_ALREADY_EXIST);
        }
        likeRepository.saveLike(userId, hospitalId);
        log.info("유저가 특정 병원에 좋아요 완료: " + userId + " " + hospitalId);
    }

    // 유저가 특정 병원에 좋아요 취소
    @Override
    @Transactional
    public void deleteLike(String userId, String hospitalId) throws ExecutionException, InterruptedException {
        if (!likeRepository.existsLikeByUserIdAndHospitalId(userId, hospitalId)) {
            throw new LikeException(ResponseCode.LIKE_NOT_FOUND);
        }
        likeRepository.deleteLike(userId, hospitalId);
        log.info("유저가 특정 병원에 좋아요 취소 완료: " + userId + " " + hospitalId);
    }

    // 유저의 메인페이지 4개 병원 분과 코드를 받아 병원 목록 조회
    @Override
    @Transactional(readOnly = true)
    public List<ResponseSimpleHospitalDto> findHospitalByMain(String address1, String address2, String code1, String code2, String code3, String code4) throws ExecutionException, InterruptedException {
        List<Hospital> hospitals = hospitalRepository.findHospitalByMain(address1, address2, code1, code2, code3, code4);
        log.info("유저의 메인페이지 4개 병원 분과 코드를 받아 병원 목록 조회 완료: " + address1 + " " + address2 + " " + MessageUtil.codeToHospitalType(code1) + " " + MessageUtil.codeToHospitalType(code2) + " " + MessageUtil.codeToHospitalType(code3) + " " + MessageUtil.codeToHospitalType(code4));
        List<ResponseSimpleHospitalDto> dtos = new ArrayList<>();
        for (Hospital hospital : hospitals) {
            dtos.add(hospitalToSimpleHospitalDto(hospital));
        }
        return dtos;
    }

    // 병원의 약국 조회
    @Transactional(readOnly = true)
    public List<ResponsePharmacyDto> findPharmacyByHospitalId(String hospitalId) throws ExecutionException, InterruptedException {
        Hospital hospital = hospitalRepository.findHospitalDetail(hospitalId);
        log.info("약국 탐색의 기준 병원 조회 완료: " + hospitalId);
        ResponseKakaoDto dto = kakaoPharInfo.getPharInfo(REST_API_KEY, hospital.getWgs84Lat(), hospital.getWgs84Lon());
        log.info("근처 약국 조회 완료: " + hospitalId + " " + dto.getDocuments().length + "개");

        List<ResponsePharmacyDto> pharmacies = new ArrayList<>();
        for (Document document : dto.getDocuments()) {
            ResponsePharmacyDto pharmacy = ResponsePharmacyDto.builder()
                    .name(document.getPlace_name())
                    .address(document.getAddress_name())
                    .build();
            pharmacies.add(pharmacy);
        }
        return pharmacies;
    }


    private List<String> findTime(Hospital hospital) {
        List<String> time = new ArrayList<>();
        time.add(hospital.getDutyTime1s() + " - " + hospital.getDutyTime1c());
        time.add(hospital.getDutyTime2s() + " - " + hospital.getDutyTime2c());
        time.add(hospital.getDutyTime3s() + " - " + hospital.getDutyTime3c());
        time.add(hospital.getDutyTime4s() + " - " + hospital.getDutyTime4c());
        time.add(hospital.getDutyTime5s() + " - " + hospital.getDutyTime5c());
        time.add(hospital.getDutyTime6s() + " - " + hospital.getDutyTime6c());
        for (int i = 0; i < 6; i++) {
            // 현재 형태는 0900 - 1800 이며, 09:00 - 18:00으로 바꿔줘야함
            String[] split = time.get(i).split(" - ");
            String start = split[0];
            String end = split[1];
            String newStart = start.substring(0, 2) + ":" + start.substring(2, 4);
            String newEnd = end.substring(0, 2) + ":" + end.substring(2, 4);
            time.set(i, days[i] + " " + newStart + " - " + newEnd);
        }
        log.info("병원 영업시간 조회 완료: " + hospital.getHpid());
        return time;
    }

    private ResponseSimpleHospitalDto hospitalToSimpleHospitalDto(Hospital hospital) throws ExecutionException, InterruptedException {
        return ResponseSimpleHospitalDto.builder()
                .hpid(hospital.getHpid())
                .name(hospital.getDutyName())
                .address(hospital.getDutyAddr())
                .rating(reviewRepository.getReviewAverage(hospital.getHpid()))
                .latitude(hospital.getWgs84Lat())
                .longitude(hospital.getWgs84Lon())
                .build();
    }
}
