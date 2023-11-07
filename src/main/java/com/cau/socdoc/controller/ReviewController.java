package com.cau.socdoc.controller;

import com.cau.socdoc.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/review")
@RestController
public class ReviewController {

    private final ReviewService reviewService;
}
