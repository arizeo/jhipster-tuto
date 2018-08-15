import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHabitat } from 'app/shared/model/habitat.model';

@Component({
    selector: 'jhi-habitat-detail',
    templateUrl: './habitat-detail.component.html'
})
export class HabitatDetailComponent implements OnInit {
    habitat: IHabitat;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ habitat }) => {
            this.habitat = habitat;
        });
    }

    previousState() {
        window.history.back();
    }
}
