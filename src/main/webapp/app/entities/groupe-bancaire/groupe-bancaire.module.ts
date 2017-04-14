import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmsbankingSharedModule } from '../../shared';

import {
    GroupeBancaireService,
    GroupeBancairePopupService,
    GroupeBancaireComponent,
    GroupeBancaireDetailComponent,
    GroupeBancaireDialogComponent,
    GroupeBancairePopupComponent,
    GroupeBancaireDeletePopupComponent,
    GroupeBancaireDeleteDialogComponent,
    groupeBancaireRoute,
    groupeBancairePopupRoute,
} from './';

let ENTITY_STATES = [
    ...groupeBancaireRoute,
    ...groupeBancairePopupRoute,
];

@NgModule({
    imports: [
        SmsbankingSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GroupeBancaireComponent,
        GroupeBancaireDetailComponent,
        GroupeBancaireDialogComponent,
        GroupeBancaireDeleteDialogComponent,
        GroupeBancairePopupComponent,
        GroupeBancaireDeletePopupComponent,
    ],
    entryComponents: [
        GroupeBancaireComponent,
        GroupeBancaireDialogComponent,
        GroupeBancairePopupComponent,
        GroupeBancaireDeleteDialogComponent,
        GroupeBancaireDeletePopupComponent,
    ],
    providers: [
        GroupeBancaireService,
        GroupeBancairePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmsbankingGroupeBancaireModule {}
