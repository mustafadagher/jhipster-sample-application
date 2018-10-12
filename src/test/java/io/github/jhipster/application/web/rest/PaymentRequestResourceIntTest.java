package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.PaymentRequest;
import io.github.jhipster.application.repository.PaymentRequestRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PaymentRequestResource REST controller.
 *
 * @see PaymentRequestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class PaymentRequestResourceIntTest {

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final Boolean DEFAULT_SETTLED = false;
    private static final Boolean UPDATED_SETTLED = true;

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    @Autowired
    private PaymentRequestRepository paymentRequestRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPaymentRequestMockMvc;

    private PaymentRequest paymentRequest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentRequestResource paymentRequestResource = new PaymentRequestResource(paymentRequestRepository);
        this.restPaymentRequestMockMvc = MockMvcBuilders.standaloneSetup(paymentRequestResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentRequest createEntity(EntityManager em) {
        PaymentRequest paymentRequest = new PaymentRequest()
            .amount(DEFAULT_AMOUNT)
            .settled(DEFAULT_SETTLED)
            .url(DEFAULT_URL)
            .token(DEFAULT_TOKEN);
        return paymentRequest;
    }

    @Before
    public void initTest() {
        paymentRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentRequest() throws Exception {
        int databaseSizeBeforeCreate = paymentRequestRepository.findAll().size();

        // Create the PaymentRequest
        restPaymentRequestMockMvc.perform(post("/api/payment-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentRequest)))
            .andExpect(status().isCreated());

        // Validate the PaymentRequest in the database
        List<PaymentRequest> paymentRequestList = paymentRequestRepository.findAll();
        assertThat(paymentRequestList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentRequest testPaymentRequest = paymentRequestList.get(paymentRequestList.size() - 1);
        assertThat(testPaymentRequest.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPaymentRequest.isSettled()).isEqualTo(DEFAULT_SETTLED);
        assertThat(testPaymentRequest.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testPaymentRequest.getToken()).isEqualTo(DEFAULT_TOKEN);
    }

    @Test
    @Transactional
    public void createPaymentRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentRequestRepository.findAll().size();

        // Create the PaymentRequest with an existing ID
        paymentRequest.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentRequestMockMvc.perform(post("/api/payment-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentRequest)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentRequest in the database
        List<PaymentRequest> paymentRequestList = paymentRequestRepository.findAll();
        assertThat(paymentRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPaymentRequests() throws Exception {
        // Initialize the database
        paymentRequestRepository.saveAndFlush(paymentRequest);

        // Get all the paymentRequestList
        restPaymentRequestMockMvc.perform(get("/api/payment-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].settled").value(hasItem(DEFAULT_SETTLED.booleanValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())));
    }
    
    @Test
    @Transactional
    public void getPaymentRequest() throws Exception {
        // Initialize the database
        paymentRequestRepository.saveAndFlush(paymentRequest);

        // Get the paymentRequest
        restPaymentRequestMockMvc.perform(get("/api/payment-requests/{id}", paymentRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentRequest.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.settled").value(DEFAULT_SETTLED.booleanValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentRequest() throws Exception {
        // Get the paymentRequest
        restPaymentRequestMockMvc.perform(get("/api/payment-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentRequest() throws Exception {
        // Initialize the database
        paymentRequestRepository.saveAndFlush(paymentRequest);

        int databaseSizeBeforeUpdate = paymentRequestRepository.findAll().size();

        // Update the paymentRequest
        PaymentRequest updatedPaymentRequest = paymentRequestRepository.findById(paymentRequest.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentRequest are not directly saved in db
        em.detach(updatedPaymentRequest);
        updatedPaymentRequest
            .amount(UPDATED_AMOUNT)
            .settled(UPDATED_SETTLED)
            .url(UPDATED_URL)
            .token(UPDATED_TOKEN);

        restPaymentRequestMockMvc.perform(put("/api/payment-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaymentRequest)))
            .andExpect(status().isOk());

        // Validate the PaymentRequest in the database
        List<PaymentRequest> paymentRequestList = paymentRequestRepository.findAll();
        assertThat(paymentRequestList).hasSize(databaseSizeBeforeUpdate);
        PaymentRequest testPaymentRequest = paymentRequestList.get(paymentRequestList.size() - 1);
        assertThat(testPaymentRequest.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPaymentRequest.isSettled()).isEqualTo(UPDATED_SETTLED);
        assertThat(testPaymentRequest.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testPaymentRequest.getToken()).isEqualTo(UPDATED_TOKEN);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentRequest() throws Exception {
        int databaseSizeBeforeUpdate = paymentRequestRepository.findAll().size();

        // Create the PaymentRequest

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentRequestMockMvc.perform(put("/api/payment-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentRequest)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentRequest in the database
        List<PaymentRequest> paymentRequestList = paymentRequestRepository.findAll();
        assertThat(paymentRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaymentRequest() throws Exception {
        // Initialize the database
        paymentRequestRepository.saveAndFlush(paymentRequest);

        int databaseSizeBeforeDelete = paymentRequestRepository.findAll().size();

        // Get the paymentRequest
        restPaymentRequestMockMvc.perform(delete("/api/payment-requests/{id}", paymentRequest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PaymentRequest> paymentRequestList = paymentRequestRepository.findAll();
        assertThat(paymentRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentRequest.class);
        PaymentRequest paymentRequest1 = new PaymentRequest();
        paymentRequest1.setId(1L);
        PaymentRequest paymentRequest2 = new PaymentRequest();
        paymentRequest2.setId(paymentRequest1.getId());
        assertThat(paymentRequest1).isEqualTo(paymentRequest2);
        paymentRequest2.setId(2L);
        assertThat(paymentRequest1).isNotEqualTo(paymentRequest2);
        paymentRequest1.setId(null);
        assertThat(paymentRequest1).isNotEqualTo(paymentRequest2);
    }
}
