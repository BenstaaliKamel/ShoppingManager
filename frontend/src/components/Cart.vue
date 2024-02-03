<template>
  <div>
    <div class="absolute top-0 left-0 p-4">
      <img src="@/assets/logo.png" alt="Logo" class="w-12 h-12 mr-2">
    </div>
    <h1 class="text-2xl font-bold text-white mt-8 ml-16">Cash Manager</h1>

    <div class="bg-slate-800 p-8 rounded shadow-lg w-full">
      <h2 class="text-2xl font-semibold mb-4">Your Cart</h2>
      <div v-if="cartItems.length === 0" class="text-gray-500">No items in the cart.</div>
      <div v-else>
        <div v-for="item in cartItems" :key="item.products.id" class="mb-4 flex items-center">
          <img :src="item.products.image" alt="Product Image" class="w-12 h-12 object-cover mr-4">
          <div>
            <div style="display:flex;">
              <h3 class="text-lg font-semibold" style="white-space: nowrap;">{{ item.products.name }} * {{ item.quantity }}</h3>
              <button @click="addToCart(item.products.id, quantity)" class="text-white ml-10 mr-2 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">+</button>
              <input id="quantity" v-model="quantity" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Black top" required>
              <button @click="removeFromCart(item.products.id, quantity)" class="text-white ml-2 bg-gray-700 hover:bg-gray-800 focus:ring-4 focus:outline-none focus:ring-gray-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-gray-600 dark:hover:bg-gray-700 dark:focus:ring-gray-800">-</button>
            </div>
            <p class="text-gray-500">{{ item.products.price }} €</p>
          </div>
        </div>
      </div>
      <div v-if="!isPaying && !isPaymentDone" class="mt-8">
        <p class="text-lg font-semibold">Total: {{ totalAmount }} €</p>
        <router-link to="/payment">
          <button class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Pay</button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { panier } from "../services/panier";

export default {
  name: "CartPage",
  data() {
    return {
      cartItems: [],
      quantity: 0,
    };
  },
  async mounted() {
    await this.getPanier();
  },
  computed: {
    imageBasePath() {
      return "../assets/"; // Base path for your images
    },
    totalAmount() {
      return this.cartItems.reduce((acc, item) => acc + item.products.price * item.quantity, 0);
    },
  },
  methods: {
    async addToCart(id, quantity) {
      if (quantity > 0) {
        await panier.addItemToPanier(id, quantity);
        await this.getPanier();
      }
    },
    async removeFromCart(id, quantity) {
      await panier.addItemToPanier(id, -quantity);
      await this.getPanier();
    },
    async getPanier() {
      console.log("2");
      this.cartItems = (await panier.getAllPanier()).data;
    },
  },
};
</script>

<style scoped>
</style>
