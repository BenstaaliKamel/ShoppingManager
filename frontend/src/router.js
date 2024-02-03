import { createRouter, createWebHistory } from 'vue-router';
import Profile from './components/Profile.vue';
import Cart from './components/Cart.vue';
import Home from "./components/Home.vue";
import Authentification from './components/Authentification.vue';
import Transaction from "./components/Transaction.vue";
import QrCodeReader from "./components/QrCodeReader.vue";
import Shopping from "./components/Shopping.vue";
import Payment from "./components/Payment.vue"

const routes= [
  { path: "/", redirect: "/authentification" },
{ path: "/authentification", name: "Authentification", component: Authentification},
{ path: "/home", name: "Home", component: Home },
{ path: "/profile", name:"Profile", component: Profile },
{ path: "/cart", name:"Cart", component: Cart },
{ path: "/transaction", name:"Transaction", component: Transaction },
{ path: "/qrcodereader", name:"QrCodeReader", component: QrCodeReader },
{ path: "/shopping", name:"Shopping", component: Shopping },
{ path: "/payment", name:"Payment", component: Payment },

];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;