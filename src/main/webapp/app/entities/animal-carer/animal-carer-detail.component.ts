import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnimalCarer } from 'app/shared/model/animal-carer.model';

@Component({
    selector: 'jhi-animal-carer-detail',
    templateUrl: './animal-carer-detail.component.html'
})
export class AnimalCarerDetailComponent implements OnInit {
    animalCarer: IAnimalCarer;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ animalCarer }) => {
            this.animalCarer = animalCarer;
        });
    }

    previousState() {
        window.history.back();
    }
}
