import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IAnimal } from 'app/shared/model/animal.model';
import { AnimalService } from './animal.service';
import { IStatistics } from 'app/shared/model/statistics.model';
import { StatisticsService } from 'app/entities/statistics';
import { IAnimalCarer } from 'app/shared/model/animal-carer.model';
import { AnimalCarerService } from 'app/entities/animal-carer';
import { IBird } from 'app/shared/model/bird.model';
import { BirdService } from 'app/entities/bird';
import { IReptile } from 'app/shared/model/reptile.model';
import { ReptileService } from 'app/entities/reptile';

@Component({
    selector: 'jhi-animal-update',
    templateUrl: './animal-update.component.html'
})
export class AnimalUpdateComponent implements OnInit {
    private _animal: IAnimal;
    isSaving: boolean;

    statistics: IStatistics[];

    animalcarers: IAnimalCarer[];

    birds: IBird[];

    reptiles: IReptile[];
    timeStamp: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private animalService: AnimalService,
        private statisticsService: StatisticsService,
        private animalCarerService: AnimalCarerService,
        private birdService: BirdService,
        private reptileService: ReptileService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ animal }) => {
            this.animal = animal;
        });
        this.statisticsService.query({ filter: 'animal(name)-is-null' }).subscribe(
            (res: HttpResponse<IStatistics[]>) => {
                if (!this.animal.statistics || !this.animal.statistics.id) {
                    this.statistics = res.body;
                } else {
                    this.statisticsService.find(this.animal.statistics.id).subscribe(
                        (subRes: HttpResponse<IStatistics>) => {
                            this.statistics = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.animalCarerService.query().subscribe(
            (res: HttpResponse<IAnimalCarer[]>) => {
                this.animalcarers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.birdService.query().subscribe(
            (res: HttpResponse<IBird[]>) => {
                this.birds = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.reptileService.query().subscribe(
            (res: HttpResponse<IReptile[]>) => {
                this.reptiles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.animal.timeStamp = moment(this.timeStamp, DATE_TIME_FORMAT);
        if (this.animal.id !== undefined) {
            this.subscribeToSaveResponse(this.animalService.update(this.animal));
        } else {
            this.subscribeToSaveResponse(this.animalService.create(this.animal));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAnimal>>) {
        result.subscribe((res: HttpResponse<IAnimal>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackStatisticsById(index: number, item: IStatistics) {
        return item.id;
    }

    trackAnimalCarerById(index: number, item: IAnimalCarer) {
        return item.id;
    }

    trackBirdById(index: number, item: IBird) {
        return item.id;
    }

    trackReptileById(index: number, item: IReptile) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get animal() {
        return this._animal;
    }

    set animal(animal: IAnimal) {
        this._animal = animal;
        this.timeStamp = moment(animal.timeStamp).format(DATE_TIME_FORMAT);
    }
}
