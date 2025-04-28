package com.aliwert.controller.impl;

import com.aliwert.controller.BaseController;
import com.aliwert.controller.IPaymentController;
import com.aliwert.controller.RootEntity;
import com.aliwert.dto.DtoPayment;
import com.aliwert.dto.insert.DtoPaymentInsert;
import com.aliwert.dto.update.DtoPaymentUpdate;
import com.aliwert.model.Payment;
import com.aliwert.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentControllerImpl extends BaseController implements IPaymentController {

    private final PaymentService paymentService;

    @GetMapping("/list")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoPayment>> getAllPayments() {
        return ok(paymentService.getAllPayments());
    }

    @GetMapping("/user/{userId}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoPayment>> getPaymentsByUserId(@PathVariable Long userId) {
        return ok(paymentService.getPaymentsByUserId(userId));
    }

    @GetMapping("/list/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoPayment> getPaymentById(@PathVariable Long id) {
        return ok(paymentService.getPaymentById(id));
    }

    @GetMapping("/transaction/{transactionId}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoPayment> getPaymentByTransactionId(@PathVariable String transactionId) {
        return ok(paymentService.getPaymentByTransactionId(transactionId));
    }

    @GetMapping("/status/{status}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<List<DtoPayment>> getPaymentsByStatus(@PathVariable String status) {
        try {
            Payment.PaymentStatus paymentStatus = Payment.PaymentStatus.valueOf(status.toUpperCase());
            return ok(paymentService.getPaymentsByStatus(paymentStatus));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid payment status: " + status);
        }
    }

    @PostMapping("/create")
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public RootEntity<DtoPayment> createPayment(@RequestBody DtoPaymentInsert dtoPaymentInsert) {
        return ok(paymentService.createPayment(dtoPaymentInsert));
    }

    @PutMapping("/update/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoPayment> updatePayment(@PathVariable Long id, @RequestBody DtoPaymentUpdate dtoPaymentUpdate) {
        return ok(paymentService.updatePayment(id, dtoPaymentUpdate));
    }

    @PostMapping("/process/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoPayment> processPayment(@PathVariable Long id) {
        return ok(paymentService.processPayment(id));
    }

    @PostMapping("/refund/{id}")
    @Override
    @ResponseStatus(HttpStatus.OK)
    public RootEntity<DtoPayment> refundPayment(@PathVariable Long id) {
        return ok(paymentService.refundPayment(id));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public RootEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return null;
    }
}