import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStatistics } from 'app/shared/model/statistics.model';

@Component({
    selector: 'jhi-statistics-detail',
    templateUrl: './statistics-detail.component.html'
})
export class StatisticsDetailComponent implements OnInit {
    statistics: IStatistics;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ statistics }) => {
            this.statistics = statistics;
        });
    }

    previousState() {
        window.history.back();
    }
}
