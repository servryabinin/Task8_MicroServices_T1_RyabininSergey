package ru.example.micro.clientprocessing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.example.micro.clientprocessing.model.BlackListEntry;
import ru.example.micro.clientprocessing.service.BlackListService;
import ru.example.micro.logging.annotation.HttpIncomeRequestLog;
import ru.example.micro.logging.annotation.HttpOutcomeRequestLog;
import ru.example.micro.logging.annotation.Metric;

import java.util.List;

@RestController
@RequestMapping("/api/blacklist")
public class BlackListController {

    private final BlackListService service;

    public BlackListController(BlackListService service) {
        this.service = service;
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @GetMapping
    @Metric
    public List<BlackListEntry> getAll() {
        return service.getAll();
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @PostMapping
    @Metric
    @PreAuthorize("hasAnyRole('MASTER', 'GRAND_EMPLOYEE')")
    public BlackListEntry add(@RequestParam String documentId, @RequestParam(required = false) String reason) {
        return service.addToBlackList(documentId, reason);
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @DeleteMapping("/{documentId}")
    @Metric
    @PreAuthorize("hasAnyRole('MASTER', 'GRAND_EMPLOYEE')")
    public ResponseEntity<Void> remove(@PathVariable String documentId) {
        service.removeFromBlackList(documentId);
        return ResponseEntity.noContent().build();
    }

    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @GetMapping("/check/{documentId}")
    @Metric
    public ResponseEntity<Boolean> check(@PathVariable String documentId) {
        return ResponseEntity.ok(service.isBlocked(documentId));
    }
}
