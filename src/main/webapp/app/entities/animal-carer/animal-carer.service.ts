import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAnimalCarer } from 'app/shared/model/animal-carer.model';

type EntityResponseType = HttpResponse<IAnimalCarer>;
type EntityArrayResponseType = HttpResponse<IAnimalCarer[]>;

@Injectable({ providedIn: 'root' })
export class AnimalCarerService {
    private resourceUrl = SERVER_API_URL + 'api/animal-carers';

    constructor(private http: HttpClient) {}

    create(animalCarer: IAnimalCarer): Observable<EntityResponseType> {
        return this.http.post<IAnimalCarer>(this.resourceUrl, animalCarer, { observe: 'response' });
    }

    update(animalCarer: IAnimalCarer): Observable<EntityResponseType> {
        return this.http.put<IAnimalCarer>(this.resourceUrl, animalCarer, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAnimalCarer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAnimalCarer[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
