package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PaymentRequest.
 */
@Entity
@Table(name = "payment_request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PaymentRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "settled")
    private Boolean settled;

    @Column(name = "url")
    private String url;

    @Column(name = "token")
    private String token;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Member from;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Member to;

    @ManyToOne
    @JsonIgnoreProperties("paymentRequests")
    private PaymentGroup group;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public PaymentRequest amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean isSettled() {
        return settled;
    }

    public PaymentRequest settled(Boolean settled) {
        this.settled = settled;
        return this;
    }

    public void setSettled(Boolean settled) {
        this.settled = settled;
    }

    public String getUrl() {
        return url;
    }

    public PaymentRequest url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public PaymentRequest token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Member getFrom() {
        return from;
    }

    public PaymentRequest from(Member member) {
        this.from = member;
        return this;
    }

    public void setFrom(Member member) {
        this.from = member;
    }

    public Member getTo() {
        return to;
    }

    public PaymentRequest to(Member member) {
        this.to = member;
        return this;
    }

    public void setTo(Member member) {
        this.to = member;
    }

    public PaymentGroup getGroup() {
        return group;
    }

    public PaymentRequest group(PaymentGroup paymentGroup) {
        this.group = paymentGroup;
        return this;
    }

    public void setGroup(PaymentGroup paymentGroup) {
        this.group = paymentGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentRequest paymentRequest = (PaymentRequest) o;
        if (paymentRequest.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentRequest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", settled='" + isSettled() + "'" +
            ", url='" + getUrl() + "'" +
            ", token='" + getToken() + "'" +
            "}";
    }
}
