import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Banque } from './banque.model';
import { BanquePopupService } from './banque-popup.service';
import { BanqueService } from './banque.service';

@Component({
    selector: 'jhi-banque-delete-dialog',
    templateUrl: './banque-delete-dialog.component.html'
})
export class BanqueDeleteDialogComponent {

    banque: Banque;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private banqueService: BanqueService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['banque']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.banqueService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'banqueListModification',
                content: 'Deleted an banque'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-banque-delete-popup',
    template: ''
})
export class BanqueDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private banquePopupService: BanquePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.banquePopupService
                .open(BanqueDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
