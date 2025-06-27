import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';

@Component({
  selector: 'app-banking',
  standalone: true,
  imports: [IonicModule, CommonModule],
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
})
export class HomePage {
  user = {
    fullName: 'Anna Nakamura dos Santos Aveiro Cuccittini',
    account: {
      id: 1,
      accountNumber: '123456-7',
      agency: '0001',
      balance: 1000000000.15,
      limit: 1000.00,
      creditCards: [
        {
          id: 1,
          number: '1234123412341234',
          limit: 800.15,
        },
        {
          id: 2,
          number: '1234123412341243',
          limit: 500.15,
        },
        {
          id: 3,
          number: '1234123412341324',
          limit: 800.15,
        },
        {
          id: 4,
          number: '1234123412344231',
          limit: 2300.15,
        },
        {
          id: 5,
          number: '1234123412344131',
          limit: 400.15,
        },
        {
          id: 6,
          number: '1234123412344221',
          limit: 300.75,
        },
        {
          id: 7,
          number: '1234123412354111',
          limit: 400.55,
        },

        {
          id: 8,
          number: '1234123412354131',
          limit: 400.25,
        },
      ],
    },
    features: [
      {
        id: 1,
        icon: "pix",
        description: "PIX",
      },
      {
        id: 2,
        icon: "pay",
        description: "Pagar",
      },
      {
        id: 3,
        icon: "transfer",
        description: "Transferir",
      },
      {
        id: 4,
        icon: "account",
        description: "Conta corrente",
      },
      {
        id: 5,
        icon: "cards",
        description: "Cartões",
      }
    ],
    news: [
      {
        id: 1,
        icon: "insurance",
        description: "Seguro para você",
      },
      {
        id: 2,
        icon: "credit",
        description: "Opções de financiamento",
      }
    ]
  };

  getFormattedBalance(): string {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(this.user.account.balance);
  }

  maskCard(cardNumber: string): string {
    return 'XXXX-XXXX-XXXX-' + cardNumber.slice(-4);
  }
}
