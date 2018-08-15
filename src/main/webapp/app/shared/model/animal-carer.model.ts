import { IAnimal } from 'app/shared/model//animal.model';

export interface IAnimalCarer {
    id?: number;
    name?: string;
    animals?: IAnimal[];
}

export class AnimalCarer implements IAnimalCarer {
    constructor(public id?: number, public name?: string, public animals?: IAnimal[]) {}
}
