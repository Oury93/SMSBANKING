import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SmsbankingSharedModule } from '../../shared';

import {
    BanqueService,
    BanquePopupService,
    BanqueComponent,
    BanqueDetailComponent,
    BanqueDialogComponent,
    BanquePopupComponent,
    BanqueDeletePopupComponent,
    BanqueDeleteDialogComponent,
    banqueRoute,
    banquePopupRoute,
} from './';

let ENTITY_STATES = [
    ...banqueRoute,
    ...banquePopupRoute,
];

@NgModule({
    imports: [
        SmsbankingSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BanqueComponent,
        BanqueDetailComponent,
        BanqueDialogComponent,
        BanqueDeleteDialogComponent,
        BanquePopupComponent,
        BanqueDeletePopupComponent,
    ],
    entryComponents: [
        BanqueComponent,
        BanqueDialogComponent,
        BanquePopupComponent,
        BanqueDeleteDialogComponent,
        BanqueDeletePopupComponent,
    ],
    providers: [
        BanqueService,
        BanquePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmsbankingBanqueModule {}
