import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SmsbankingGroupeBancaireModule } from './groupe-bancaire/groupe-bancaire.module';
import { SmsbankingBanqueModule } from './banque/banque.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SmsbankingGroupeBancaireModule,
        SmsbankingBanqueModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SmsbankingEntityModule {}
