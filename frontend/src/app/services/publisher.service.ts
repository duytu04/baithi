import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Publisher } from '../models/book.model';

@Injectable({
    providedIn: 'root'
})
export class PublisherService {
    private apiUrl = '/api/publishers';

    constructor(private http: HttpClient) { }

    /**
     * Get all publishers
     */
    getPublishers(): Observable<Publisher[]> {
        return this.http.get<Publisher[]>(this.apiUrl).pipe(
            catchError(this.handleError)
        );
    }

    /**
     * Get publisher by ID
     */
    getById(id: number): Observable<Publisher> {
        return this.http.get<Publisher>(`${this.apiUrl}/${id}`).pipe(
            catchError(this.handleError)
        );
    }

    /**
     * Create new publisher
     */
    create(publisher: Publisher): Observable<Publisher> {
        return this.http.post<Publisher>(this.apiUrl, publisher).pipe(
            catchError(this.handleError)
        );
    }

    /**
     * Update publisher
     */
    update(id: number, publisher: Publisher): Observable<Publisher> {
        return this.http.put<Publisher>(`${this.apiUrl}/${id}`, publisher).pipe(
            catchError(this.handleError)
        );
    }

    /**
     * Delete publisher
     */
    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
            catchError(this.handleError)
        );
    }

    /**
     * Handle HTTP errors
     */
    private handleError(error: HttpErrorResponse) {
        let errorMessage = 'Đã xảy ra lỗi';

        if (error.error instanceof ErrorEvent) {
            errorMessage = `Lỗi: ${error.error.message}`;
        } else {
            if (typeof error.error === 'string') {
                errorMessage = error.error;
            } else if (error.error?.message) {
                errorMessage = error.error.message;
            } else {
                errorMessage = `Lỗi ${error.status}: ${error.message}`;
            }
        }

        console.error(errorMessage);
        return throwError(() => new Error(errorMessage));
    }
}

