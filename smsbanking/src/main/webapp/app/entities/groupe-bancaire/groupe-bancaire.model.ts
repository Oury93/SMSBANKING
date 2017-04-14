import { Banque } from '../banque';
export class GroupeBancaire {
    constructor(
        public id?: number,
        public libelleG?: string,
        public codeG?: number,
        public banque?: Banque,
    ) {
    }
}
