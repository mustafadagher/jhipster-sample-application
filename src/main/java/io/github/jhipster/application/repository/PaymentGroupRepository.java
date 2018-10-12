package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.PaymentGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the PaymentGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentGroupRepository extends JpaRepository<PaymentGroup, Long> {

    @Query(value = "select distinct payment_group from PaymentGroup payment_group left join fetch payment_group.members",
        countQuery = "select count(distinct payment_group) from PaymentGroup payment_group")
    Page<PaymentGroup> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct payment_group from PaymentGroup payment_group left join fetch payment_group.members")
    List<PaymentGroup> findAllWithEagerRelationships();

    @Query("select payment_group from PaymentGroup payment_group left join fetch payment_group.members where payment_group.id =:id")
    Optional<PaymentGroup> findOneWithEagerRelationships(@Param("id") Long id);

}
