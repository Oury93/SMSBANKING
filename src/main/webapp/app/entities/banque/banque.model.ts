import { GroupeBancaire } from '../groupe-bancaire';
export class Banque {
    constructor(
        public id?: number,
        public libelleB?: string,
        public codeB?: string,
        public codeP?: string,
        public groupeBancaire?: GroupeBancaire,
    ) {
    }
}
