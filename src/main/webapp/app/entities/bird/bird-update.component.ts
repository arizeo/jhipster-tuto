import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IBird } from 'app/shared/model/bird.model';
import { BirdService } from './bird.service';

@Component({
    selector: 'jhi-bird-update',
    templateUrl: './bird-update.component.html'
})
export class BirdUpdateComponent implements OnInit {
    private _bird: IBird;
    isSaving: boolean;

    constructor(private birdService: BirdService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bird }) => {
            this.bird = bird;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bird.id !== undefined) {
            this.subscribeToSaveResponse(this.birdService.update(this.bird));
        } else {
            this.subscribeToSaveResponse(this.birdService.create(this.bird));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBird>>) {
        result.subscribe((res: HttpResponse<IBird>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get bird() {
        return this._bird;
    }

    set bird(bird: IBird) {
        this._bird = bird;
    }
}
