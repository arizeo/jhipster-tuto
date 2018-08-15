import { IAnimal } from 'app/shared/model//animal.model';

export const enum HabitatType {
    FOREST = 'FOREST',
    DESERT = 'DESERT',
    WATER = 'WATER',
    GRASSLAND = 'GRASSLAND',
    TUNDRA = 'TUNDRA'
}

export interface IHabitat {
    id?: number;
    name?: HabitatType;
    temperature?: string;
    animal?: IAnimal;
}

export class Habitat implements IHabitat {
    constructor(public id?: number, public name?: HabitatType, public temperature?: string, public animal?: IAnimal) {}
}
