import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { GroupeBancaire } from './groupe-bancaire.model';
import { GroupeBancaireService } from './groupe-bancaire.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-groupe-bancaire',
    templateUrl: './groupe-bancaire.component.html'
})
export class GroupeBancaireComponent implements OnInit, OnDestroy {
groupeBancaires: GroupeBancaire[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private groupeBancaireService: GroupeBancaireService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['groupeBancaire']);
    }

    loadAll() {
        this.groupeBancaireService.query().subscribe(
            (res: Response) => {
                this.groupeBancaires = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInGroupeBancaires();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: GroupeBancaire) {
        return item.id;
    }



    registerChangeInGroupeBancaires() {
        this.eventSubscriber = this.eventManager.subscribe('groupeBancaireListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
