import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { GroupeBancaire } from './groupe-bancaire.model';
import { GroupeBancairePopupService } from './groupe-bancaire-popup.service';
import { GroupeBancaireService } from './groupe-bancaire.service';

@Component({
    selector: 'jhi-groupe-bancaire-delete-dialog',
    templateUrl: './groupe-bancaire-delete-dialog.component.html'
})
export class GroupeBancaireDeleteDialogComponent {

    groupeBancaire: GroupeBancaire;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private groupeBancaireService: GroupeBancaireService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['groupeBancaire']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.groupeBancaireService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'groupeBancaireListModification',
                content: 'Deleted an groupeBancaire'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-groupe-bancaire-delete-popup',
    template: ''
})
export class GroupeBancaireDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private groupeBancairePopupService: GroupeBancairePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.groupeBancairePopupService
                .open(GroupeBancaireDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
