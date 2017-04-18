import { Banque } from '../banque';
export class GroupeBancaire {
    constructor(
        public id?: number,
        public libelleG?: string,
        public codeG?: string,
        public banque?: Banque,
    ) {
    }
}
