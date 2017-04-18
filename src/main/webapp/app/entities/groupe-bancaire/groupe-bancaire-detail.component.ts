import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { GroupeBancaire } from './groupe-bancaire.model';
import { GroupeBancaireService } from './groupe-bancaire.service';

@Component({
    selector: 'jhi-groupe-bancaire-detail',
    templateUrl: './groupe-bancaire-detail.component.html'
})
export class GroupeBancaireDetailComponent implements OnInit, OnDestroy {

    groupeBancaire: GroupeBancaire;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private groupeBancaireService: GroupeBancaireService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['groupeBancaire']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInGroupeBancaires();
    }

    load (id) {
        this.groupeBancaireService.find(id).subscribe(groupeBancaire => {
            this.groupeBancaire = groupeBancaire;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGroupeBancaires() {
        this.eventSubscriber = this.eventManager.subscribe('groupeBancaireListModification', response => this.load(this.groupeBancaire.id));
    }

}
