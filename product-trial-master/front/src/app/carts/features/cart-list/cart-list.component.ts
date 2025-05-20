import { CommonModule } from "@angular/common";
import { Component, OnInit, inject, signal } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Cart } from "app/carts/data-access/cart.model";
import { CartService } from "app/carts/data-access/carts.service";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { RatingModule } from 'primeng/rating';
import { BadgeModule } from 'primeng/badge';
import { InputTextModule } from "primeng/inputtext";
import { InputNumberModule } from "primeng/inputnumber";
const emptyCart: Cart = {
  id: 0,
  code: "",
  name: "",
  description: "",
  image: "",
  category: "",
  price: 0,
  quantity: 0,
  internalReference: "",
  shellId: 0,
  inventoryStatus: "INSTOCK",
  rating: 0,
  createdAt: 0,
  updatedAt: 0,
};

@Component({
  selector: 'app-cart-list',
  standalone: true,
  imports: [DataViewModule, CardModule, InputNumberModule, InputTextModule, RatingModule, FormsModule, ButtonModule, DialogModule, CommonModule, BadgeModule],
  templateUrl: './cart-list.component.html',
  styleUrl: './cart-list.component.css'
})
export class CartListComponent implements OnInit {

  public quantities = 0;
  public isDialogVisible = false;
  public isCreation = false;
  searchText!: string;
  public readonly editedCart = signal<Cart>(emptyCart);
  public _cart: Cart[] = [];
  ngOnInit() {
    const saved = localStorage.getItem('cart');
    if (saved) this._cart = JSON.parse(saved);
    this.quantities = this.quantitiesOfProducts(this._cart);
  }


  public onUpdate(product: Cart) {
    this.isCreation = false;
    this.isDialogVisible = true;
    this.editedCart.set(product);
  }

  public onDelete(cart: Cart) {
    this._cart = this._cart.filter(p => p.id !== cart.id);
    this.updateLocalStorage();
  }

  public quantitiesOfProducts(carts: Cart[]) {
    return carts.reduce((total: number, item: any) => total + item.quantity, 0)
  }

  updateQuantity(cart: Cart) {
    const existingItem = this._cart.find(item => item.id === cart.id);
    if (existingItem !== undefined && cart.quantity > 0) {
      existingItem.quantity = cart.quantity;
    } else {
      this._cart = this._cart.filter(p => p.id !== cart.id);
    }
    this.updateLocalStorage();
  }

  updateLocalStorage() {

    this.quantities = this.quantitiesOfProducts(this._cart);
    localStorage.removeItem("cart");
    localStorage.setItem('cart', JSON.stringify(this._cart));
  }

  public onCancel() {
    this.closeDialog();
  }

  private closeDialog() {
    this.isDialogVisible = false;
  }

  get filteredCarts() {
    const all = this._cart;
    if (!this.searchText?.trim()) return all;
    const term = this.searchText.toLowerCase();
    return all.filter(p =>
      p.name.toLowerCase().includes(term) ||
      p.category.toLowerCase().includes(term)
    );
  }

}

