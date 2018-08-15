import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBird } from 'app/shared/model/bird.model';

@Component({
    selector: 'jhi-bird-detail',
    templateUrl: './bird-detail.component.html'
})
export class BirdDetailComponent implements OnInit {
    bird: IBird;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bird }) => {
            this.bird = bird;
        });
    }

    previousState() {
        window.history.back();
    }
}
