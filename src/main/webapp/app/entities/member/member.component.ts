import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMember } from 'app/shared/model/member.model';
import { Principal } from 'app/core';
import { MemberService } from './member.service';

@Component({
    selector: 'jhi-member',
    templateUrl: './member.component.html'
})
export class MemberComponent implements OnInit, OnDestroy {
    members: IMember[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private memberService: MemberService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.memberService.query().subscribe(
            (res: HttpResponse<IMember[]>) => {
                this.members = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMembers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMember) {
        return item.id;
    }

    registerChangeInMembers() {
        this.eventSubscriber = this.eventManager.subscribe('memberListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
