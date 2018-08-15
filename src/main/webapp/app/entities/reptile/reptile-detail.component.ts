import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReptile } from 'app/shared/model/reptile.model';

@Component({
    selector: 'jhi-reptile-detail',
    templateUrl: './reptile-detail.component.html'
})
export class ReptileDetailComponent implements OnInit {
    reptile: IReptile;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reptile }) => {
            this.reptile = reptile;
        });
    }

    previousState() {
        window.history.back();
    }
}
