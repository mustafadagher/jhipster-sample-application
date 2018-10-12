package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.PaymentGroup;
import io.github.jhipster.application.repository.PaymentGroupRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PaymentGroupResource REST controller.
 *
 * @see PaymentGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class PaymentGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ONGOING = false;
    private static final Boolean UPDATED_ONGOING = true;

    private static final Boolean DEFAULT_SETTLED = false;
    private static final Boolean UPDATED_SETTLED = true;

    @Autowired
    private PaymentGroupRepository paymentGroupRepository;

    @Mock
    private PaymentGroupRepository paymentGroupRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPaymentGroupMockMvc;

    private PaymentGroup paymentGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentGroupResource paymentGroupResource = new PaymentGroupResource(paymentGroupRepository);
        this.restPaymentGroupMockMvc = MockMvcBuilders.standaloneSetup(paymentGroupResource)
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
    public static PaymentGroup createEntity(EntityManager em) {
        PaymentGroup paymentGroup = new PaymentGroup()
            .name(DEFAULT_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .ongoing(DEFAULT_ONGOING)
            .settled(DEFAULT_SETTLED);
        return paymentGroup;
    }

    @Before
    public void initTest() {
        paymentGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentGroup() throws Exception {
        int databaseSizeBeforeCreate = paymentGroupRepository.findAll().size();

        // Create the PaymentGroup
        restPaymentGroupMockMvc.perform(post("/api/payment-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentGroup)))
            .andExpect(status().isCreated());

        // Validate the PaymentGroup in the database
        List<PaymentGroup> paymentGroupList = paymentGroupRepository.findAll();
        assertThat(paymentGroupList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentGroup testPaymentGroup = paymentGroupList.get(paymentGroupList.size() - 1);
        assertThat(testPaymentGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaymentGroup.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPaymentGroup.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPaymentGroup.isOngoing()).isEqualTo(DEFAULT_ONGOING);
        assertThat(testPaymentGroup.isSettled()).isEqualTo(DEFAULT_SETTLED);
    }

    @Test
    @Transactional
    public void createPaymentGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentGroupRepository.findAll().size();

        // Create the PaymentGroup with an existing ID
        paymentGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentGroupMockMvc.perform(post("/api/payment-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentGroup)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentGroup in the database
        List<PaymentGroup> paymentGroupList = paymentGroupRepository.findAll();
        assertThat(paymentGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPaymentGroups() throws Exception {
        // Initialize the database
        paymentGroupRepository.saveAndFlush(paymentGroup);

        // Get all the paymentGroupList
        restPaymentGroupMockMvc.perform(get("/api/payment-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].ongoing").value(hasItem(DEFAULT_ONGOING.booleanValue())))
            .andExpect(jsonPath("$.[*].settled").value(hasItem(DEFAULT_SETTLED.booleanValue())));
    }
    
    public void getAllPaymentGroupsWithEagerRelationshipsIsEnabled() throws Exception {
        PaymentGroupResource paymentGroupResource = new PaymentGroupResource(paymentGroupRepositoryMock);
        when(paymentGroupRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPaymentGroupMockMvc = MockMvcBuilders.standaloneSetup(paymentGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPaymentGroupMockMvc.perform(get("/api/payment-groups?eagerload=true"))
        .andExpect(status().isOk());

        verify(paymentGroupRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllPaymentGroupsWithEagerRelationshipsIsNotEnabled() throws Exception {
        PaymentGroupResource paymentGroupResource = new PaymentGroupResource(paymentGroupRepositoryMock);
            when(paymentGroupRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restPaymentGroupMockMvc = MockMvcBuilders.standaloneSetup(paymentGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPaymentGroupMockMvc.perform(get("/api/payment-groups?eagerload=true"))
        .andExpect(status().isOk());

            verify(paymentGroupRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPaymentGroup() throws Exception {
        // Initialize the database
        paymentGroupRepository.saveAndFlush(paymentGroup);

        // Get the paymentGroup
        restPaymentGroupMockMvc.perform(get("/api/payment-groups/{id}", paymentGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.ongoing").value(DEFAULT_ONGOING.booleanValue()))
            .andExpect(jsonPath("$.settled").value(DEFAULT_SETTLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentGroup() throws Exception {
        // Get the paymentGroup
        restPaymentGroupMockMvc.perform(get("/api/payment-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentGroup() throws Exception {
        // Initialize the database
        paymentGroupRepository.saveAndFlush(paymentGroup);

        int databaseSizeBeforeUpdate = paymentGroupRepository.findAll().size();

        // Update the paymentGroup
        PaymentGroup updatedPaymentGroup = paymentGroupRepository.findById(paymentGroup.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentGroup are not directly saved in db
        em.detach(updatedPaymentGroup);
        updatedPaymentGroup
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .ongoing(UPDATED_ONGOING)
            .settled(UPDATED_SETTLED);

        restPaymentGroupMockMvc.perform(put("/api/payment-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaymentGroup)))
            .andExpect(status().isOk());

        // Validate the PaymentGroup in the database
        List<PaymentGroup> paymentGroupList = paymentGroupRepository.findAll();
        assertThat(paymentGroupList).hasSize(databaseSizeBeforeUpdate);
        PaymentGroup testPaymentGroup = paymentGroupList.get(paymentGroupList.size() - 1);
        assertThat(testPaymentGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaymentGroup.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPaymentGroup.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPaymentGroup.isOngoing()).isEqualTo(UPDATED_ONGOING);
        assertThat(testPaymentGroup.isSettled()).isEqualTo(UPDATED_SETTLED);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentGroup() throws Exception {
        int databaseSizeBeforeUpdate = paymentGroupRepository.findAll().size();

        // Create the PaymentGroup

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentGroupMockMvc.perform(put("/api/payment-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentGroup)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentGroup in the database
        List<PaymentGroup> paymentGroupList = paymentGroupRepository.findAll();
        assertThat(paymentGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaymentGroup() throws Exception {
        // Initialize the database
        paymentGroupRepository.saveAndFlush(paymentGroup);

        int databaseSizeBeforeDelete = paymentGroupRepository.findAll().size();

        // Get the paymentGroup
        restPaymentGroupMockMvc.perform(delete("/api/payment-groups/{id}", paymentGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PaymentGroup> paymentGroupList = paymentGroupRepository.findAll();
        assertThat(paymentGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentGroup.class);
        PaymentGroup paymentGroup1 = new PaymentGroup();
        paymentGroup1.setId(1L);
        PaymentGroup paymentGroup2 = new PaymentGroup();
        paymentGroup2.setId(paymentGroup1.getId());
        assertThat(paymentGroup1).isEqualTo(paymentGroup2);
        paymentGroup2.setId(2L);
        assertThat(paymentGroup1).isNotEqualTo(paymentGroup2);
        paymentGroup1.setId(null);
        assertThat(paymentGroup1).isNotEqualTo(paymentGroup2);
    }
}
