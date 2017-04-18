import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Banque } from './banque.model';
import { BanqueService } from './banque.service';

@Component({
    selector: 'jhi-banque-detail',
    templateUrl: './banque-detail.component.html'
})
export class BanqueDetailComponent implements OnInit, OnDestroy {

    banque: Banque;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private banqueService: BanqueService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['banque']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInBanques();
    }

    load (id) {
        this.banqueService.find(id).subscribe(banque => {
            this.banque = banque;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBanques() {
        this.eventSubscriber = this.eventManager.subscribe('banqueListModification', response => this.load(this.banque.id));
    }

}
