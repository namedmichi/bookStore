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

@Component({
  standalone: true,
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  imports: [SharedModule, RouterModule, BookCardComponent],
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
    let newBook: IBook = {
      id: 1,
      title: 'Book 1',
      subTitle: 'Book 1 extra Titel',
      description: 'Book 1 Beschreibung',
      language: 'Deutsch',
      listPrice: '19€',
      author: 'Author 1',
    };
    let newBook2: IBook = {
      id: 2,
      title: 'Book 2',
      subTitle: 'Book 2 extra Titel',
      description: 'Book 2 Beschreibung',
      language: 'Deutsch',
      listPrice: '19€',
      author: 'Author 2',
    };
    let newBook3: IBook = {
      id: 3,
      title: 'Book 3',
      subTitle: 'Book 3 extra Titel',
      description: 'Book 3 Beschreibung',
      language: 'Deutsch',
      listPrice: '19€',
      author: 'Author 3',
    };
    let newBook4: IBook = {
      id: 4,
      title: 'Book 4',
      subTitle: 'Book 4 extra Titel',
      description: 'Book 4 Beschreibung',
      language: 'Deutsch',
      listPrice: '19€',
      author: 'Author 4',
    };

    var url2 = 'http://localhost:9000/api/books/count';
    // Set the headers
    const headers = new HttpHeaders()
      .set('accept', '*/*')
      .set(
        'Authorization',
        'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4OTk3NDA5NywiYXV0aCI6IlJPTEVfQURNSU4gUk9MRV9VU0VSIiwiaWF0IjoxNjg5ODg3Njk3fQ.veaH5nIEKb2GVKad1hg8AsgECwSII23dZ0cxxxr9GKHh2mNJfnDKC9Ckmh0J0wSn42sezzvnXzeAZUKkdSZdHA'
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
