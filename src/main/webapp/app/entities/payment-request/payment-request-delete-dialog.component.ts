import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaymentRequest } from 'app/shared/model/payment-request.model';
import { PaymentRequestService } from './payment-request.service';

@Component({
    selector: 'jhi-payment-request-delete-dialog',
    templateUrl: './payment-request-delete-dialog.component.html'
})
export class PaymentRequestDeleteDialogComponent {
    paymentRequest: IPaymentRequest;

    constructor(
        private paymentRequestService: PaymentRequestService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.paymentRequestService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'paymentRequestListModification',
                content: 'Deleted an paymentRequest'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-payment-request-delete-popup',
    template: ''
})
export class PaymentRequestDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ paymentRequest }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PaymentRequestDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.paymentRequest = paymentRequest;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
