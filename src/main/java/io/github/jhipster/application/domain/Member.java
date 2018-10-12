package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Member.
 */
@Entity
@Table(name = "member")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "iban")
    private String iban;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "platform_token")
    private String platformToken;

    @Column(name = "user_token")
    private String userToken;

    @Column(name = "bank_account_token")
    private String bankAccountToken;

    @OneToOne    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("friends")
    private Member member;

    @OneToMany(mappedBy = "member")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Member> friends = new HashSet<>();
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

    public Member name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIban() {
        return iban;
    }

    public Member iban(String iban) {
        this.iban = iban;
        return this;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getMobile() {
        return mobile;
    }

    public Member mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPlatformToken() {
        return platformToken;
    }

    public Member platformToken(String platformToken) {
        this.platformToken = platformToken;
        return this;
    }

    public void setPlatformToken(String platformToken) {
        this.platformToken = platformToken;
    }

    public String getUserToken() {
        return userToken;
    }

    public Member userToken(String userToken) {
        this.userToken = userToken;
        return this;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getBankAccountToken() {
        return bankAccountToken;
    }

    public Member bankAccountToken(String bankAccountToken) {
        this.bankAccountToken = bankAccountToken;
        return this;
    }

    public void setBankAccountToken(String bankAccountToken) {
        this.bankAccountToken = bankAccountToken;
    }

    public User getUser() {
        return user;
    }

    public Member user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Member getMember() {
        return member;
    }

    public Member member(Member member) {
        this.member = member;
        return this;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Set<Member> getFriends() {
        return friends;
    }

    public Member friends(Set<Member> members) {
        this.friends = members;
        return this;
    }

    public Member addFriend(Member member) {
        this.friends.add(member);
        member.setMember(this);
        return this;
    }

    public Member removeFriend(Member member) {
        this.friends.remove(member);
        member.setMember(null);
        return this;
    }

    public void setFriends(Set<Member> members) {
        this.friends = members;
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
        Member member = (Member) o;
        if (member.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), member.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Member{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", iban='" + getIban() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", platformToken='" + getPlatformToken() + "'" +
            ", userToken='" + getUserToken() + "'" +
            ", bankAccountToken='" + getBankAccountToken() + "'" +
            "}";
    }
}
