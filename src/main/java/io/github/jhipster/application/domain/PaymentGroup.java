package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PaymentGroup.
 */
@Entity
@Table(name = "payment_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PaymentGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "ongoing")
    private Boolean ongoing;

    @Column(name = "settled")
    private Boolean settled;

    @OneToMany(mappedBy = "paymentGroup")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Bill> bills = new HashSet<>();
    @OneToMany(mappedBy = "group")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PaymentRequest> paymentRequests = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("")
    private Member author;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "payment_group_member",
               joinColumns = @JoinColumn(name = "payment_groups_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "members_id", referencedColumnName = "id"))
    private Set<Member> members = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PaymentGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public PaymentGroup startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public PaymentGroup endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Boolean isOngoing() {
        return ongoing;
    }

    public PaymentGroup ongoing(Boolean ongoing) {
        this.ongoing = ongoing;
        return this;
    }

    public void setOngoing(Boolean ongoing) {
        this.ongoing = ongoing;
    }

    public Boolean isSettled() {
        return settled;
    }

    public PaymentGroup settled(Boolean settled) {
        this.settled = settled;
        return this;
    }

    public void setSettled(Boolean settled) {
        this.settled = settled;
    }

    public Set<Bill> getBills() {
        return bills;
    }

    public PaymentGroup bills(Set<Bill> bills) {
        this.bills = bills;
        return this;
    }

    public PaymentGroup addBill(Bill bill) {
        this.bills.add(bill);
        bill.setPaymentGroup(this);
        return this;
    }

    public PaymentGroup removeBill(Bill bill) {
        this.bills.remove(bill);
        bill.setPaymentGroup(null);
        return this;
    }

    public void setBills(Set<Bill> bills) {
        this.bills = bills;
    }

    public Set<PaymentRequest> getPaymentRequests() {
        return paymentRequests;
    }

    public PaymentGroup paymentRequests(Set<PaymentRequest> paymentRequests) {
        this.paymentRequests = paymentRequests;
        return this;
    }

    public PaymentGroup addPaymentRequest(PaymentRequest paymentRequest) {
        this.paymentRequests.add(paymentRequest);
        paymentRequest.setGroup(this);
        return this;
    }

    public PaymentGroup removePaymentRequest(PaymentRequest paymentRequest) {
        this.paymentRequests.remove(paymentRequest);
        paymentRequest.setGroup(null);
        return this;
    }

    public void setPaymentRequests(Set<PaymentRequest> paymentRequests) {
        this.paymentRequests = paymentRequests;
    }

    public Member getAuthor() {
        return author;
    }

    public PaymentGroup author(Member member) {
        this.author = member;
        return this;
    }

    public void setAuthor(Member member) {
        this.author = member;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public PaymentGroup members(Set<Member> members) {
        this.members = members;
        return this;
    }

    public PaymentGroup addMember(Member member) {
        this.members.add(member);
        return this;
    }

    public PaymentGroup removeMember(Member member) {
        this.members.remove(member);
        return this;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
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
        PaymentGroup paymentGroup = (PaymentGroup) o;
        if (paymentGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", ongoing='" + isOngoing() + "'" +
            ", settled='" + isSettled() + "'" +
            "}";
    }
}
