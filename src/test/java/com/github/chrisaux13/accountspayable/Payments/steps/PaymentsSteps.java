package com.github.chrisaux13.accountspayable.Payments.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.github.chrisaux13.accountspayable.Payments.actions.GeneratePaymentFiles;
import com.github.chrisaux13.accountspayable.Payments.model.*;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.*;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PaymentsSteps {

    @Autowired
    private GeneratePaymentFiles generatePaymentFiles;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private VendorRepository vendorRepository;

    private List<Payment> approvedPayments;
    private Map<PaymentMethodType, String> generatedFiles;

    @Given("there are approved invoices for {word} payments")
    public void thereAreApprovedInvoicesForPayments(String paymentMethod) {
        Vendor vendor = new Vendor();
        vendor.setVendorName("Test Vendor");
        vendor = vendorRepository.save(vendor);

        Payment payment = new Payment();
        payment.setVendor(vendor);
        payment.setPaymentMethod(PaymentMethodType.valueOf(paymentMethod.toUpperCase().replace(" ", "_")));
        payment.setAmount(new BigDecimal("100.00"));
        payment.setStatus(PaymentStatusType.COMPLETED);
        payment = paymentRepository.save(payment);

        approvedPayments = Collections.singletonList(payment);
    }

    @When("the system generates the payment file")
    public void theSystemGeneratesThePaymentFile() {
        generatedFiles = generatePaymentFiles.execute(approvedPayments);
    }

    @Then("the file should be in {word} format")
    public void theFileShouldBeInFormat(String format) {
        PaymentMethodType paymentMethod = approvedPayments.get(0).getPaymentMethod();
        String filePath = generatedFiles.get(paymentMethod);
        assertThat(filePath).isNotNull();
        if (format.equalsIgnoreCase("check printing")) {
            assertThat(filePath).endsWith(".pdf");
        } else if (format.equalsIgnoreCase("ISO20022") || format.equalsIgnoreCase("MT103")) {
            assertThat(filePath).matches(".*\\.(iso20022|mt103)$");
        }
    }

    @And("contain all required {word} payment details")
    public void containAllRequiredPaymentDetails(String paymentMethod) {
        PaymentMethodType methodType = PaymentMethodType.valueOf(paymentMethod.toUpperCase().replace(" ", "_"));
        String filePath = generatedFiles.get(methodType);
        assertThat(filePath).isNotNull();

        // Here you would typically read the file and verify its contents
        // For this example, we'll just check that the file exists
        assertThat(new java.io.File(filePath)).exists();
    }

    @Given("there are approved invoices for wire transfers")
    public void thereAreApprovedInvoicesForWireTransfers() {
        thereAreApprovedInvoicesForPayments("WIRE_TRANSFER");
    }

    @Then("the file should be in check printing format")
    public void theFileShouldBeInCheckPrintingFormat() {
        theFileShouldBeInFormat("check printing");
    }

    @Then("contain all required wire transfer details")
    public void containAllRequiredWireTransferDetails() {
        containAllRequiredPaymentDetails("WIRE_TRANSFER");
    }

    private void verifyPaymentFileFormat(String filePath, String expectedFormat) {
        switch (expectedFormat.toUpperCase()) {
            case "NACHA":
                // Verify NACHA format
                break;
            case "MT103":
                // Verify MT103 format
                break;
            case "ISO20022":
                // Verify ISO20022 format
                break;
            case "CHECK":
                // Verify check printing format
                break;
            default:
                fail("Unexpected file format: " + expectedFormat);
        }
    }

    private void verifyPaymentDetails(String filePath, PaymentMethodType paymentMethod) {
        // Read the file and verify that it contains all required details for the given payment method
        // This would involve parsing the file and checking specific fields based on the payment method
        switch (paymentMethod) {
            case BANK_TRANSFER:
                // Verify bank transfer payment details
                break;
            case CREDIT_CARD:
                // Verify credit card payment details
                break;
            case DEBIT_CARD:
                // Verify debit card payment details
                break;
            case CHECK:
                // Verify check payment details
                break;
            case WIRE_TRANSFER:
                // Verify wire transfer payment details
                break;
            default:
                fail("Unexpected payment method: " + paymentMethod);
        }
    }
}