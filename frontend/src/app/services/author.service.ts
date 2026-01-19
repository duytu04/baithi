import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Author } from '../models/book.model';

@Injectable({
    providedIn: 'root'
})
export class AuthorService {
    private apiUrl = '/api/authors';

    constructor(private http: HttpClient) { }

    /**
     * Get all authors with profiles
     */
    getAll(): Observable<Author[]> {
        return this.http.get<Author[]>(this.apiUrl).pipe(
            catchError(this.handleError)
        );
    }

    /**
     * Get author by ID with profile
     */
    getById(id: number): Observable<Author> {
        return this.http.get<Author>(`${this.apiUrl}/${id}`).pipe(
            catchError(this.handleError)
        );
    }

    /**
     * Create new author with profile
     */
    create(author: Author): Observable<Author> {
        return this.http.post<Author>(this.apiUrl, author).pipe(
            catchError(this.handleError)
        );
    }

    /**
     * Update author and profile
     */
    update(id: number, author: Author): Observable<Author> {
        return this.http.put<Author>(`${this.apiUrl}/${id}`, author).pipe(
            catchError(this.handleError)
        );
    }

    /**
     * Delete author
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
