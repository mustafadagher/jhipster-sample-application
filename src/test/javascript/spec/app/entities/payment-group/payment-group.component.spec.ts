/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PaymentGroupComponent } from 'app/entities/payment-group/payment-group.component';
import { PaymentGroupService } from 'app/entities/payment-group/payment-group.service';
import { PaymentGroup } from 'app/shared/model/payment-group.model';

describe('Component Tests', () => {
    describe('PaymentGroup Management Component', () => {
        let comp: PaymentGroupComponent;
        let fixture: ComponentFixture<PaymentGroupComponent>;
        let service: PaymentGroupService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [PaymentGroupComponent],
                providers: []
            })
                .overrideTemplate(PaymentGroupComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PaymentGroupComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentGroupService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PaymentGroup(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.paymentGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
