import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Book, BookCreateRequest } from '../models/book.model';

@Injectable({
    providedIn: 'root'
})
export class BookManagementService {
    private apiUrl = '/api/books';

    constructor(private http: HttpClient) { }

    /**
     * Get all books with authors and publisher
     */
    getAll(): Observable<Book[]> {
        return this.http.get<Book[]>(this.apiUrl).pipe(
            catchError(this.handleError)
        );
    }

    /**
     * Get book by ID
     */
    getById(id: number): Observable<Book> {
        return this.http.get<Book>(`${this.apiUrl}/${id}`).pipe(
            catchError(this.handleError)
        );
    }

    /**
     * Create new book
     */
    create(book: BookCreateRequest): Observable<Book> {
        return this.http.post<Book>(this.apiUrl, book).pipe(
            catchError(this.handleError)
        );
    }

    /**
     * Update book
     */
    update(id: number, book: BookCreateRequest): Observable<Book> {
        return this.http.put<Book>(`${this.apiUrl}/${id}`, book).pipe(
            catchError(this.handleError)
        );
    }

    /**
     * Delete book
     */
    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
            catchError(this.handleError)
        );
    }

    /**
     * Add author to book
     */
    addAuthor(bookId: number, authorId: number): Observable<void> {
        return this.http.post<void>(`${this.apiUrl}/${bookId}/authors/${authorId}`, {}).pipe(
            catchError(this.handleError)
        );
    }

    /**
     * Remove author from book
     */
    removeAuthor(bookId: number, authorId: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${bookId}/authors/${authorId}`).pipe(
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
