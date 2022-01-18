package com.n11bootcamp.fourthhomework.api;

import com.n11bootcamp.fourthhomework.dto.DebtCreatDto;
import com.n11bootcamp.fourthhomework.dto.DebtDto;
import com.n11bootcamp.fourthhomework.entity.enumeration.DebtType;
import com.n11bootcamp.fourthhomework.service.DebtService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/debts")
@RequiredArgsConstructor
@CrossOrigin
public class DebtController {

    private final DebtService debtService;

    @PostMapping
    public ResponseEntity<DebtDto> createDebt(@RequestBody DebtCreatDto newDebt) {
        return ResponseEntity.ok(debtService.createDebt(newDebt, DebtType.NORMAL));
    }

    @GetMapping("/date-range/{startDate}/{endDate}")
    public ResponseEntity<List<DebtDto>> getDebtsInDateRange(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                             @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(debtService.getDebtsInDateRange(startDate, endDate));
    }

    @GetMapping("/user")
    public ResponseEntity getUserDebt(@RequestParam Long userId) {
        return ResponseEntity.ok(debtService.getUserDebts(userId));
    }

    @GetMapping("/overdue/user")
    public ResponseEntity getOverdueUserDebts(@RequestParam Long userId) {
        return ResponseEntity.ok(debtService.getOverdueUserDebts(userId));
    }

    @GetMapping("/total-debt/user")
    public ResponseEntity getUserTotalDebt(@RequestParam Long userId) {
        return ResponseEntity.ok(debtService.getUserTotalDebt(userId));
    }

    @GetMapping("/overdue-total-debt/user")
    public ResponseEntity getUserOverTotalDebt(@RequestParam Long userId) {
        return ResponseEntity.ok(debtService.getUserOverTotalDebt(userId));
    }

    @GetMapping("/late-interest-total/user")
    public ResponseEntity getUserTotalLateInterest(@RequestParam Long userId) {
        return ResponseEntity.ok(debtService.getUserTotalLateInterest(userId));
    }


}
