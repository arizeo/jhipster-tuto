import { IAnimal } from 'app/shared/model//animal.model';

export interface IReptile {
    id?: number;
    subspecies?: string;
    legsNbr?: number;
    shell?: boolean;
    animals?: IAnimal[];
}

export class Reptile implements IReptile {
    constructor(
        public id?: number,
        public subspecies?: string,
        public legsNbr?: number,
        public shell?: boolean,
        public animals?: IAnimal[]
    ) {
        this.shell = false;
    }
}
