import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import SharedModule from 'app/shared/shared.module';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { BookCardComponent } from '../book-card/book-card.component';
import { IBook } from '../entities/book/book.model';
import { forkJoin } from 'rxjs'; // Don't forget to import forkJoin
import { MatInputModule } from '@angular/material/input';
import { MatFormField, MatFormFieldModule } from '@angular/material/form-field';

@Component({
  standalone: true,
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  imports: [SharedModule, RouterModule, BookCardComponent, MatFormFieldModule, MatInputModule],
})
export default class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  books: IBook[] = [];
  bookCount: number = 0;

  private readonly destroy$ = new Subject<void>();

  constructor(private accountService: AccountService, private router: Router, private http: HttpClient) {}

  async ngOnInit(): Promise<void> {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));
    this.getBooks();
  }

  getBooks() {
    var url2 = 'http://localhost:9000/api/books/count';
    // Set the headers
    const headers = new HttpHeaders()
      .set('accept', '*/*')
      .set(
        'Authorization',
        'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY5MDIwODA5NiwiYXV0aCI6IlJPTEVfQURNSU4gUk9MRV9VU0VSIiwiaWF0IjoxNjkwMTIxNjk2fQ.dUjOBUnJKeT_KfNhIUW3fhVRQn7saqh9PD9wDfDNifURG6KZoN50y_AN9zMK0xHXkuzLnMBoygmfqqKuAV3VTg'
      );

    this.http.get(url2, { headers }).subscribe(
      response => {
        this.bookCount = response as number;
        var pages = Math.ceil(this.bookCount / 20);
        var Responses: any[] = [];
        let completedRequests = 0;

        for (let index = 0; index < pages; index++) {
          var url = 'http://localhost:9000/api/books?page=' + index + '&size=20';

          this.http.get(url, { headers }).subscribe(
            response => {
              console.log(response);
              Responses.push(response);
              completedRequests++;

              // If all requests have completed, log the responses
              if (completedRequests === pages) {
                this.books = [];
                console.log(Responses);
                var len = Responses.length;
                for (let index = 0; index < len; index++) {
                  var lenSub = Responses[index].length;
                  for (let indexSub = 0; indexSub < lenSub; indexSub++) {
                    this.books.push(Responses[index][indexSub]);
                  }
                }
              }
            },
            error => {
              // Handle any errors here
              console.error(error);
            }
          );
        }
      },
      error => {
        // Handle any errors here
        console.error(error);
      }
    );
  }

  searchName() {
    let value = (<HTMLInputElement>document.getElementById('nameInput')).value;
    console.log(value);

    let searchString = 'name.contains=' + value;
    console.log(searchString);
    if (value == null || value == '') {
      this.getBooks();
      return;
    }

    var url2 = 'http://localhost:9000/api/books/count';
    url2 = url2 + '?' + searchString;
    // Set the headers
    const headers = new HttpHeaders()
      .set('accept', '*/*')
      .set(
        'Authorization',
        'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY5MDIwODA5NiwiYXV0aCI6IlJPTEVfQURNSU4gUk9MRV9VU0VSIiwiaWF0IjoxNjkwMTIxNjk2fQ.dUjOBUnJKeT_KfNhIUW3fhVRQn7saqh9PD9wDfDNifURG6KZoN50y_AN9zMK0xHXkuzLnMBoygmfqqKuAV3VTg'
      );

    this.http.get(url2, { headers }).subscribe(
      response => {
        this.bookCount = response as number;
        var pages = Math.ceil(this.bookCount / 20);
        var Responses: any[] = [];
        let completedRequests = 0;

        for (let index = 0; index < pages; index++) {
          var url = 'http://localhost:9000/api/books?page=' + index + '&size=20';
          url = url + '&' + searchString;
          this.http.get(url, { headers }).subscribe(
            response => {
              console.log(response);
              Responses.push(response);
              completedRequests++;

              // If all requests have completed, log the responses
              if (completedRequests === pages) {
                this.books = [];
                console.log(Responses);
                var len = Responses.length;
                for (let index = 0; index < len; index++) {
                  var lenSub = Responses[index].length;
                  for (let indexSub = 0; indexSub < lenSub; indexSub++) {
                    this.books.push(Responses[index][indexSub]);
                  }
                }
              }
            },
            error => {
              // Handle any errors here
              console.error(error);
            }
          );
        }
      },
      error => {
        // Handle any errors here
        console.error(error);
      }
    );
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
