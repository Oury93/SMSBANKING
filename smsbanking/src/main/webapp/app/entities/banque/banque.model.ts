import { GroupeBancaire } from '../groupe-bancaire';
export class Banque {
    constructor(
        public id?: number,
        public libelleB?: string,
        public codeB?: number,
        public codeP?: number,
        public groupeBancaire?: GroupeBancaire,
    ) {
    }
}
