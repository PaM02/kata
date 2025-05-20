import { Injectable, inject, signal } from "@angular/core";
import { Cart } from "./cart.model";
import { HttpClient } from "@angular/common/http";
import { catchError, Observable, of, tap } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private readonly http = inject(HttpClient);
  private readonly path = "/api/carts";

  private readonly _carts = signal<Cart[]>([]);

  public readonly carts = this._carts.asReadonly();

  public get(): Observable<Cart[]> {
    return this.http.get<Cart[]>(this.path).pipe(
      catchError((error) => {
        return this.http.get<Cart[]>("assets/carts.json");
      }),
      tap((carts) => this._carts.set(carts)),
    );
  }

  public delete(cartId: number): Observable<boolean> {
    return this.http.delete<boolean>(`${this.path}/${cartId}`).pipe(
      catchError(() => {
        return of(true);
      }),
      tap(() => this._carts.update(carts => carts.filter(cart => cart.id !== cartId))),
    );
  }
}
