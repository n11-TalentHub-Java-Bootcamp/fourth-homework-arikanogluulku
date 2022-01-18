package com.n11bootcamp.fourthhomework.api;

import com.n11bootcamp.fourthhomework.service.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@RestController
@RequestMapping("api/v1/collections")
@RequiredArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;

    @PostMapping
    public ResponseEntity createCollection(BigDecimal amount, Long debtId) {
        return ResponseEntity.ok(collectionService.createCollection(amount, debtId));
    }

    @GetMapping("/date-range/{startDate}/{endDate}")
    public ResponseEntity getCollectionsInDateRange(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                    @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(collectionService.getCollectionsInDateRange(startDate, endDate));
    }

    @GetMapping("/user")
    public ResponseEntity getUsersCollections(@RequestParam Long userId) {
        return ResponseEntity.ok(collectionService.getUsersCollections(userId));
    }

    @GetMapping("/total-late-fee/user")
    public ResponseEntity getUserLateFeeTotal(@RequestParam Long userId) {
        return ResponseEntity.ok(collectionService.getUserLateFeeTotal(userId));
    }
}
