import { Moment } from 'moment';
import { IStatistics } from 'app/shared/model//statistics.model';
import { IHabitat } from 'app/shared/model//habitat.model';
import { IAnimalCarer } from 'app/shared/model//animal-carer.model';
import { IBird } from 'app/shared/model//bird.model';
import { IReptile } from 'app/shared/model//reptile.model';

export interface IAnimal {
    id?: number;
    name?: string;
    weight?: number;
    hasOwner?: boolean;
    age?: number;
    speed?: number;
    height?: number;
    diet?: string;
    timeStamp?: Moment;
    statistics?: IStatistics;
    habitats?: IHabitat[];
    animalcarers?: IAnimalCarer[];
    bird?: IBird;
    reptile?: IReptile;
}

export class Animal implements IAnimal {
    constructor(
        public id?: number,
        public name?: string,
        public weight?: number,
        public hasOwner?: boolean,
        public age?: number,
        public speed?: number,
        public height?: number,
        public diet?: string,
        public timeStamp?: Moment,
        public statistics?: IStatistics,
        public habitats?: IHabitat[],
        public animalcarers?: IAnimalCarer[],
        public bird?: IBird,
        public reptile?: IReptile
    ) {
        this.hasOwner = false;
    }
}
