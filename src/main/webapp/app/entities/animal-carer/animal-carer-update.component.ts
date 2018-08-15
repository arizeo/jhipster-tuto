import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAnimalCarer } from 'app/shared/model/animal-carer.model';
import { AnimalCarerService } from './animal-carer.service';
import { IAnimal } from 'app/shared/model/animal.model';
import { AnimalService } from 'app/entities/animal';

@Component({
    selector: 'jhi-animal-carer-update',
    templateUrl: './animal-carer-update.component.html'
})
export class AnimalCarerUpdateComponent implements OnInit {
    private _animalCarer: IAnimalCarer;
    isSaving: boolean;

    animals: IAnimal[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private animalCarerService: AnimalCarerService,
        private animalService: AnimalService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ animalCarer }) => {
            this.animalCarer = animalCarer;
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
        if (this.animalCarer.id !== undefined) {
            this.subscribeToSaveResponse(this.animalCarerService.update(this.animalCarer));
        } else {
            this.subscribeToSaveResponse(this.animalCarerService.create(this.animalCarer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAnimalCarer>>) {
        result.subscribe((res: HttpResponse<IAnimalCarer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get animalCarer() {
        return this._animalCarer;
    }

    set animalCarer(animalCarer: IAnimalCarer) {
        this._animalCarer = animalCarer;
    }
}
