import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnimalCarer } from 'app/shared/model/animal-carer.model';
import { AnimalCarerService } from './animal-carer.service';

@Component({
    selector: 'jhi-animal-carer-delete-dialog',
    templateUrl: './animal-carer-delete-dialog.component.html'
})
export class AnimalCarerDeleteDialogComponent {
    animalCarer: IAnimalCarer;

    constructor(
        private animalCarerService: AnimalCarerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.animalCarerService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'animalCarerListModification',
                content: 'Deleted an animalCarer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-animal-carer-delete-popup',
    template: ''
})
export class AnimalCarerDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ animalCarer }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AnimalCarerDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.animalCarer = animalCarer;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
