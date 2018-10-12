/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PaymentRequestComponent } from 'app/entities/payment-request/payment-request.component';
import { PaymentRequestService } from 'app/entities/payment-request/payment-request.service';
import { PaymentRequest } from 'app/shared/model/payment-request.model';

describe('Component Tests', () => {
    describe('PaymentRequest Management Component', () => {
        let comp: PaymentRequestComponent;
        let fixture: ComponentFixture<PaymentRequestComponent>;
        let service: PaymentRequestService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [PaymentRequestComponent],
                providers: []
            })
                .overrideTemplate(PaymentRequestComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PaymentRequestComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentRequestService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PaymentRequest(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.paymentRequests[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
