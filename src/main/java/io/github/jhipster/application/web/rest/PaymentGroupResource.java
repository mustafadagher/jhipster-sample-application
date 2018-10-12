package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.PaymentGroup;
import io.github.jhipster.application.repository.PaymentGroupRepository;
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
 * REST controller for managing PaymentGroup.
 */
@RestController
@RequestMapping("/api")
public class PaymentGroupResource {

    private final Logger log = LoggerFactory.getLogger(PaymentGroupResource.class);

    private static final String ENTITY_NAME = "paymentGroup";

    private final PaymentGroupRepository paymentGroupRepository;

    public PaymentGroupResource(PaymentGroupRepository paymentGroupRepository) {
        this.paymentGroupRepository = paymentGroupRepository;
    }

    /**
     * POST  /payment-groups : Create a new paymentGroup.
     *
     * @param paymentGroup the paymentGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paymentGroup, or with status 400 (Bad Request) if the paymentGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payment-groups")
    @Timed
    public ResponseEntity<PaymentGroup> createPaymentGroup(@RequestBody PaymentGroup paymentGroup) throws URISyntaxException {
        log.debug("REST request to save PaymentGroup : {}", paymentGroup);
        if (paymentGroup.getId() != null) {
            throw new BadRequestAlertException("A new paymentGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentGroup result = paymentGroupRepository.save(paymentGroup);
        return ResponseEntity.created(new URI("/api/payment-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payment-groups : Updates an existing paymentGroup.
     *
     * @param paymentGroup the paymentGroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paymentGroup,
     * or with status 400 (Bad Request) if the paymentGroup is not valid,
     * or with status 500 (Internal Server Error) if the paymentGroup couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payment-groups")
    @Timed
    public ResponseEntity<PaymentGroup> updatePaymentGroup(@RequestBody PaymentGroup paymentGroup) throws URISyntaxException {
        log.debug("REST request to update PaymentGroup : {}", paymentGroup);
        if (paymentGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentGroup result = paymentGroupRepository.save(paymentGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paymentGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payment-groups : get all the paymentGroups.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of paymentGroups in body
     */
    @GetMapping("/payment-groups")
    @Timed
    public List<PaymentGroup> getAllPaymentGroups(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all PaymentGroups");
        return paymentGroupRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /payment-groups/:id : get the "id" paymentGroup.
     *
     * @param id the id of the paymentGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paymentGroup, or with status 404 (Not Found)
     */
    @GetMapping("/payment-groups/{id}")
    @Timed
    public ResponseEntity<PaymentGroup> getPaymentGroup(@PathVariable Long id) {
        log.debug("REST request to get PaymentGroup : {}", id);
        Optional<PaymentGroup> paymentGroup = paymentGroupRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(paymentGroup);
    }

    /**
     * DELETE  /payment-groups/:id : delete the "id" paymentGroup.
     *
     * @param id the id of the paymentGroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payment-groups/{id}")
    @Timed
    public ResponseEntity<Void> deletePaymentGroup(@PathVariable Long id) {
        log.debug("REST request to delete PaymentGroup : {}", id);

        paymentGroupRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
