import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IStatistics } from 'app/shared/model/statistics.model';
import { StatisticsService } from './statistics.service';
import { IAnimal } from 'app/shared/model/animal.model';
import { AnimalService } from 'app/entities/animal';

@Component({
    selector: 'jhi-statistics-update',
    templateUrl: './statistics-update.component.html'
})
export class StatisticsUpdateComponent implements OnInit {
    private _statistics: IStatistics;
    isSaving: boolean;

    animals: IAnimal[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private statisticsService: StatisticsService,
        private animalService: AnimalService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ statistics }) => {
            this.statistics = statistics;
        });
        this.animalService.query().subscribe(
            (res: HttpResponse<IAnimal[]>) => {
                this.animals = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.statistics.id !== undefined) {
            this.subscribeToSaveResponse(this.statisticsService.update(this.statistics));
        } else {
            this.subscribeToSaveResponse(this.statisticsService.create(this.statistics));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStatistics>>) {
        result.subscribe((res: HttpResponse<IStatistics>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAnimalById(index: number, item: IAnimal) {
        return item.id;
    }
    get statistics() {
        return this._statistics;
    }

    set statistics(statistics: IStatistics) {
        this._statistics = statistics;
    }
}
