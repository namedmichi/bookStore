import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
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
import { CdkVirtualScrollViewport, ScrollingModule } from '@angular/cdk/scrolling';

@Component({
  standalone: true,
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  imports: [SharedModule, RouterModule, BookCardComponent, FormsModule, ReactiveFormsModule, ScrollingModule],
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
  sortStringTemplate: string = '&sort=';
  sortString: string = '';
  sortCount: number = 0;
  sortBool: boolean = false;
  order = 'asc';
  firstStart: boolean = true;
  private readonly destroy$ = new Subject<void>();

  constructor(private accountService: AccountService, private router: Router, private http: HttpClient) {}

  async ngOnInit(): Promise<void> {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));
    this.getBooks();
  }

  sortOrder(order) {
    if (order == 'asc' && this.order != 'asc') {
      console.log(order);
      this.sortString = this.sortString.replace('desc', 'asc');
      this.order = order;
      this.getBooks();
      return;
    } else if (order == 'desc' && this.order != 'desc') {
      console.log(order);
      this.sortString = this.sortString.replace('asc', 'desc');
      this.order = order;
      this.getBooks();
      return;
    }
  }

  addSort(item) {
    console.log(item);
    if (item == 'Name') {
      item = 'name';
    }
    if (item == 'Preis') {
      item = 'listPrice';
    }
    if (item == 'Seitenanzahl') {
      item = 'pageCount';
    }
    if (item == 'ISBN') {
      item = 'isbn';
    }
    if (item == 'Sprache') {
      item = 'language';
    }

    if (this.sortString.includes(item + ',')) {
      this.sortString = this.sortString.replace(item + ',' + this.order, '');
      this.sortCount--;
      if (this.sortCount == 0) {
        this.sortBool = false;
        console.log(this.sortString);
        console.log(this.sortBool);
      }
      this.getBooks();
      return;
    }
    this.sortString += this.sortStringTemplate + item + ',' + this.order;
    this.sortCount++;
    this.sortBool = true;
    console.log(this.sortString);
    console.log(this.sortBool);
    this.getBooks();
  }

  @ViewChild(CdkVirtualScrollViewport) viewPort: CdkVirtualScrollViewport;
  scrollToTop() {
    console.log(this.viewPort);
    this.viewPort.scrollToIndex(0, 'smooth');
  }
  getBooks() {
    if (!this.firstStart) {
      this.scrollToTop();
    }
    this.firstStart = false;
    var url2 = 'http://localhost:9000/api/books/count';
    // Set the headers
    const headers = new HttpHeaders().set('accept', '*/*');

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
            console.log(url);
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
    const headers = new HttpHeaders().set('accept', '*/*');

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
