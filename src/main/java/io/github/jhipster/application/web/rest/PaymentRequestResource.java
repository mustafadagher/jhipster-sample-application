package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.PaymentRequest;
import io.github.jhipster.application.repository.PaymentRequestRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PaymentRequest.
 */
@RestController
@RequestMapping("/api")
public class PaymentRequestResource {

    private final Logger log = LoggerFactory.getLogger(PaymentRequestResource.class);

    private static final String ENTITY_NAME = "paymentRequest";

    private final PaymentRequestRepository paymentRequestRepository;

    public PaymentRequestResource(PaymentRequestRepository paymentRequestRepository) {
        this.paymentRequestRepository = paymentRequestRepository;
    }

    /**
     * POST  /payment-requests : Create a new paymentRequest.
     *
     * @param paymentRequest the paymentRequest to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paymentRequest, or with status 400 (Bad Request) if the paymentRequest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payment-requests")
    @Timed
    public ResponseEntity<PaymentRequest> createPaymentRequest(@RequestBody PaymentRequest paymentRequest) throws URISyntaxException {
        log.debug("REST request to save PaymentRequest : {}", paymentRequest);
        if (paymentRequest.getId() != null) {
            throw new BadRequestAlertException("A new paymentRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentRequest result = paymentRequestRepository.save(paymentRequest);
        return ResponseEntity.created(new URI("/api/payment-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payment-requests : Updates an existing paymentRequest.
     *
     * @param paymentRequest the paymentRequest to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paymentRequest,
     * or with status 400 (Bad Request) if the paymentRequest is not valid,
     * or with status 500 (Internal Server Error) if the paymentRequest couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payment-requests")
    @Timed
    public ResponseEntity<PaymentRequest> updatePaymentRequest(@RequestBody PaymentRequest paymentRequest) throws URISyntaxException {
        log.debug("REST request to update PaymentRequest : {}", paymentRequest);
        if (paymentRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentRequest result = paymentRequestRepository.save(paymentRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paymentRequest.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payment-requests : get all the paymentRequests.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of paymentRequests in body
     */
    @GetMapping("/payment-requests")
    @Timed
    public List<PaymentRequest> getAllPaymentRequests() {
        log.debug("REST request to get all PaymentRequests");
        return paymentRequestRepository.findAll();
    }

    /**
     * GET  /payment-requests/:id : get the "id" paymentRequest.
     *
     * @param id the id of the paymentRequest to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paymentRequest, or with status 404 (Not Found)
     */
    @GetMapping("/payment-requests/{id}")
    @Timed
    public ResponseEntity<PaymentRequest> getPaymentRequest(@PathVariable Long id) {
        log.debug("REST request to get PaymentRequest : {}", id);
        Optional<PaymentRequest> paymentRequest = paymentRequestRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paymentRequest);
    }

    /**
     * DELETE  /payment-requests/:id : delete the "id" paymentRequest.
     *
     * @param id the id of the paymentRequest to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payment-requests/{id}")
    @Timed
    public ResponseEntity<Void> deletePaymentRequest(@PathVariable Long id) {
        log.debug("REST request to delete PaymentRequest : {}", id);

        paymentRequestRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
