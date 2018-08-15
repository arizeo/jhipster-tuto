import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IHabitat } from 'app/shared/model/habitat.model';
import { HabitatService } from './habitat.service';
import { IAnimal } from 'app/shared/model/animal.model';
import { AnimalService } from 'app/entities/animal';

@Component({
    selector: 'jhi-habitat-update',
    templateUrl: './habitat-update.component.html'
})
export class HabitatUpdateComponent implements OnInit {
    private _habitat: IHabitat;
    isSaving: boolean;

    animals: IAnimal[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private habitatService: HabitatService,
        private animalService: AnimalService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ habitat }) => {
            this.habitat = habitat;
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
        if (this.habitat.id !== undefined) {
            this.subscribeToSaveResponse(this.habitatService.update(this.habitat));
        } else {
            this.subscribeToSaveResponse(this.habitatService.create(this.habitat));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IHabitat>>) {
        result.subscribe((res: HttpResponse<IHabitat>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get habitat() {
        return this._habitat;
    }

    set habitat(habitat: IHabitat) {
        this._habitat = habitat;
    }
}
