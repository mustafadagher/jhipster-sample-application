/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PaymentRequestDetailComponent } from 'app/entities/payment-request/payment-request-detail.component';
import { PaymentRequest } from 'app/shared/model/payment-request.model';

describe('Component Tests', () => {
    describe('PaymentRequest Management Detail Component', () => {
        let comp: PaymentRequestDetailComponent;
        let fixture: ComponentFixture<PaymentRequestDetailComponent>;
        const route = ({ data: of({ paymentRequest: new PaymentRequest(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [PaymentRequestDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PaymentRequestDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PaymentRequestDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.paymentRequest).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
