<template>
  <div class="bg-gray-900 text-white min-h-screen">
    <div class="absolute top-4 left-4">
      <img src="@/assets/logo.png" alt="Logo" class="w-12 h-12">
    </div>
    <h1 class="text-4xl font-bold text-white mt-8 ml-4">Cash Manager</h1>

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 mt-8 mx-4">
      <router-link to="/QrCodeReader" class="p-4 rounded-md shadow bg-blue-500 flex items-center justify-center">
        <img src="@/assets/qrcode.png" alt="Scan QR Code" class="w-12 h-12 md:w-16 md:h-16 lg:w-20 lg:h-20 mr-2" />
        <span class="text-xl font-semibold">SCAN HERE</span>
      </router-link>

      <router-link to="/shopping" class="p-4 rounded-md shadow bg-purple-500 flex items-center justify-center">
        <img src="@/assets/buy.png" alt="Buy Now" class="w-20 h-20 md:w-24 md:h-24 lg:w-32 lg:h-32 mr-2" />
        <span class="text-xl font-semibold">BUY NOW</span>
      </router-link>
      <router-link to="/profile" class="p-4 rounded-md shadow bg-yellow-500 flex items-center justify-center">
        <img src="@/assets/carte_bancaire.png" alt="Case 4" class="w-12 h-12 md:w-16 md:h-16 lg:w-20 lg:h-20 mr-2" />
        <span class="text-xl font-semibold">ADD CARD</span>
      </router-link>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 mt-8 mx-4">
      <!-- Iterate over your products and display them -->
      <div v-for="product in products" :key="product.id" class="p-4 rounded-md shadow bg-gray-800 flex flex-col items-center justify-center">
        <img :src="product.image" alt="Product Image" class="w-24 h-24 md:w-32 md:h-32 lg:w-40 lg:h-40 mb-2 rounded-full border border-gray-600">
        <h2 class="text-lg font-semibold">{{ product.name }}</h2>
        <p class="text-md">{{ product.price }} €</p>
        <p class="text-md">Quantity: {{ product.quantity }}</p>
        <button class="mt-2 bg-blue-900 text-white p-2 rounded-md flex items-center" @click="addToCart(product.id,1)">
          <img src="@/assets/panier.png" alt="Add to Cart" class="w-4 h-4 md:w-6 md:h-6 lg:w-8 lg:h-8 mr-2">
          Add to Cart
        </button>
      </div>
    </div>
  </div>
</template>



<script>
import {authentication} from "../services/authentication"
import {items} from "../services/products"
import {panier} from "../services/panier"
export default {
  name: 'HomePage',
  data() {
    return {
      products: [

      ],};
  },
  methods: {
    addToCart(id,quantity){
        panier.addItemToPanier(id,quantity)
      },
    },
  async mounted(){
    this.products= (await items.getItems()).data
    console.log(this.products)
    authentication.testConnectivity(this.$router)
  }
};
</script>

<style scoped>

</style>
