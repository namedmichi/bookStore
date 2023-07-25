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
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { trigger, state, style, transition, animate } from '@angular/animations';

@Component({
  standalone: true,
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  imports: [SharedModule, RouterModule, BookCardComponent, FormsModule, ReactiveFormsModule],
  animations: [
    trigger('slideInOut', [
      state(
        'in',
        style({
          transform: 'translate3d(0, 0, 0)',
        })
      ),
      state(
        'out',
        style({
          transform: 'translate3d(100%, 0, 0)',
        })
      ),
      transition('in => out', animate('400ms ease-in-out')),
      transition('out => in', animate('400ms ease-in-out')),
    ]),
  ],
})
export default class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  books: IBook[] = [];
  bookCount: number = 0;
  toppings = new FormControl('');
  toppingList: string[] = ['Name', 'Preis', 'Seitenanzahl', 'ISBN', 'Sprache'];
  sortString: string = '&sort=';
  sortCount: number = 0;
  sortBool: boolean = false;
  private readonly destroy$ = new Subject<void>();

  constructor(private accountService: AccountService, private router: Router, private http: HttpClient) {}

  async ngOnInit(): Promise<void> {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));
    this.getBooks();
  }
  addSort(item) {
    console.log(item);
    if (this.sortString.includes(item + ',')) {
      this.sortString = this.sortString.replace(item + ',', '');
      this.sortCount--;
      if (this.sortCount == 0) {
        this.sortBool = false;
        console.log(this.sortString);
        console.log(this.sortBool);
      }
      return;
    }
    this.sortString = this.sortString + item + ',';
    this.sortCount++;
    this.sortBool = true;
    console.log(this.sortString);
    console.log(this.sortBool);
  }

  getBooks() {
    var url2 = 'http://localhost:9000/api/books/count';
    // Set the headers
    const headers = new HttpHeaders()
      .set('accept', '*/*')
      .set(
        'Authorization',
        'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY5MDM1MjE4OCwiYXV0aCI6IlJPTEVfQURNSU4gUk9MRV9VU0VSIiwiaWF0IjoxNjkwMjY1Nzg4fQ.IRTTud1czNHLQXmNfX8Zh14M4vS_iI92UYL4gJfDkVXyMHjtRnpYkYZvUW3VR7x3sXNulwnzozasX4vrgkthlA'
      );

    this.http.get(url2, { headers }).subscribe(
      response => {
        this.bookCount = response as number;
        var pages = Math.ceil(this.bookCount / 20);
        var Responses: any[] = [];
        let completedRequests = 0;

        for (let index = 0; index < pages; index++) {
          var url = 'http://localhost:9000/api/books?page=' + index + '&size=20';
          if (this.sortBool) {
            url = url + this.sortString;
          }
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
        'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY5MDM1MjE4OCwiYXV0aCI6IlJPTEVfQURNSU4gUk9MRV9VU0VSIiwiaWF0IjoxNjkwMjY1Nzg4fQ.IRTTud1czNHLQXmNfX8Zh14M4vS_iI92UYL4gJfDkVXyMHjtRnpYkYZvUW3VR7x3sXNulwnzozasX4vrgkthlA'
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

          if (this.sortBool) {
            url = url + this.sortString;
          }
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
