<template>
<div>
  <div class="absolute top-0 left-0 p-4">
    <img src="@/assets/logo.png" alt="Logo" class="w-12 h-12 mr-2">
  </div>
  <h1 class="text-2xl font-bold text-white mt-8 ml-16">Cash Manager</h1>

  <!-- Section du profil utilisateur -->
  <div class="p-8 rounded-lg shadow bg-slate-700">
    <div class="flex items-center mb-6">
      <img src="https://placekitten.com/100/100" alt="Profile" class="rounded-full w-16 h-16 mr-4">
      <div>
        <h2 class="text-2xl font-semibold text-white">{{ name }}</h2>
        <p class="text-white">{{ email }}</p>
      </div>
    </div>

    <div>
      <h3 v-if="role=='true'" class="text-xl font-semibold mb-4" > You are an admin</h3>
      <div>
        
      </div>
    </div>
  </div>
  <router-link to="/transaction">  
  <button
    class="bg-slate-800 shadow text-white px-4 py-2 rounded-md mt-4 hover:bg-blue-600 focus:outline-none focus:ring focus:border-blue-300"
  >
  View transaction history
  </button>
</router-link>
  <!--Bouton pour ouvrir le modal de carte bancaire -->
  <button
    @click="switchCardModal"
    class="bg-slate-800 shadow text-white px-4 py-2 rounded-md mt-4 hover:bg-blue-600 focus:outline-none focus:ring focus:border-blue-300"
  >
    Add a Credit Card
  </button>

  <!-- Modal pour ajouter la carte bancaire -->
  <daisy-modal v-if="isCardModalOpen" @close="closeCardModal">
    <!-- modal -->
    <div class="p-8 bg-slate-800 shadow rounded-md">
      <h2 class="text-2xl font-bold mb-4">Add a Credit Card</h2>


      <form @submit.prevent="submitCardForm">
        <div class="mb-4">
          <label for="cardNumber" class="block text-gray-700 text-sm font-bold mb-2">Card Number:</label>
          <input
            type="text"
            id="cardNumber"
            v-model="cardNumber"
            class="w-full border rounded-md py-2 px-3 focus:outline-none focus:ring focus:border-blue-300"
            placeholder="1234 5678 9012 3456"
          />
        </div>

        <div class="mb-4">
          <label for="cardHolder" class="block text-gray-700 text-sm font-bold mb-2">Cardholder Name:</label>
          <input
            type="text"
            id="cardHolder"
            v-model="cardHolder"
            class="w-full border rounded-md py-2 px-3 focus:outline-none focus:ring focus:border-blue-300"
            placeholder="John Doe"
          />
        </div>

        <div class="grid grid-cols-2 gap-4 mb-4">
          <div>
            <label for="expiryDate" class="block text-gray-700 text-sm font-bold mb-2">Expiration Date:</label>
            <input
              type="text"
              id="expiryDate"
              v-model="expiryDate"
              class="w-full border rounded-md py-2 px-3 focus:outline-none focus:ring focus:border-blue-300"
              placeholder="MM/YY"
            />
          </div>
          <div>
            <label for="cvv" class="block text-gray-700 text-sm font-bold mb-2">CVV:</label>
            <input
              type="text"
              id="cvv"
              v-model="cvv"
              class="w-full border rounded-md py-2 px-3 focus:outline-none focus:ring focus:border-blue-300"
              placeholder="123"
            />
          </div>
        </div>

        <div class="flex justify-end">
          <button
            type="button"
            @click="closeCardModal"
            class="mr-2 bg-gray-300 text-gray-700 px-4 py-2 rounded-md hover:bg-gray-400 focus:outline-none focus:ring focus:border-blue-300"
          >
            Cancel
          </button>
          <button
            type="submit"
            class="bg-slate-800 text-white px-4 py-2 rounded-md hover:bg-blue-600 focus:outline-none focus:ring focus:border-blue-300"
          >
            Add
          </button>
        </div>
      </form>
    </div>
  </daisy-modal>
</div>
</template>

<script>
import {payment} from "../services/payment"
export default {
  name: 'ProfilePage',
  data() {
    return {
      isCardModalOpen: false,
      cardNumber: '',
      cardHolder: '',
      expiryDate: '',
      cvv: '',
      name: '',
      email: '',
      role: ''
 
    };
  },mounted(){
    this.name = localStorage.getItem('name');
    this.email = localStorage.getItem('email');
    this.role = localStorage.getItem('role')
    
  },
  methods: {
    switchCardModal() {
      this.isCardModalOpen = !this.isCardModalOpen;
    },
    closeCardModal() {
      this.isCardModalOpen = false;
    },
    submitCardForm() {
      let formatDate = this.expiryDate.split("/")

      payment.paymentCreation(this.cardNumber,this.cardHolder,"20"+formatDate[1]+"-"+formatDate[0]+"-01",this.cvv)
      this.closeCardModal();


    },
  },
};
</script>

<style scoped>
</style>
