import { Component, OnInit, HostListener } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BookManagementService } from '../../services/book-management.service';
import { PublisherService } from '../../services/publisher.service';
import { AuthorService } from '../../services/author.service';
import { Book, Publisher, Author, BookCreateRequest } from '../../models/book.model';

@Component({
    selector: 'app-book-management',
    templateUrl: './book-management.component.html',
    styleUrls: ['./book-management.component.css']
})
export class BookManagementComponent implements OnInit {
    books: Book[] = [];
    publishers: Publisher[] = [];
    authors: Author[] = [];
    bookForm!: FormGroup;
    loading = false;
    errorMessage = '';
    successMessage = '';
    editingBookId: number | null = null;
    showAuthorDropdown = false;
    showPublisherDropdown = false;

    constructor(
        private fb: FormBuilder,
        private bookService: BookManagementService,
        private publisherService: PublisherService,
        private authorService: AuthorService
    ) {
        this.initForm();
    }

    ngOnInit(): void {
        this.loadData();
    }

    private initForm(): void {
        this.bookForm = this.fb.group({
            title: ['', [Validators.required]],
            isbn: ['', [Validators.required, Validators.pattern(/^[0-9-]+$/)]],
            publishedYear: ['', [Validators.required, Validators.min(1900)]],
            pages: ['', [Validators.required, Validators.min(1)]],
            publisherId: ['', Validators.required],
            authorIds: [[]]
        });
    }

    loadData(): void {
        this.loading = true;
        this.publisherService.getPublishers().subscribe({
            next: (pubs) => {
                this.publishers = pubs;
                this.authorService.getAll().subscribe({
                    next: (authors) => {
                        this.authors = authors;
                        this.bookService.getAll().subscribe({
                            next: (books) => {
                                this.books = books;
                                this.loading = false;
                            },
                            error: (error) => {
                                this.errorMessage = error.message;
                                this.loading = false;
                            }
                        });
                    }
                });
            }
        });
    }

    onSubmit(): void {
        if (this.bookForm.invalid) {
            Object.keys(this.bookForm.controls).forEach(key => {
                this.bookForm.get(key)?.markAsTouched();
            });
            return;
        }

        this.loading = true;
        this.errorMessage = '';
        this.successMessage = '';

        const payload = this.buildRequestPayload();
        const request$ = this.editingBookId
            ? this.bookService.update(this.editingBookId, payload)
            : this.bookService.create(payload);

        request$.subscribe({
            next: (book) => {
                if (this.editingBookId) {
                    const index = this.books.findIndex(b => b.id === book.id);
                    if (index !== -1) {
                        this.books[index] = book;
                    }
                    this.successMessage = `Đã cập nhật sách: ${book.title}`;
                } else {
                    this.books.unshift(book);
                    this.successMessage = `Đã thêm sách: ${book.title}`;
                }
                this.loading = false;
                this.cancelEdit();
                setTimeout(() => this.successMessage = '', 3000);
            },
            error: (error) => {
                this.errorMessage = error.message;
                this.loading = false;
            }
        });
    }

    deleteBook(id: number): void {
        if (!confirm('Xóa sách này?')) return;

        this.loading = true;
        this.bookService.delete(id).subscribe({
            next: () => {
                this.books = this.books.filter(b => b.id !== id);
                this.successMessage = 'Đã xóa!';
                if (this.editingBookId === id) {
                    this.cancelEdit();
                }
                this.loading = false;
                setTimeout(() => this.successMessage = '', 3000);
            },
            error: (error) => {
                this.errorMessage = error.message;
                this.loading = false;
            }
        });
    }

    startEdit(book: Book): void {
        this.editingBookId = book.id ?? null;
        this.bookForm.patchValue({
            title: book.title,
            isbn: book.isbn,
            publishedYear: book.publishedYear,
            pages: book.pages,
            publisherId: book.publisherId,
            authorIds: book.authorIds || []
        });
    }

    cancelEdit(): void {
        this.editingBookId = null;
        this.clearFormInputs();
    }

    resetForm(): void {
        this.clearFormInputs();
        this.errorMessage = '';
        this.successMessage = '';
        this.editingBookId = null;
    }

    private clearFormInputs(): void {
        this.bookForm.reset();
        this.bookForm.get('authorIds')?.setValue([]);
        this.bookForm.get('publisherId')?.setValue('');
    }

    private buildRequestPayload(): BookCreateRequest {
        const raw = this.bookForm.value;
        const authorIds = (raw.authorIds || []).map((value: string | number) => Number(value))
            .filter((value: number) => !Number.isNaN(value));
        return {
            title: raw.title?.trim() ?? '',
            isbn: raw.isbn?.trim() ?? '',
            publishedYear: Number(raw.publishedYear),
            pages: Number(raw.pages),
            publisherId: Number(raw.publisherId),
            authorIds
        };
    }

    toggleAuthorDropdown(): void {
        this.showAuthorDropdown = !this.showAuthorDropdown;
    }

    isAuthorSelected(authorId: number): boolean {
        const selectedIds = this.bookForm.get('authorIds')?.value || [];
        return selectedIds.includes(authorId);
    }

    toggleAuthor(authorId: number): void {
        const currentIds = this.bookForm.get('authorIds')?.value || [];
        const index = currentIds.indexOf(authorId);

        if (index > -1) {
            currentIds.splice(index, 1);
        } else {
            currentIds.push(authorId);
        }

        this.bookForm.get('authorIds')?.setValue([...currentIds]);
    }

    getSelectedAuthors(): string[] {
        const selectedIds = this.bookForm.get('authorIds')?.value || [];
        return this.authors
            .filter(author => selectedIds.includes(author.id))
            .map(author => author.fullName);
    }

    togglePublisherDropdown(): void {
        this.showPublisherDropdown = !this.showPublisherDropdown;
    }

    isPublisherSelected(publisherId: number): boolean {
        return this.bookForm.get('publisherId')?.value == publisherId;
    }

    selectPublisher(publisherId: number): void {
        this.bookForm.get('publisherId')?.setValue(publisherId);
        this.showPublisherDropdown = false;
    }

    getSelectedPublisher(): string {
        const selectedId = this.bookForm.get('publisherId')?.value;
        const publisher = this.publishers.find(p => p.id == selectedId);
        return publisher ? publisher.name : '';
    }

    @HostListener('document:click', ['$event'])
    closeDropdownOnClickOutside(event: MouseEvent): void {
        const target = event.target as HTMLElement;
        if (!target.closest('.author-dropdown')) {
            this.showAuthorDropdown = false;
            this.showPublisherDropdown = false;
        }
    }
}
