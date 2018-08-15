import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBird } from 'app/shared/model/bird.model';

type EntityResponseType = HttpResponse<IBird>;
type EntityArrayResponseType = HttpResponse<IBird[]>;

@Injectable({ providedIn: 'root' })
export class BirdService {
    private resourceUrl = SERVER_API_URL + 'api/birds';

    constructor(private http: HttpClient) {}

    create(bird: IBird): Observable<EntityResponseType> {
        return this.http.post<IBird>(this.resourceUrl, bird, { observe: 'response' });
    }

    update(bird: IBird): Observable<EntityResponseType> {
        return this.http.put<IBird>(this.resourceUrl, bird, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBird>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBird[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
