package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.PaymentRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PaymentRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long> {

}
