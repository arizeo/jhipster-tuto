import { IAnimal } from 'app/shared/model//animal.model';

export interface IBird {
    id?: number;
    subspecies?: string;
    canFly?: boolean;
    migratory?: boolean;
    animals?: IAnimal[];
}

export class Bird implements IBird {
    constructor(
        public id?: number,
        public subspecies?: string,
        public canFly?: boolean,
        public migratory?: boolean,
        public animals?: IAnimal[]
    ) {
        this.canFly = false;
        this.migratory = false;
    }
}
