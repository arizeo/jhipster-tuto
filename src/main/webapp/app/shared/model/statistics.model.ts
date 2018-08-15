import { IAnimal } from 'app/shared/model//animal.model';

export interface IStatistics {
    id?: number;
    bmi?: number;
    animal?: IAnimal;
}

export class Statistics implements IStatistics {
    constructor(public id?: number, public bmi?: number, public animal?: IAnimal) {}
}
