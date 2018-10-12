/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PaymentRequestUpdateComponent } from 'app/entities/payment-request/payment-request-update.component';
import { PaymentRequestService } from 'app/entities/payment-request/payment-request.service';
import { PaymentRequest } from 'app/shared/model/payment-request.model';

describe('Component Tests', () => {
    describe('PaymentRequest Management Update Component', () => {
        let comp: PaymentRequestUpdateComponent;
        let fixture: ComponentFixture<PaymentRequestUpdateComponent>;
        let service: PaymentRequestService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [PaymentRequestUpdateComponent]
            })
                .overrideTemplate(PaymentRequestUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PaymentRequestUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentRequestService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PaymentRequest(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.paymentRequest = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PaymentRequest();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.paymentRequest = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
