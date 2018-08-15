import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IReptile } from 'app/shared/model/reptile.model';
import { ReptileService } from './reptile.service';

@Component({
    selector: 'jhi-reptile-update',
    templateUrl: './reptile-update.component.html'
})
export class ReptileUpdateComponent implements OnInit {
    private _reptile: IReptile;
    isSaving: boolean;

    constructor(private reptileService: ReptileService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reptile }) => {
            this.reptile = reptile;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.reptile.id !== undefined) {
            this.subscribeToSaveResponse(this.reptileService.update(this.reptile));
        } else {
            this.subscribeToSaveResponse(this.reptileService.create(this.reptile));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IReptile>>) {
        result.subscribe((res: HttpResponse<IReptile>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get reptile() {
        return this._reptile;
    }

    set reptile(reptile: IReptile) {
        this._reptile = reptile;
    }
}
